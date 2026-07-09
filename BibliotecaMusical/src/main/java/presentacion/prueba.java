/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import dtos.UsuarioDTO;
import negocio.AlbumNegocio;
import negocio.CancionNegocio;
import negocio.IAlbumNegocio;
import negocio.ICancionNegocio;
import negocio.IUsuarioNegocio;
import negocio.NegocioException;
import negocio.UsuarioNegocio;
import persistencia.AlbumDAO;
import persistencia.CancionDAO;
import persistencia.ConexionDAO;
import persistencia.IAlbumDAO;
import persistencia.ICancionDAO;
import persistencia.IConexionDAO;
import persistencia.IUsuarioDAO;
import persistencia.UsuarioDAO;

/**
 *
 * @author luisf
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IConexionDAO conexionDAO = new ConexionDAO();

        ICancionDAO cancionDAO = new CancionDAO(conexionDAO);
        ICancionNegocio cancionNegocio = new CancionNegocio(cancionDAO);

        IAlbumDAO albumDAO = new AlbumDAO(conexionDAO);
        IAlbumNegocio albumNegocio = new AlbumNegocio(albumDAO);

        IUsuarioDAO usuarioDAO = new UsuarioDAO(conexionDAO);
        IUsuarioNegocio usuarioNegocio = new UsuarioNegocio(usuarioDAO);

        try {
            UsuarioDTO usuario = usuarioNegocio.buscar("luis@correo.com", "1234");

            if (usuario == null) {
                System.out.println("No se encontró el usuario en la base de datos.");
                return; 
            }

            Navegador navegador = new Navegador(usuario, usuarioNegocio, cancionNegocio, albumNegocio);

            navegador.abrirInicio(null);

        } catch (Exception ex) {
            System.out.println("Error al iniciar la aplicación: " + ex.getMessage());
        }
    }

}
