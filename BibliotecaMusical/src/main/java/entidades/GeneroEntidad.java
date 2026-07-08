/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import org.bson.types.ObjectId;

/**
 *
 * @author cinca
 */
public class GeneroEntidad {
    private ObjectId id;
    private String nombre;

    public GeneroEntidad() {
    }

    public GeneroEntidad(ObjectId id, String nombre) {
        this.id = id;
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
        return "GeneroEntidad{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
    
}
