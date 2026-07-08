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
    private GeneroEntidad genero;
    private String tipo;
    private Date fechaAgregacion;

    public FavoritoEntidad() {
    }

    public FavoritoEntidad(String nombre, GeneroEntidad genero, String tipo, Date fechaAgregacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaAgregacion = fechaAgregacion;
        this.genero = genero;
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

    public GeneroEntidad getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntidad genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "FavoritoEntidad{" + "nombre=" + nombre + ", genero=" + genero + ", tipo=" + tipo + ", fechaAgregacion=" + fechaAgregacion + '}';
    }
    
    
}
