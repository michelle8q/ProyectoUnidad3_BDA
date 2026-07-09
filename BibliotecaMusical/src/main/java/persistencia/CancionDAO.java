/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import entidades.AlbumEntidad;
import entidades.CancionEntidad;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase DAO que se encarga de manejar las operaciones de persistencia relacionadas con
 * las canciones.
 *
 * En esta base de datos las canciones no se guardan como una coleccion aparte,
 * sino dentro de la lista de canciones de cada album, por eso esta clase trabaja
 * sobre la coleccion albumes cuando necesita guardar o consultar canciones.
 *
 * @author cinca
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
    * Consulta todas las canciones registradas en todos los albumes.
    *
    * Como las canciones estan guardadas dentro de cada album primero se consultan
    * todos los albumes y despues se recorre la lista de canciones de cada uno.
    * Esto sirve para llenar la pantalla general de canciones.
    *
    * @return lista con todas las canciones encontradas.
    * @throws PersistenciaException si ocurre un error al consultar la base de datos.
    */
    @Override
    public List<CancionEntidad> consultarTodas() throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();

            MongoCollection<AlbumEntidad> collection = bd.getCollection("albumes", AlbumEntidad.class);

            List<CancionEntidad> canciones = new ArrayList<>();

            for (AlbumEntidad album : collection.find()) {
                if (album.getCanciones() != null) {
                    canciones.addAll(album.getCanciones());
                }
            }
            return canciones;

        } catch (Exception ex) {
            throw new PersistenciaException("Error al consultar canciones: " + ex.getMessage());
        }
    }
    
    /**
    * Busca canciones por coincidencia en el nombre.
    *
    * La busqueda se hace recorriendo las canciones que estan dentro de los albumes.
    * No se ocupa que el nombre sea exactamente igual, porque se usa contains
    * para permitir coincidencias parciales asi como el buscador en spotify.
    *
    * @param nombre texto que se desea buscar dentro del nombre de la cancion.
    * @return lista de canciones que coinciden con el texto buscado.
    * @throws PersistenciaException si ocurre un error durante la consulta.
    */
    @Override
    public List<CancionEntidad> buscarPorNombre(String nombre) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();

            MongoCollection<AlbumEntidad> collection = bd.getCollection("albumes", AlbumEntidad.class);

            List<CancionEntidad> canciones = new ArrayList<>();

            for (AlbumEntidad album : collection.find()) {
                if (album.getCanciones() != null) {
                    for (CancionEntidad cancion : album.getCanciones()) {
                        if (cancion.getNombre() != null
                                && cancion.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                            canciones.add(cancion);
                        }
                    }
                }
            } return canciones;
        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar canciones por nombre: " + ex.getMessage());
        }
    }
    
    /**
    * Busca canciones tomando en cuenta el genero del album.
    *
    * La cancion no guarda genero propio porque hereda el genero del album donde
    * esta registrada. Por eso primero se buscan los albumes que coincidan con el
    * genero recibido y despues se agregan sus canciones al resultado.
    *
    * @param nombreGenero nombre del genero que se desea buscar.
    * @return lista de canciones que pertenecen a albumes de ese genero.
    * @throws PersistenciaException si ocurre un error al consultar la base de datos.
    */
    @Override
    public List<CancionEntidad> buscarPorGeneroAlbum(String nombreGenero) throws PersistenciaException {
        try {
            MongoDatabase bd = conexion.conexion();

            MongoCollection<AlbumEntidad> collection =bd.getCollection("albumes", AlbumEntidad.class);

            List<CancionEntidad> canciones = new ArrayList<>();

            for (AlbumEntidad album : collection.find(Filters.regex("genero.nombre", nombreGenero, "i"))) {

                if (album.getCanciones() != null) {
                    canciones.addAll(album.getCanciones());
                }
            } return canciones;

        } catch (Exception ex) {
            throw new PersistenciaException("Error al buscar canciones por genero: " + ex.getMessage());
        }
    }
}
