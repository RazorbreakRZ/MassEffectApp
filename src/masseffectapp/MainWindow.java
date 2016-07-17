/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Razorbreak
 */
public class MainWindow extends javax.swing.JFrame {
    //Variables Gamercard
    private PlayerInformation player;
    private Config cfg;
    private Gamercard gg;    
    //Variables Build
    
    
    //Variables Selector
    private ListaPersonajes lp=new ListaPersonajes();
    private ListaArmas lw=new ListaArmas();
    private ListaArmas lw2=new ListaArmas();
    private DefaultListModel lm=new DefaultListModel();
    private DefaultListModel lmW=new DefaultListModel();
    private DefaultListModel lmW2=new DefaultListModel();
    private ArrayList<Personaje> lpjs=new ArrayList();
    private ArrayList<Arma> lwpns=new ArrayList();
    private ArrayList<Arma> lwpns2=new ArrayList();
    
    //Otras variables
    private SystemTray tray;
    private TrayIcon icon;
    private MainWindow me = this;
    private Timer timer;//Hilo para controlar el temporizador
    private boolean isLoading = true;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icon.png")));
        this.setLocationRelativeTo(null);
        //Pestaña Gamercard
        player = new PlayerInformation();
        cfg = new Config();
        cfg.loadConfiguration(this.player);
        gg = new Gamercard(this.player,this.cfg,this.jPanel_ResultadoGC);
        gg.scanResources();
        updateGamercardTextFields();
        //Pestaña Build
        
        //Pestaña Selector
        updatePJList();
        updateWpnList();
        
        //Icono de notificación
        if(SystemTray.isSupported()){
            tray = SystemTray.getSystemTray();
            try {
                Image imagenIcono = Toolkit.getDefaultToolkit().getImage(cfg.resourcesFolder+"\\gold.png");
                
                MouseListener mouseListener = new MouseListener() {
                    public void mouseClicked(MouseEvent e) {
                        //System.out.println("Icono del System Tray - Mouse clicked!");
                        me.setVisible(!me.isVisible());
                    }
                    public void mouseEntered(MouseEvent e) {
                        //System.out.println("Icono del System Tray - Mouse entered!");
                    }
                    public void mouseExited(MouseEvent e) {
                        //System.out.println("Icono del System Tray - Mouse exited!");
                    }
                    public void mousePressed(MouseEvent e) {
                        //System.out.println("Icono del System Tray - Mouse pressed!");
                    }
                    public void mouseReleased(MouseEvent e) {
                        //System.out.println("Icono del System Tray - Mouse released!");
                    }
                };
                //iniciamos el objeto TrayIcon
                icon = new TrayIcon(imagenIcono, "MassEffect3 App");
                icon.setImageAutoSize(true);
                icon.addMouseListener(mouseListener);
                tray.add(icon);
                
            } catch (Exception ex) {}
        }
        isLoading = false;
        this.tray.getTrayIcons()[0].displayMessage("ME3App", "Pincha aquí para Ocultar/Mostrar", TrayIcon.MessageType.INFO);
    }
    
    private void updatePJList(){
        this.jTextField_RandomPJ.setText("");
        this.jTextField_RandomPJ.setToolTipText(null);
        lm.clear();
        lpjs= lp.searchByRazaClase(this.selectorRaza.getSelectedItem().toString(), this.selectorClase.getSelectedItem().toString());
        for(int i=0;i<lpjs.size();i++){
            lm.addElement(lpjs.get(i).toString());
        }
        this.jList_Personajes.setModel(lm);
        String title=lpjs.size()+" Personajes disponibles";
        this.jPanel_ListaPersonajes.setBorder(BorderFactory.createTitledBorder(title));
    }
    
    private void updateWpnList(){
        int nArmas=0;
        String w;
        this.jTextField_RandomWeapon1.setText("");
        this.jTextField_RandomWeapon2.setText("");
        this.jTextField_RandomWeapon1.setToolTipText(null);
        this.jTextField_RandomWeapon2.setToolTipText(null);
        lmW.clear();
        lmW2.clear();
        lwpns= lw.searchByTipo(this.selectorTipoArma.getSelectedItem().toString());        
        for(int i=0;i<lwpns.size();i++){
            lmW.addElement(lwpns.get(i).toString());
            nArmas++;
        }
        this.jList_Armas.setModel(lmW);        
        if(this.jCheckBox_DosArmas.isSelected()){
            lwpns2= lw2.searchByTipo(this.selectorTipoArma2.getSelectedItem().toString());
            for(int i=0;i<lwpns2.size();i++){
                lmW2.addElement((w=lwpns2.get(i).toString()));
                if(!lmW.contains(w)){nArmas++;}
            }
            this.jList_Armas2.setModel(lmW2);
        }        
        String title=nArmas+" Armas disponibles";
        this.jPanel_ListaArmas.setBorder(BorderFactory.createTitledBorder(title));
    }
    
    public void updateGamercardTextFields(){
        this.jTextField_Gamertag.setText(this.player.name);
        this.jTextField_URL.setText("http://social.bioware.com/n7hq/?name="+this.player.name+"&platform=pc");
        this.jButton_VerPerfilOnline.setEnabled(!this.player.name.equals(""));
        this.jTextField_Pais.setText(this.player.country);
        this.jTextField_N7.setText(this.player.n7level);
        this.jTextField_PntDesafio.setText(this.player.challenges);
        this.selector_Titulo.setSelectedItem(this.player.title);
        this.jTextField_Partidas.setText(this.player.games);
        this.jTextField_Horas.setText(this.player.hours);
        this.jTextField_Creditos.setText(this.player.credits);
        this.selector_Operacion.setSelectedItem(this.player.operation);
        this.jCheckBox_OpCompletada.setSelected(this.player.opUnlocked);
        this.jTextField_LastDesafio.setText(this.player.lastChallenge);
        this.jCheckBox_DesfDorado.setSelected(this.player.lastChType);
        this.jCheckBox_OpCompletada.setSelected(true);
        
        if(this.cfg.saveFolder!=null){
            this.jTextField_SaveFolder.setText(this.cfg.saveFolder.getPath());
        }
    }
    
    public void updateGamercard(){
        if(!isLoading){
            this.jButton_Guardar.setEnabled(true);
            this.player.name=this.jTextField_Gamertag.getText();
            this.jTextField_URL.setText("http://social.bioware.com/n7hq/?name="+this.player.name+"&platform=pc");
            this.jButton_VerPerfilOnline.setEnabled(!this.player.name.equals(""));
            this.player.country=this.jTextField_Pais.getText();
            this.player.n7level=this.jTextField_N7.getText();
            this.player.challenges=this.jTextField_PntDesafio.getText();
            this.player.title=this.selector_Titulo.getSelectedItem().toString();
            this.player.games=this.jTextField_Partidas.getText();
            this.player.hours=this.jTextField_Horas.getText();
            this.player.credits=this.jTextField_Creditos.getText();
            this.player.operation=this.selector_Operacion.getSelectedItem().toString();
            this.player.opUnlocked=this.jCheckBox_OpCompletada.isSelected() && this.selector_Operacion.getSelectedIndex()!=0;
            this.player.lastChallenge=this.jTextField_LastDesafio.getText();
            this.player.lastChUnlocked=!this.jTextField_LastDesafio.getText().equals("");
            this.player.lastChType=this.jCheckBox_DesfDorado.isSelected();
            this.gg.generateGamercard();
            this.gg.forceRedraw();
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

        jTabbedPanel = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        selectorClase = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        selectorRaza = new javax.swing.JComboBox();
        jPanel_ListaPersonajes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Personajes = new javax.swing.JList();
        jPanel6 = new javax.swing.JPanel();
        jButton_RandomPJ = new javax.swing.JButton();
        jTextField_RandomPJ = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        selectorTipoArma = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        selectorTipoArma2 = new javax.swing.JComboBox();
        jPanel_ListaArmas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_Armas = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_Armas2 = new javax.swing.JList();
        jPanel10 = new javax.swing.JPanel();
        jButton_RandomWeapon = new javax.swing.JButton();
        jTextField_RandomWeapon1 = new javax.swing.JTextField();
        jTextField_RandomWeapon2 = new javax.swing.JTextField();
        jCheckBox_DosArmas = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_Gamertag = new javax.swing.JTextField();
        jTextField_Pais = new javax.swing.JTextField();
        selector_Titulo = new javax.swing.JComboBox();
        jTextField_N7 = new javax.swing.JTextField();
        jTextField_PntDesafio = new javax.swing.JTextField();
        jTextField_Horas = new javax.swing.JTextField();
        jTextField_Partidas = new javax.swing.JTextField();
        jTextField_Creditos = new javax.swing.JTextField();
        jTextField_LastDesafio = new javax.swing.JTextField();
        selector_Operacion = new javax.swing.JComboBox();
        jCheckBox_OpCompletada = new javax.swing.JCheckBox();
        jCheckBox_DesfDorado = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jPanel_ResultadoGC = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jTextField_SaveFolder = new javax.swing.JTextField();
        jButton_GuardarComo = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_VerPerfilOnline = new javax.swing.JButton();
        jTextField_URL = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jButton_AbrirExplorador = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_Salir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem_AcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mass Effect 3 - Tools (by RazorbreakRZ)");
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jTabbedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPanelMouseClicked(evt);
            }
        });

        jPanel3.setPreferredSize(new java.awt.Dimension(395, 400));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros de búsqueda"));

        jLabel1.setText("Clase:");

        selectorClase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Adepto", "Soldado", "Ingeniero", "Centinela", "Infiltrado", "Vanguardia" }));
        selectorClase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorClaseActionPerformed(evt);
            }
        });

        jLabel2.setText("Raza:");

        selectorRaza.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Humano", "Turiano", "Krogan", "Asari", "Salariano", "Drell", "Vorcha", "Batariano", "Geth", "Volus", "Recolector", "Sintético" }));
        selectorRaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorRazaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(selectorClase, 0, 101, Short.MAX_VALUE)
                    .addComponent(selectorRaza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(selectorClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(selectorRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_ListaPersonajes.setBorder(javax.swing.BorderFactory.createTitledBorder("Personajes"));

        jList_Personajes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_Personajes.setEnabled(false);
        jScrollPane1.setViewportView(jList_Personajes);

        javax.swing.GroupLayout jPanel_ListaPersonajesLayout = new javax.swing.GroupLayout(jPanel_ListaPersonajes);
        jPanel_ListaPersonajes.setLayout(jPanel_ListaPersonajesLayout);
        jPanel_ListaPersonajesLayout.setHorizontalGroup(
            jPanel_ListaPersonajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel_ListaPersonajesLayout.setVerticalGroup(
            jPanel_ListaPersonajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector aleatorio"));

        jButton_RandomPJ.setText("Escoger pj");
        jButton_RandomPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RandomPJActionPerformed(evt);
            }
        });

        jTextField_RandomPJ.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField_RandomPJ.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_RandomPJ)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_RandomPJ)
                .addGap(42, 42, 42))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jTextField_RandomPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_RandomPJ))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros de búsqueda"));

        jLabel3.setText("Tipo 1:");

        selectorTipoArma.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Pistola", "Subfusil", "Escopeta", "Fusil de asalto", "Rifle de francotirador" }));
        selectorTipoArma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorTipoArmaActionPerformed(evt);
            }
        });

        jLabel4.setText("Tipo 2:");

        selectorTipoArma2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "Pistola", "Subfusil", "Escopeta", "Fusil de asalto", "Rifle de francotirador" }));
        selectorTipoArma2.setEnabled(false);
        selectorTipoArma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectorTipoArma2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectorTipoArma, 0, 1, Short.MAX_VALUE)
                    .addComponent(selectorTipoArma2, 0, 1, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(selectorTipoArma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(selectorTipoArma2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_ListaArmas.setBorder(javax.swing.BorderFactory.createTitledBorder("Armas"));

        jList_Armas.setEnabled(false);
        jScrollPane2.setViewportView(jList_Armas);

        jList_Armas2.setEnabled(false);
        jScrollPane3.setViewportView(jList_Armas2);

        javax.swing.GroupLayout jPanel_ListaArmasLayout = new javax.swing.GroupLayout(jPanel_ListaArmas);
        jPanel_ListaArmas.setLayout(jPanel_ListaArmasLayout);
        jPanel_ListaArmasLayout.setHorizontalGroup(
            jPanel_ListaArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ListaArmasLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
        );
        jPanel_ListaArmasLayout.setVerticalGroup(
            jPanel_ListaArmasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Selector aleatorio"));

        jButton_RandomWeapon.setText("Escoger armas");
        jButton_RandomWeapon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RandomWeaponActionPerformed(evt);
            }
        });

        jTextField_RandomWeapon1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField_RandomWeapon1.setEnabled(false);

        jTextField_RandomWeapon2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField_RandomWeapon2.setEnabled(false);

        jCheckBox_DosArmas.setText("Dos armas");
        jCheckBox_DosArmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_DosArmasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_RandomWeapon1)
            .addComponent(jTextField_RandomWeapon2)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jButton_RandomWeapon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_DosArmas)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jTextField_RandomWeapon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_RandomWeapon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_RandomWeapon)
                    .addComponent(jCheckBox_DosArmas)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel_ListaPersonajes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_ListaArmas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel_ListaPersonajes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel_ListaArmas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPanel.addTab("Player selector", jPanel3);

        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        jLabel5.setText("Gamertag:");

        jLabel6.setText("País:");

        jLabel7.setText("Título:");

        jLabel8.setText("Nivel N7:");

        jLabel9.setText("Puntos desafío:");

        jLabel10.setText("Créditos:");

        jLabel11.setText("Horas de juego:");

        jLabel12.setText("Partidas jugadas:");

        jLabel13.setText("Operación:");

        jLabel14.setText("Último desafío completado:");

        jTextField_Gamertag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_GamertagKeyReleased(evt);
            }
        });

        jTextField_Pais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_PaisKeyReleased(evt);
            }
        });

        selector_Titulo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "El mejor de los mejores", "Operador", "Duro", "Lobo solitario", "N7", "Nómada", "Dios biótico", "Matemágico", "Hallowed Hero", "Loyalist", "Corsario", "Héroe de los últimos días", "Rompecorazones", "Operación Tributo", "Rápidos, fuertes y juntos", "Insurgente", "Rebelde", "Superviviente", "Defensor", "Monstruo", "Operativo del consejo", "Maquinista", "Intruso", "Castigador", "Barrendero callejero", "Tirador", "Francotirador", "Maestría con el subfusil", "Pistolero", "Perro de guerra", "Guerrero", "Guardia letal", "Guerrero cibernético", "General recolector" }));
        selector_Titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selector_TituloActionPerformed(evt);
            }
        });

        jTextField_N7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_N7KeyReleased(evt);
            }
        });

        jTextField_PntDesafio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_PntDesafioKeyReleased(evt);
            }
        });

        jTextField_Horas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_HorasKeyReleased(evt);
            }
        });

        jTextField_Partidas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_PartidasKeyReleased(evt);
            }
        });

        jTextField_Creditos.setMaximumSize(new java.awt.Dimension(133, 2147483647));
        jTextField_Creditos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_CreditosKeyReleased(evt);
            }
        });

        jTextField_LastDesafio.setMaximumSize(new java.awt.Dimension(133, 2147483647));
        jTextField_LastDesafio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_LastDesafioKeyReleased(evt);
            }
        });

        selector_Operacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Goliath", "Raptor", "Resurgence", "Exorcist", "Silencer", "Shieldwall", "Mastiff", "Savage", "Broadside", "Overwatch", "Olympus", "Alloy", "Vigilance", "Patriot", "Overdrive", "Sed de sangre", "Valquiria", "Martillo pilón", "Altos hornos", "Chatarrero", "CORSARIO", "Detonador", "Acometida", "Álamo", "Profecía", "Génesis", "Gerónimo", "Ballistic", "Tormenta de fuego", "Anochecer", "Rompecorazones", "Tributo", "Impacto", "Estrella Polar (Normal)", "Estrella Polar (Locura)" }));
        selector_Operacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selector_OperacionActionPerformed(evt);
            }
        });

        jCheckBox_OpCompletada.setText("Completada");
        jCheckBox_OpCompletada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_OpCompletadaActionPerformed(evt);
            }
        });

        jCheckBox_DesfDorado.setText("Maestría (Dorado)");
        jCheckBox_DesfDorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_DesfDoradoActionPerformed(evt);
            }
        });

        jLabel15.setText("Cr.");

        jPanel_ResultadoGC.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));

        javax.swing.GroupLayout jPanel_ResultadoGCLayout = new javax.swing.GroupLayout(jPanel_ResultadoGC);
        jPanel_ResultadoGC.setLayout(jPanel_ResultadoGCLayout);
        jPanel_ResultadoGCLayout.setHorizontalGroup(
            jPanel_ResultadoGCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );
        jPanel_ResultadoGCLayout.setVerticalGroup(
            jPanel_ResultadoGCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );

        jLabel16.setText("Destino guardado:");

        jTextField_SaveFolder.setEnabled(false);

        jButton_GuardarComo.setText("...");
        jButton_GuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarComoActionPerformed(evt);
            }
        });

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.setEnabled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jButton_VerPerfilOnline.setText("Ver perfil online");
        jButton_VerPerfilOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VerPerfilOnlineActionPerformed(evt);
            }
        });

        jTextField_URL.setEditable(false);

        jLabel17.setText("URL del perfil:");

        jButton_AbrirExplorador.setText("Editar iconos");
        jButton_AbrirExplorador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AbrirExploradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel_ResultadoGC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_URL)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jButton_AbrirExplorador))
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 1, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField_SaveFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton_GuardarComo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton_VerPerfilOnline, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_Creditos, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_N7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selector_Titulo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_Gamertag, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Horas, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_Partidas, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_PntDesafio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_Pais, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_LastDesafio, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selector_Operacion, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_OpCompletada)
                            .addComponent(jCheckBox_DesfDorado))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_Gamertag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Pais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(selector_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_N7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_PntDesafio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jTextField_Horas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Partidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_Creditos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(selector_Operacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_OpCompletada))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField_LastDesafio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox_DesfDorado))
                .addGap(13, 13, 13)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_URL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_VerPerfilOnline)
                            .addComponent(jButton_AbrirExplorador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_GuardarComo)
                            .addComponent(jTextField_SaveFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Guardar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel_ResultadoGC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPanel.addTab("Gamercard", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jTabbedPanel.addTab("Build constructor", jPanel2);

        jMenu1.setText("Opciones");

        jMenuItem_Salir.setText("Salir");
        jMenuItem_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SalirActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Salir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem_AcercaDe.setText("Acerca de...");
        jMenuItem_AcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AcercaDeActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_AcercaDe);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPanel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectorClaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorClaseActionPerformed
        updatePJList();
    }//GEN-LAST:event_selectorClaseActionPerformed

    private void selectorRazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorRazaActionPerformed
        updatePJList();
    }//GEN-LAST:event_selectorRazaActionPerformed

    private void jButton_RandomPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RandomPJActionPerformed
        if(this.lpjs.size()>0){
            int index= (int)(Math.random()*this.lpjs.size());
            Personaje pj=this.lpjs.get(index);
            this.jTextField_RandomPJ.setText(pj.getName());
            this.jTextField_RandomPJ.setToolTipText("<html><b>"+pj.getName()+
                    "</b><br>Clase: "+pj.getClase()+
                    "<br>Raza: "+pj.getRaza()+
                    "</html>");
            this.jList_Personajes.setSelectedIndex(index);
            this.jList_Personajes.ensureIndexIsVisible(index);
        }
    }//GEN-LAST:event_jButton_RandomPJActionPerformed

    private void jButton_RandomWeaponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RandomWeaponActionPerformed
        String tipo1="";
        if(this.lwpns.size()>0){
            int index= (int)(Math.random()*this.lwpns.size());
            Arma wpn=this.lwpns.get(index);
            this.jTextField_RandomWeapon1.setText(wpn.getName());
            this.jTextField_RandomWeapon1.setToolTipText("<html><b>"+wpn.getName()+
                    "</b><br>Tipo: "+(tipo1=wpn.getTipo())+
                    "</html>");
            this.jList_Armas.setSelectedIndex(index);
            this.jList_Armas.ensureIndexIsVisible(index);
        }
        if(this.lwpns2.size()>0 && this.jCheckBox_DosArmas.isSelected()){
            int index2= (int)(Math.random()*this.lwpns2.size());
            Arma wpn2=this.lwpns2.get(index2);
            while(tipo1.equals(wpn2.getTipo())){
                index2= (int)(Math.random()*this.lwpns2.size());
                wpn2=this.lwpns2.get(index2);
            }
            this.jTextField_RandomWeapon2.setText(wpn2.getName());
            this.jTextField_RandomWeapon2.setToolTipText("<html><b>"+wpn2.getName()+
                    "</b><br>Tipo: "+wpn2.getTipo()+
                    "</html>");
            this.jList_Armas2.setSelectedIndex(index2);
            this.jList_Armas2.ensureIndexIsVisible(index2);
        }
    }//GEN-LAST:event_jButton_RandomWeaponActionPerformed

    private void selectorTipoArmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorTipoArmaActionPerformed
        int selectedIndex;
        if((selectedIndex=this.selectorTipoArma.getSelectedIndex())!=0 && selectedIndex==this.selectorTipoArma2.getSelectedIndex()){
            this.selectorTipoArma.setSelectedIndex(0);
        }
        updateWpnList();
    }//GEN-LAST:event_selectorTipoArmaActionPerformed

    private void jCheckBox_DosArmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_DosArmasActionPerformed
        this.selectorTipoArma2.setEnabled(this.jCheckBox_DosArmas.isSelected());
        if(!this.jCheckBox_DosArmas.isSelected()){
            this.selectorTipoArma2.setSelectedIndex(0);
        }
        updateWpnList();
    }//GEN-LAST:event_jCheckBox_DosArmasActionPerformed

    private void selectorTipoArma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectorTipoArma2ActionPerformed
        int selectedIndex;
        if((selectedIndex=this.selectorTipoArma2.getSelectedIndex())!=0 && selectedIndex==this.selectorTipoArma.getSelectedIndex()){
            this.selectorTipoArma2.setSelectedIndex(0);
        }
        updateWpnList();
    }//GEN-LAST:event_selectorTipoArma2ActionPerformed

    private void selector_TituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selector_TituloActionPerformed
        updateGamercard();
    }//GEN-LAST:event_selector_TituloActionPerformed

    private void selector_OperacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selector_OperacionActionPerformed
        updateGamercard();
    }//GEN-LAST:event_selector_OperacionActionPerformed

    private void jCheckBox_OpCompletadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_OpCompletadaActionPerformed
        updateGamercard();
    }//GEN-LAST:event_jCheckBox_OpCompletadaActionPerformed

    private void jCheckBox_DesfDoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_DesfDoradoActionPerformed
        updateGamercard();
    }//GEN-LAST:event_jCheckBox_DesfDoradoActionPerformed

    private void jButton_GuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarComoActionPerformed
        JFileChooser dialog = new JFileChooser();
        if(cfg.saveFolder!=null){
            dialog.setCurrentDirectory(cfg.saveFolder);
        }
        dialog.setDialogTitle("Seleccionar carpeta destino...");
        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        dialog.setAcceptAllFileFilterUsed(false);
        if (dialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
            cfg.saveFolder = dialog.getSelectedFile();
            updateGamercard();
        }
        this.jTextField_SaveFolder.setText(cfg.saveFolder.toString());
    }//GEN-LAST:event_jButton_GuardarComoActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        this.jButton_Guardar.setEnabled(false);
        this.cfg.saveConfiguration(player);
        this.gg.saveGamercard();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_VerPerfilOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VerPerfilOnlineActionPerformed
        String url = this.jTextField_URL.getText();
        
    }//GEN-LAST:event_jButton_VerPerfilOnlineActionPerformed

    private void jTextField_GamertagKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_GamertagKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_GamertagKeyReleased

    private void jMenuItem_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem_SalirActionPerformed

    private void jMenuItem_AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AcercaDeActionPerformed
        AboutBox a= new AboutBox(this,true);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem_AcercaDeActionPerformed

    private void jTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPanelMouseClicked
        if(this.jTabbedPanel.getSelectedIndex()==1){
            this.gg.forceRedraw();
        }
    }//GEN-LAST:event_jTabbedPanelMouseClicked

    private void jTextField_PaisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PaisKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_PaisKeyReleased

    private void jTextField_N7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_N7KeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_N7KeyReleased

    private void jTextField_PntDesafioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PntDesafioKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_PntDesafioKeyReleased

    private void jTextField_HorasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_HorasKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_HorasKeyReleased

    private void jTextField_PartidasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_PartidasKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_PartidasKeyReleased

    private void jTextField_CreditosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CreditosKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_CreditosKeyReleased

    private void jTextField_LastDesafioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_LastDesafioKeyReleased
        updateGamercard();
    }//GEN-LAST:event_jTextField_LastDesafioKeyReleased

    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        this.gg.forceRedraw();
    }//GEN-LAST:event_jPanel1MouseMoved

    private void jButton_AbrirExploradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AbrirExploradorActionPerformed
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            p = r.exec("explorer.exe "+cfg.resourcesFolder);
        } catch (Exception e) {}
    }//GEN-LAST:event_jButton_AbrirExploradorActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if(evt.getNewState()==ICONIFIED){
            me.setVisible(false);
            this.tray.getTrayIcons()[0].displayMessage("ME3App", "Minimizado en la bandeja del sistema", TrayIcon.MessageType.INFO);
        }else if(evt.getNewState()==NORMAL){
            me.setVisible(true);
        }
    }//GEN-LAST:event_formWindowStateChanged

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        try {
            //</editor-fold>
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_AbrirExplorador;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_GuardarComo;
    private javax.swing.JButton jButton_RandomPJ;
    private javax.swing.JButton jButton_RandomWeapon;
    private javax.swing.JButton jButton_VerPerfilOnline;
    private javax.swing.JCheckBox jCheckBox_DesfDorado;
    private javax.swing.JCheckBox jCheckBox_DosArmas;
    private javax.swing.JCheckBox jCheckBox_OpCompletada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList_Armas;
    private javax.swing.JList jList_Armas2;
    private javax.swing.JList jList_Personajes;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_AcercaDe;
    private javax.swing.JMenuItem jMenuItem_Salir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel_ListaArmas;
    private javax.swing.JPanel jPanel_ListaPersonajes;
    private javax.swing.JPanel jPanel_ResultadoGC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPanel;
    private javax.swing.JTextField jTextField_Creditos;
    private javax.swing.JTextField jTextField_Gamertag;
    private javax.swing.JTextField jTextField_Horas;
    private javax.swing.JTextField jTextField_LastDesafio;
    private javax.swing.JTextField jTextField_N7;
    private javax.swing.JTextField jTextField_Pais;
    private javax.swing.JTextField jTextField_Partidas;
    private javax.swing.JTextField jTextField_PntDesafio;
    private javax.swing.JTextField jTextField_RandomPJ;
    private javax.swing.JTextField jTextField_RandomWeapon1;
    private javax.swing.JTextField jTextField_RandomWeapon2;
    private javax.swing.JTextField jTextField_SaveFolder;
    private javax.swing.JTextField jTextField_URL;
    private javax.swing.JComboBox selectorClase;
    private javax.swing.JComboBox selectorRaza;
    private javax.swing.JComboBox selectorTipoArma;
    private javax.swing.JComboBox selectorTipoArma2;
    private javax.swing.JComboBox selector_Operacion;
    private javax.swing.JComboBox selector_Titulo;
    // End of variables declaration//GEN-END:variables
}
