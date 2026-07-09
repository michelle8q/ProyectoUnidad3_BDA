/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import DTOs.AlbumDTO;
import java.util.List;
import negocio.IAlbumNegocio;

/**
 *
 * @author luisf
 */
public class FrmAlbumes extends javax.swing.JFrame {

    private IAlbumNegocio albumNegocio;

    /**
     * Creates new form FrmAlbumes
     */
    public FrmAlbumes() {
        persistencia.IConexionDAO conexion = new persistencia.ConexionDAO();
        persistencia.IAlbumDAO albumDAO = new persistencia.AlbumDAO(conexion);
        this.albumNegocio = new negocio.AlbumNegocio(albumDAO);

        initComponents();
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlFondo = new javax.swing.JPanel();
        LblInicio = new javax.swing.JLabel();
        LblAlbumes = new javax.swing.JLabel();
        LblArtistas = new javax.swing.JLabel();
        LblCanciones = new javax.swing.JLabel();
        LblPerfil = new javax.swing.JLabel();
        BtnInsert = new javax.swing.JButton();
        BtnCerrarSesion = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblFecha = new javax.swing.JLabel();
        TxTBuscador1 = new javax.swing.JTextField();
        BtnBuscar1 = new javax.swing.JButton();
        LblInicio2 = new javax.swing.JLabel();
        cbxGenero = new javax.swing.JComboBox<>();
        lblGenero1 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setMaximumSize(new java.awt.Dimension(1073, 622));
        setMinimumSize(new java.awt.Dimension(1073, 622));
        setPreferredSize(new java.awt.Dimension(1073, 622));

        PnlFondo.setBackground(new java.awt.Color(51, 51, 51));
        PnlFondo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(144, 104, 225)));

        LblInicio.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblInicio.setForeground(new java.awt.Color(255, 255, 255));
        LblInicio.setText("Inicio");

        LblAlbumes.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblAlbumes.setForeground(new java.awt.Color(255, 255, 255));
        LblAlbumes.setText("Albumes");

        LblArtistas.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblArtistas.setForeground(new java.awt.Color(255, 255, 255));
        LblArtistas.setText("Artistas");

        LblCanciones.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblCanciones.setForeground(new java.awt.Color(255, 255, 255));
        LblCanciones.setText("Canciones");

        LblPerfil.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblPerfil.setForeground(new java.awt.Color(255, 255, 255));
        LblPerfil.setText("Perfil");

        BtnInsert.setBackground(new java.awt.Color(109, 79, 130));
        BtnInsert.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        BtnInsert.setText("Insert Masivo");
        BtnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsertActionPerformed(evt);
            }
        });

        BtnCerrarSesion.setBackground(new java.awt.Color(0, 0, 0));
        BtnCerrarSesion.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        BtnCerrarSesion.setText("Cerrar sesion");
        BtnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlFondoLayout = new javax.swing.GroupLayout(PnlFondo);
        PnlFondo.setLayout(PnlFondoLayout);
        PnlFondoLayout.setHorizontalGroup(
            PnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlFondoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblPerfil)
                    .addComponent(LblArtistas)
                    .addComponent(LblAlbumes)
                    .addComponent(LblInicio)
                    .addComponent(LblCanciones))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        PnlFondoLayout.setVerticalGroup(
            PnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlFondoLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(LblInicio)
                .addGap(51, 51, 51)
                .addComponent(LblArtistas)
                .addGap(58, 58, 58)
                .addComponent(LblAlbumes)
                .addGap(50, 50, 50)
                .addComponent(LblCanciones)
                .addGap(51, 51, 51)
                .addComponent(LblPerfil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(BtnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(817, 91));

        lblFecha.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha de lanzamiento:");

        TxTBuscador1.setBackground(new java.awt.Color(0, 0, 0));
        TxTBuscador1.setForeground(new java.awt.Color(255, 255, 255));

        BtnBuscar1.setBackground(new java.awt.Color(109, 79, 130));
        BtnBuscar1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        BtnBuscar1.setForeground(new java.awt.Color(255, 255, 255));
        BtnBuscar1.setText("Buscar");
        BtnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscar1ActionPerformed(evt);
            }
        });

        LblInicio2.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        LblInicio2.setForeground(new java.awt.Color(255, 255, 255));
        LblInicio2.setText("Albumes");

        cbxGenero.setBackground(new java.awt.Color(109, 79, 130));
        cbxGenero.setForeground(new java.awt.Color(255, 255, 255));
        cbxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Rock Alternativo", "Reggaeton" }));
        cbxGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxGeneroActionPerformed(evt);
            }
        });

        lblGenero1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblGenero1.setForeground(new java.awt.Color(255, 255, 255));
        lblGenero1.setText("Genero:");

        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(cbxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TxTBuscador1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(35, 35, 35)
                    .addComponent(LblInicio2)
                    .addContainerGap(684, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(lblGenero1)
                    .addContainerGap(690, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxTBuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(cbxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(LblInicio2)
                    .addContainerGap(69, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(93, Short.MAX_VALUE)
                    .addComponent(lblGenero1)
                    .addContainerGap()))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PnlFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PnlFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnInsertActionPerformed

    private void BtnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCerrarSesionActionPerformed

    private void BtnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscar1ActionPerformed
        try {
            String texto = TxTBuscador1.getText();

            String genero = cbxGenero.getSelectedItem().toString();
            if (genero.equals("Todos")) {
                genero = null;
            }

            String fechaTexto = jFormattedTextField1.getText().trim();
            String fecha = fechaTexto; // De entrada es el String original

            if (fechaTexto.isEmpty() || fechaTexto.equals("dd/mm/aaaa") || fechaTexto.equals("__/__/____")) {
                fecha = null;
            }

            List<AlbumDTO> resultados = albumNegocio.buscarAlbumes(texto, genero, fecha);

            jPanel1.removeAll();

            for (AlbumDTO album : resultados) {
                pnlAlbum panelItem = new pnlAlbum(album);

                jPanel1.add(panelItem);

                jPanel1.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
            }

            jPanel1.revalidate();
            jPanel1.repaint();

        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error en la búsqueda: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_BtnBuscar1ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void cbxGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxGeneroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar1;
    private javax.swing.JButton BtnCerrarSesion;
    private javax.swing.JButton BtnInsert;
    private javax.swing.JLabel LblAlbumes;
    private javax.swing.JLabel LblArtistas;
    private javax.swing.JLabel LblCanciones;
    private javax.swing.JLabel LblInicio;
    private javax.swing.JLabel LblInicio2;
    private javax.swing.JLabel LblPerfil;
    private javax.swing.JPanel PnlFondo;
    private javax.swing.JTextField TxTBuscador1;
    private javax.swing.JComboBox<String> cbxGenero;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblGenero1;
    // End of variables declaration//GEN-END:variables
}
