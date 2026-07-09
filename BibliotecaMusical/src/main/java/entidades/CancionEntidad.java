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
    private String duracion;

    public CancionEntidad() {
    }

    public CancionEntidad(ObjectId id, String nombre, String duracion) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    @Override
    public String toString() {
        return "CancionEntidad{" + "id=" + id + ", nombre=" + nombre + ", duracion= " + duracion +'}';
    }
    
}