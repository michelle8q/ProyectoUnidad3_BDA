package persistencia;

import dtos.FavoritoDTO;
import entidades.GeneroEntidad;
import entidades.UsuarioEntidad;
import org.bson.types.ObjectId;

/**
 *
 * @author piña
 */
public interface IUsuarioDAO {
<<<<<<< Updated upstream
    /**
     * metodo para guardar un usuario nuevo
     * @param usuario a guardar
     * @return UsuarioEntidad del usuario guardado
     * @throws PersistenciaException en caso de algun error
     */
=======

>>>>>>> Stashed changes
    UsuarioEntidad registar(UsuarioEntidad usuario) throws PersistenciaException;

    UsuarioEntidad buscar(String correo, String contra) throws PersistenciaException;

    UsuarioEntidad buscarPorId(ObjectId idUsuario) throws PersistenciaException;

    UsuarioEntidad actualizarPerfil(ObjectId idUsuario, String usuario, String imagen) throws PersistenciaException;

    UsuarioEntidad agregarGeneroNoDeseado(ObjectId idUsuario, GeneroEntidad genero) throws PersistenciaException;

    UsuarioEntidad eliminarGeneroNoDeseado(ObjectId idUsuario, String nombreGenero) throws PersistenciaException;
<<<<<<< Updated upstream
    
    /**
     * metodo para buscar un usuario por su nombre
     * @param correo el nombre a buscar
     * @return UsuarioEntidad encontrada
     * @throws PersistenciaException en caso de un error
     */
    UsuarioEntidad buscarPorUsuario(String correo) throws PersistenciaException;
    
    
}
=======

    public void agregarFavorito(String idUsuario, FavoritoDTO favorito) throws PersistenciaException;

    public void eliminarFavorito(String idUsuario, String idElemento) throws PersistenciaException;

    }
>>>>>>> Stashed changes
