/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.types.ObjectId;

/**
 *
 * @author cinca luisf
 */
public class GeneroEntidad {
    private ObjectId idGenero;
    private String nombre;

    public GeneroEntidad() {
    }

    public GeneroEntidad(ObjectId idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    public ObjectId getId() {
        return idGenero;
    }

    public void setId(ObjectId id) {
        this.idGenero = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "GeneroEntidad{" + "idGenero=" + idGenero + ", nombre=" + nombre + '}';
    }
    
    
}
