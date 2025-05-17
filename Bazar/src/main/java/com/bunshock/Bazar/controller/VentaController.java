package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.MayorVentaDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.ResumenVentasDTO;
import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.dto.VentaMostrarDTO;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.Venta;
import com.bunshock.Bazar.service.IVentaService;
import com.bunshock.Bazar.utils.IControllerUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


@RestController
@RequestMapping("/ventas")
@PreAuthorize("hasRole('ADMIN')")
public class VentaController {
    
    private final IVentaService ventaService;
    private final IControllerUtils controllerUtils;
    
    @Autowired
    public VentaController(IVentaService ventaService, IControllerUtils controllerUtils) {
        this.ventaService = ventaService;
        this.controllerUtils = controllerUtils;
    }
    
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> crearVenta(@Validated(OnCreate.class) @RequestBody VentaDTO datosVenta,
            BindingResult bindingResult, @RequestParam Long id_cliente) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        try {
            ventaService.saveVenta(datosVenta, id_cliente);
        }
        catch (EntityNotFoundException | IllegalArgumentException e) {
            return new ResponseEntity<>("Error al crear venta: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Venta creada satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<VentaMostrarDTO>> traerVentas() {
        return new ResponseEntity<>(ventaService.getVentas(), HttpStatus.OK);
    }
    
    @GetMapping("/{codigo_venta}")
    public ResponseEntity<VentaMostrarDTO> traerVentaPorId(@PathVariable Long codigo_venta) {
        return new ResponseEntity<>(ventaService.getVentaById(codigo_venta),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{codigo_venta}")
    public ResponseEntity<String> borrarVenta(@PathVariable Long codigo_venta) {
        return new ResponseEntity<>("Producto borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{codigo_venta}")
    public ResponseEntity<?> editarVenta(@PathVariable Long codigo_venta,
            @Validated(OnUpdate.class) @RequestBody VentaDTO productoEditado,
            BindingResult bindingResult, @RequestParam Long id_cliente) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        VentaMostrarDTO ventaEditada;
        
        try {
            ventaEditada = ventaService.editVenta(codigo_venta, productoEditado, id_cliente);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Error al editar venta: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(ventaEditada, HttpStatus.OK);
    }
    
    @GetMapping("/productos/{codigo_venta}")
    public ResponseEntity<List<Producto>> traerProductosDeVenta(@PathVariable Long codigo_venta) {
        return new ResponseEntity<>(ventaService.getVentaProductos(codigo_venta),
                HttpStatus.OK);
    }
    
    // Decisión: Para no tener definiciones ambiguas con el metodo traerVentaPorId,
    // agregamos a la ruta el prefijo /resumen
    @GetMapping("/resumen/{fecha_venta}")
    public ResponseEntity<ResumenVentasDTO> traerResumenVentasPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_venta) {
        return new ResponseEntity<>(ventaService.getVentaResumeByDate(fecha_venta),
                HttpStatus.OK);
    }
    
    @GetMapping("/mayor_venta")
    public ResponseEntity<MayorVentaDTO> traerMayorVenta() {
        Venta mayorVenta = ventaService.getHighestTotalVenta();
        
        return new ResponseEntity<>(new MayorVentaDTO(
                mayorVenta.getCodigo_venta(),
                mayorVenta.getTotal(),
                mayorVenta.getListaProductos().size(),
                mayorVenta.getUnCliente().getNombre(),
                mayorVenta.getUnCliente().getApellido()
        ), HttpStatus.OK);
    }
    
    @PutMapping("/concretar/{codigo_venta}")
    public ResponseEntity<String> concretarVentaPendiente(@PathVariable Long codigo_venta) {
        ventaService.concretarVenta(codigo_venta);
        return new ResponseEntity<>("Venta (" + codigo_venta + ") concretada", HttpStatus.OK);
    }
    
    @PostMapping("/mis-ventas/crear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> crearMiVenta(@Validated(OnCreate.class) @RequestBody VentaDTO datosVenta,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        try {
            ventaService.saveMiVenta(datosVenta);
        }
        catch (EntityNotFoundException | UsernameNotFoundException e) {
            return new ResponseEntity<>("Error al crear venta: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Venta creada satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("/mis-ventas")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<VentaMostrarDTO>> traerMisVentas() {
        return new ResponseEntity<>(ventaService.getMisVentas(), HttpStatus.OK);
    }
    
}
