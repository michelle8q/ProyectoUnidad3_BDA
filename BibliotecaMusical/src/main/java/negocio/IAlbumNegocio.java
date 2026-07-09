/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.AlbumDTO;
import java.util.List;

/**
 *
 * @author luisf
 */
public interface IAlbumNegocio {

    List<AlbumDTO> buscarAlbumes(String textoBusqueda, String genero, String fechaTexto) throws NegocioException;
}
