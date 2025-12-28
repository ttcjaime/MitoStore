package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    boolean refrescar;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.conectar();
        System.out.println("Conexión después de conectar = " + modelo.getConexion());
        refrescarTodo();
        setOptions();
    }

    private void addActionListener(ActionListener listener) {
        vista.btnAdd.addActionListener(listener);
        vista.btnAddDisc.addActionListener(listener);
        vista.btnAddDisco.addActionListener(listener);
        vista.btnGuardar.addActionListener(listener);
        vista.btnGuardarDisc.addActionListener(listener);
        vista.btnGuardarDisco.addActionListener(listener);
        vista.btnModificarDisc.addActionListener(listener);
        vista.btnModificar.addActionListener(listener);
        vista.btnModificarDisco.addActionListener(listener);
    }

    private void refrescarTodo() {
        refrescarArtistas();
        refrescar = false;
    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    void iniciar() {
        vista.tableArtista.setCellSelectionEnabled(true); //teniendo esto en true, el usuario puede seleccionar una celda en especifico
        ListSelectionModel cellSelectedModel = vista.tableArtista.getSelectionModel();
        cellSelectedModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //solo se puede seleccionar una celda a la vez

        cellSelectedModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                        int row = vista.tableArtista.getSelectedRow();
                        vista.txtNombreArt.setText(String.valueOf(vista.tableArtista.getValueAt(row, 1)));
                        vista.txtPaisArt.setText(String.valueOf(vista.tableArtista.getValueAt(row, 2)));
                        vista.boxGeneroArt.setSelectedItem(String.valueOf(vista.tableArtista.getValueAt(row, 3)));
                        vista.boxDiscoArt.setSelectedItem(String.valueOf(vista.tableArtista.getValueAt(row, 4)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                            borrarCamposArtista();
                        } else if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                            borrarCamposDiscos();
                        } else if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                            borrarCamposDiscografica();
                        }
                    }
                }
            }
        });

        vista.tableDiscografica.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectedModel2 = vista.tableDisco.getSelectionModel();
        cellSelectedModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectedModel2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                        int row = vista.tableDisco.getSelectedRow();
                        vista.txtNombreDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 1)));
                        vista.txtCancionesDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                        vista.boxArtDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 3)));
                        vista.boxGeneroDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 4)));
                        vista.boxDiscoDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
                        vista.boxColores.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 6)));
                        vista.fechaDis.setDate((Date.valueOf(String.valueOf(vista.tableDisco.getValueAt(row, 7)))).toLocalDate());
                        vista.spinnerPrecio.setValue((Integer) vista.tableDisco.getValueAt(row, 8));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                            borrarCamposArtista();
                        } else if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                            borrarCamposDiscos();
                        } else if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                            borrarCamposDiscografica();
                        }
                    }
                }
            }
        });

        vista.tableDiscografica.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectedModel3 = vista.tableArtista.getSelectionModel();
        cellSelectedModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectedModel3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                        int row = vista.tableDiscografica.getSelectedRow();
                        vista.boxNombreDiscografica.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 1)));
                        vista.txtPaisDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                        vista.webTxtDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 3)));
                        vista.emailTxtDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 4)));
                        vista.txtTelefonoDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                            borrarCamposArtista();
                        } else if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                            borrarCamposDiscos();
                        } else if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                            borrarCamposDiscografica();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                int row = vista.tableArtista.getSelectedRow();
                vista.txtNombreArt.setText(String.valueOf(vista.tableArtista.getValueAt(row, 1)));
                vista.txtPaisArt.setText(String.valueOf(vista.tableArtista.getValueAt(row, 2)));
                vista.boxGeneroArt.setSelectedItem(String.valueOf(vista.tableArtista.getValueAt(row, 3)));
                vista.boxDiscoArt.setSelectedItem(String.valueOf(vista.tableArtista.getValueAt(row, 4)));
            } else if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                int row = vista.tableDisco.getSelectedRow();
                vista.txtNombreDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 1)));
                vista.txtCancionesDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                vista.boxArtDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 3)));
                vista.boxGeneroDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 4)));
                vista.boxDiscoDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
                vista.boxColores.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 6)));
                vista.fechaDis.setDate((Date.valueOf(String.valueOf(vista.tableDisco.getValueAt(row, 7)))).toLocalDate());
                vista.spinnerPrecio.setValue((Integer) vista.tableDisco.getValueAt(row, 8));
            } else if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                int row = vista.tableDiscografica.getSelectedRow();
                vista.boxNombreDiscografica.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 1)));
                vista.txtPaisDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                vista.webTxtDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 3)));
                vista.emailTxtDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 4)));
                vista.txtTelefonoDisc.setText(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
            } else if (e.getValueIsAdjusting()
                    && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                    borrarCamposDiscografica();
                } else if (e.getSource().equals(vista.tableDisco.getSelectionModel())) {
                    borrarCamposDiscos();
                } else if (e.getSource().equals(vista.tableArtista.getSelectionModel())) {
                    borrarCamposArtista();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private void borrarCamposDiscografica() {
        vista.boxNombreDiscografica.setSelectedIndex(-1);
        vista.txtPaisDisc.setText("");
        vista.webTxtDisc.setText("");
        vista.emailTxtDis.setText("");
        vista.txtTelefonoDisc.setText("");
    }

    private void borrarCamposArtista() {
        vista.txtNombreArt.setText("");
        vista.txtPaisArt.setText("");
        vista.boxGeneroArt.setSelectedIndex(-1);
        vista.boxDiscoArt.setSelectedIndex(-1);
    }

    private void borrarCamposDiscos() {
        vista.txtNombreDis.setText("");
        vista.txtCancionesDis.setText("");
        vista.boxArtDis.setSelectedIndex(-1);
        vista.boxGeneroDis.setSelectedIndex(-1);
        vista.boxDiscoDis.setSelectedIndex(-1);
        vista.boxColores.setSelectedIndex(-1);
        vista.fechaDis.setText("");
        vista.spinnerPrecio.setValue(0);
    }

    private void refrescarArtistas() {
        try {
            vista.tableArtista.setModel(construirTableModelArtista(modelo.consultarArtista()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelArtista(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnLabel(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        System.out.println(rs);
        vista.dtmArtistas.setDataVector(data, columnNames);

        return vista.dtmArtistas;

    }

    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }

    private void setOptions() {
        vista.optionDialog.txtIp.setText(modelo.getIp());
        vista.optionDialog.txtUsuario.setText(modelo.getUser());
        vista.optionDialog.txtPswd.setText(modelo.getPassword());
        vista.optionDialog.txtAdminPswd.setText(modelo.getAdminPassword());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
