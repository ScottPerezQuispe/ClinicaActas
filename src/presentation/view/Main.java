package presentation.view;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import domain.entidades.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import presentation.drawer.MyDrawerBuilder;
import raven.drawer.Drawer;
import raven.popup.GlassPanePopup;
import raven.tabbed.WindowsTabbed;
import raven.toast.Notifications;
//import raven.drawer.Drawer;
//import raven.drawer.MyDrawerBuilder;
//import raven.login.Login;
//import raven.popup.GlassPanePopup;
//import raven.tabbed.WindowsTabbed;
//import raven.toast.Notifications;

/**
 *
 * @author RAVEN
 */
public class Main extends javax.swing.JFrame {
    private Usuario usuarioLogueado;
    public static Main main;
    // 🎯 Nuevos campos para el nombre y el rol
    private JLabel lblNombreUsuario;
    private JLabel lblRolUsuario;
  
    public Main() {
        initComponents();
         GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        
      // Creamos un nuevo panel que será el único contenido del JFrame
    JPanel mainContainer = new JPanel(new BorderLayout()); 
    
    // --- Lógica del Header ---
    
    JPanel headerPanel = new JPanel();
   // 2a. Configuración visual esencial para que se vea y se alinee a la DERECHA
       // Recomendado: Gris muy suave (Lighter Gray, #EFEFEF)
headerPanel.setBackground(new Color(239, 239, 239));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        // 🛑 CLAVE PARA LA ESQUINA DERECHA: Usar FlowLayout.RIGHT
        headerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0)); 
    
    lblNombreUsuario = new JLabel("Bienvenido:");
    lblRolUsuario = new JLabel("Rol:");
    
     // 3a. Estilo de los labels
        // Cambiando el color del texto a negro para el fondo claro
lblNombreUsuario.setForeground(new Color(51, 51, 51)); // Gris oscuro/casi negro
lblRolUsuario.setForeground(new Color(0, 102, 204)); // Azul para el rol
        lblNombreUsuario.setFont(lblNombreUsuario.getFont().deriveFont(Font.BOLD, 14f));
    headerPanel.add(lblNombreUsuario);
    headerPanel.add(lblRolUsuario);

    // 1. Añadir el header al NORTE del contenedor
    mainContainer.add(headerPanel, BorderLayout.NORTH);
    
    // 2. Añadir el 'body' (el panel central) al CENTRO del contenedor
    // body ya fue creado en initComponents()
    mainContainer.add(body, BorderLayout.CENTER);
    
    // 3. Reemplazar el layout del JFrame
    // Esto es necesario porque initComponents lo establece, pero esta vez,
    // solo establecemos el JPanel que tiene el BorderLayout.
    setContentPane(mainContainer);

       // init();
    }

    private void init(Usuario usuario) {
    
       
        MyDrawerBuilder myDrawerBuilder = new MyDrawerBuilder(this,usuario);
        Drawer.getInstance().setDrawerBuilder(myDrawerBuilder);
        WindowsTabbed.getInstance().install(this, body);
     //  login();
    }

    public void login() {
       // if (loginForm == null) {
      //      loginForm = new LoginPanel();
      //  }
      WindowsTabbed.getInstance().showTabbed(false);
       // loginForm.applyComponentOrientation(getComponentOrientation());
     //  setContentPane(loginForm);
     
     
        revalidate();
        repaint();
    }

    public void showMainForm(Usuario usuario) {
        this.usuarioLogueado = usuario;
        // Actualizar los labels con la información dinámica de la BD
    lblNombreUsuario.setText("Bienvenido: " + usuario.getNombreCompleto());
    lblRolUsuario.setText("Rol: " + usuario.getNombreRol());
        init(usuario);
        WindowsTabbed.getInstance().showTabbed(true);
        WindowsTabbed.getInstance().removeAllTabbed();
       // setContentPane(body);
        revalidate();
        repaint();
    }
    public void showPanel(javax.swing.JPanel panel) {
    // 1. Ocultar el sistema de pestañas si está activo
    WindowsTabbed.getInstance().showTabbed(true); 
        raven.drawer.Drawer.getInstance().closeDrawer();
    // 2. Limpiar el contenedor central 'body'
    body.removeAll();
    
    // 3. Establecer el layout (GridBagLayout o BorderLayout son comunes para un solo panel)
    // Usaremos un BorderLayout para que el panel ocupe todo el espacio.
    body.setLayout(new java.awt.BorderLayout());
    
    // 4. Añadir el nuevo panel al centro
    body.add(panel, java.awt.BorderLayout.CENTER);
    
    // 5. Refrescar la ventana para que los cambios se vean
    body.revalidate();
    body.repaint();
}

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1193, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 173, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacDarkLaf.setup();
        com.formdev.flatlaf.themes.FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
           // main = new Main();
           // main.setVisible(true);
           
           
           // 1. Inicializa la instancia principal (Main), y la guarda en 'main'.
        Main mainInstance = new Main(); 
        
        // Asignar a la variable estática (opcional, pero mantiene compatibilidad)
        main = mainInstance; 
        
        // La ventana principal NO debe ser visible al inicio.
        
        // 2. Crea y MUESTRA el Login, pasándole la referencia de Main.
        new Login(mainInstance).setVisible(true); // <-- ¡Referencia pasada aquí!
        
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
