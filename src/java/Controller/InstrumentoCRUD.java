package Controller;

import model.Enum.CategoriaInstrumento;
import model.Enum.EstadoInstrumento;
import model.Instrumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstrumentoCRUD {

    public void insertar(Instrumento instrumento) throws SQLException {
        String sql = "INSERT INTO Instrumentos(id, marca, modelo, precio_dia, stock_total, stock_disponible, categoria, estado) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, instrumento.getId());
            ps.setString(2, instrumento.getMarca());
            ps.setString(3, instrumento.getModelo());
            ps.setDouble(4, instrumento.getPrecioDia());
            ps.setInt(5, instrumento.getStockTotal());
            ps.setInt(6, instrumento.getStockDisponible());
            ps.setString(7, instrumento.getCategoria().name());
            ps.setString(8, instrumento.getEstado().name());

            ps.executeUpdate();

            System.out.println("Instrumento insertado: " + instrumento.getId());
        }
    }

    public void listar() throws SQLException { //listar todos los instrumentos
        String sql = "SELECT * FROM Instrumentos";

        try (Connection conn = ConexionBD.conexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n\n Lista de Instrumentos");

            while (rs.next()) {
                Instrumento instrumento = crearInstrumentoDesdeResultSet(rs);
                System.out.println(instrumento);
            }
        }
    }

    public Instrumento listarInstrumentoPorId(int idInstrumento) throws SQLException {
        String sql = "SELECT * FROM Instrumentos WHERE id=?";
        Instrumento instrumento = null;

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idInstrumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    instrumento = crearInstrumentoDesdeResultSet(rs);
                }
            }
        }

        return instrumento;
    }


    public void actualizar(Instrumento instrumento) throws SQLException {
        String sql = "UPDATE Instrumentos SET marca=?, modelo=?, precio_dia=?, stock_total=?, stock_disponible=?, categoria=?, estado=? WHERE id=?";

        try (Connection conn = ConexionBD.conexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, instrumento.getMarca());
            ps.setString(2, instrumento.getModelo());
            ps.setDouble(3, instrumento.getPrecioDia());
            ps.setInt(4, instrumento.getStockTotal());
            ps.setInt(5, instrumento.getStockDisponible());
            ps.setString(6, instrumento.getCategoria().name());
            ps.setString(7, instrumento.getEstado().name());
            ps.setInt(8, instrumento.getId());

            ps.executeUpdate();

            System.out.println("Instrumento actualizado: Id: " + instrumento.getId());
        }
    }

    public void eliminar(int id) throws SQLException { //eliminar 1 instrumento
        String sql = "DELETE FROM Instrumentos WHERE id=?";

        try (Connection conn = ConexionBD.conexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int fA = ps.executeUpdate();

            if (fA > 0) {
                System.out.println("Instrumento eliminado");
            } else {
                System.out.println("No hay ningún instrumento con esta id/ Este instrumento no existe");
            }
        }
    }

    // Metodo privado para no repetir codigo al crear objetos Instrumento desde la base de datos
    private Instrumento crearInstrumentoDesdeResultSet(ResultSet rs) throws SQLException {
        Instrumento instrumento = new Instrumento(
                rs.getInt("id"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getDouble("precio_dia"),
                rs.getInt("stock_total"),
                rs.getInt("stock_disponible"),
                CategoriaInstrumento.valueOf(rs.getString("categoria")),
                EstadoInstrumento.valueOf(rs.getString("estado"))
        );

        return instrumento;
    }
}

