/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import dtos.AlbumDTO;
import dtos.CancionDetallesDTO;
import dtos.FavoritoDTO;
import dtos.UsuarioDTO;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import negocio.AlbumNegocio;
import negocio.IAlbumNegocio;
import negocio.ICancionNegocio;
import negocio.IUsuarioNegocio;
import negocio.NegocioException;

/**
 *
 * @author cinca
 */
public class FrmFavoritos extends javax.swing.JFrame {

    private UsuarioDTO usuarioActual;
    private Navegador navegador;
    private IUsuarioNegocio usuarioNegocio;
    private ICancionNegocio cancionNegocio;
    private IAlbumNegocio albumNegocio;

    /**
     * Creates new form FrmFavoritos
     */
    public FrmFavoritos() {
        initComponents();
    }

    public FrmFavoritos(UsuarioDTO usuarioActual, Navegador navegador, IUsuarioNegocio usuarioNegocio) {
        this.usuarioActual = usuarioActual;
        this.navegador = navegador;
        this.usuarioNegocio = usuarioNegocio;

        persistencia.IConexionDAO conexion = new persistencia.ConexionDAO();
        this.albumNegocio = new negocio.AlbumNegocio(new persistencia.AlbumDAO(conexion));
        this.cancionNegocio = new negocio.CancionNegocio(new persistencia.CancionDAO(conexion));

        initComponents();
        setLocationRelativeTo(null);

        pnlMenuLateral1.configurarSesion(usuarioActual, navegador);
        pnlContenedor.setLayout(new BoxLayout(pnlContenedor, BoxLayout.Y_AXIS));

        pnlBuscador1.setTitulo("Favoritos");

        cargarFavoritos();
    }

    private void cargarFavoritos() {
        try {
            pnlContenedor.removeAll();

            List<FavoritoDTO> favoritos = usuarioActual.getFavoritos();

            if (favoritos != null && !favoritos.isEmpty()) {
                for (FavoritoDTO favorito : favoritos) {

                    if (favorito.getTipo().equalsIgnoreCase("Album")) {

                        AlbumDTO album = albumNegocio.obtenerPorNombre(favorito.getNombre());

                        if (album != null) {
                            presentacion.pnlAlbum panelItem = new presentacion.pnlAlbum(album, true);

                            panelItem.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(109, 79, 130), 2),
                                    "ÁLBUM FAVORITO",
                                    javax.swing.border.TitledBorder.LEFT,
                                    javax.swing.border.TitledBorder.TOP,
                                    new java.awt.Font("Segoe UI Black", 0, 12),
                                    java.awt.Color.WHITE));

                            panelItem.setAccionFavorito((seleccionado) -> {
                                try {
                                    if (seleccionado) {
                                        FavoritoDTO nuevoFav = new FavoritoDTO();
                                        nuevoFav.setNombre(album.getNombre());
                                        nuevoFav.setGenero(album.getNombreGenero());
                                        nuevoFav.setTipo("Album");

                                        usuarioNegocio.agregarFavorito(usuarioActual.getId(), nuevoFav);
                                    } else {
                                        usuarioNegocio.eliminarFavorito(usuarioActual.getId(), favorito.getId());

                                        if (usuarioActual.getFavoritos() != null) {
                                            usuarioActual.getFavoritos().removeIf(f -> f.getId().equals(favorito.getId()));
                                        }

                                        pnlContenedor.remove(panelItem);
                                        pnlContenedor.revalidate();
                                        pnlContenedor.repaint();
                                    }
                                } catch (NegocioException ex) {
                                    javax.swing.JOptionPane.showMessageDialog(this, "Error al modificar favorito: " + ex.getMessage());
                                }
                            });

                            pnlContenedor.add(panelItem);
                        }

                    } else if (favorito.getTipo().equalsIgnoreCase("Cancion")) {

                        CancionDetallesDTO cancion = cancionNegocio.obtenerPorNombre(favorito.getNombre());

                        if (cancion != null) {
                            presentacion.pnlCancion panelItem = new presentacion.pnlCancion(cancion, true);

                            panelItem.setBorder(javax.swing.BorderFactory.createTitledBorder(
                                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(109, 79, 130), 2),
                                    "CANCIÓN FAVORITA",
                                    javax.swing.border.TitledBorder.LEFT,
                                    javax.swing.border.TitledBorder.TOP,
                                    new java.awt.Font("Segoe UI Black", 0, 12),
                                    java.awt.Color.WHITE));

                            panelItem.setAccionFavorito((seleccionado) -> {
                                try {
                                    if (seleccionado) {
                                        FavoritoDTO nuevoFav = new FavoritoDTO();
                                        nuevoFav.setNombre(cancion.getNombre());
                                        nuevoFav.setGenero(cancion.getGenero());
                                        nuevoFav.setTipo("Cancion");

                                        usuarioNegocio.agregarFavorito(usuarioActual.getId(), nuevoFav);
                                    } else {
                                        usuarioNegocio.eliminarFavorito(usuarioActual.getId(), favorito.getId());

                                        if (usuarioActual.getFavoritos() != null) {
                                            usuarioActual.getFavoritos().removeIf(f -> f.getId().equals(favorito.getId()));
                                        }

                                        pnlContenedor.remove(panelItem);
                                        pnlContenedor.revalidate();
                                        pnlContenedor.repaint();
                                    }
                                } catch (NegocioException ex) {
                                    javax.swing.JOptionPane.showMessageDialog(this, "Error al modificar favorito: " + ex.getMessage());
                                }
                            });

                            pnlContenedor.add(panelItem);
                        }
                    }

                    pnlContenedor.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));
                }
            } else {
                javax.swing.JLabel lblVacio = new javax.swing.JLabel("Aún no tienes favoritos agregados.");
                lblVacio.setForeground(java.awt.Color.WHITE);
                lblVacio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
                pnlContenedor.add(lblVacio);
            }

            pnlContenedor.revalidate();
            pnlContenedor.repaint();

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al cargar la lista de favoritos: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlMenuLateral1 = new presentacion.PnlMenuLateral();
        pnlBuscador1 = new presentacion.PnlBuscador();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnlContenedor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1086, 617));
        setMinimumSize(new java.awt.Dimension(1086, 617));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(1086, 617));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlContenedor.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 823, Short.MAX_VALUE)
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(pnlContenedor);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMenuLateral1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlBuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlBuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane2))))
                    .addComponent(pnlMenuLateral1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private presentacion.PnlBuscador pnlBuscador1;
    private javax.swing.JPanel pnlContenedor;
    private presentacion.PnlMenuLateral pnlMenuLateral1;
    // End of variables declaration//GEN-END:variables
}
