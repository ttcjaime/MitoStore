package gui;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel panelDisco;
    private JPanel panelArtista;
    private JPanel panelDiscografica;
    private JTextField textField1;
    private JPanel panelNombre;
    private JPanel panelGenero;
    private JPanel primerPanel;
    private JPanel segundoPanel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton btnGuardar;
    private JPanel panelPais;
    private JPanel panelDiscografia;
    private JPanel panelTipo;
    private JPanel tercerPanel;
    private JPanel panelBtnGuardar;
    private JButton btnAdd;
    private JButton btnModificar;
    private JPanel panelBtnAdd;
    private JPanel panelModificar;
    private JTable table1;

    public Vista() {
        super("MitoStore");
        initFrame();
    }

    public void initFrame() {
        this.setVisible(true);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));
        this.setLocationRelativeTo(null);
    }

}
