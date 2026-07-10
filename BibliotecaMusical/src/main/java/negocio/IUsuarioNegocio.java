package negocio;

import dtos.FavoritoDTO;
import dtos.GeneroDTO;
import dtos.UsuarioDTO;
import entidades.UsuarioEntidad;
import java.util.List;

/**
 *
 * @author piña
 */
public interface IUsuarioNegocio {
    
    /**
     * metodo para reguistrar un usuario nuevo
     * @param usuario a reguistrar
     * @return UsuarioEntidad del usuarios reguistrado
     * @throws NegocioException en caso de algun error 
     */

    public UsuarioEntidad reguistrar(UsuarioEntidad usuario) throws NegocioException;

    public UsuarioDTO buscar(String correo, String contra) throws NegocioException;

    UsuarioDTO buscarPorId(String idUsuario) throws NegocioException;

    UsuarioDTO actualizarPerfil(UsuarioDTO usuario) throws NegocioException;

    UsuarioDTO agregarGeneroNoDeseado(String idUsuario, GeneroDTO genero) throws NegocioException;

    UsuarioDTO eliminarGeneroNoDeseado(String idUsuario, String nombreGenero) throws NegocioException;
    
    /**
     * metodo para iniciar sesion
     * @param nombreUsuario usuario a validar
     * @param contrasenaPlana contraseña a validar
     * @return UsuarioEntidad encontrado
     * @throws NegocioException en caso de algun error
     */
  

    public void agregarFavorito(String idUsuario, FavoritoDTO favorito) throws NegocioException;

    public void eliminarFavorito(String idUsuario, String idElemento) throws NegocioException;
    UsuarioDTO login(String correo, String contra) throws NegocioException;
    

}
