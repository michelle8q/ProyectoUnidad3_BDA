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
public class ArtistaEntidad {
     private ObjectId id;
    private String nombre;
    private String imagen;
    private GeneroEntidad genero;
    private String tipo;
    private List<IntegranteEntidad> integrantes;

    public ArtistaEntidad() {
    }

    public ArtistaEntidad(ObjectId id, String nombre, String imagen, GeneroEntidad genero, String tipo, List<IntegranteEntidad> integrantes) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
        this.tipo = tipo;
        this.integrantes = integrantes;
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

    public GeneroEntidad getGenero() {
        return genero;
    }

    public void setGenero(GeneroEntidad genero) {
        this.genero = genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<IntegranteEntidad> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<IntegranteEntidad> integrantes) {
        this.integrantes = integrantes;
    }

    @Override
    public String toString() {
        return "ArtistaEntidad{" + "id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", genero=" + genero + ", tipo=" + tipo + ", integrantes=" + integrantes + '}';
    }
    
    
}
