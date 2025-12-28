package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enums.Colores;
import gui.enums.Generos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {

    //inicio
     JPanel panel1;
     JTabbedPane tabbedPane1;

    //disco
     JPanel panelNombreDis;
     JPanel panelGeneroDis;
     JPanel panelPrecioDis;
     JPanel panelFechaDis;
     JPanel panelColorDis;
     JPanel panelDiscoDis;
     JPanel panelArtistaDis;
     JPanel panelBtnGuardarDis;
     JPanel panelBtnAddDis;
     JPanel panelBtnModificarDis;
     JTextField txtNombreDis;
     DatePicker fechaDis;
     JTable tableDisco;
     JComboBox boxGeneroDis;
     JComboBox boxGeneroArt;
     JTextField txtCancionesDis;
     JPanel panelCancionesDis;
     JComboBox boxDiscoDis;
     JComboBox boxArtDis;
     JButton btnGuardarDisco;
     JButton btnAddDisco;
     JButton btnModificarDisco;
     JPanel panel1Disco;
     JPanel panel2Disco;
     JPanel panel3Disco;
     JPanel panel4Disco;
     JSpinner spinnerPrecio;
     JComboBox boxColores;

    //discografica
     JTextField txtPaisDisc;
     JTextField webTxtDisc;
     JTextField emailTxtDis;
     JTextField txtTelefonoDisc;
     JButton btnGuardarDisc;
     JButton btnAddDisc;
     JButton btnModificarDisc;
     JPanel panelModiDisc;
     JPanel panelAddDisc;
     JPanel panelGuardarDisc;
     JPanel panel3Disc;
     JPanel panel2Disc;
     JPanel panelEmail;
     JPanel panelTelefono;
     JPanel panelWeb;
     JPanel panelPaisDisc;
     JPanel panelNombreDisc;
     JPanel panel1Disc;
     JTable tableDiscografica;
     JComboBox boxNombreDiscografica;

    //artista
     JPanel panelDisco;
     JPanel panelArtista;
     JPanel panelDiscografica;
     JTextField txtNombreArt;
     JPanel panelNombreArt;
     JPanel panelGeneroArt;
     JPanel panel1Artista;
     JPanel panel2Artista;
     JComboBox boxDiscoArt;
     JButton btnGuardar;
     JPanel panelPaisArt;
     JPanel panelDiscoArt;
     JPanel panel3Artista;
     JPanel panelBtnGuardarArt;
     JButton btnAdd;
     JButton btnModificar;
     JPanel panelBtnAddArt;
     JPanel panelModificarArt;
     JTable tableArtista;
     JTextField txtPaisArt;
    private JTextField txtBuscarArt;
    private JPanel panelBuscarArt;
    private JButton btnBuscarArt;
    private JPanel panelBuscarDis;
    private JButton btnBuscarDis;
    private JTextField txtBuscarDis;
    private JPanel panelBuscarDisc;
    private JButton btnBuscarDisc;
    private JTextField txtBuscarDisc;
    private JPanel panelOrdenarArt;
    private JButton btnOrdenarArt;
    private JPanel panelOrdenarDisc;
    private JButton btnOrdenar;
    private JButton btnOrdenarDis;
    private JPanel panelOrdenarDis;


    //JMenuBar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;

    DefaultTableModel dtmArtistas;
    DefaultTableModel dtmDiscografica;
    DefaultTableModel dtmDisco;

    OptionDialog optionDialog;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        super("MitoStore");
        initFrame();
    }

    public void initFrame() {
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));
        this.setLocationRelativeTo(null);
        setMenu();
        setEnumComboBox();
    }

    private void setEnumComboBox() {
        //recorrer los enumerados y los cargo en el comboBox correspondiente
        //.values cogemos valores del enumerado
        //.getValor los añadimos al combo
        for (Colores constant: Colores.values()) {
            boxColores.addItem(constant.getValor());
        }
        boxColores.setSelectedIndex(-1);
        for (Generos constant: Generos.values()) {
            boxGeneroDis.addItem(constant.getValor());
            boxGeneroArt.addItem(constant.getValor());
        }
        boxGeneroDis.setSelectedIndex(-1);
        boxGeneroArt.setSelectedIndex(-1);
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir=new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    private void setTableModels() {
        //librosTabla, autoresTabla, editorialesTabla
        this.dtmArtistas=new DefaultTableModel();
        this.tableArtista.setModel(dtmArtistas);

        this.dtmDiscografica=new DefaultTableModel();
        this.tableDiscografica.setModel(dtmDiscografica);

        this.dtmDisco=new DefaultTableModel();
        this.tableDisco.setModel(dtmDisco);
    }

    private void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword = new JPasswordField();
        //dimension al cuadro de texto
        adminPassword.setPreferredSize(new Dimension(100,26));
        Object[] options=new Object[] {adminPassword,btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña",JOptionPane.WARNING_MESSAGE,
                JOptionPane.YES_NO_OPTION,null,options);
        adminPasswordDialog = new JDialog(this,"Opciones",true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

}
