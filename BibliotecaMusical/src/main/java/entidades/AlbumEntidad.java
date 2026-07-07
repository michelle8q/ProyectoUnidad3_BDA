/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author cinca
 */
public class AlbumEntidad {
    private ObjectId id;
    private String nombre;
    //Genero no se si enum o entidad
    private String imagen;
    private List<CancionEntidad> canciones;

    public AlbumEntidad() {
    }
    
    // Falta definir el genero
    public AlbumEntidad(ObjectId id, String nombre, String imagen, List<CancionEntidad> canciones) {
        this.id = id;
        this.nombre = nombre;

        this.imagen = imagen;
        this.canciones = canciones;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<CancionEntidad> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionEntidad> canciones) {
        this.canciones = canciones;
    }
    
    
}
