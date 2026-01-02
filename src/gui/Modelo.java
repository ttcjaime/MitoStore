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

    private static Connection conexion;

    void conectar() {

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://"+"localhost"+":3306/mitostore","root", "");
            System.out.println("Conectado");
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://"+"localhost"+":3306/mitostore","root", "");

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

    public Connection getConexion() {
        return conexion;
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

        int idDiscografica = obtenerIdDiscografica(discografica);

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

        int idDiscografica = obtenerIdDiscografica(discografica);
        int idArtista = obtenerIdArtista(artista);

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
        String sentenciaSql = "INSERT INTO discografica (nombre, pais, sitio_web, email_contacto, telefono_contacto)" +
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

        String sentenciaSql = "UPDATE disco SET nombre = ?, genero = ?, precio = ?, fecha_lanzamiento = ?, color = ?," +
                " id_discografica = ?, canciones = ?, id_artista = ? " + "WHERE id = ?";
        PreparedStatement sentencia = null;

        int idDiscografica = obtenerIdDiscografica(discografica);
        int idArtista = obtenerIdArtista(artista);

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

    void eliminarArtista(int idAutor) {
        String sentenciaSql = "DELETE FROM artista WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idAutor);
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

    void eliminarDiscografica(int idDiscografica) {
        String sentenciaSql = "DELETE FROM discografica WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idDiscografica);
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

    void eliminarDisco(int idDisco) {
        String sentenciaSql = "DELETE FROM disco WHERE id = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idDisco);
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

    ResultSet consultarArtista() throws SQLException {
        String sentenciaSql = "SELECT a.id as 'ID', " +
                "a.nombre as 'Artista', " +
                "a.pais as 'Pais', " +
                "a.genero as 'Genero', " +
                "d.nombre as 'Discografica' "+
                "FROM artista a " +
                "INNER JOIN discografica d " +
                "ON d.id = a.id_discografica";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    public static int obtenerIdArtista(String nombre) {
        String sentenciaSql = "SELECT id FROM artista WHERE nombre = ?";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                return resultado.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null)
                    resultado.close();
                if (sentencia != null)
                    sentencia.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;

    }

    public static int obtenerIdDiscografica(String nombre) {
        String sentenciaSql = "SELECT id FROM discografica WHERE nombre = ?";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombre);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                return resultado.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null)
                    resultado.close();
                if (sentencia != null)
                    sentencia.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;

    }

    ResultSet consultarDiscografica() throws SQLException {
        String sentenciaSql = "SELECT d.id as 'ID', " +
                "d.nombre as 'Discografica', " +
                "d.pais as 'Pais', " +
                "d.sitio_web as 'Web', " +
                "d.email_contacto as 'Email', " +
                "d.telefono_contacto as 'Telefono'" +
                "FROM discografica d";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    ResultSet consultarDisco() throws SQLException {
        String sentenciaSql = "SELECT d.id AS ID, " +
                "d.nombre AS Disco, " +
                "d.genero AS Genero, " +
                "d.precio AS Precio, " +
                "d.fecha_lanzamiento AS Fecha_Lanzamiento, " +
                "d.color AS Color, " +
                "d.canciones AS Canciones, " +
                "ds.nombre AS Discografica, " +
                "a.nombre AS Artista " +
                "FROM disco d " +
                "INNER JOIN discografica ds ON ds.id = d.id_discografica " +
                "INNER JOIN artista a ON a.id = d.id_artista";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
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

    public boolean existeArtista(String nombre) {
        String artistaConsulta = "SELECT existeArtista(?)";
        PreparedStatement function;
        boolean artistaExiste = false;
        try {
            function = conexion.prepareStatement(artistaConsulta);
            function.setString(1, nombre);
            ResultSet rs = function.executeQuery();
            rs.next();

            artistaExiste = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistaExiste;
    }

    public boolean existeDisco(String nombre) {
        String discoConsulta = "SELECT existeDisco(?)";
        PreparedStatement function;
        boolean existeDisco = false;
        try {
            function = conexion.prepareStatement(discoConsulta);
            function.setString(1, nombre);
            ResultSet rs = function.executeQuery();
            rs.next();

            existeDisco = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existeDisco;
    }

    public boolean existeDiscografica(String nombre) {
        String discograficaConsulta = "SELECT existeDiscografica(?)";
        PreparedStatement function;
        boolean existeDiscografica = false;
        try {
            function = conexion.prepareStatement(discograficaConsulta);
            function.setString(1, nombre);
            ResultSet rs = function.executeQuery();
            rs.next();

            existeDiscografica = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existeDiscografica;
    }

}
