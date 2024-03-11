package cibertec.com.pe.controller;

import cibertec.com.pe.model.Boleto;
import cibertec.com.pe.model.Ciudad;
import cibertec.com.pe.model.Venta;
import cibertec.com.pe.model.Venta_Detalle;
import cibertec.com.pe.repository.Ciudad_Repository;
import cibertec.com.pe.repository.VentaDetalle_Repository;
import cibertec.com.pe.repository.Venta_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"boletosAgregados"})
public class Controller_Boletos {

    @Autowired
    private Ciudad_Repository ciudadRepository;

    @Autowired
    private Venta_Repository ventaRepository;

    @Autowired
    private VentaDetalle_Repository ventaDetalleRepository;

    @GetMapping("/index")
    public String mostrarPaginaIndex(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<Boleto> boletosAgregados = (List<Boleto>) model.getAttribute("boletosAgregados");

        Boleto ultimoBoleto = boletosAgregados.isEmpty() ? new Boleto() : boletosAgregados.get(boletosAgregados.size() - 1);

        model.addAttribute("boleto", ultimoBoleto);
        model.addAttribute("ciudades", ciudades);

        return "index";
    }

    @GetMapping("/volver-compra")
    public String volverCompra(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        Boleto nuevoBoleto = new Boleto();
        model.addAttribute("boleto", nuevoBoleto);
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("boletosAgregados", new ArrayList<>());

        return "index";
    }

    @GetMapping("/inicio")
    public String mostrarInicio(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<Boleto> boletosAgregados = (List<Boleto>) model.getAttribute("boletosAgregados");

        if (!boletosAgregados.isEmpty()) {
            Boleto ultimoBoleto = boletosAgregados.get(boletosAgregados.size() - 1);
            model.addAttribute("boleto", ultimoBoleto);
        } else {
            model.addAttribute("boleto", new Boleto());
        }

        model.addAttribute("ciudades", ciudades);

        return "index";
    }

    @PostMapping("/agregar-boleto")
    public String procesarBoleto(Model model, @ModelAttribute Boleto boleto) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        List<Boleto> boletosAgregados = (List<Boleto>) model.getAttribute("boletosAgregados");
        Double precioBoleto = 50.00;
        boleto.setSubTotal(boleto.getCantidad() * precioBoleto);
        boletosAgregados.add(boleto);
        model.addAttribute("boletosAgregados", boletosAgregados);
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("boleto", new Boleto());

        return "redirect:/inicio";
    }

    @PostMapping("/comprar")
    public String procesarCompra(Model model) {
        try {
            List<Boleto> boletos = (List<Boleto>) model.getAttribute("boletosAgregados");
            Double montoTotal = boletos.stream().mapToDouble(Boleto::getSubTotal).sum();

            Venta nuevaVenta = new Venta();
            nuevaVenta.setFechaVenta(new Date());
            nuevaVenta.setMontoTotal(montoTotal);
            nuevaVenta.setNombreComprador(boletos.get(0).getNombreComprador());

            // Guardar la venta en la base de datos
            Venta ventaGuardada = ventaRepository.saveAndFlush(nuevaVenta);

            // Procesar cada boleto y guardar los detalles de venta
            for (Boleto boleto : boletos) {
                Venta_Detalle ventaDetalle = new Venta_Detalle();

                Ciudad ciudadDestino = ciudadRepository.findById(boleto.getCiudadDestino()).orElse(null);
                Ciudad ciudadOrigen = ciudadRepository.findById(boleto.getCiudadOrigen()).orElse(null);

                if (ciudadDestino != null && ciudadOrigen != null) {
                    ventaDetalle.setCiudadDestino(ciudadDestino);
                    ventaDetalle.setCiudadOrigen(ciudadOrigen);
                    ventaDetalle.setCantidad(boleto.getCantidad());
                    ventaDetalle.setSubTotal(boleto.getSubTotal());
                    ventaDetalle.setFechaRetorno(new SimpleDateFormat("yyyy-MM-dd").parse(boleto.getFechaRetorno()));
                    ventaDetalle.setFechaViaje(new SimpleDateFormat("yyyy-MM-dd").parse(boleto.getFechaSalida()));
                    ventaDetalle.setVenta(ventaGuardada);

                    ventaDetalleRepository.save(ventaDetalle);
                }
            }

            // Limpiar la lista de boletos agregados
            boletos.clear();
            return "redirect:/inicio";
        } catch (Exception e) {
            // Manejar la excepción (puedes imprimir un mensaje de log o redirigir a una página de error)
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/limpiar")
    public String limpiar(Model model) {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        model.addAttribute("boletosAgregados", new ArrayList<>());
        model.addAttribute("boleto", new Boleto());
        model.addAttribute("ciudades", ciudades);

        return "redirect:/inicio";
    }

    @ModelAttribute("boletosAgregados")
    public List<Boleto> boletosComprados() {
        return new ArrayList<>();
    }
}
