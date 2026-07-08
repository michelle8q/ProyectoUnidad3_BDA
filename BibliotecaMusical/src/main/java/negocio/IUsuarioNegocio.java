
package negocio;

import entidades.UsuarioEntidad;

/**
 *
 * @author piña
 */
public interface IUsuarioNegocio {
    
    public UsuarioEntidad reguistrar(UsuarioEntidad usuario) throws NegocioException;
    
    public UsuarioEntidad buscar(String correo, String contra) throws NegocioException;
    
}
