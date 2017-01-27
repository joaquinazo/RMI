package Servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BBDD {
    Connection con;
    BBDD bbDD;
    static int id;

    private String[][] ejecutaQuery(String query) {
        String sMatrixRes[][] = null;
        try {

            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);
            rset.last();
            int f = rset.getRow();
            rset.beforeFirst();
            ResultSetMetaData rsmd = rset.getMetaData();
            int c = rsmd.getColumnCount();
            int i = 0;
            sMatrixRes = new String[f][c];
            while (rset.next()) {
                for (int j = 0; j < c; j++) {
                    sMatrixRes[i][j] = rset.getString(j + 1);
                }
                i++;
            }
            rset.close();
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return sMatrixRes;
    }

    public void crearConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
            System.out.println("Conecta con basse de datos");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean comprobarUser(String nombre, int contra) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery("SELECT nombre,contra,iduser FROM user");
            boolean com = false;
            while (rs.next()) {

                String no = rs.getString("nombre");
                int ac = rs.getInt("contra");
                int id = rs.getInt("iduser");

                if (nombre.equals(no) && contra == ac) {
                    this.id = id;
                    com = true;
                    return com;
                }

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    public ArrayList<String> verContactos() {
        ArrayList<String> conn = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int id = this.id;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM contactos where idU = " + id);
            boolean com = false;

            while (rs.next()) {

                String no = rs.getString("nombre");
                int nu = rs.getInt("numero");
                String em = rs.getString("email");
                int idC = rs.getInt("idcontactos");

                conn.add(no + " ");
                conn.add(nu + " ");
                conn.add(em + " ");
                conn.add(" id --> " + idC + "\n");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;

    }

    public ArrayList<String> buscarC(String nombre) {
        ArrayList<String> conn = new ArrayList<>();
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int id = this.id;
        try {

            ResultSet rs = stmt.executeQuery("SELECT * FROM contactos where idU = " + id + " and nombre = '" + nombre + "'");
            boolean com = false;

            while (rs.next()) {

                String no = rs.getString("nombre");
                int nu = rs.getInt("numero");
                String em = rs.getString("email");
                int idC = rs.getInt("idcontactos");

                conn.add(no + " ");
                conn.add(nu + " ");
                conn.add(em + " ");
                conn.add(" id --> " + idC + "\n");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;

    }

    public String borrarcontacto(int id) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            String query = "DELETE FROM `tareapedro`.`contactos` WHERE `idcontactos`='" + id + "';";
            PreparedStatement psts = con.prepareStatement(query);
            psts.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Borrado correctamente";

    }

    public String modificarContacto(String nombre, int numero, String email, int id) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String sql = "UPDATE `tareapedro`.`contactos` SET `nombre`='" + nombre + "', `numero`='" + numero
                + "', `email`='" + email + "' WHERE `idcontactos`='" + id + "';";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Modificado";

    }

    public void insertContact(String nombre, int numero, String email) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/agenda", "root", "root");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String sql = "INSERT INTO contactos ( `nombre`, `numero`,`email`,`idU`) VALUES (?,?,?,?)";

        try {
            int id = this.id;
            PreparedStatement psts = null;
            psts = con.prepareStatement(sql);
            psts.setString(1, nombre);
            psts.setInt(2, numero);
            psts.setString(3, email);
            psts.setInt(4, id);

            psts.executeUpdate();

            psts.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
