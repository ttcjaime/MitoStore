package gui;
import util.Util;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    boolean refrescar;
    String camposVacios;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.conectar();
        setOptions();
        addActionListener(this);
        addItemListeners(this);
        addWindowListeners(this);
        refrescarTodo();
        iniciar();
    }

    private void addActionListener(ActionListener listener) {
        vista.btnAdd.addActionListener(listener);
        vista.btnAdd.setActionCommand("addArtista");
        vista.btnAddDisc.addActionListener(listener);
        vista.btnAddDisc.setActionCommand("addDiscografica");
        vista.btnAddDisco.addActionListener(listener);
        vista.btnAddDisco.setActionCommand("addDisco");
        vista.btnModificarDisc.addActionListener(listener);
        vista.btnModificarDisc.setActionCommand("modificarDiscografica");
        vista.btnModificar.addActionListener(listener);
        vista.btnModificar.setActionCommand("modificarArtista");
        vista.btnModificarDisco.addActionListener(listener);
        vista.btnModificarDisco.setActionCommand("modificarDisco");
        vista.btnBorrarArt.addActionListener(listener);
        vista.btnBorrarArt.setActionCommand("borrarArtista");
        vista.btnBorrarDis.addActionListener(listener);
        vista.btnBorrarDis.setActionCommand("borrarDisco");
        vista.btnBorrarDisc.addActionListener(listener);
        vista.btnBorrarDisc.setActionCommand("borrarDiscografica");
        vista.optionDialog.btnOpcionesGuardar.addActionListener(listener);
        vista.optionDialog.btnOpcionesGuardar.setActionCommand("guardarOpciones");
        vista.btnBuscarArt.addActionListener(listener);
        vista.btnBuscarArt.setActionCommand("buscarArtista");
        vista.itemDesconectar.addActionListener(listener);
        vista.btnBuscarDis.addActionListener(listener);
        vista.btnBuscarDis.setActionCommand("buscarDisco");
        vista.btnBuscarDisc.addActionListener(listener);
        vista.btnBuscarDisc.setActionCommand("buscarDiscografica");
        vista.btnVolverDisco.addActionListener(listener);
        vista.btnVolverDisco.setActionCommand("volverDisco");
        vista.btnVolverDisc.addActionListener(listener);
        vista.btnVolverDisc.setActionCommand("volverDiscografica");
        vista.btnVolverArt.addActionListener(listener);
        vista.btnVolverArt.setActionCommand("volverArtista");
        vista.btnOrdenarArt.addActionListener(listener);
        vista.btnOrdenarArt.setActionCommand("ordenarArtista");
        vista.btnOrdenarDis.addActionListener(listener);
        vista.btnOrdenarDis.setActionCommand("ordenarDisco");
        vista.btnOrdenar.addActionListener(listener);
        vista.btnOrdenar.setActionCommand("ordenarDiscografica");
        vista.itemOpciones.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.btnValidate.addActionListener(listener);
    }

    private void refrescarTodo() {
        refrescarDiscografica();
        refrescarArtistas();
        refrescarDisco();
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

        vista.tableDisco.setCellSelectionEnabled(true);
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
                        vista.txtCancionesDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 6)));
                        vista.boxArtDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 8)));
                        vista.boxGeneroDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                        vista.boxDiscoDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 7)));
                        vista.boxColores.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
                        vista.fechaDis.setDate((Date.valueOf(String.valueOf(vista.tableDisco.getValueAt(row, 4)))).toLocalDate());
                        vista.spinnerPrecio.setValue((Integer) vista.tableDisco.getValueAt(row, 3));
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
        ListSelectionModel cellSelectedModel3 = vista.tableDiscografica.getSelectionModel();
        cellSelectedModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectedModel3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                        int row = vista.tableDiscografica.getSelectedRow();
                        vista.txtNombreDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 1)));
                        vista.txtPaisDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 2)));
                        vista.webTxtDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 3)));
                        vista.emailTxtDis.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 4)));
                        vista.txtTelefonoDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 5)));
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
                vista.txtCancionesDis.setText(String.valueOf(vista.tableDisco.getValueAt(row, 6)));
                vista.boxArtDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 8)));
                vista.boxGeneroDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 2)));
                vista.boxDiscoDis.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 7)));
                vista.boxColores.setSelectedItem(String.valueOf(vista.tableDisco.getValueAt(row, 5)));
                vista.fechaDis.setDate((Date.valueOf(String.valueOf(vista.tableDisco.getValueAt(row, 4)))).toLocalDate());
                vista.spinnerPrecio.setValue((Integer) vista.tableDisco.getValueAt(row, 3));
            } else if (e.getSource().equals(vista.tableDiscografica.getSelectionModel())) {
                int row = vista.tableDiscografica.getSelectedRow();
                vista.txtNombreDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 1)));
                vista.txtPaisDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 2)));
                vista.webTxtDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 3)));
                vista.emailTxtDis.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 4)));
                vista.txtTelefonoDisc.setText(String.valueOf(vista.tableDiscografica.getValueAt(row, 5)));
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
        String command = e.getActionCommand();
        switch (command) {
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;
            case "Desconectar":
                if (vista.itemDesconectar.getText().equals("Desconectar")) {
                    modelo.desconectar();
                    vista.itemDesconectar.setText("Conectar");
                } else {
                    modelo.conectar();
                    vista.itemDesconectar.setText("Desconectar");
                }
                break;
            case "Salir":
                int resp = Util.showWindowOption("¿Desea cerrar la ventana?", "Cerrar Ventana");
                if (resp == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
                break;
            case "abrirOpciones":
                if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())) {
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("La contraseña introducida no es la correcta");
                }
                break;
            case "guardarOpciones":
                modelo.setPropValues(vista.optionDialog.txtIp.getText(), vista.optionDialog.txtUsuario.getText(),
                        String.valueOf(vista.optionDialog.txtPswd.getPassword()), String.valueOf(vista.optionDialog.txtAdminPswd.getPassword()));
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista());;
                break;
            case "addArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoArtistaVacio()) {
                        Util.showErrorAlert(camposArtistaVacio());
                        camposVacios = "";
                    } else if (modelo.existeArtista(vista.txtNombreArt.getText())) {
                        Util.showErrorAlert("El artista " + vista.txtNombreArt.getText() + " ya existe \n" +
                                "Introduce un artista diferente");
                        vista.tableArtista.clearSelection();
                    } else {
                        modelo.insertarArtista(vista.txtNombreArt.getText(), (String) vista.boxGeneroArt.getSelectedItem(), vista.txtPaisArt.getText(), (String) vista.boxDiscoArt.getSelectedItem());
                    }
                    borrarCamposArtista();
                    refrescarArtistas();
                }
                break;
            case "modificarArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoArtistaVacio()) {
                        Util.showErrorAlert(camposArtistaVacio());
                        camposVacios = "";
                        vista.tableArtista.clearSelection();
                    } else {
                        try {
                            modelo.modificarArtista(vista.txtNombreArt.getText(), (String) vista.boxGeneroArt.getSelectedItem(),
                                    vista.txtPaisArt.getText(), (String) vista.boxDiscoArt.getSelectedItem(),
                                    (Integer) vista.tableArtista.getValueAt(vista.tableArtista.getSelectedRow(), 0));
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Util.showErrorAlert("Selecciona un artista a modificar");
                        }
                    }
                    borrarCamposArtista();
                    refrescarArtistas();
                }
                    break;
            case "borrarArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        try {
                            modelo.eliminarArtista((Integer) vista.tableArtista.getValueAt(vista.tableArtista.getSelectedRow(), 0));
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Util.showErrorAlert("Selecciona un artista a borrar");
                        }
                    } catch (RuntimeException ex) {
                        if ("Artista_con_relaciones".equals(ex.getMessage())) {
                            Util.showErrorAlert("Estas intentando borrar un artista que pertenece a un disco \n" +
                                    "Borra primero ese disco");
                        } else {
                            throw ex;
                        }
                    }
                    borrarCamposArtista();
                    refrescarArtistas();
                }
                break;
            case "addDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoDiscoVacio()) {
                        Util.showErrorAlert(camposDiscoVacio());
                        camposVacios = "";
                    } else if (modelo.existeDisco(vista.txtNombreDis.getText())) {
                        Util.showErrorAlert("El disco " + vista.txtNombreDisc.getText() + " ya existe \n" +
                                "Introduce un disco diferente");
                        vista.tableDisco.clearSelection();
                    } else {
                        modelo.insertarDisco(vista.txtNombreDis.getText(), (String) vista.boxGeneroDis.getSelectedItem(), vista.getPrecio(),
                                vista.fechaDis.getDate(), (String) vista.boxColores.getSelectedItem(), (String) vista.boxDiscoDis.getSelectedItem(),
                                vista.txtCancionesDis.getText(), (String) vista.boxArtDis.getSelectedItem());
                        vista.spinnerPrecio.setValue(1);
                    }
                    borrarCamposDiscos();
                    refrescarDisco();
                }
                break;
            case "modificarDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoDiscoVacio()) {
                        Util.showErrorAlert(camposDiscoVacio());
                        camposVacios = "";
                        vista.tableDisco.clearSelection();
                    } else {
                        try {
                            modelo.modificarDisco(vista.txtNombreDis.getText(), (String) vista.boxGeneroDis.getSelectedItem(), vista.getPrecio(),
                                    vista.fechaDis.getDate(), (String) vista.boxColores.getSelectedItem(), (String) vista.boxDiscoDis.getSelectedItem(),
                                    vista.txtCancionesDis.getText(), (String) vista.boxArtDis.getSelectedItem(), (Integer) vista.tableDisco.getValueAt(vista.tableDisco.getSelectedRow(), 0));
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Util.showErrorAlert("Selecciona un disco a modificar");
                        }
                    }
                    borrarCamposDiscos();
                    refrescarDisco();
                }
                break;
            case "borrarDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        modelo.eliminarDisco((Integer) vista.tableDisco.getValueAt(vista.tableDisco.getSelectedRow(), 0));
                        borrarCamposDiscos();
                        refrescarDisco();
                    } catch (ArrayIndexOutOfBoundsException aiobe) {
                        Util.showErrorAlert("Selecciona un disco a borrar");
                    }
                }
                break;
            case "addDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoDiscograficaVacio()) {
                        Util.showErrorAlert(camposDiscograficaVacio());
                        camposVacios = "";
                    } else if (modelo.existeDiscografica(vista.txtNombreDisc.getText())) {
                        Util.showErrorAlert("La discografica " + vista.txtNombreArt.getText() + "ya existe \n" +
                                "Introduce una diferente");
                        vista.tableDiscografica.clearSelection();
                    } else {
                        try {
                            modelo.insertarDiscografica(vista.txtNombreDisc.getText(), vista.txtPaisDisc.getText(), vista.webTxtDisc.getText(),
                                    vista.emailTxtDis.getText(), Integer.parseInt(vista.txtTelefonoDisc.getText()));
                            refrescarTodo();
                        } catch (NumberFormatException nfe) {
                            Util.showErrorAlert("Introduce un número correcto");
                        }
                    }
                    borrarCamposDiscografica();
                }
                break;
            case "modificarDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    if (algunCampoDiscograficaVacio()) {
                        Util.showErrorAlert(camposDiscograficaVacio());
                        camposVacios = "";
                        vista.tableDiscografica.clearSelection();
                    } else {
                        try {
                            modelo.modificarDiscografica(vista.txtNombreDisc.getText(), vista.txtPaisDisc.getText(), vista.webTxtDisc.getText(),
                                    vista.emailTxtDis.getText(), Integer.parseInt(vista.txtTelefonoDisc.getText()),
                                    (Integer) vista.tableDiscografica.getValueAt(vista.tableDiscografica.getSelectedRow(), 0));
                            refrescarTodo();
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Util.showErrorAlert("Selecciona una discografica a editar");
                        }
                    }
                    borrarCamposDiscografica();
                }
                break;
            case "borrarDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        try {
                            modelo.eliminarDiscografica((Integer) vista.tableDiscografica.getValueAt(vista.tableDiscografica.getSelectedRow(), 0));
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            Util.showErrorAlert("Selecciona una discografica a borrar");
                        }
                    } catch (RuntimeException ex) {
                        if ("Discografica_con_relaciones".equals(ex.getMessage())) {
                            Util.showErrorAlert("Estas intentando borrar una discografica que esta conectada a un artista o disco \n" +
                                    "Elimina ese artista o disco primero");
                        } else {
                            throw ex;
                        }
                    }
                    borrarCamposDiscografica();
                    refrescarTodo();
                }
                break;
            case "buscarArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        if (vista.txtBuscarArt.getText().equals("")) {
                            Util.showWarningAlert("Introduce un artista a buscar");
                        } else if (!modelo.existeArtista(vista.txtBuscarArt.getText())) {
                            Util.showWarningAlert("El artista " + vista.txtBuscarArt.getText() + " no existe");
                        } else {
                            construirTableModelArtista(modelo.buscarArtista(vista.txtBuscarArt.getText()));
                            vista.btnVolverArt.setVisible(true);
                            vista.txtBuscarArt.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "buscarDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        if (vista.txtBuscarDisc.getText().equals("")) {
                            Util.showWarningAlert("Introduce una discografica a buscar");
                        } else if (!modelo.existeDiscografica(vista.txtBuscarDisc.getText())) {
                            Util.showWarningAlert("La discografica " + vista.txtBuscarDisc + " no existe");
                        } else {
                            construirTableModelDiscografica(modelo.buscarDiscografica(vista.txtBuscarDisc.getText()));
                            vista.btnVolverDisc.setVisible(true);
                            vista.txtBuscarDisc.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "buscarDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        if (vista.txtBuscarDis.getText().equals("")) {
                            Util.showWarningAlert("Introduce un disco a buscar");
                        } else if (!modelo.existeDisco(vista.txtBuscarDis.getText())) {
                            Util.showWarningAlert("El disco " + vista.txtBuscarDis.getText() + " no existe");
                        } else {
                            construirTableModelDisco(modelo.buscarDisco(vista.txtBuscarDis.getText()));
                            vista.btnVolverDisco.setVisible(true);
                            vista.txtBuscarDis.setText("");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "volverDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    refrescarTodo();
                    vista.btnVolverDisco.setVisible(false);
                }
                break;
            case "volverArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    refrescarTodo();
                    vista.btnVolverArt.setVisible(false);
                }
                break;
            case "volverDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    refrescarTodo();
                    vista.btnVolverDisc.setVisible(false);
                }
                break;
            case "ordenarArtista":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        construirTableModelArtista(modelo.ordenarArtista());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "ordenarDiscografica":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        construirTableModelDiscografica(modelo.ordenarDiscografica());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            case "ordenarDisco":
                if (!modelo.isConectado()) {
                    Util.showErrorAlert("Estas desconectado de la base de datos.\n" +
                            "Para conectarte ve a Archivo -> Conectar");
                } else {
                    try {
                        construirTableModelDisco(modelo.ordenarDisco());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }
    private void borrarCamposDiscografica() {
        vista.txtNombreDisc.setText("");
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
        vista.spinnerPrecio.setValue(1);
    }

    private void refrescarArtistas() {
        try {
            vista.tableArtista.setModel(construirTableModelArtista(modelo.consultarArtista()));
            vista.boxArtDis.removeAllItems();
            for(int i = 0; i < vista.dtmArtistas.getRowCount(); i++) {
                vista.boxArtDis.addItem(vista.dtmArtistas.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refrescarDisco() {
        try {
            vista.tableDisco.setModel(construirTableModelDisco(modelo.consultarDisco()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refrescarDiscografica() {
        try {
            vista.tableDiscografica.setModel(construirTableModelDiscografica(modelo.consultarDiscografica()));
            vista.boxDiscoDis.removeAllItems();
            vista.boxDiscoArt.removeAllItems();
            for(int i = 0; i < vista.dtmDiscografica.getRowCount(); i++) {
                vista.boxDiscoDis.addItem(vista.dtmDiscografica.getValueAt(i, 1));
            }
            for(int i = 0; i < vista.dtmDiscografica.getRowCount(); i++) {
                vista.boxDiscoArt.addItem(vista.dtmDiscografica.getValueAt(i, 1));
            }
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

        vista.dtmArtistas.setDataVector(data, columnNames);

        return vista.dtmArtistas;

    }

    private DefaultTableModel construirTableModelDisco(ResultSet rs)
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

        vista.dtmDisco.setDataVector(data, columnNames);

        return vista.dtmDisco;

    }

    private DefaultTableModel construirTableModelDiscografica(ResultSet rs)
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
        vista.dtmDiscografica.setDataVector(data, columnNames);

        return vista.dtmDiscografica;

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

    private boolean algunCampoArtistaVacio() {
        if (vista.txtNombreArt.getText().isEmpty() || vista.txtPaisArt.getText().isEmpty() || vista.boxGeneroArt.getSelectedItem() == null
         || vista.boxDiscoArt.getSelectedItem() == null) {
            return true;
        }
        return false;
    }

    private String camposArtistaVacio() {
        camposVacios = "Los siguientes campos estan vacios: \n";
        if (vista.txtNombreArt.getText().isEmpty()) {
            camposVacios += "Nombre \n";
        }
        if (vista.txtPaisArt.getText().isEmpty()) {
            camposVacios += "Pais \n";
        }
        if (vista.boxGeneroArt.getSelectedItem() == null) {
            camposVacios += "Genero \n";
        }
        if (vista.boxDiscoArt.getSelectedItem() == null) {
            camposVacios += "Discografica";
        }
        return camposVacios;
    }

    private boolean algunCampoDiscoVacio() {
        if (vista.txtNombreDis.getText().isEmpty() || vista.txtCancionesDis.getText().isEmpty() || vista.boxGeneroDis.getSelectedItem() == null
                || vista.fechaDis.getText().isEmpty() || vista.boxColores.getSelectedItem() == null || vista.boxDiscoDis.getSelectedItem() == null
                || vista.boxArtDis.getSelectedItem() == null) {
            return true;
        }
        return false;
    }

    private String camposDiscoVacio() {
        camposVacios = "Los siguientes campos estan vacios: \n";
        if (vista.txtNombreDis.getText().isEmpty()) {
            camposVacios += "Nombre \n";
        }
        if (vista.txtCancionesDis.getText().isEmpty()) {
            camposVacios += "Canciones \n";
        }
        if (vista.boxGeneroDis.getSelectedItem() == null) {
            camposVacios += "Genero \n";
        }
        if (vista.fechaDis.getText().isEmpty()) {
            camposVacios += "Fecha \n";
        }
        if (vista.boxColores.getSelectedItem() == null) {
            camposVacios += "Colores \n";
        }
        if (vista.boxDiscoDis.getSelectedItem() == null) {
            camposVacios += "Discografica \n";
        }
        if (vista.boxArtDis.getSelectedItem() == null) {
            camposVacios += "Artista \n";
        }
        return camposVacios;
    }

    private boolean algunCampoDiscograficaVacio() {
        if (vista.txtPaisDisc.getText().isEmpty() || vista.txtNombreDisc.getText().isEmpty() || vista.txtTelefonoDisc.getText().isEmpty()
         || vista.emailTxtDis.getText().isEmpty() || vista.webTxtDisc.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private String camposDiscograficaVacio() {
        camposVacios = "Los siguientes campos estan vacios: \n";
        if (vista.txtPaisDisc.getText().isEmpty()) {
            camposVacios += "Pais \n";
        }
        if (vista.txtNombreDisc.getText().isEmpty()) {
            camposVacios += "Nombre \n";
        }
        if (vista.txtTelefonoDisc.getText().isEmpty()) {
            camposVacios += "Telefono \n";
        }
        if (vista.emailTxtDis.getText().isEmpty()) {
            camposVacios += "Email";
        }
        if (vista.webTxtDisc.getText().isEmpty()) {
            camposVacios += "Web";
        }
        return camposVacios;
    }

    private void addItemListeners(Controlador controlador) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.showWindowOption("¿Desea cerrar la ventana?", "Cerrar Ventana");
        if (resp == JOptionPane.OK_OPTION) {
        System.exit(0);
        }
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
