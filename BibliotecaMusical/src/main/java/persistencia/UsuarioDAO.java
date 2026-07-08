
package persistencia;

import entidades.UsuarioEntidad;

/**
 *
 * @author piña
 */
public class UsuarioDAO implements IUsuarioDAO{
    
    private IConexionDAO conexion;
    
    public UsuarioDAO(IConexionDAO conexion){
        this.conexion = conexion;
    }

    @Override
    public UsuarioEntidad reguistar(UsuarioEntidad usuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UsuarioEntidad verificar(UsuarioEntidad usuario) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
