package Controller;

import model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteCRUD {

    //Constructor
    public ClienteCRUD() {

    }

    public void insertarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes(dni,nombre,apellidos,fecha_nacimiento,email,telefono) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getDni());
                ps.setString(2, cliente.getNombre());
                ps.setString(3, cliente.getApellidos());
                ps.setObject(4, cliente.getFechaNacimiento());
                ps.setString(5, cliente.getEmail());
                ps.setString(6, cliente.getTelefono());


                int fA = ps.executeUpdate();

                System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", insertado correctamente");

            }
        }
    }

    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes set nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefono=? WHERE dni=? ";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getNombre());
                ps.setString(2, cliente.getApellidos());
                ps.setObject(3, cliente.getFechaNacimiento());
                ps.setString(4, cliente.getEmail());
                ps.setString(5, cliente.getTelefono());
                ps.setString(6, cliente.getDni());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Cliente con dni:  " + cliente.getDni() + ", actualizado correctamente.");
                } else {
                    System.out.println("No se encontró ningún cliente con DNI: " + cliente.getDni() + ".");
                }

            }
        }
    }

    public ArrayList<Cliente> listarTodosClientes() throws SQLException {
        String sql = "SELECT * FROM clientes";
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Cliente cliente = new Cliente(rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getObject("fecha_nacimiento", LocalDate.class));//Formateo para que el constructor acepte el valor

                    listaClientes.add(cliente);
                }
                if (listaClientes.isEmpty()) {

                    System.out.println("No hay entradas de clientes en la base de datos");
                }

                return listaClientes;
            }
        }
    }

    //Devuelve null si no hay coincidencias, después, tratamos ese null en el programa
    public Cliente listarClientePorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE dni=?";
        Cliente cliente = null;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, dni);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        cliente = new Cliente(rs.getString("dni"),
                                rs.getString("nombre"),
                                rs.getString("telefono"),
                                rs.getString("apellidos"),
                                rs.getString("email"),
                                rs.getObject("fecha_nacimiento", LocalDate.class));//Formateo para que el constructor acepte el valor
                    }

                }


            }
        }
        if (cliente == null) {

            System.out.println("No hay coincidencias de clientes con el dni: " + dni);
        }
        return cliente;
    }


    public void deleteCliente(String dni) throws SQLException {
        String sql = "DELETE FROM clientes WHERE dni=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, dni);

                int filas = ps.executeUpdate();

                if (filas <= 0) {
                    System.out.println("Cliente con dni: " + dni + ", no existe.");

                } else {
                    System.out.println("Cliente con dni: " + dni + "eliminado");
                }

            }
        }

    }
}
