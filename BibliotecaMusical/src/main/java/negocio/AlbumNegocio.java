/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.AlbumDTO;
import DTOs.CancionDTO;
import entidades.AlbumEntidad;
import entidades.CancionEntidad;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import persistencia.IAlbumDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author luisf
 */
public class AlbumNegocio implements IAlbumNegocio {

    private final IAlbumDAO albumDAO;

    public AlbumNegocio(IAlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    @Override
    public List<AlbumDTO> buscarAlbumes(String texto, String genero, String fechaTexto) throws NegocioException {
        try {
            String textoLimpio = (texto != null && !texto.isBlank()) ? texto.trim() : null;
            String generoFinal = (genero != null && !genero.equalsIgnoreCase("Todos") && !genero.isBlank()) ? genero : null;

            Date fechaFinal = null;
            if (fechaTexto != null && !fechaTexto.trim().isEmpty() && !fechaTexto.equals("dd/mm/aaaa")) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(false);
                    fechaFinal = sdf.parse(fechaTexto);
                } catch (ParseException ex) {
                    throw new NegocioException("La fecha no tiene el formato correcto. Usa dd/MM/yyyy.");
                }
            }

            List<AlbumEntidad> listaEntidades = albumDAO.buscarAlbumesAvanzado(textoLimpio, generoFinal, fechaFinal);

            List<AlbumDTO> listaDTOs = new ArrayList<>();
            for (AlbumEntidad entidad : listaEntidades) {
                AlbumDTO dto = new AlbumDTO();

                if (entidad.getId() != null) {
                    dto.setId(entidad.getId().toString());
                }
                dto.setNombre(entidad.getNombre());
                dto.setNombreArtista(entidad.getNombreArtista());
                dto.setFechaLanzamiento(entidad.getFechaLanzamiento());

                if (entidad.getGenero() != null) {
                    dto.setNombreGenero(entidad.getGenero().getNombre());
                }
                List<CancionDTO> listaCancionesDTO = new ArrayList<>();
                if (entidad.getCanciones() != null) {
                    for (CancionEntidad cancionEntidad : entidad.getCanciones()) {
                        CancionDTO cancionDTO = new CancionDTO();
                        cancionDTO.setNombre(cancionEntidad.getNombre());
                        listaCancionesDTO.add(cancionDTO);
                    }
                }
                dto.setCanciones(listaCancionesDTO);
                listaDTOs.add(dto);
            }

            return listaDTOs;

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar la base de datos: " + ex.getMessage());
        }
    }
}
