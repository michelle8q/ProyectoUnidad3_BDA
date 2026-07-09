/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;

/**
 *
 * @author cinca
 */
public class FavoritoDTO {
    private String id;
    private String nombre;
    private String genero;
    private String tipo;
    private Date fechaAgregacion;

    public FavoritoDTO() {
    }

    public FavoritoDTO(String id, String nombre, String genero, String tipo, Date fechaAgregacion) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.tipo = tipo;
        this.fechaAgregacion = fechaAgregacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
