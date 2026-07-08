
package persistencia;

import entidades.UsuarioEntidad;

/**
 *
 * @author piña
 */
public interface IUsuarioDAO {
    
    UsuarioEntidad reguistar(UsuarioEntidad usuario) throws PersistenciaException;
    
    UsuarioEntidad verificar(UsuarioEntidad usuario) throws PersistenciaException;
    
}
