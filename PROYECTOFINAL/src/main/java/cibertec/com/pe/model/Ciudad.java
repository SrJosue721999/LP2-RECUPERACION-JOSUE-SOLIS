package cibertec.com.pe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ciudad")
public class Ciudad {

    @Id
    @Column(name = "codigo_postal", nullable = false, length = 50)
    public String codigoPostal;

    @Column(nullable = false, length = 100)
    public String nombre;

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
