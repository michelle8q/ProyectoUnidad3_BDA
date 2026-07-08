
package persistencia;

import com.mongodb.client.MongoDatabase;

/**
 *
 * @author piña
 */
public interface IConexionDAO {
    
    public MongoDatabase conexion();
    
}
