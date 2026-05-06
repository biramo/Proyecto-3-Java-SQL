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
        String sql = "INSERT INTO Clientes(dni,nombre,apellidos,fecha_nacimiento,email,telefono) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getDni());
                ps.setString(2, cliente.getNombre());
                ps.setString(3, cliente.getApellidos());
                ps.setObject(4, cliente.getFechaNacimiento());
                ps.setString(5, cliente.getEmail());
                ps.setString(6, cliente.getTelefono());


                ps.executeUpdate();

                System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", insertado correctamente");

            }
        }
    }

    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes set nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefono=? WHERE dni=? ";

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
        String sql = "SELECT * FROM Clientes";
        ArrayList<Cliente> listaClientes = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Cliente cliente = crearClienteDesdeResultSet(rs);
                    listaClientes.add(cliente);
                }
            }
        }

        if (listaClientes.isEmpty()) {
            System.out.println("No hay entradas de clientes en la base de datos");
        }

        return listaClientes;
    }

    // ------------ LISTAR CLIENTE POR DNI ------------ //
    // Devuelve null si no hay coincidencias
    public Cliente listarClientePorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE dni=?";
        Cliente cliente = null;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, dni);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cliente = crearClienteDesdeResultSet(rs);
                    }
                }
            }
        }

        if (cliente == null) {
            System.out.println("No hay coincidencias de clientes con el dni: " + dni);
        }

        return cliente;
    }

    // ------------ LISTAR CLIENTE POR EMAIL ------------ //
    // Devuelve null si no hay coincidencias
    public Cliente listarClientePorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE email = ?";
        Cliente cliente = null;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cliente = crearClienteDesdeResultSet(rs);
                    }
                }
            }
        }

        if (cliente == null) {
            System.out.println("No hay coincidencias de clientes con el email: " + email);
        }

        return cliente;
    }

    public void deleteCliente(String dni) throws SQLException {
        String sqlCliente = "DELETE FROM Clientes WHERE dni = ?";

        try (Connection con = ConexionBD.conexion()) {
            if (con == null) return;

            // --- PASO 1: Iniciar Transacción ---

            try (PreparedStatement psCli = con.prepareStatement(sqlCliente)) {


                psCli.setString(1, dni);

                // --- PASO 2: Ejecutar borrados -

                int filas = psCli.executeUpdate();

                if (filas <= 0) {
                    System.out.println("Cliente con dni: " + dni + ", no existe.");
                    // Si no existe el cliente, no hay nada que guardar
                    con.rollback();
                } else {
                    // --- PASO 3: Confirmar cambios ---
                    System.out.println("Cliente y datos relacionados eliminados correctamente");
                }

            }
        }
    }

    // Método privado para no repetir código al crear objetos Cliente desde la base de datos
    private Cliente crearClienteDesdeResultSet(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getObject("fecha_nacimiento", LocalDate.class)
        );
    }
}
