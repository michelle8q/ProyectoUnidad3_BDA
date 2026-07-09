
package negocio;

/**
 * Excepcion usada para errores de reglas de negocio.
 *
 * Se lanza cuando los datos recibidos no son validos o cuando ocurre un error
 * controlado en la capa de negocio.
 * 
 * @author piña
 */
public class NegocioException extends Exception{
    
    public NegocioException(String msj){
        super(msj);
    }
    
}
