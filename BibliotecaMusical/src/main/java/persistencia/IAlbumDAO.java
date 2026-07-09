/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import entidades.AlbumEntidad;
import java.util.Date;
import java.util.List;
/**
 *
 * @author luisf
 */
public interface IAlbumDAO {

    List<AlbumEntidad> buscarAlbumesAvanzado(String textoBusqueda, String genero, Date fechaLanzamiento) throws PersistenciaException;

}
