
package entidades;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author cinca
 */
public class UsuarioEntidad {
    private ObjectId id;
    private String usuario;
    private String correo;
    private String contrasena;
    private String imagen;
    private List<FavoritoEntidad> favoritos;
    // Genero no deseados

    public UsuarioEntidad() {
    }

    public UsuarioEntidad(ObjectId id, String usuario, String correo, String contrasena, String imagen, List<FavoritoEntidad> favoritos) {
        this.id = id;
        this.usuario = usuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.imagen = imagen;
        this.favoritos = favoritos;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<FavoritoEntidad> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritoEntidad> favoritos) {
        this.favoritos = favoritos;
    }

    
}
