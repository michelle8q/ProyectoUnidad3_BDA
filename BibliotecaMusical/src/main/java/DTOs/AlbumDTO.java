/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luisf
 */
public class AlbumDTO {

    private String id;
    private String nombre;
    private String nombreGenero;
    private String nombreArtista;
    private Date fechaLanzamiento;
    private List<CancionDTO> canciones;
    private String imagen;

    public AlbumDTO() {
    }

    public AlbumDTO(String id, String nombre, String nombreGenero, String nombreArtista, Date fechaLanzamiento) {
        this.id = id;
        this.nombre = nombre;
        this.nombreGenero = nombreGenero;
        this.nombreArtista = nombreArtista;
        this.fechaLanzamiento = fechaLanzamiento;
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

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public List<CancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<CancionDTO> canciones) {
        this.canciones = canciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
