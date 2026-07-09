/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author cinca
 */
public class UsuarioDTO {
    private String id;
    private String usuario;
    private String correo;
    private String imagen;
    private List<GeneroDTO> generosNoDeseados;
    private List<FavoritoDTO> favoritos;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String usuario, String correo, String imagen, List<GeneroDTO> generosNoDeseados, List<FavoritoDTO> favoritos) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.imagen = imagen;
        this.generosNoDeseados = generosNoDeseados;
        this.favoritos = favoritos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<GeneroDTO> getGenerosNoDeseados() {
        return generosNoDeseados;
    }

    public void setGenerosNoDeseados(List<GeneroDTO> generosNoDeseados) {
        this.generosNoDeseados = generosNoDeseados;
    }

    public List<FavoritoDTO> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritoDTO> favoritos) {
        this.favoritos = favoritos;
    }
    
    
}
