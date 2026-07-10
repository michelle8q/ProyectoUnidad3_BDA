
package negocio;

import dtos.FavoritoDTO;
import dtos.GeneroDTO;
import dtos.UsuarioDTO;
import entidades.FavoritoEntidad;
import entidades.GeneroEntidad;
import entidades.UsuarioEntidad;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import persistencia.IUsuarioDAO;
import persistencia.PersistenciaException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author piña
 */
public class UsuarioNegocio implements IUsuarioNegocio{
    
    private final IUsuarioDAO usuarioDAO;

    public UsuarioNegocio(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }


    @Override
    public UsuarioEntidad reguistrar(UsuarioEntidad usuario) throws NegocioException {
        
        try {
            validarUsuarioNuevo(usuario);
            
            String contrasenaEncriptada = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            
            usuario.setContrasena(contrasenaEncriptada);
            
            UsuarioEntidad usuarioGuardado= usuarioDAO.registar(usuario);
            
            return usuarioGuardado;
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo registrar al usuario"+e.getMessage());
        }
    }

    @Override
    public UsuarioDTO buscar(String correo, String contra) throws NegocioException {
        try {
            validarLogin(correo, contra);

            UsuarioEntidad usuario = usuarioDAO.buscar(correo.trim(), contra.trim());

            if (usuario == null) {
                throw new NegocioException("Correo o contrasena incorrectos.");
            }

            return convertirADTO(usuario);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo buscar el usuario: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioDTO buscarPorId(String idUsuario) throws NegocioException {
        try {
            ObjectId id = validarObjectId(idUsuario, "El usuario no es valido.");
            UsuarioEntidad usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                throw new NegocioException("No se encontro el usuario.");
            }

            return convertirADTO(usuario);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo buscar el usuario: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioDTO actualizarPerfil(UsuarioDTO usuario) throws NegocioException {
       try {
            validarUsuarioActualizar(usuario);
            ObjectId id = validarObjectId(usuario.getId(), "El usuario no es valido.");

            UsuarioEntidad actualizado = usuarioDAO.actualizarPerfil(id,
                    usuario.getUsuario().trim(),
                    usuario.getImagen());

            return convertirADTO(actualizado);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo actualizar el perfil: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioDTO agregarGeneroNoDeseado(String idUsuario, GeneroDTO genero) throws NegocioException {
        try {
            ObjectId id = validarObjectId(idUsuario, "El usuario no es valido.");
            validarGenero(genero);

            GeneroEntidad entidad = convertirGeneroAEntidad(genero);

            if (entidad.getId() == null) {
                entidad.setId(new ObjectId());
            }

            UsuarioEntidad actualizado = usuarioDAO.agregarGeneroNoDeseado(id, entidad);
            return convertirADTO(actualizado);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo agregar el genero: " + ex.getMessage());
        }
    }

    @Override
    public UsuarioDTO eliminarGeneroNoDeseado(String idUsuario, String nombreGenero) throws NegocioException {
        try {
            ObjectId id = validarObjectId(idUsuario, "El usuario no es valido.");

            if (nombreGenero == null || nombreGenero.trim().isEmpty()) {
                throw new NegocioException("Debe seleccionar un genero.");
            }

            UsuarioEntidad actualizado = usuarioDAO.eliminarGeneroNoDeseado(id, nombreGenero.trim());
            return convertirADTO(actualizado);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo eliminar el genero: " + ex.getMessage());
        }
    }
    
    /**
    * Metodo para realizar las validaciones del registro de un usuario.
    * Valida que no sea nulo, su nombre ingresado y el correo.
    */
    private void validarUsuarioRegistro(UsuarioDTO usuario) throws NegocioException {
        if (usuario == null) {
            throw new NegocioException("El usuario no puede estar vacio.");
        }
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new NegocioException("El nombre de usuario es obligatorio.");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new NegocioException("El correo es obligatorio.");
        }
    }
    
    /**
    * Metodo para realizar las validaciones del actualizacion de un usuario.
    * Valida que el usuario no este vacio, que sea valido y sus campos que no sean
    * vacios.
    */
    private void validarUsuarioActualizar(UsuarioDTO usuario) throws NegocioException {
        if (usuario == null) {
            throw new NegocioException("El usuario no puede estar vacio.");
        }
        if (usuario.getId() == null || usuario.getId().trim().isEmpty()) {
            throw new NegocioException("El usuario no es valido.");
        }
        if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
            throw new NegocioException("El nombre de usuario es obligatorio.");
        }
    }
    
    /**
    * Metodo para realizar las validaciones del login de usuario.
    * Valida que los campos no esten vacios.
    */
    private void validarLogin(String correo, String contrasena) throws NegocioException {
        if (correo == null || correo.trim().isEmpty()) {
            throw new NegocioException("Debe ingresar el correo.");
        }
        
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        
        if (!Pattern.matches(regex, correo)) {
            throw new IllegalArgumentException("El formato del correo no es válido (ejemplo@dominio.com).");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new NegocioException("Debe ingresar la contrasena.");
        }
    }
    
    /**
     * metodo para validar los datos de un usuario nuevo
     * @return String la ruta de la foto de perfil, en caso de no tener ningna se asigna la de dafault
     * @param entidad nombre de usuario
     * @param correo correo del usuario
     * @param contra contraseña del usuario
     * @param imagen foto de perfil
     * @exception en caso de algun error
     */
    private void validarUsuarioNuevo(UsuarioEntidad entidad) throws NegocioException{
        String correo = entidad.getCorreo();
        String contra = entidad.getContrasena();
        validarLogin(correo, contra);
        
        String usuario = entidad.getUsuario();
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new NegocioException("el usuario no puede estar en blanco.");
        }
        if (entidad.getImagen() == null || entidad.getImagen().trim().isEmpty()) {
            entidad.setImagen("/imagenes/fotoPerfilDefault.jpg"); 
        }
    }
    
    /**
    * Metodo para realizar las validaciones del los generos.
    * Valida que el usuario seleccione un genero al querer agregarlo a una 
    * lista.
    */
    private void validarGenero(GeneroDTO genero) throws NegocioException {
        if (genero == null) {
            throw new NegocioException("Necesita seleccioar un genero.");
        }
    }
    
    /**
    * Metodo para validar el ID.
    */
    private ObjectId validarObjectId(String id, String mensaje) throws NegocioException {
        if (id == null || id.trim().isEmpty() || !ObjectId.isValid(id)) {
            throw new NegocioException(mensaje);
        }

        return new ObjectId(id);
    }

    private UsuarioDTO convertirADTO(UsuarioEntidad entidad) {
        UsuarioDTO dto = new UsuarioDTO();

        if (entidad.getId() != null) {
            dto.setId(entidad.getId().toString());
        }

        dto.setUsuario(entidad.getUsuario());
        dto.setCorreo(entidad.getCorreo());
        dto.setImagen(entidad.getImagen());
        dto.setGenerosNoDeseados(convertirGenerosADTO(entidad.getGenerosNoDeseados()));
        dto.setFavoritos(convertirFavoritosADTO(entidad.getFavoritos()));

        return dto;
    }
    
    /**
    *
    * Se mapea a entidad ya que se necesitara actualizar y registrar. Por lo tanto, 
    * la informacion que vendra desde arriba ((presentacion)) ira hacia abajo.
    * 
    */
    private UsuarioEntidad convertirAEntidad(UsuarioDTO dto) {
        UsuarioEntidad entidad = new UsuarioEntidad();

        if (dto.getId() != null && ObjectId.isValid(dto.getId())) {
            entidad.setId(new ObjectId(dto.getId()));
        }

        entidad.setUsuario(dto.getUsuario());
        entidad.setCorreo(dto.getCorreo());
        entidad.setImagen(dto.getImagen());
        entidad.setGenerosNoDeseados(convertirGenerosAEntidad(dto.getGenerosNoDeseados()));

        return entidad;
    }
    
    /**
    *
    * Se mapea a entidad ya que se necesitara actualizar y registrar. Por lo tanto, 
    * la informacion que vendra desde arriba ((presentacion)) ira hacia abajo.
    * 
    */
    private GeneroEntidad convertirGeneroAEntidad(GeneroDTO dto) {
        GeneroEntidad entidad = new GeneroEntidad();

        if (dto.getId() != null && ObjectId.isValid(dto.getId())) {
            entidad.setId(new ObjectId(dto.getId()));
        }

        entidad.setNombre(dto.getNombre().trim());
        return entidad;
    }

    private List<GeneroDTO> convertirGenerosADTO(List<GeneroEntidad> generos) {
        List<GeneroDTO> resultado = new ArrayList<>();

        if (generos != null) {
            for (GeneroEntidad genero : generos) {
                GeneroDTO dto = new GeneroDTO();

                if (genero.getId() != null) {
                    dto.setId(genero.getId().toString());
                }

                dto.setNombre(genero.getNombre());
                resultado.add(dto);
            }
        }

        return resultado;
    }

    /**
    *
    * Se mapea a entidad ya que se necesitara actualizar y registrar. Por lo tanto, 
    * la informacion que vendra desde arriba ((presentacion)) ira hacia abajo.
    * 
    */
    private List<GeneroEntidad> convertirGenerosAEntidad(List<GeneroDTO> generos) {
        List<GeneroEntidad> resultado = new ArrayList<>();

        if (generos != null) {
            for (GeneroDTO genero : generos) {
                resultado.add(convertirGeneroAEntidad(genero));
            }
        }

        return resultado;
    }

    private List<FavoritoDTO> convertirFavoritosADTO(List<FavoritoEntidad> favoritos) {
        List<FavoritoDTO> resultado = new ArrayList<>();

        if (favoritos != null) {
            for (FavoritoEntidad favorito : favoritos) {
                FavoritoDTO dto = new FavoritoDTO();

                if (favorito.getId() != null) {
                    dto.setId(favorito.getId().toString());
                }

                dto.setNombre(favorito.getNombre());

                if (favorito.getGenero() != null) {
                    dto.setGenero(favorito.getGenero().getNombre());
                }

                if (favorito.getTipo() != null) {
                    dto.setTipo(favorito.getTipo().name());
                }

                dto.setFechaAgregacion(favorito.getFechaAgregacion());
                resultado.add(dto);
            }
        }

        return resultado;
    }
    
}
