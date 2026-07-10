/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import dtos.AlbumDTO;
import entidades.AlbumEntidad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author luisf
 */
public class AlbumDAO implements IAlbumDAO {

    private final IConexionDAO conexionDAO;

    public AlbumDAO(IConexionDAO conexionDAO) {
        this.conexionDAO = conexionDAO;
    }

    @Override
    public List<AlbumEntidad> buscarAlbumesAvanzado(String texto, String genero, Date fecha) throws PersistenciaException {
        try {
            MongoDatabase baseDatos = conexionDAO.conexion();
            MongoCollection<AlbumEntidad> coleccion = baseDatos.getCollection("albumes", AlbumEntidad.class);

            List<Bson> filtros = new ArrayList<>();

            if (texto != null && !texto.trim().isEmpty()) {
                Bson filtroTexto = Filters.or(
                        Filters.regex("nombre", ".*" + texto + ".*", "i"),
                        Filters.regex("nombreArtista", ".*" + texto + ".*", "i"),
                        Filters.regex("canciones.nombre", ".*" + texto + ".*", "i")
                );
                filtros.add(filtroTexto);
            }

            if (genero != null && !genero.trim().isEmpty()) {
                filtros.add(Filters.eq("genero.nombre", genero));
            }

            if (fecha != null) {
                filtros.add(Filters.eq("fechaLanzamiento", fecha));
            }

            Bson filtroFinal = filtros.isEmpty() ? new Document() : Filters.and(filtros);

            return coleccion.find(filtroFinal).into(new ArrayList<>());

        } catch (Exception ex) {
            throw new PersistenciaException("Error al consultar los álbumes en la base de datos: " + ex.getMessage());
        }
    }

    @Override
    public AlbumEntidad obtenerPorId(String id) throws Exception {
        try {
            MongoDatabase baseDatos = conexionDAO.conexion();
            MongoCollection<Document> coleccion = baseDatos.getCollection("albumes");

            Document doc = coleccion.find(Filters.eq("_id", new ObjectId(id))).first();

            if (doc != null) {
                AlbumEntidad album = new AlbumEntidad();

                album.setId(doc.getObjectId("_id"));
                album.setNombre(doc.getString("nombre"));

                if (doc.containsKey("imagen")) {
                    album.setImagen(doc.getString("imagen"));
                }
                if (doc.containsKey("nombreArtista")) {
                    album.setNombreArtista(doc.getString("nombreArtista"));
                }

                return album;
            }
            return null;
        } catch (Exception ex) {
            throw new Exception("Error al buscar el álbum por ID en la BD: " + ex.getMessage());
        }
    }

    @Override
    public AlbumEntidad obtenerPorNombre(String nombre) throws Exception {
        try {
            MongoDatabase baseDatos = conexionDAO.conexion();
            MongoCollection<Document> coleccion = baseDatos.getCollection("albumes");

            Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();

            if (doc != null) {
                AlbumEntidad album = new AlbumEntidad();

                album.setId(doc.getObjectId("_id"));
                album.setNombre(doc.getString("nombre"));

                if (doc.containsKey("imagen")) {
                    album.setImagen(doc.getString("imagen"));
                }
                if (doc.containsKey("nombreArtista")) {
                    album.setNombreArtista(doc.getString("nombreArtista"));
                }

                return album;
            }
            return null;
        } catch (Exception ex) {
            throw new Exception("Error al buscar el álbum por nombre en la BD: " + ex.getMessage());
        }
    }

}
