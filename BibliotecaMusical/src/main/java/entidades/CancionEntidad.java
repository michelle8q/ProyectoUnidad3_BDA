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
    private String nombreAlbum;
    private String imagenAlbum;
    private String nombreArtista;
    private GeneroEntidad genero;
    
    public CancionEntidad() {
    }

    public CancionEntidad(ObjectId id, String nombre, String duracion, String nombreAlbum, String imagenAlbum, String nombreArtista, GeneroEntidad genero) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.nombreAlbum = nombreAlbum;
        this.imagenAlbum = imagenAlbum;
        this.nombreArtista = nombreArtista;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getImagenAlbum() {
        return imagenAlbum;
    }

    public void setImagenAlbum(String imagenAlbum) {
        this.imagenAlbum = imagenAlbum;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public GeneroEntidad getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntidad genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "CancionEntidad{" + "id=" + id + ", nombre=" + nombre + ", duracion=" + duracion + ", nombreAlbum=" + nombreAlbum + ", imagenAlbum=" + imagenAlbum + ", nombreArtista=" + nombreArtista + ", genero=" + genero + '}';
    }
    
    
    
}