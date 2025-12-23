package gui;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Modelo {

    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    public Modelo() {
        getPropValues();
    }

    public String getIp() {
        return ip;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
    public String getAdminPassword() {
        return adminPassword;
    }

    private Connection conexion;

    void conectar() {

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/mibase",user, password);
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://"+ip+":3306/",user, password);

                PreparedStatement statement = null;

                String code = leerFichero();
                String[] query = code.split("--");
                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basedatos_java.sql")) ;
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    void desconectar() {
        try {
            conexion.close();
            conexion = null;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    void insertarArtista(String nombre, String genero, String pais, String discografica) {
        String sentenciaSql = "INSERT INTO artista (nombre, genero, pais, id_discografica)" +
                "VALUES (?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        int idDiscografica = Integer.valueOf(discografica.split("")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, genero);
            sentencia.setString(3, pais);
            sentencia.setInt(4, idDiscografica);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

    }

    void insertarDisco(String nombre, String genero, int precio, LocalDate fechaLanzamiento, String color,
                       String discografica, String canciones, String artista) {
        String sentenciaSql = "INSERT INTO disco (nombre, genero, precio, fecha_lanzamiento, color, id_discografica, canciones, id_artista)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        int idDiscografica = Integer.valueOf(discografica.split("")[0]);
        int idArtista = Integer.valueOf(artista.split("")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, genero);
            sentencia.setInt(3, precio);
            sentencia.setDate(4, Date.valueOf(fechaLanzamiento));
            sentencia.setString(5, color);
            sentencia.setInt(6, idDiscografica);
            sentencia.setString(7, canciones);
            sentencia.setInt(8, idArtista);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

    }

    void insertarDiscografica(String nombre, String pais, String sitioWeb, String email, int numero) {
        String sentenciaSql = "INSERT INTO artista (nombre, pais, sitio_web, email_contacto, telefono_contacto)" +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, pais);
            sentencia.setString(3, sitioWeb);
            sentencia.setString(4, email);
            sentencia.setInt(5, numero);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }

    }

    void modificarArtista(String nombre, String genero, String pais, String discografica, int idArtista){

        String sentenciaSql = "UPDATE artista SET nombre = ?, genero = ?, pais = ?, discografica = ?" +
                "WHERE id_artista = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, genero);
            sentencia.setString(3, pais);
            sentencia.setString(4, discografica);
            sentencia.setInt(5, idArtista);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarDisco(String nombre, String genero, int precio, LocalDate fechaLanzamiento, String color,
                        String discografica, String canciones, String artista, int idDisco){

        String sentenciaSql = "UPDATE disco SET nombre = ?, genero = ?, precio = ?, fechaLanzamiento = ?, color = ?," +
                " discografica = ?, canciones = ?, artista = ?" + "WHERE id_disco = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, genero);
            sentencia.setInt(3, precio);
            sentencia.setDate(4, Date.valueOf(fechaLanzamiento));
            sentencia.setString(5, color);
            sentencia.setString(6, discografica);
            sentencia.setString(7, color);
            sentencia.setString(8, artista);
            sentencia.setInt(9, idDisco);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarDiscografica(String nombre, String pais, String sitioWeb, String email, int numero, int idDiscografica){

        String sentenciaSql = "UPDATE discografica SET nombre = ?, pais = ?, sitio_web = ?, email_contacto = ?, telefono_contacto = ?" +
                "WHERE id_discografica = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            sentencia.setString(2, pais);
            sentencia.setString(3, sitioWeb);
            sentencia.setString(4, email);
            sentencia.setInt(5, numero);
            sentencia.setInt(6, idDiscografica);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
