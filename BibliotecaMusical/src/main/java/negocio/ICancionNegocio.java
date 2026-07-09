/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import entidades.CancionEntidad;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Interfaz que define las operaciones de negocio relacionadas con canciones, se
 * hace un contrato.
 *
 * La interfaz sirve para que la capa de presentacion trabaje con canciones
 * sin llamar directamente al DAO. 
 * 
 * @author cinca
 */
public interface ICancionNegocio {
    /**
     * Guarda una cancion dentro de un album.
     *
     * Antes de llegar al DAO, la capa de negocio debe validar que se haya
     * seleccionado un album y que la cancion tenga datos completos.
     *
     * @param idAlbum id del album donde se agregara la cancion.
     * @param nuevaCancion cancion que se desea guardar.
     * @return la cancion guardada.
     * @throws NegocioException si los datos no son validos o si ocurre un error.
     */
    CancionEntidad guardar(ObjectId idAlbum, CancionEntidad nuevaCancion) throws NegocioException;
    
    
    /**
     * Consulta todas las canciones registradas.
     *
     * Este metodo se usa para mostrar la lista general de canciones en la
     * interfaz de la aplicacion.
     *
     * @return lista de canciones registradas.
     * @throws NegocioException si ocurre un error al consultar.
     */
    List<CancionEntidad> consultarTodas() throws NegocioException;
    
    /**
     * Busca canciones por su nombre.
     *
     * Se utiliza cuando el usuario escribe texto en el buscador de canciones.
     *
     * @param nombre texto que se desea buscar.
     * @return lista de canciones que coinciden con el texto.
     * @throws NegocioException si el texto de busqueda no es valido o si ocurre un error.
     */
    List<CancionEntidad> buscarPorNombre(String nombre) throws NegocioException;
    
    /**
     * Busca canciones por el genero del album.
     *
     * Como la cancion no guarda genero propio se agarra como referencia el genero
     * del album al que pertenece.
     *
     * @param nombreGenero genero del album que se desea buscar.
     * @return lista de canciones que pertenecen a albumes de ese genero.
     * @throws NegocioException si el genero no es valido o si ocurre un error.
     */
    List<CancionEntidad> buscarPorGeneroAlbum(String nombreGenero) throws NegocioException;
}
