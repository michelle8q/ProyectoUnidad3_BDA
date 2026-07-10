
package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entidades.GeneroEntidad;
import entidades.UsuarioEntidad;
import org.bson.types.ObjectId;

/**
 *
 * @author piña
 */
public class UsuarioDAO implements IUsuarioDAO{
    
    private IConexionDAO conexion;
    
    public UsuarioDAO(IConexionDAO conexion){
        this.conexion = conexion;
    }

    @Override
    public UsuarioEntidad registar(UsuarioEntidad usuario) throws PersistenciaException {
        try {
          MongoDatabase bd = conexion.conexion();
          MongoCollection<UsuarioEntidad> collection = bd.getCollection("usuarios", UsuarioEntidad.class);

          collection.insertOne(usuario);
          return usuario;

        } catch (Exception ex) {
            throw new PersistenciaException("Error al registrar el usuario: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioEntidad buscar(String correo, String contra ) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> usuarios = bd.getCollection("usuarios", UsuarioEntidad.class);

            return usuarios.find(
                    Filters.and(Filters.eq("correo", correo),
                                Filters.eq("contrasena", contra))).first();
            
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar usuario: " + ex.getMessage());
        }
    }
    
    @Override
    public UsuarioEntidad buscarPorId(ObjectId idUsuario) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> usuarios = bd.getCollection("usuarios", UsuarioEntidad.class);

            return usuarios.find(Filters.eq("_id", idUsuario)).first();

        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar usuario: " + ex.getMessage());
        }
    }
    
    @Override
    public UsuarioEntidad actualizarPerfil(ObjectId idUsuario, String usuario, String imagen) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> usuarios = bd.getCollection("usuarios", UsuarioEntidad.class);

            usuarios.updateOne(
                    Filters.eq("_id", idUsuario),
                    Updates.combine(
                            Updates.set("usuario", usuario), Updates.set("imagen", imagen))); 
            
            return buscarPorId(idUsuario);

        } catch (Exception ex) {
            throw new PersistenciaException("Error al actualizar perfil: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioEntidad agregarGeneroNoDeseado(ObjectId idUsuario, GeneroEntidad genero) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> usuarios = bd.getCollection("usuarios", UsuarioEntidad.class);

            usuarios.updateOne(
                    Filters.eq("_id", idUsuario),
                    Updates.push("generosNoDeseados", genero));

            return buscarPorId(idUsuario);

        } catch (Exception ex) {
            throw new PersistenciaException("Error al agregar genero: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioEntidad eliminarGeneroNoDeseado(ObjectId idUsuario, String nombreGenero) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> usuarios = bd.getCollection("usuarios", UsuarioEntidad.class);

            usuarios.updateOne(
                    Filters.eq("_id", idUsuario),
                    Updates.pull("generosNoDeseados", Filters.eq("nombre", nombreGenero)));

            return buscarPorId(idUsuario);

        } catch (Exception ex) {
            throw new PersistenciaException("Error al eliminar genero: " + ex.getMessage());
        }
    }
    
    
}
