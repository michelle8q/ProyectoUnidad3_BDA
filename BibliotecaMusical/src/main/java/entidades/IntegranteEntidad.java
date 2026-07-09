/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.RolIntegrante;
import java.util.Date;

/**
 *
 * @author cinca luisf
 */
public class IntegranteEntidad {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private RolIntegrante rol;
    private Date fechaIngreso;
    private Date fechaSalida;

    public IntegranteEntidad() {
    }

    public IntegranteEntidad(String nombres, String apellidoPaterno, String apellidoMaterno, RolIntegrante rol, Date fechaIngreso, Date fechaSalida) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public RolIntegrante getRol() {
        return rol;
    }

    public void setRol(RolIntegrante rol) {
        this.rol = rol;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    @Override
    public String toString() {
        return "IntegranteEntidad{" + "nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", rol=" + rol + ", fechaIngreso=" + fechaIngreso + ", fechaSalida=" + fechaSalida + '}';
    }
    
    
}
