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
    private ObjectId id;
    private String nombre;

    public GeneroEntidad() {
    }

    public GeneroEntidad(ObjectId idGenero, String nombre) {
        this.id = idGenero;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "GeneroEntidad{" + "idGenero=" + id + ", nombre=" + nombre + '}';
    }
    
    
}
