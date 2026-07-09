
package negocio;

import dtos.GeneroDTO;
import dtos.UsuarioDTO;
import entidades.UsuarioEntidad;

/**
 *
 * @author piña
 */
public interface IUsuarioNegocio {
    
    public UsuarioEntidad reguistrar(UsuarioEntidad usuario) throws NegocioException;
    
    public UsuarioDTO buscar(String correo, String contra) throws NegocioException;
    
    UsuarioDTO buscarPorId(String idUsuario) throws NegocioException;

    UsuarioDTO actualizarPerfil(UsuarioDTO usuario) throws NegocioException;

    UsuarioDTO agregarGeneroNoDeseado(String idUsuario, GeneroDTO genero) throws NegocioException;

    UsuarioDTO eliminarGeneroNoDeseado(String idUsuario, String nombreGenero) throws NegocioException;
  
}
