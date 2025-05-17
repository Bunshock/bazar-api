package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.service.interfaces.ISaleService;
import com.bunshock.Bazar.dto.sale.DateSalesSummaryDTO;
import com.bunshock.Bazar.dto.sale.InputSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.model.Sale;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bunshock.Bazar.repository.IClientRepository;
import com.bunshock.Bazar.repository.IProductRepository;
import com.bunshock.Bazar.repository.ISaleRepository;
import com.bunshock.Bazar.utils.mapper.SaleMapper;


@Service
public class SaleService implements ISaleService {
    
    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;
    private final IUserRepository userRepository;
    private final SaleMapper saleMapper;
    
    @Autowired
    public SaleService(ISaleRepository saleRepository,
            IProductRepository productRepository, IClientRepository clientRepository,
            IUserRepository userRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    @Transactional
    public void saveSale(InputSaleDTO inputSale, Long id_client) {
        Sale sale = new Sale();
        
        sale.setSaleDate(inputSale.getSaleDate());
        sale.setTotalPrice(inputSale.getTotalPrice());

        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, tiramos excepción.
        List<Product> productList = inputSale.getProductCodeList().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con id: " + id))
                )
                .collect(Collectors.toList());
        sale.setProductList(productList);
        
        // Obtengo el Cliente relacionado al id ingresado
        Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + id_client));
        sale.setClient(client);
        
        saleRepository.save(sale);
    }

    @Override
    public List<ShowSaleDTO> getSales() {
        List<Sale> saleList = saleRepository.findAll();
        
        return saleList.stream()
                .map(venta -> saleMapper.SaleToShowSaleDTO(venta))
                .collect(Collectors.toList());
    }

    @Override
    public ShowSaleDTO getSaleById(Long sale_code) {
        Sale sale = saleRepository.findById(sale_code)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + sale_code + ") no fue encontrada"));
        
        return saleMapper.SaleToShowSaleDTO(sale);
    }

    @Override
    public void deleteSale(Long sale_code) {
        saleRepository.deleteById(sale_code);
    }

    @Override
    @Transactional
    public ShowSaleDTO editSale(Long sale_code, InputSaleDTO editedSale, Long id_client) {
        Sale sale = saleRepository.findById(sale_code)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + sale_code + ") no fue encontrada"));
        
        if (editedSale.getSaleDate() != null)
            sale.setSaleDate(editedSale.getSaleDate());
        if (editedSale.getTotalPrice() != null)
            sale.setTotalPrice(editedSale.getTotalPrice());
        if (editedSale.getProductCodeList() != null) {
            List<Product> productList = productRepository
                .findAllById(editedSale.getProductCodeList());
            sale.setProductList(productList);
        }
        if (id_client != null) {
            Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + id_client));
            sale.setClient(client);
        }
        
        saleRepository.save(sale);
        
        return saleMapper.SaleToShowSaleDTO(sale);
    }

    @Override
    public List<Product> getSaleProducts(Long sale_code) {
        Sale sale = saleRepository.findById(sale_code)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + sale_code + ") no fue encontrada"));
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
        Sale sale = saleRepository.findById(sale_code)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + sale_code + ") no fue encontrada"));

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
        productCountByCode.entrySet().stream()
                .map(entry -> {
                    Long productCode = entry.getKey();
                    Long productCount = entry.getValue();
                    
                    Product product = productRepository.findById(productCode)
                            .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con código: " + productCode));
                    
                    if (product.getAmountAvailable() < productCount)
                        throw new IllegalArgumentException("Stock insuficiente"
                                + " para el producto (" + product.getName()
                                + ") con código: " + productCode);
                    
                    product.setAmountAvailable(product.getAmountAvailable() - productCount);
                    productRepository.save(product);
                    return product;
                });
        
        sale.setFinalized(true);
        
        saleRepository.save(sale);
    }
    
    @Override
    @Transactional
    public void saveMySale(InputSaleDTO inputSale) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Sale mySale = new Sale();
        
        mySale.setClient(user.getCliente());
        
        mySale.setSaleDate(inputSale.getSaleDate());
        mySale.setTotalPrice(inputSale.getTotalPrice());

        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, tiramos excepción.
        List<Product> productList = inputSale.getProductCodeList().stream()
                .map(productCode -> productRepository.findById(productCode)
                        .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con código: " + productCode))
                )
                .collect(Collectors.toList());
        mySale.setProductList(productList);
        
        saleRepository.save(mySale);
    }
    
    @Override
    public List<ShowSaleDTO> getMySales() {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        List<Sale> mySaleList = saleRepository.findByClient_IdClient(
                user.getCliente().getIdClient());
        
        return mySaleList.stream()
                .map(sale -> saleMapper.SaleToShowSaleDTO(sale))
                .collect(Collectors.toList());
    }

    @Override
    public ShowSaleDTO getMySaleById(Long sale_code) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Sale sale = saleRepository
                .findByClient_IdClientAndSaleCode(user.getCliente().getIdClient(), sale_code)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con id ("
                        + user.getCliente().getIdClient() + ") no tiene asociado "
                                + "una venta con código: " + sale_code));
        
        return saleMapper.SaleToShowSaleDTO(sale);
    }

    @Override
    public void deleteMySale(Long sale_code) {
        // Obtenemos el usuario logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        // Verificamos que la venta indicada corresponda al cliente del usuario logueado
        Sale sale = saleRepository
                .findByClient_IdClientAndSaleCode(user.getCliente().getIdClient(), sale_code)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con id (" 
                        + user.getCliente().getIdClient() + ") no tiene asociado "
                                + "una venta con código: " + sale_code));
        
        saleRepository.delete(sale);
    }
    
}
