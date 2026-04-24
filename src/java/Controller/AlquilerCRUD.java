package Controller;

import model.Alquiler;
import model.Cliente;
import model.Penalizacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AlquilerCRUD {
    // Constructor
    public AlquilerCRUD() {
    }
    // ------------   INSERTAR ALQUILER ------------ //
    public void insertarAlquiler(Alquiler alquiler) throws SQLException {
        String sql = "INSERT INTO Alquileres(dni_cliente,id_instrumento,fecha_inicio,fecha_fin_prevista,fecha_devolucion,importe_base,observaciones,estadopago) VALUES(?, ?, ?, ?, ?, ?, , ?, ?)";
        try (Connection con = ConexionBD.conexion(); PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) /* devuelve el ID egenrado automaticamente en sql */{

            ps.setString(1, alquiler.getCliente().getDni());
            ps.setInt(2, alquiler.getInstrumento().getId());
            ps.setObject(3, alquiler.getFechaInicio());
            ps.setObject(4, alquiler.getFechaFinPrevista());
            ps.setObject(5, alquiler.getFechaDevolucion());
            ps.setDouble(6, alquiler.getImporteBase());
            ps.setString(7, alquiler.getObservaciones());
            ps.setBoolean(8, alquiler.isPagado());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                //del resultado buscamos los key generados
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        //colocamos el id dentro del objeto alquiler (al pasar el objeto se envia como direccion de memoria)
                        alquiler.setId(rs.getInt(1));
                    }
                }
            }

            System.out.println("Alquiler registrado coorectament con ID: " + alquiler.getId());

        }
    }
    // ------------   UPDATE ALQUILER ------------ //
    public void updateAlquiler(Alquiler alquiler) throws SQLException {
        String sql = "UPDATE Alquileres SET dni_cliente=?, id_instrumento=?, fecha_inicio=?, fecha_fin_prevista=?, fecha_devolucion=?, importe_base=? ,observaciones=?, estadopago=? WHERE id=?";

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, alquiler.getCliente().getDni());
            ps.setInt(2, alquiler.getInstrumento().getId());
            ps.setObject(3, alquiler.getFechaInicio());
            ps.setObject(4, alquiler.getFechaFinPrevista());
            ps.setObject(5, alquiler.getFechaDevolucion());
            ps.setDouble(6, alquiler.getImporteBase());
            ps.setString(7, alquiler.getObservaciones());
            ps.setBoolean(8, alquiler.isPagado());


            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Alquiler con ID: " + alquiler.getId() + ", actualizado correctamente");
            } else {
                System.out.println("No se encontro ningun alquiler con ID: " + alquiler.getId());
            }
        }
    }
    // ------------   DELETE ALQUILER ------------ //
    public void deleteAlquiler(int id) throws SQLException {
        String sql = "DELETE FROM Alquilerres WHERE id=?";

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Alquiler con ID: " + id + ", eliminado correctamente");
            } else {
                System.out.println("No existe ningún alquiler con ID: " + id);
            }
        }
    }
    // ---------- LISTAR TODOS LOS ALQUILERES
    public void listarTodosAlquileres() throws SQLException {
        String sql = "SELECT * FROM Alquileres";

        try (Connection con = ConexionBD.conexion(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            System.out.println("=== Lista de Ordenadores ===");

            while (rs.next()) {
                System.out.println(
                                "\nID: " + rs.getInt("id") +
                                "\nCliente: " + rs.getString("dni_cliente") +
                                "\nInstrumento: " + rs.getInt("id_instrumento") +
                                "\nFecha Inicio: " + rs.getObject("fecha_inicio") +
                                "\nFecha Fin Prevista: " + rs.getObject("fecha_fin_prevista") +
                                "\nFecha Devolución: " + rs.getObject("fecha_devolucion")+
                                "\nImporte Base: " + rs.getDouble("importe_base") +
                                "\nImporte Final: " + rs.getDouble("importe_final") +
                                "\nObservaciones: " + rs.getString("observaciones") +
                                "\nEstado pago: " + rs.getBoolean("estadopago")
                );
            }
        }
    }
    // ------------   BUSCAR ALQUILER POR ID ------------ //
    public void buscarAlquilerPorID(int id) throws SQLException {

        String sql = "SELECT * FROM Alquileres WHERE id=?";

        try (Connection con = ConexionBD.conexion(); PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            System.out.println("=== Resultado de la búsqueda ===");

            while (rs.next()) {
                System.out.println(
                                "\nID: " + rs.getInt("id") +
                                "\nCliente: " + rs.getString("dni_cliente") +
                                "\nInstrumento: " + rs.getInt("id_instrumento") +
                                "\nFecha Inicio: " + rs.getObject("fecha_inicio") +
                                "\nFecha Fin Prevista: " + rs.getObject("fecha_fin_prevista") +
                                "\nFecha Devolución: " + rs.getObject("fecha_devolucion")+
                                "\nImporte Base: " + rs.getDouble("importe_base") +
                                "\nImporte Final: " + rs.getDouble("importe_final") +
                                "\nObservaciones: " + rs.getString("observaciones") +
                                "\nEstado pago: " + rs.getBoolean("estadopago")
                );
            }
            rs.close();
        }
    }
}
