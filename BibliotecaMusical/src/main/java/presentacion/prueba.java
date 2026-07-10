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
        java.awt.EventQueue.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }

}
