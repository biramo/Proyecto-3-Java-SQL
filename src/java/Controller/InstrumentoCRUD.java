package Controller;

import model.Enum.CategoriaInstrumento;
import model.Enum.EstadoInstrumento;
import model.Instrumento;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class InstrumentoCRUD {

    //Insertar un instrumento
    public void insertar(Instrumento instrumento) throws SQLException {
        String sql = "INSERT INTO Instrumentos(marca, modelo, precio_dia, stock_total, stock_disponible, categoria, estado) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, instrumento.getMarca());
                ps.setString(2, instrumento.getModelo());
                ps.setDouble(3, instrumento.getPrecioDia());
                ps.setInt(4, instrumento.getStockTotal());
                ps.setInt(5, instrumento.getStockDisponible());
                ps.setString(6, instrumento.getCategoria().name());
                ps.setString(7, instrumento.getEstado().name());

                int fA = ps.executeUpdate(); //Mira la cantidad de filas afectadas, si el número equivale a 0, no se ha hecho un cambio en la tabla.

                if (fA > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idGenerada = rs.getInt(1);
                            instrumento.setId(idGenerada);
                            System.out.println("Instrumento insertado, ID: " + idGenerada);
                        }
                    }
                } else {
                    System.out.println("No se ha podido añadir el Instrumento");
                }
            }
        }
    }

    public List<Instrumento> listarTodo() throws SQLException { //listar todos los instrumentos
        String sql = "SELECT * FROM Instrumentos";
        List<Instrumento> lista = new ArrayList<>();

        try (Connection conn = ConexionBD.conexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Instrumento ins = crearInstrumentoDesdeResultSet(rs);
                    lista.add(ins);
                }
            }
        }
        return lista;
    }

    public Instrumento listarInstrumentoPorId(int idInstrumento) throws SQLException { //El mismo de arriba pero solo con la id.
        String sql = "SELECT * FROM Instrumentos WHERE id=?";
        Instrumento instrumento = null;

        try (Connection conn = ConexionBD.conexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, idInstrumento);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        instrumento = crearInstrumentoDesdeResultSet(rs);
                    }
                }
            }
        }

        return instrumento;
    }

    // Método privado para no repetir código al crear objetos Instrumento desde la base de datos
    private Instrumento crearInstrumentoDesdeResultSet(ResultSet rs) throws SQLException {

        return new Instrumento(
                rs.getInt("id"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getDouble("precio_dia"),
                rs.getInt("stock_total"),
                rs.getInt("stock_disponible"),
                CategoriaInstrumento.valueOf(rs.getString("categoria")),
                EstadoInstrumento.valueOf(rs.getString("estado"))
        );
    }

    public List<Instrumento> listarInstrumentoPorTipo(String tipoInstrumento) throws SQLException{ //Mismo de los 2 de arriba pero ahora solo con el tipo de instrumento

        String sql = "SELECT * FROM Instrumentos WHERE categoria=?";
        List<Instrumento> listaTipo = new ArrayList<>();


        try (Connection conn = ConexionBD.conexion()){
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setString(1, tipoInstrumento);

                try (ResultSet rs = ps.executeQuery()){
                    System.out.println("Lista de instrumentos de tipo: " + tipoInstrumento);

                    while (rs.next()){
                        listaTipo.add(crearInstrumentoDesdeResultSet(rs));
                    }
                }
            }
        }
        return listaTipo;
    }

    public void updateInstrumento(Instrumento instrumento) throws SQLException { //Actualizar un instrumento
        String sql = "UPDATE Instrumentos SET marca=?, modelo=?, precio_dia=?, stock_total=?, stock_disponible=?, categoria=?, estado=? WHERE id=?";

        try (Connection conn = ConexionBD.conexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, instrumento.getMarca());
                ps.setString(2, instrumento.getModelo());
                ps.setDouble(3, instrumento.getPrecioDia());
                ps.setInt(4, instrumento.getStockTotal());
                ps.setInt(5, instrumento.getStockDisponible());
                ps.setString(6, instrumento.getCategoria().name());
                ps.setString(7, instrumento.getEstado().name());
                ps.setInt(8, instrumento.getId());

                int fA = ps.executeUpdate();

                if (fA > 0) {
                    System.out.println("Instrumento actualizado: Id: " + instrumento.getId());
                } else{
                    System.out.println("Ya hay un instrumento con esta Id / Este instrumento no existe");
                }
            }
        }
    }

    public void eliminar(int id) throws SQLException { //Eliminar 1 instrumento
        String sql = "DELETE FROM Instrumentos WHERE id=?";

        try (Connection conn = ConexionBD.conexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);

                int fA = ps.executeUpdate();

                if (fA > 0) {
                    System.out.println("Instrumento eliminado");
                } else {
                    System.out.println("No hay ningún instrumento con esta id/ Este instrumento no existe");
                }
            }
        }
    }
}

