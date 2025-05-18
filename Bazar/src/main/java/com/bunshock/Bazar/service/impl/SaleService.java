package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.service.interfaces.ISaleService;
import com.bunshock.Bazar.dto.sale.DateSalesSummaryDTO;
import com.bunshock.Bazar.dto.sale.InputSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.exception.app.ClientNotFoundException;
import com.bunshock.Bazar.exception.app.FinalizeSaleException;
import com.bunshock.Bazar.exception.app.InsufficientStockException;
import com.bunshock.Bazar.exception.app.ProductNotFoundException;
import com.bunshock.Bazar.exception.app.SaleNotFoundException;
import com.bunshock.Bazar.exception.app.UserWithoutClientException;
import com.bunshock.Bazar.exception.security.UserNotFoundException;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.model.Sale;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bunshock.Bazar.repository.IClientRepository;
import com.bunshock.Bazar.repository.IProductRepository;
import com.bunshock.Bazar.repository.ISaleRepository;
import com.bunshock.Bazar.utils.SecurityUtils;


@Service
public class SaleService implements ISaleService {
    
    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;
    private final SecurityUtils securityUtils;
    
    @Autowired
    public SaleService(ISaleRepository saleRepository,
            IProductRepository productRepository, IClientRepository clientRepository,
            SecurityUtils securityUtils) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.securityUtils = securityUtils;
    }
    
    // Infiero el precio total de venta basado en la lista de productos
    private Double calculateSalePrice(List<Product> saleProducts) {
        return saleProducts.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
    }
    
    private void saveSaleWithClient(InputSaleDTO inputSale, Client client) {
        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, devuelvo una excepción.
        List<Product> productList = inputSale.getProductCodeList().stream()
                .map(productCode -> productRepository.findById(productCode)
                        .orElseThrow(() -> new ProductNotFoundException("crear venta", productCode))
                )
                .collect(Collectors.toList());
        
        Sale sale = new Sale();
        
        sale.setSaleDate(inputSale.getSaleDate());
        sale.setTotalPrice(calculateSalePrice(productList));
        sale.setProductList(productList);
        sale.setClient(client);
        
        saleRepository.save(sale);
    }

    @Override
    @Transactional
    public void saveSale(InputSaleDTO inputSale, Long id_client) {
        Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new ClientNotFoundException("crear venta", id_client));
        this.saveSaleWithClient(inputSale, client);
    }

    @Override
    public List<Sale> getSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleByCode(Long sale_code) {
        return saleRepository.findById(sale_code)
                .orElseThrow(() -> new SaleNotFoundException("obtener venta", sale_code));
    }

    @Override
    public void deleteSale(Long sale_code) {
        this.getSaleByCode(sale_code);  // Verifico que la venta exista
        saleRepository.deleteById(sale_code);
    }

    @Override
    @Transactional
    public Sale editSale(Long sale_code, InputSaleDTO editedSale, Long id_client) {
        Sale sale = this.getSaleByCode(sale_code);
        
        if (editedSale.getSaleDate() != null)
            sale.setSaleDate(editedSale.getSaleDate());
        if (editedSale.getProductCodeList() != null) {
            List<Product> productList = productRepository
                .findAllById(editedSale.getProductCodeList());
            sale.setProductList(productList);
        }
        if (id_client != null) {
            Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new ClientNotFoundException("editar venta", id_client));
            sale.setClient(client);
        }
        
        sale.setTotalPrice(calculateSalePrice(sale.getProductList()));
        
        return saleRepository.save(sale);
    }

    @Override
    public List<Product> getSaleProducts(Long sale_code) {
        Sale sale = this.getSaleByCode(sale_code);
        return sale.getProductList();
    }

    @Override
    public DateSalesSummaryDTO getSaleSummaryByDate(LocalDate date) { 
        Object[] summary = saleRepository.getSummaryByDate(date).get(0);
        
        Double totalDouble = (Double) summary[0];
        Long countLong = (Long) summary[1];
        
        // SUM puede devolver null si no hay coincidencias.
        Double total = totalDouble != null ? totalDouble : 0.0;
        // COUNT devuelve 0 si no hay coincidencias, no chequeamos por null.
        int count = countLong.intValue();
        
        return new DateSalesSummaryDTO(total, count);
    }

    @Override
    public Sale getHighestTotalSale() {
        return saleRepository.findTopByOrderByTotalPriceDesc();
    }

    @Override
    @Transactional
    public void finalizeSale(Long sale_code) {
        Sale sale = this.getSaleByCode(sale_code);
        
        if (sale.isFinalized()) throw new FinalizeSaleException("concretar venta", sale_code);

        // Para descontar los productos vendidos de cantidad_disponible en Producto
        // (siempre y cuando la cantidad vendida no supere la cantidad disponible)
        // junto los ids de los productos, con las veces que aparecen en la lista
        // de ids de productos recibida.
        Map<Long, Long> productCountByCode = sale.getProductList().stream()
                .map(producto -> producto.getProductCode())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        // Verifico que la cantidad de dicho producto que se quiere vender no supera
        // la cantidad disponible. Dado el caso que se supere la cantidad disponible,
        // tiro una excepcion.
        productCountByCode.entrySet().forEach(entry -> {
            Long productCode = entry.getKey();
            Long productCount = entry.getValue();

            Product product = productRepository.findById(productCode)
                    .orElseThrow(() -> new ProductNotFoundException("concretar venta", productCode));

            if (product.getAmountAvailable() < productCount)
                throw new InsufficientStockException("concretar venta", productCode, product.getName());

            product.setAmountAvailable(product.getAmountAvailable() - productCount);
            productRepository.save(product);
        });
        
        sale.setFinalized(true);
        
        saleRepository.save(sale);
    }
    
    @Override
    @Transactional
    public void saveMySale(InputSaleDTO inputSale) {
        UserEntity user = securityUtils.getUserFromContext();
        this.saveSaleWithClient(inputSale, user.getClient());
    }
    
    @Override
    public List<Sale> getMySales() {
        UserEntity user = securityUtils.getUserFromContext();
        return saleRepository.findByClient_IdClient(user.getClient().getIdClient());
    }

    @Override
    public Sale getMySaleByCode(Long sale_code) {
        UserEntity user = securityUtils.getUserFromContext();
        return saleRepository.findByClient_IdClientAndSaleCode(user.getClient().getIdClient(), sale_code)
                .orElseThrow(() -> new SaleNotFoundException("obtener mi venta", sale_code));
    }

    @Override
    public void deleteMySale(Long sale_code) {
        UserEntity user = securityUtils.getUserFromContext();
        
        // Verificamos que la venta indicada exista y corresponda al cliente del usuario logueado
        // (por eso no usamos simplemente this.getSaleById(sale_code) )
        Sale sale = saleRepository
                .findByClient_IdClientAndSaleCode(user.getClient().getIdClient(), sale_code)
                .orElseThrow(() -> new SaleNotFoundException("borrar mi venta", sale_code));
        
        saleRepository.delete(sale);
    }
    
}
