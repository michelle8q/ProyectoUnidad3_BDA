/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dtos.CancionDetallesDTO;
import entidades.CancionEntidad;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Interfaz que define las operaciones de negocio relacionadas con canciones, se
 * hace un contrato.
 *
 * La interfaz sirve para que la capa de presentacion trabaje con canciones sin
 * llamar directamente al DAO.
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
     * @throws NegocioException si los datos no son validos o si ocurre un
     * error.
     */
    CancionDetallesDTO guardar(ObjectId idAlbum, CancionEntidad nuevaCancion) throws NegocioException;

    /**
     * Consulta todas las canciones registradas.
     *
     * Este metodo se usa para mostrar la lista general de canciones en la
     * interfaz de la aplicacion.
     *
     * @return lista de canciones registradas.
     * @throws NegocioException si ocurre un error al consultar.
     */
    List<CancionDetallesDTO> consultarTodas() throws NegocioException;

    List<CancionDetallesDTO> buscarPorTexto(String texto) throws NegocioException;

    List<CancionDetallesDTO> buscarCanciones(String texto, String genero) throws NegocioException;

    CancionDetallesDTO obtenerPorId(String id) throws NegocioException;

    CancionDetallesDTO obtenerPorNombre(String nombre) throws NegocioException;

}
