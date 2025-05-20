package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.ApiResponseDTO;
import com.bunshock.Bazar.dto.ApiSuccessResponseDTO;
import com.bunshock.Bazar.dto.sale.HighestSaleDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.sale.DateSalesSummaryDTO;
import com.bunshock.Bazar.dto.sale.InputSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Product;
import java.time.LocalDateTime;
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
import java.time.LocalDate;
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
    public ResponseEntity<ApiResponseDTO> createSale(
            @Validated(OnCreate.class) @RequestBody InputSaleDTO inputSale,
            BindingResult bindingResult, @RequestParam Long id_client) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        saleService.saveSale(inputSale, id_client);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Venta creada correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getAllSales() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<ShowSaleDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de ventas obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(saleService.getSales().stream()
                        .map(sale -> SaleMapper.SaleToShowSaleDTO(sale))
                        .collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/{sale_code}")
    public ResponseEntity<ApiResponseDTO> getOneSale(
            @PathVariable Long sale_code) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowSaleDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Venta obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(SaleMapper.SaleToShowSaleDTO(
                        saleService.getSaleByCode(sale_code)))
                .build(), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{sale_code}")
    public ResponseEntity<ApiResponseDTO> deleteSale(
            @PathVariable Long sale_code) {
        saleService.deleteSale(sale_code);
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Venta borrada exitosamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/{sale_code}")
    public ResponseEntity<ApiResponseDTO> editSale(
            @PathVariable Long sale_code,
            @Validated(OnUpdate.class) @RequestBody InputSaleDTO editedSale,
            BindingResult bindingResult, @RequestParam Long id_client) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowSaleDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Venta editada correctamente")
                .timestamp(LocalDateTime.now())
                .data(SaleMapper.SaleToShowSaleDTO(
                        saleService.editSale(sale_code, editedSale, id_client)))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/productos/{sale_code}")
    public ResponseEntity<ApiResponseDTO> getSaleProductList(
            @PathVariable Long sale_code) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<Product>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de productos de venta obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(saleService.getSaleProducts(sale_code))
                .build(), HttpStatus.OK);
    }
    
    // Decisión: Para no tener definiciones ambiguas con el metodo getOneSale,
    // agregamos a la ruta el prefijo /resumen
    @GetMapping("/resumen/{sale_date}")
    public ResponseEntity<ApiResponseDTO> getSalesSummaryByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sale_date) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<DateSalesSummaryDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Resumen de ventas de la fecha obtenido correctamente")
                .timestamp(LocalDateTime.now())
                .data(saleService.getSaleSummaryByDate(sale_date))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/mayor_venta")
    public ResponseEntity<ApiResponseDTO> getHighestSale() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<HighestSaleDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Mayor venta obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(SaleMapper.SaleToHighestSaleDTO(
                        saleService.getHighestTotalSale()))
                .build(), HttpStatus.OK);
    }
    
    @PutMapping("/concretar/{sale_code}")
    public ResponseEntity<ApiResponseDTO> finalizePendingSale(
            @PathVariable Long sale_code) {
        saleService.finalizeSale(sale_code);
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Venta concretada correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.OK);
    }
    
    // Operaciones de usuarios con rol : USER
    
    @PostMapping("/mis-ventas/crear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> createMySale(
            @Validated(OnCreate.class) @RequestBody InputSaleDTO inputSale,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        saleService.saveMySale(inputSale);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Venta de usuario creada satisfactoriamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.CREATED);
    }
    
    @GetMapping("/mis-ventas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> getMySales() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<ShowSaleDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de ventas de usuario obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(saleService.getMySales().stream()
                        .map(sale -> SaleMapper.SaleToShowSaleDTO(sale))
                        .collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/mis-ventas/{sale_code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> getMySaleByCode(
            @PathVariable Long sale_code) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowSaleDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Venta de usuario obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(SaleMapper.SaleToShowSaleDTO(
                        saleService.getMySaleByCode(sale_code)))
                .build(), HttpStatus.OK);
    }
    
    @DeleteMapping("/mis-ventas/eliminar/{sale_code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> deleteMySale(
            @PathVariable Long sale_code) {
        saleService.deleteMySale(sale_code);
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Venta borrada correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.OK);
    }
    
}
