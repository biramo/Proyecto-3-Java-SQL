package Controller;

import model.Alquiler;
import model.Instrumento;

import java.sql.*;

public class AlquilerCRUD {
    // Constructor
    public AlquilerCRUD() {
    }
    public void insertarAlquiler(Alquiler alquiler) throws SQLException {
        String sql = "INSERT INTO alquileres(dni_cliente,id_instrumento,fecha_inicio,fecha_fin_prevista,fecha_devolucion,importe_base,penalizacion,observaciones,estadopago) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, alquiler.getCliente().getDni());
            ps.setInt(2, alquiler.getInstrumento().getId());
            ps.setObject(3, alquiler.getFechaInicio());
            ps.setObject(4, alquiler.getFechaFinPrevista());
            ps.setObject(5, alquiler.getFechaDevolucion());
            ps.setString(6, cliente.getTelefono());


            int filas = ps.executeUpdate();

            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + ", insertado correctamento");

        }
    }
}
