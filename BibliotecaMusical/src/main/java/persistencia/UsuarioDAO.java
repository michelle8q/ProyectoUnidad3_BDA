package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dtos.FavoritoDTO;
import entidades.GeneroEntidad;
import entidades.UsuarioEntidad;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author piña
 */
public class UsuarioDAO implements IUsuarioDAO {

    private IConexionDAO conexion;

    public UsuarioDAO(IConexionDAO conexion) {
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
    public UsuarioEntidad buscar(String correo, String contra) throws PersistenciaException {
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

    @Override
<<<<<<< Updated upstream
    public UsuarioEntidad buscarPorUsuario(String correo) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<UsuarioEntidad> collection = bd.getCollection("usuarios", UsuarioEntidad.class);

            return collection.find(com.mongodb.client.model.Filters.eq("correo", correo)).first();
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar usuario: " + ex.getMessage());
        }
    }

=======
    public void agregarFavorito(String idUsuario, FavoritoDTO favorito) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<Document> coleccionUsuarios = bd.getCollection("usuarios", Document.class);

            Document docFavorito = new Document("_id", new ObjectId(favorito.getId()))
                    .append("nombre", favorito.getNombre())
                    .append("tipo", favorito.getTipo())
                    .append("fechaAgregacion", new Date());

            if (favorito.getGenero() != null) {
                Document docGenero = new Document();
                docGenero.append("nombre", favorito.getGenero());
                docFavorito.append("genero", docGenero);
            }

            coleccionUsuarios.updateOne(
                    Filters.eq("_id", new ObjectId(idUsuario)),
                    Updates.push("favoritos", docFavorito)
            );

        } catch (Exception ex) {
            throw new PersistenciaException("Error en BD al agregar favorito: " + ex.getMessage());
        }
    }

    @Override
    public void eliminarFavorito(String idUsuario, String idElemento) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<Document> coleccionUsuarios = bd.getCollection("usuarios", Document.class);

            coleccionUsuarios.updateOne(
                    Filters.eq("_id", new ObjectId(idUsuario)),
                    Updates.pull("favoritos", Filters.eq("_id", new ObjectId(idElemento)))
            );

        } catch (Exception ex) {
            throw new PersistenciaException("Error en BD al eliminar favorito: " + ex.getMessage());
        }
    }
>>>>>>> Stashed changes
}
