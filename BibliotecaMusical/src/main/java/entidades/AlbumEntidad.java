/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Entidad de dominio que representa un album de un artista. 
 * El album contiene una lista
 * de canciones.
 * 
 * @author cinca
 */
public class AlbumEntidad {

    private ObjectId id;
    private String nombre;
    private GeneroEntidad genero;
    private String imagen;
    private Date fechaLanzamiento;
    private List<CancionEntidad> canciones;
    private String nombreArtista;

    public AlbumEntidad() {
    }

    public AlbumEntidad(ObjectId id, String nombre, GeneroEntidad genero, String imagen, Date fechaLanzamiento, List<CancionEntidad> canciones) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.imagen = imagen;
        this.fechaLanzamiento = fechaLanzamiento;
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

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public List<CancionEntidad> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionEntidad> canciones) {
        this.canciones = canciones;
    }

    public GeneroEntidad getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntidad genero) {
        this.genero = genero;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    @Override
    public String toString() {
        return "AlbumEntidad{" + "id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", imagen=" + imagen + ", canciones=" + canciones + '}';
    }

}
