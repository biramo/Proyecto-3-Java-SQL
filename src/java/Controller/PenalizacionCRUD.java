package Controller;

import model.Enum.TipoDesperfecto;
import model.Penalizacion;

import java.sql.*;
import java.util.ArrayList;

public class PenalizacionCRUD {
    //Constructor
    public PenalizacionCRUD() {

    }

    public void insertarPenalizacion(int idAlquiler, Penalizacion penalizacion) throws SQLException {
        String sql = "INSERT INTO Penalizaciones (id_alquiler, motivo, importe, desperfecto) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conexion();
             //vamos a querer recuperar el id insertado por lo que hacemos uso del PreparedStatement.RETURN_GENERATED_KEYS
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idAlquiler);
            ps.setString(2, penalizacion.getMotivo());
            ps.setDouble(3, penalizacion.getImporte());
            ps.setString(4, penalizacion.getDesperfecto().name());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                //del resultado buscamos los key generados
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        //colocamos el id dentro del objeto penalizacion (al pasar el objeto se envia como direccion de memoria)
                        penalizacion.setId(rs.getInt(1));
                    }
                }
            }
        }
    }

    public void updatePenalizacion(Penalizacion penalizacion) throws SQLException {
        String sql = "UPDATE Penalizaciones SET motivo=?, importe=?, desperfecto=? WHERE id=?";

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, penalizacion.getMotivo());
            ps.setDouble(2, penalizacion.getImporte());
            ps.setString(3, penalizacion.getDesperfecto().name());
            ps.setInt(4, penalizacion.getId());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Penalizacion con ID: " + penalizacion.getId() + ", actualizada correctamente");
            } else {
                System.out.println("No se encontro ninguna penalizacion con ID: " + penalizacion.getId());
            }
        }
    }

    public void deletePenalizacion(int id) throws SQLException {
        String sql = "DELETE FROM Penalizaciones WHERE id=?";

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Penalizacion con ID: " + id + ", eliminada correctamente");
            } else {
                System.out.println("No existe ninguna penalizacion con ID: " + id);
            }
        }
    }

    public Penalizacion listarPenalizacionPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Penalizaciones WHERE id=?";
        Penalizacion penalizacion = null;

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    penalizacion = crearPenalizacionDesdeResultSet(rs);
                }
            }
        }

        return penalizacion;
    }

    public ArrayList<Penalizacion> listarPenalizacionesPorIdAlquiler(int idAlquiler) throws SQLException {
        String sql = "SELECT * FROM Penalizaciones WHERE id_alquiler=?";
        ArrayList<Penalizacion> listaPenalizaciones = new ArrayList<>();

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAlquiler);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Penalizacion penalizacion = crearPenalizacionDesdeResultSet(rs);
                    listaPenalizaciones.add(penalizacion);
                }
            }
        }

        return listaPenalizaciones;
    }

    public ArrayList<Penalizacion> listarTodasPenalizaciones() throws SQLException {
        String sql = "SELECT * FROM Penalizaciones";
        ArrayList<Penalizacion> listaPenalizaciones = new ArrayList<>();

        try (Connection con = ConexionBD.conexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Penalizacion penalizacion = crearPenalizacionDesdeResultSet(rs);
                listaPenalizaciones.add(penalizacion);
            }
        }

        return listaPenalizaciones;
    }

    // Metodo privado para no repetir codigo al crear objetos Penalizacion desde la base de datos
    private Penalizacion crearPenalizacionDesdeResultSet(ResultSet rs) throws SQLException {
        Penalizacion penalizacion = new Penalizacion(
                rs.getString("motivo"),
                rs.getDouble("importe"),
                TipoDesperfecto.valueOf(rs.getString("desperfecto"))
        );

        penalizacion.setId(rs.getInt("id"));

        return penalizacion;
    }

}
