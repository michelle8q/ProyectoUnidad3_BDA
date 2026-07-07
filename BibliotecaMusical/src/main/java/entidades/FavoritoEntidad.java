/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 *
 * @author cinca
 */
public class FavoritoEntidad {
    private String nombre;
    //genero
    private String tipo;
    private Date fechaAgregacion;

    public FavoritoEntidad() {
    }

    public FavoritoEntidad(String nombre, String tipo, Date fechaAgregacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaAgregacion() {
        return fechaAgregacion;
    }

    public void setFechaAgregacion(Date fechaAgregacion) {
        this.fechaAgregacion = fechaAgregacion;
    }
    
    
}
