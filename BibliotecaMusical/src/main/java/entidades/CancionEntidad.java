/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.types.ObjectId;

/**
 *
 * @author cinca
 * 
 * Entidad de dominio que representa a una cancion dentro del sistema. 
 * Almacena sus datos como su nombre, genero al que pertenece, artista,
 * alnum y muestra la duracion.
 * 
 */
public class CancionEntidad {
    
    private ObjectId id;
    private String nombre;
    private String genero;

    public CancionEntidad() {
    }

    public CancionEntidad(ObjectId id, String nombre, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    
    
}