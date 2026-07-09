/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import entidades.CancionEntidad;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * Interfaz que define las operaciones de persistencia para las canciones, funciona
 * como un contrato.
 *
 * @author cinca
 */
public interface ICancionDAO {
    /**
     * Guarda una cancion dentro de un album existente.
     *
     * @param idAlbum id del album donde se agregara la cancion.
     * @param nuevaCancion cancion que se desea guardar.
     * @return la cancion guardada.
     * @throws PersistenciaException si ocurre un error al guardar.
     */
    CancionEntidad guardar(ObjectId idAlbum, CancionEntidad nuevaCancion) throws PersistenciaException;
    
    /**
     * Consulta todas las canciones registradas en los albumes.
     *
     * @return lista de canciones encontradas.
     * @throws PersistenciaException si ocurre un error al consultar.
     */
    List<CancionEntidad> consultarTodas() throws PersistenciaException;
    
    List<CancionEntidad> buscarPorTexto(String texto) throws PersistenciaException;
}
