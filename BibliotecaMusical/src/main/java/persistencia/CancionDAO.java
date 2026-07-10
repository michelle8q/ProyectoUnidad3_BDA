/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.AlbumEntidad;
import entidades.CancionEntidad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Clase DAO que se encarga de manejar las operaciones de persistencia relacionadas con
 * las canciones.
 *
 * En esta base de datos las canciones no se guardan como una coleccion aparte,
 * sino dentro de la lista de canciones de cada album, por eso esta clase trabaja
 * sobre la coleccion albumes cuando necesita guardar o consultar canciones.
 *
 * @author cinca luisf
 * 
 */
public class CancionDAO implements ICancionDAO {
    
    /**
     * Conexion utilizada para acceder a la base de datos.
     */
    private final IConexionDAO conexion;
    
    /**
     * Constructor de CancionDAO.
     *
     * Recibe una conexion ya configurada para poder acceder a MongoDB. 
     * @param conexion objeto encargado de proporcionar la conexion a la base 
     * de datos.
     */
    public CancionDAO(IConexionDAO conexion){
        this.conexion = conexion;
    }
    
    /**
    * Guarda una nueva cancion dentro de un album existente. 
    * Como las canciones no tienen una coleccion propia en la base de datos 
    * se agregan directamente dentro del arreglo "canciones" del album. 
    * Por lo tanto se recibe el id del album donde se quiere guardar la cancion.
    *
    * @param idAlbum id del album al que se le agregara la cancion.
    * @param nuevaCancion cancion con los datos de la cancion nueva a guaradar.
    * @return la misma cancion que se guardo.
    * @throws PersistenciaException si no se encuentra el album o si ocurre un error con MongoDB.
    */
    @Override
    public CancionEntidad guardar(ObjectId idAlbum, CancionEntidad nuevaCancion) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();

            MongoCollection<AlbumEntidad> collection = bd.getCollection("albumes", AlbumEntidad.class);
            
            UpdateResult resultado = collection.updateOne(Filters.eq("_id", idAlbum), Updates.push("canciones", nuevaCancion));

            if (resultado.getMatchedCount() == 0) {
                throw new PersistenciaException("No se encontro el album para agregar la cancion.");
            }

            return nuevaCancion;

        } catch (PersistenciaException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al registrar la cancion: " + ex.getMessage());
        }
    }
    
    /**
     * Consulta todas las canciones registradas, ya combinadas con el nombre e
     * imagen del album, el nombre del artista y el genero. Se usa cuando la
     * pantalla de Canciones se abre sin ningun filtro activo en el buscador.
     *
     * @return lista de canciones con su informacion de contexto.
     * @throws PersistenciaException si ocurre un error al consultar la base de datos.
    */
    @Override
    public List<CancionEntidad> consultarTodas() throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<Document> collection = bd.getCollection("albumes");

            Bson etapaUnwind = Aggregates.unwind("$canciones");

            Bson etapaProyeccion = Aggregates.project(Projections.fields(
                    Projections.computed("_id", "$canciones._id"),
                    Projections.computed("nombre", "$canciones.nombre"),
                    Projections.computed("duracion", "$canciones.duracion"),
                    Projections.computed("nombreAlbum", "$nombre"),
                    Projections.computed("imagenAlbum", "$imagen"),
                    Projections.computed("nombreArtista", "$nombreArtista"),
                    Projections.include("genero")));

            return collection.aggregate(
                    Arrays.asList(etapaUnwind, etapaProyeccion),
                    CancionEntidad.class
            ).into(new ArrayList<>());
            
        } catch (Exception ex) {
            throw new PersistenciaException("Error al consultar canciones: " + ex.getMessage());
        }
    }
    
    /**
     * Busca canciones por texto libre, para el buscador general de la aplicacion.
     *
     * El texto se compara tanto contra el nombre de la cancion como contra el
     * nombre del genero del album al que pertenece, usando coincidencia parcial
     * (sin distinguir mayusculas).
     *
     * @param texto texto escrito por el usuario en el buscador.
     * @return lista de canciones que coinciden con el texto, con su informacion de contexto.
     * @throws PersistenciaException si ocurre un error al consultar la base de datos.
     */
    @Override
    public List<CancionEntidad> buscarPorTexto(String texto) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<Document> collection = bd.getCollection("albumes");

            Bson etapaUnwind = Aggregates.unwind("$canciones");

            Bson etapaFiltro = Aggregates.match(Filters.or(
                    Filters.regex("canciones.nombre", texto, "i"),
                    Filters.regex("genero.nombre", texto, "i"),
                    Filters.regex("nombreArtista", texto, "i")));

            Bson etapaProyeccion = Aggregates.project(Projections.fields(
                    Projections.computed("_id", "$canciones._id"),
                    Projections.computed("nombre", "$canciones.nombre"),
                    Projections.computed("duracion", "$canciones.duracion"),
                    Projections.computed("nombreAlbum", "$nombre"),
                    Projections.computed("imagenAlbum", "$imagen"),
                    Projections.computed("nombreArtista", "$nombreArtista"),
                    Projections.include("genero")));

            return collection.aggregate(
                    Arrays.asList(etapaUnwind, etapaFiltro, etapaProyeccion),
                    CancionEntidad.class
            ).into(new ArrayList<>());

        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar canciones: " + ex.getMessage());
        }
    }
    
    
     @Override
    public List<CancionEntidad> buscarCanciones(String texto, String genero) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();
            MongoCollection<Document> collection = bd.getCollection("albumes");

            Bson etapaUnwind = Aggregates.unwind("$canciones");

            List<Bson> filtros = new ArrayList<>();
            
            if (texto != null && !texto.trim().isEmpty()) {
                filtros.add(Filters.or(
                        Filters.regex("canciones.nombre", texto, "i"),
                        Filters.regex("nombreArtista", texto, "i")
                ));
            }
            
            if (genero != null && !genero.trim().isEmpty()) {
                filtros.add(Filters.eq("genero.nombre", genero));
            }

            Bson etapaFiltro = filtros.isEmpty() ? Aggregates.match(new Document()) : Aggregates.match(Filters.and(filtros));

            Bson etapaProyeccion = Aggregates.project(Projections.fields(
                    Projections.computed("_id", "$canciones._id"),
                    Projections.computed("nombre", "$canciones.nombre"),
                    Projections.computed("duracion", "$canciones.duracion"),
                    Projections.computed("nombreAlbum", "$nombre"),
                    Projections.computed("imagenAlbum", "$imagen"),
                    Projections.computed("nombreArtista", "$nombreArtista"),
                    Projections.include("genero")));

            return collection.aggregate(
                    Arrays.asList(etapaUnwind, etapaFiltro, etapaProyeccion),
                    CancionEntidad.class
            ).into(new ArrayList<>());

        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar canciones por filtros: " + ex.getMessage());
        }
    }
    
}
