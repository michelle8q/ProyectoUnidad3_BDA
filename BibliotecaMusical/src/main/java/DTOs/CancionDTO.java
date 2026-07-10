/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 * DTO que muestra datos especificos en las pantallas respectivas de canciones o
 * las que lo necesiten.
 * 
 * @author luisf
 */
public class CancionDTO {

    private String nombre;
    private String duracion;

    public CancionDTO() {
    }

    public CancionDTO(String nombre, String duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
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
    
    
}
