/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import dtos.UsuarioDTO;
import javax.swing.JFrame;
import negocio.IAlbumNegocio;
import negocio.ICancionNegocio;
import negocio.IUsuarioNegocio;

/**
 * Esta clase permite mantener la sesion abierta del usuario, dandole la
 * responsabilidad sobre este y evtando pasar usuario a todos los frames
 * (ventanas)
 *
 * @author cinca
 */
public class Navegador {

    private UsuarioDTO usuarioActual;
    private IUsuarioNegocio usuarioNegocio;
    private ICancionNegocio cancionNegocio;
    private IAlbumNegocio albumNegocio;

    public Navegador(UsuarioDTO usuarioActual, IUsuarioNegocio usuarioNegocio, ICancionNegocio cancionNegocio, IAlbumNegocio albumNegocio) {
        this.usuarioActual = usuarioActual;
        this.usuarioNegocio = usuarioNegocio;
        this.cancionNegocio = cancionNegocio;
        this.albumNegocio = albumNegocio;
    }

    public void abrirCanciones(javax.swing.JFrame actual) {
        FrmCanciones ventana = new FrmCanciones(usuarioActual, this, cancionNegocio);
        ventana.setVisible(true);
        actual.dispose();
    }

    public void abrirAlbumes(javax.swing.JFrame actual) {
        FrmAlbumes ventana = new FrmAlbumes(usuarioActual, this);
        ventana.setVisible(true);
        actual.dispose();
    }

    public void abrirPerfil(javax.swing.JFrame actual) {
        FrmPerfilUsuario ventana = new FrmPerfilUsuario(usuarioActual, this);
        ventana.setVisible(true);
        actual.dispose();
    }

    public void abrirListaGeneros(javax.swing.JFrame ventanaActual) {
        FrmListaGeneros ventana = new FrmListaGeneros(usuarioActual, this);
        ventana.setVisible(true);

        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
    }

    public void abrirInicio(JFrame ventanaActual) {
        FrmInicio ventana = new FrmInicio(usuarioActual, this);
        ventana.setVisible(true);
        if (ventanaActual != null) {
            ventanaActual.dispose();
        }
    }

    public void actualizarUsuarioActual(UsuarioDTO usuarioActualizado) {
        this.usuarioActual = usuarioActualizado;
    }
}
