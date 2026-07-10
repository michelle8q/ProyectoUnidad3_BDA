/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import dtos.CancionDetallesDTO;
import dtos.GeneroDTO;
import dtos.UsuarioDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import negocio.ICancionNegocio;
import negocio.NegocioException;

/**
 *
 * @author cinca
 */
public class FrmCanciones extends javax.swing.JFrame {
    private UsuarioDTO usuarioActual;
    private Navegador navegador;
    private ICancionNegocio cancionNegocio;
    
    /**
     * Creates new form FrmCanciones
     */
    public FrmCanciones() {
        initComponents(); 
        
        configurarPantalla();
        cargarCanciones();

    }
    
    public FrmCanciones(UsuarioDTO usuarioActual, Navegador navegador, ICancionNegocio cancionNegocio) {
        initComponents();
        
        jScrollPane1.setPreferredSize(new Dimension(820, 514));
        this.setMinimumSize(new Dimension(1080, 650));
        this.setLocationRelativeTo(null);

        this.usuarioActual = usuarioActual;
        this.navegador = navegador;
        this.cancionNegocio = cancionNegocio;
        
        pnlContenedor.removeAll();
       // pnlContenedor.setLayout(new BoxLayout(pnlContenedor, BoxLayout.Y_AXIS));
        
        pnlMenuLateral1.configurarSesion(usuarioActual, navegador);
        configurarPantalla();
        cargarCanciones();
        
    }
    
    public void cargarCanciones() {
        try {
            List<CancionDetallesDTO> canciones = filtrarGenerosNoDeseados(cancionNegocio.consultarTodas());
            cargarCanciones(canciones);
        } catch (NegocioException ex) {
            mostrarError("No se pudieron cargar las canciones: " + ex.getMessage());
        }
    }
    
    private void configurarPantalla() {   
        pnlBuscador1.setTitulo("Canciones");
        pnlBuscador1.addBuscarActionListener(evt -> buscarCanciones());

        pnlContenedor.removeAll();
        pnlContenedor.setLayout(new BoxLayout(pnlContenedor, BoxLayout.Y_AXIS));
        pnlContenedor.setBackground(new Color(51, 51, 51));
    }

    private void buscarCanciones() {
        String texto = pnlBuscador1.getTextoBusqueda();

        try {
            List<CancionDetallesDTO> canciones = texto.isBlank()
                    ? cancionNegocio.consultarTodas()
                    : cancionNegocio.buscarPorTexto(texto);
            
            canciones = filtrarGenerosNoDeseados(canciones);
            
            cargarCanciones(canciones);
        } catch (NegocioException ex) {
            mostrarError("Error en la busqueda: " + ex.getMessage());
        }
    }

    private void cargarCanciones(List<CancionDetallesDTO> canciones) {
        pnlContenedor.removeAll();

        if (canciones == null || canciones.isEmpty()) {
            JLabel lblSinResultados = new JLabel("No se encontraron canciones.");
            lblSinResultados.setForeground(Color.WHITE);
            lblSinResultados.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
            lblSinResultados.setAlignmentX(LEFT_ALIGNMENT);

            pnlContenedor.add(Box.createRigidArea(new Dimension(16, 20)));
            pnlContenedor.add(lblSinResultados);
        } else {
            for (CancionDetallesDTO cancion : canciones) {
                boolean esFavorita = false;
                pnlCancion panelCancion = new pnlCancion(cancion, esFavorita);
                panelCancion.setAlignmentX(LEFT_ALIGNMENT);

                pnlContenedor.add(panelCancion);
                pnlContenedor.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        pnlContenedor.revalidate();
        pnlContenedor.repaint();
    }
    
     private List<CancionDetallesDTO> filtrarGenerosNoDeseados(List<CancionDetallesDTO> canciones) {
        if (usuarioActual.getGenerosNoDeseados() == null || usuarioActual.getGenerosNoDeseados().isEmpty()) {
            return canciones;
        }

        List<CancionDetallesDTO> resultado = new ArrayList<>();
        for (CancionDetallesDTO cancion : canciones) {
            boolean restringido = false;

            for (GeneroDTO noDeseado : usuarioActual.getGenerosNoDeseados()) {
                if (cancion.getGenero() != null
                        && cancion.getGenero().equalsIgnoreCase(noDeseado.getNombre())) {
                    restringido = true;
                    break;
                }
            }

            if (!restringido) {
                resultado.add(cancion);
            }
        }
        return resultado;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        pnlBuscador1 = new presentacion.PnlBuscador();
        pnlMenuLateral1 = new presentacion.PnlMenuLateral();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlContenedor = new javax.swing.JPanel();
        pnlCancion = new presentacion.pnlCancion();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        pnlPrincipal.setBackground(new java.awt.Color(51, 51, 51));
        pnlPrincipal.setForeground(new java.awt.Color(51, 51, 51));
        pnlPrincipal.setMinimumSize(new java.awt.Dimension(1086, 617));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlContenedor.setBackground(new java.awt.Color(51, 51, 51));
        pnlContenedor.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout pnlContenedorLayout = new javax.swing.GroupLayout(pnlContenedor);
        pnlContenedor.setLayout(pnlContenedorLayout);
        pnlContenedorLayout.setHorizontalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContenedorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlContenedorLayout.setVerticalGroup(
            pnlContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContenedorLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(pnlCancion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(293, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(pnlContenedor);

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMenuLateral1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(pnlBuscador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlMenuLateral1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private presentacion.PnlBuscador pnlBuscador1;
    private presentacion.pnlCancion pnlCancion;
    private javax.swing.JPanel pnlContenedor;
    private presentacion.PnlMenuLateral pnlMenuLateral1;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables
}
