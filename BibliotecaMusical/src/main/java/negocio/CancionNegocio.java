/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.CancionDetallesDTO;
import entidades.CancionEntidad;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import persistencia.ICancionDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author cinca
 */
public class CancionNegocio implements ICancionNegocio {
    /**
     * DAO utilizado para acceder a las canciones guardadas dentro de los albumes.
     */
    private final ICancionDAO cancionDAO;

    /**
     * Constructor de CancionNegocio.
     *
     * Recibe el DAO de canciones para poder usar sus metodos despues de validar
     * la informacion recibida desde la interfaz.
     *
     * @param cancionDAO objeto DAO encargado de la persistencia de canciones.
     */
    public CancionNegocio(ICancionDAO cancionDAO) {
        this.cancionDAO = cancionDAO;
    }

    /**
     * Guarda una cancion dentro de un album existente.
     *
     * Antes de guardar se valida que el album exista por medio de su id y que la
     * cancion tenga los datos basicos completos, como nombre y duracion.
     *
     * @param idAlbum id del album donde se agregara la cancion.
     * @param nuevaCancion cancion que se desea guardar.
     * @return la cancion guardada.
     * @throws NegocioException si los datos no son validos o si ocurre un error en persistencia.
     */
    @Override
    public CancionDetallesDTO guardar(ObjectId idAlbum, CancionEntidad nuevaCancion) throws NegocioException {
        try {
            validarIdAlbum(idAlbum);
            validarCancion(nuevaCancion);
            
            CancionEntidad entidad = new CancionEntidad();
            nuevaCancion.setNombre(nuevaCancion.getNombre().trim());
            nuevaCancion.setDuracion(nuevaCancion.getDuracion().trim());
            
            CancionEntidad guardada = cancionDAO.guardar(idAlbum, entidad);
            return convertirADTO(guardada);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo guardar la cancion: " + ex.getMessage());
        }
    }

    /**
     * Consulta todas las canciones registradas.
     *
     * Este metodo se usa principalmente para llenar la pantalla general de
     * canciones dentro de la aplicacion.
     *
     * @return lista de canciones registradas.
     * @throws NegocioException si ocurre un error al consultar.
     */
    @Override
    public List<CancionDetallesDTO> consultarTodas() throws NegocioException {
        try {
            List<CancionEntidad> entidades = cancionDAO.consultarTodas();
            return convertirListaADTO(entidades);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron consultar las canciones: " + ex.getMessage());
        }
    }

    /**
     * Busca canciones por nombre.
     *
     * Se valida que el texto de busqueda no este vacio antes de mandarlo al DAO.
     * Esto evita hacer consultas sin sentido desde la interfaz.
     *
     * @param texto texto que se desea buscar.
     * @return lista de canciones que coinciden con el nombre.
     * @throws NegocioException si el nombre no es valido o si ocurre un error al buscar.
     */
    @Override
    public List<CancionDetallesDTO> buscarPorTexto(String texto) throws NegocioException {
        try {
            validarTextoBusqueda(texto);
            List<CancionEntidad> entidades = cancionDAO.buscarPorTexto(texto.trim());
            return convertirListaADTO(entidades);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudieron buscar canciones por nombre: " + ex.getMessage());
        }
    }


    /**
     * Valida que el id del album no venga vacio.
     *
     * @param idAlbum id que se desea validar.
     * @throws NegocioException si el id es null.
     */
    private void validarIdAlbum(ObjectId idAlbum) throws NegocioException {
        if (idAlbum == null) {
            throw new NegocioException("Debe seleccionar un album.");
        }
    }

    /**
     * Valida los datos principales de una cancion.
     *
     * @param cancion cancion que se desea validar.
     * @throws NegocioException si la cancion no tiene datos validos.
     */
    private void validarCancion(CancionEntidad cancion) throws NegocioException {
        if (cancion == null) {
            throw new NegocioException("La cancion no puede estar vacia.");
        }

        if (cancion.getNombre() == null || cancion.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre de la cancion es obligatorio.");
        }

        if (cancion.getDuracion() == null || cancion.getDuracion().trim().isEmpty()) {
            throw new NegocioException("La duracion de la cancion es obligatoria.");
        }
    }

    /**
     * Valida que un texto de busqueda tenga contenido.
     *
     * @param texto texto recibido.
     * @param campo nombre del campo que se esta validando.
     * @throws NegocioException si el texto esta vacio.
     */
    private void validarTextoBusqueda(String texto) throws NegocioException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new NegocioException("Debe ingresar un TExto para buscar.");
        }
    }
    
     /**
     * Convierte una entidad de cancion a su DTO correspondiente.
     */
    private CancionDetallesDTO convertirADTO(CancionEntidad entidad) {
        CancionDetallesDTO dto = new CancionDetallesDTO();
        
        if (entidad.getId() != null) {
            dto.setId(entidad.getId().toString());
        }
        dto.setNombre(entidad.getNombre());
        dto.setDuracion(entidad.getDuracion());
        dto.setNombreAlbum(entidad.getNombreAlbum());
        dto.setImagenAlbum(entidad.getImagenAlbum());
        dto.setNombreArtista(entidad.getNombreArtista());
        
        if (entidad.getGenero() != null) {
            dto.setGenero(entidad.getGenero().getNombre());
        }
        
        return dto;
    }
    
    /**
     * Convierte una lista de entidades de cancion a una lista de DTOs.
     */
    private List<CancionDetallesDTO> convertirListaADTO(List<CancionEntidad> entidades) {
        List<CancionDetallesDTO> resultado = new ArrayList<>();
        for (CancionEntidad entidad : entidades) {
            resultado.add(convertirADTO(entidad));
        }
        return resultado;
    }

}
