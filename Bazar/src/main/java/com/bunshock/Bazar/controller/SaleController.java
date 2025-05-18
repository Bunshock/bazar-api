package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.sale.HighestSaleDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.sale.DateSalesSummaryDTO;
import com.bunshock.Bazar.dto.sale.InputSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.Sale;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bunshock.Bazar.service.interfaces.ISaleService;
import com.bunshock.Bazar.exception.ValidationHandler;
import com.bunshock.Bazar.utils.mapper.SaleMapper;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/ventas")
@PreAuthorize("hasRole('ADMIN')")
public class SaleController {
    
    private final ISaleService saleService;
    
    @Autowired
    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<String> createSale(@Validated(OnCreate.class) @RequestBody InputSaleDTO inputSale,
            BindingResult bindingResult, @RequestParam Long id_client) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        saleService.saveSale(inputSale, id_client);
        return new ResponseEntity<>("Venta creada satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<ShowSaleDTO>> getAllSales() {
        List<Sale> saleList = saleService.getSales();
        return new ResponseEntity<>(saleList.stream()
                .map(sale -> SaleMapper.SaleToShowSaleDTO(sale))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
    
    @GetMapping("/{sale_code}")
    public ResponseEntity<ShowSaleDTO> getOneSale(@PathVariable Long sale_code) {
        Sale sale = saleService.getSaleByCode(sale_code);
        return new ResponseEntity<>(SaleMapper.SaleToShowSaleDTO(sale), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{sale_code}")
    public ResponseEntity<String> deleteSale(@PathVariable Long sale_code) {
        saleService.deleteSale(sale_code);
        return new ResponseEntity<>("Venta borrada exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{sale_code}")
    public ResponseEntity<?> editSale(@PathVariable Long sale_code,
            @Validated(OnUpdate.class) @RequestBody InputSaleDTO editedSale,
            BindingResult bindingResult, @RequestParam Long id_client) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        Sale updatedSale = saleService.editSale(sale_code, editedSale, id_client);
        return new ResponseEntity<>(SaleMapper.SaleToShowSaleDTO(updatedSale), HttpStatus.OK);
    }
    
    @GetMapping("/productos/{sale_code}")
    public ResponseEntity<List<Product>> getSaleProductList(@PathVariable Long sale_code) {
        return new ResponseEntity<>(saleService.getSaleProducts(sale_code), HttpStatus.OK);
    }
    
    // Decisión: Para no tener definiciones ambiguas con el metodo getOneSale,
    // agregamos a la ruta el prefijo /resumen
    @GetMapping("/resumen/{sale_date}")
    public ResponseEntity<DateSalesSummaryDTO> getSalesSummaryByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sale_date) {
        return new ResponseEntity<>(saleService.getSaleSummaryByDate(sale_date), HttpStatus.OK);
    }
    
    @GetMapping("/mayor_venta")
    public ResponseEntity<HighestSaleDTO> getHighestSale() {
        Sale highestSale = saleService.getHighestTotalSale();
        return new ResponseEntity<>(SaleMapper.SaleToHighestSaleDTO(highestSale), HttpStatus.OK);
    }
    
    @PutMapping("/concretar/{sale_code}")
    public ResponseEntity<String> finalizePendingSale(@PathVariable Long sale_code) {
        saleService.finalizeSale(sale_code);
        return new ResponseEntity<>("Venta (" + sale_code + ") concretada", HttpStatus.OK);
    }
    
    // Operaciones de usuarios con rol : USER
    
    @PostMapping("/mis-ventas/crear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createMySale(@Validated(OnCreate.class) @RequestBody InputSaleDTO inputSale,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        saleService.saveMySale(inputSale);
        return new ResponseEntity<>("Venta creada satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("/mis-ventas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ShowSaleDTO>> getMySales() {
        List<Sale> mySales = saleService.getMySales();
        return new ResponseEntity<>(mySales.stream()
                .map(sale -> SaleMapper.SaleToShowSaleDTO(sale))
                .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
    
    @GetMapping("/mis-ventas/{sale_code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMySaleByCode(@PathVariable Long sale_code) {
        return new ResponseEntity<>(saleService.getMySaleByCode(sale_code), HttpStatus.OK);
    }
    
    @DeleteMapping("/mis-ventas/eliminar/{sale_code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteMySale(@PathVariable Long sale_code) {
        saleService.deleteMySale(sale_code);
        return new ResponseEntity<>("Venta borrada exitosamente", HttpStatus.OK);
    }
    
}
