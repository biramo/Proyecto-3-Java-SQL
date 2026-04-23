package Controller;

import model.Cliente;

import java.sql.*;

public class ClienteCRUD {

    //Constructor
    public ClienteCRUD() {

    }

    public void insertarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes(dni,nombre,apellidos,fecha_nacimiento,email,telefono) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellidos());
            ps.setObject(4, cliente.getFechaNacimiento());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getTelefono());


            int filas = ps.executeUpdate();

            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", insertado correctamento");

        }
    }

    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes set nombre=?,apellidos=?,fecha_nacimiento=?,email=?,telefono=? WHERE dni=? ";

        try (Connection con = ConexionBD.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setObject(3, cliente.getFechaNacimiento());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getDni());

            int filas = ps.executeUpdate();

            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", actualizado correctamento");

        }
    }

    public void listarTodosClientes() {
        String sql = ""
    }

}
