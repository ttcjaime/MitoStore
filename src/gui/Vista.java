package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.enums.Colores;
import gui.enums.Generos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {

    //inicio
    private JPanel panel1;
    private JTabbedPane tabbedPane1;

    //disco
    private JPanel panelNombreDis;
    private JPanel panelGeneroDis;
    private JPanel panelPrecioDis;
    private JPanel panelFechaDis;
    private JPanel panelColorDis;
    private JPanel panelDiscoDis;
    private JPanel panelArtistaDis;
    private JPanel panelBtnGuardarDis;
    private JPanel panelBtnAddDis;
    private JPanel panelBtnModificarDis;
    private JTextField txtNombreDis;
    private DatePicker fechaDis;
    private JTable tableDisco;
    private JComboBox boxGeneroDis;
    private JComboBox boxGeneroArt;
    private JTextField txtCancionesDis;
    private JPanel panelCancionesDis;
    private JComboBox boxDiscoDis;
    private JComboBox boxArtDis;
    private JButton btnGuardarDisco;
    private JButton btnAddDisco;
    private JButton btnModificarDisco;
    private JPanel panel1Disco;
    private JPanel panel2Disco;
    private JPanel panel3Disco;
    private JPanel panel4Disco;
    private JSpinner spinnerPrecio;
    private JComboBox boxColores;

    //discografica
    private JTextField txtPaisDisc;
    private JTextField webTxtDisc;
    private JTextField emailTxtDis;
    private JTextField txtTelefonoDisc;
    private JButton btnGuardarDisc;
    private JButton btnAddDisc;
    private JButton btnModificarDisc;
    private JPanel panelModiDisc;
    private JPanel panelAddDisc;
    private JPanel panelGuardarDisc;
    private JPanel panel3Disc;
    private JPanel panel2Disc;
    private JPanel panelEmail;
    private JPanel panelTelefono;
    private JPanel panelWeb;
    private JPanel panelPaisDisc;
    private JPanel panelNombreDisc;
    private JPanel panel1Disc;
    private JTable tableDiscografica;
    private JComboBox boxNombreDiscografica;

    //artista
    private JPanel panelDisco;
    private JPanel panelArtista;
    private JPanel panelDiscografica;
    private JTextField txtNombreArt;
    private JPanel panelNombreArt;
    private JPanel panelGeneroArt;
    private JPanel panel1Artista;
    private JPanel panel2Artista;
    private JComboBox boxDiscoArt;
    private JButton btnGuardar;
    private JPanel panelPaisArt;
    private JPanel panelDiscoArt;
    private JPanel panel3Artista;
    private JPanel panelBtnGuardarArt;
    private JButton btnAdd;
    private JButton btnModificar;
    private JPanel panelBtnAddArt;
    private JPanel panelModificarArt;
    private JTable tableArtista;
    private JTextField txtPaisArt;


    //JMenuBar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;

    DefaultTableModel dtmArtistas;
    DefaultTableModel dtmDiscografica;
    DefaultTableModel dtmDisco;

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

}
