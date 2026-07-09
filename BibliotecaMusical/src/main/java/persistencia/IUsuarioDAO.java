
package persistencia;

import entidades.GeneroEntidad;
import entidades.UsuarioEntidad;
import org.bson.types.ObjectId;

/**
 *
 * @author piña
 */
public interface IUsuarioDAO {
    
    UsuarioEntidad registar(UsuarioEntidad usuario) throws PersistenciaException;
    
    UsuarioEntidad buscar(String correo, String contra ) throws PersistenciaException;
    
    UsuarioEntidad buscarPorId(ObjectId idUsuario) throws PersistenciaException;
    
    UsuarioEntidad actualizarPerfil(ObjectId idUsuario, String usuario, String imagen) throws PersistenciaException;
    
    UsuarioEntidad agregarGeneroNoDeseado(ObjectId idUsuario, GeneroEntidad genero) throws PersistenciaException;
    
    UsuarioEntidad eliminarGeneroNoDeseado(ObjectId idUsuario, String nombreGenero) throws PersistenciaException;
    
}
