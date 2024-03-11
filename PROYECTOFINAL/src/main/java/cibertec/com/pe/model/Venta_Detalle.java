package cibertec.com.pe.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_detalle_venta")
public class Venta_Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_postal_destino", referencedColumnName = "codigo_postal")
    public Ciudad ciudadDestino;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "codigo_postal_origen", referencedColumnName = "codigo_postal")
    public Ciudad ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    public Venta venta;

    @Column(nullable = false)
    public int cantidad;

    @Column(name = "fecha_viaje", nullable = false)
    public Date fechaViaje;

    @Column(name = "fecha_retorno", nullable = false)
    public Date fechaRetorno;

    @Column(name = "sub_total", nullable = false, scale = 2)
    public Double subTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
