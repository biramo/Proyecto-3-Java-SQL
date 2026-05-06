package Controller;

import model.Alquiler;
import model.Cliente;
import model.Enum.EstadoPago;
import model.Instrumento;
import model.Penalizacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AlquilerCRUD {

    // Constructor
    public AlquilerCRUD() {

    }

    // ------------ INSERTAR ALQUILER ------------ //
    public void insertarAlquiler(Alquiler alquiler) throws SQLException {
        String sql = "INSERT INTO Alquileres " +
                "(dni_cliente, id_instrumento, fecha_inicio, fecha_fin_prevista, fecha_fin_real, importe_base, importe_final, observaciones, estadopago) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, alquiler.getCliente().getDni());
                ps.setInt(2, alquiler.getInstrumento().getId());
                ps.setObject(3, alquiler.getFechaInicio());
                ps.setObject(4, alquiler.getFechaFinPrevista());
                ps.setObject(5, alquiler.getFechaFinReal());
                ps.setDouble(6, alquiler.getImporteBase());
                ps.setDouble(7, alquiler.getImporteFinal());
                ps.setString(8, alquiler.getObservaciones());

                // Guardamos el enum como numero:
                // PENDIENTE = 0, PAGADO = 1
                ps.setInt(9, alquiler.getEstadoPagoBD());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            alquiler.setId(rs.getInt(1));
                        }
                    }

                    System.out.println("Alquiler registrado correctamente con ID: " + alquiler.getId());
                }
            }
        }
    }

    // ------------ UPDATE ALQUILER ------------ //
    public void updateAlquiler(Alquiler alquiler) throws SQLException {
        String sql = "UPDATE Alquileres SET dni_cliente=?, id_instrumento=?, fecha_inicio=?, fecha_fin_prevista=?, " +
                "fecha_fin_real=?, importe_base=?, importe_final=?, observaciones=?, estadopago=? WHERE id=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, alquiler.getCliente().getDni());
                ps.setInt(2, alquiler.getInstrumento().getId());
                ps.setObject(3, alquiler.getFechaInicio());
                ps.setObject(4, alquiler.getFechaFinPrevista());
                ps.setObject(5, alquiler.getFechaFinReal());
                ps.setDouble(6, alquiler.getImporteBase());
                ps.setDouble(7, alquiler.getImporteFinal());
                ps.setString(8, alquiler.getObservaciones());
                ps.setInt(9, alquiler.getEstadoPagoBD());
                ps.setInt(10, alquiler.getId());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Alquiler con ID: " + alquiler.getId() + ", actualizado correctamente");
                } else {
                    System.out.println("No se encontro ningun alquiler con ID: " + alquiler.getId());
                }
            }
        }
    }

    // ------------ DELETE ALQUILER ------------ //
    public void deleteAlquiler(int id) throws SQLException {
        String sql = "DELETE FROM Alquileres WHERE id=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Alquiler con ID: " + id + ", eliminado correctamente");
                } else {
                    System.out.println("No existe ningun alquiler con ID: " + id);
                }
            }
        }
    }

    // ------------ CANCELAR ALQUILER (SOFT DELETE) ------------ //
    public void cancelarAlquiler(int id, String motivo, LocalDate fechaCancelacion) throws SQLException {
        String sql = "UPDATE Alquileres SET cancelado=true, fecha_cancelacion=?, motivo_cancelacion=? WHERE id=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setObject(1, fechaCancelacion);
                ps.setString(2, motivo);
                ps.setInt(3, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Alquiler con ID: " + id + ", cancelado correctamente");
                } else {
                    System.out.println("No existe ningun alquiler con ID: " + id);
                }
            }
        }
    }

    // ------------ LISTAR ALQUILER POR ID ------------ //
    public Alquiler listarAlquilerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Alquileres WHERE id=?";
        Alquiler alquiler = null;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        alquiler = crearAlquilerDesdeResultSet(rs);
                    }
                }
            }
        }

        return alquiler;
    }

    // ------------ LISTAR TODOS LOS ALQUILERES ------------ //
    public ArrayList<Alquiler> listarTodosAlquileres() throws SQLException {
        String sql = "SELECT * FROM Alquileres";
        ArrayList<Alquiler> listaAlquileres = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Alquiler alquiler = crearAlquilerDesdeResultSet(rs);
                    listaAlquileres.add(alquiler);
                }
            }
        }

        return listaAlquileres;
    }

    // ------------ LISTAR ALQUILERES POR CLIENTE ------------ //
    public ArrayList<Alquiler> listarAlquileresPorCliente(String dniCliente) throws SQLException {
        String sql = "SELECT * FROM Alquileres WHERE dni_cliente=? ORDER BY fecha_inicio DESC";
        ArrayList<Alquiler> listaAlquileres = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, dniCliente);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Alquiler alquiler = crearAlquilerDesdeResultSet(rs);
                        listaAlquileres.add(alquiler);
                    }
                }
            }
        }

        return listaAlquileres;
    }

    // ------------ LISTAR ALQUILERES POR INSTRUMENTO ------------ //
    public ArrayList<Alquiler> listarAlquileresPorInstrumento(int idInstrumento) throws SQLException {
        String sql = "SELECT * FROM Alquileres WHERE id_instrumento=? ORDER BY fecha_inicio DESC";
        ArrayList<Alquiler> listaAlquileres = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idInstrumento);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Alquiler alquiler = crearAlquilerDesdeResultSet(rs);
                        listaAlquileres.add(alquiler);
                    }
                }
            }
        }

        return listaAlquileres;
    }

    // ------------ LISTAR ALQUILERES ACTIVOS ------------ //
    public ArrayList<Alquiler> listarAlquileresActivos() throws SQLException {
        String sql = "SELECT * FROM Alquileres WHERE fecha_fin_real IS NULL ORDER BY fecha_inicio ASC";
        ArrayList<Alquiler> listaAlquileres = new ArrayList<>();

        try (Connection con = ConexionBD.conexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Alquiler alquiler = crearAlquilerDesdeResultSet(rs);
                listaAlquileres.add(alquiler);
            }
        }

        return listaAlquileres;
    }

    // ------------ REGISTRAR DEVOLUCIÓN ------------ //
    public void registrarDevolucion(Alquiler alquiler, LocalDate fechaFinReal) throws SQLException {
        String sql = "UPDATE Alquileres SET fecha_fin_real=?, importe_final=? WHERE id=?";

        alquiler.registrarDevolucion(fechaFinReal);
        alquiler.recalcularImporteFinal();

        try (Connection con = ConexionBD.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, alquiler.getFechaFinReal());
            ps.setDouble(2, alquiler.getImporteFinal());
            ps.setInt(3, alquiler.getId());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Devolución registrada correctamente");
            } else {
                System.out.println("No se encontro ningun alquiler con ID: " + alquiler.getId());
            }
        }
    }

    // ------------ MARCAR COMO PAGADO ------------ //
    public void marcarComoPagado(int id) throws SQLException {
        String sql = "UPDATE Alquileres SET estadopago=? WHERE id=?";


        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setBoolean(1, true);
                ps.setInt(2, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Alquiler marcado como pagado");
                } else {
                    System.out.println("No se encontro ningun alquiler con ID: " + id);
                }
            }
        }
    }

    // Método privado para no repetir código al crear objetos Alquiler desde la base de datos
    private Alquiler crearAlquilerDesdeResultSet(ResultSet rs) throws SQLException {
        ClienteCRUD clienteCRUD = new ClienteCRUD();
        InstrumentoCRUD instrumentoCRUD = new InstrumentoCRUD();
        PenalizacionCRUD penalizacionCRUD = new PenalizacionCRUD();

        Cliente cliente = clienteCRUD.listarClientePorDni(rs.getString("dni_cliente"));
        Instrumento instrumento = instrumentoCRUD.listarInstrumentoPorId(rs.getInt("id_instrumento"));

        Alquiler alquiler = new Alquiler(
                cliente,
                instrumento,
                rs.getObject("fecha_inicio", LocalDate.class),
                rs.getObject("fecha_fin_prevista", LocalDate.class),
                rs.getString("observaciones"),
                //método desde bd -> para transformar ese boolean en el enum y saber a cuál me refiero
                EstadoPago.desdeBD(rs.getInt("estadopago"))
        );

        alquiler.setId(rs.getInt("id"));
        alquiler.setFechaFinReal(rs.getObject("fecha_fin_real", LocalDate.class));
        alquiler.setImporteBase(rs.getDouble("importe_base"));
        alquiler.setImporteFinal(rs.getDouble("importe_final"));

        try {
            alquiler.setCancelado(rs.getBoolean("cancelado"));
            alquiler.setFechaCancelacion(rs.getObject("fecha_cancelacion", LocalDate.class));
            alquiler.setMotivoCancelacion(rs.getString("motivo_cancelacion"));
        } catch (SQLException ignored) {
            // Columnas opcionales si la BD aun no fue migrada
        }

        //Listamos las penalizacion y las agregamos al objeto de cliente
        ArrayList<Penalizacion> penalizaciones =
                penalizacionCRUD.listarPenalizacionesPorIdAlquiler(alquiler.getId());

        alquiler.setPenalizaciones(penalizaciones);

        return alquiler;
    }
}
