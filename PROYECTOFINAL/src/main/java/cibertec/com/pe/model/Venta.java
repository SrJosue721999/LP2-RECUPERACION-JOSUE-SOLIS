package cibertec.com.pe.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public int id;

    @Column(name = "nombre_comprador", nullable = false, length = 100)
    public String nombreComprador;

    @Column(name = "fecha_venta", nullable = false)
    public Date fechaVenta;

    @Column(name = "monto_total", nullable = false, scale = 2)
    public Double montoTotal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    public List<Venta_Detalle> detalle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<Venta_Detalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Venta_Detalle> detalle) {
        this.detalle = detalle;
    }
}
