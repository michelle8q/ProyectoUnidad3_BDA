/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.AlbumEntidad;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

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
}
