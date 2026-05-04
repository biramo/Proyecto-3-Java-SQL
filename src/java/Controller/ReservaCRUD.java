package Controller;

import model.Cliente;
import model.Instrumento;
import model.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaCRUD {

    // Constructor
    public ReservaCRUD() {

    }

    public void insertarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reservas (dni_cliente, id_instrumento, fecha_reserva, posicion_lista_espera, activa) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (// Vamos a querer recuperar la id insertada por lo que hacemos uso del PreparedStatement.RETURN_GENERATED_KEYS
                 PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                int posicion = calcularSiguientePosicionListaEspera(reserva.getInstrumento().getId());
                //me guardo la posicion de la lista de espera
                reserva.setPosicionListaEspera(posicion);

                ps.setString(1, reserva.getCliente().getDni());
                ps.setInt(2, reserva.getInstrumento().getId());
                ps.setObject(3, reserva.getFechaReserva());
                ps.setInt(4, reserva.getPosicionListaEspera());
                ps.setBoolean(5, reserva.isActiva());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    // Del resultado buscamos los key generados
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            // Colocamos la id dentro del objeto reserva
                            reserva.setId(rs.getInt(1));
                        }
                    }
                }
            }
        }
    }

    public void cancelarReserva(Reserva reserva) throws SQLException {
        String sqlCancelar = "UPDATE Reservas SET activa=false WHERE id=?";

        String sqlReordenar = "UPDATE Reservas SET posicion_lista_espera = posicion_lista_espera - 1 " +
                "WHERE id_instrumento=? AND activa=true AND posicion_lista_espera > ?";

        try (Connection con = ConexionBD.conexion()) {

            // Cancelamos la reserva en el objeto
            reserva.setActiva(false);

            // Cancelamos la reserva en la base de datos
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sqlCancelar)) {
                ps.setInt(1, reserva.getId());
                ps.executeUpdate();
            }

            // Reordenamos las reservas que estaban por debajo
            try (PreparedStatement ps = con.prepareStatement(sqlReordenar)) {
                ps.setInt(1, reserva.getInstrumento().getId());
                ps.setInt(2, reserva.getPosicionListaEspera());
                ps.executeUpdate();
            }

            System.out.println("Reserva cancelada correctamente y lista de espera actualizada");
        }
    }

    /* No quiero Usarlo - complicaria la gestion de las colas en la reserva innecesariamente*/
    public void updateReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE Reservas SET dni_cliente=?, id_instrumento=?, fecha_reserva=?, posicion_lista_espera=?, activa=? WHERE id=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, reserva.getCliente().getDni());
                ps.setInt(2, reserva.getInstrumento().getId());
                ps.setObject(3, reserva.getFechaReserva());
                ps.setInt(4, reserva.getPosicionListaEspera());
                ps.setBoolean(5, reserva.isActiva());
                ps.setInt(6, reserva.getId());

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Reserva con ID: " + reserva.getId() + ", actualizada correctamente");
                } else {
                    System.out.println("No se encontro ninguna reserva con ID: " + reserva.getId());
                }
            }
        }
    }

    /*No quiero usarlo, prefiero el SOFT Delete de cancelar */
    public void deleteReserva(int id) throws SQLException {
        String sql = "DELETE FROM Reservas WHERE id=?";

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    System.out.println("Reserva con ID: " + id + ", eliminada correctamente");
                } else {
                    System.out.println("No existe ninguna reserva con ID: " + id);
                }
            }
        }
    }

    public Reserva listarReservaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE id=?";
        Reserva reserva = null;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        reserva = crearReservaDesdeResultSet(rs);
                    }
                }
            }
        }

        return reserva;
    }

    public ArrayList<Reserva> listarReservasPorInstrumento(int idInstrumento) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE id_instrumento=? ORDER BY posicion_lista_espera ASC";
        ArrayList<Reserva> listaReservas = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idInstrumento);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Reserva reserva = crearReservaDesdeResultSet(rs);
                        listaReservas.add(reserva);
                    }
                }
            }
        }

        return listaReservas;
    }

    public ArrayList<Reserva> listarReservasActivasPorInstrumento(int idInstrumento) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE id_instrumento=? AND activa=true ORDER BY posicion_lista_espera ASC";
        ArrayList<Reserva> listaReservas = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idInstrumento);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Reserva reserva = crearReservaDesdeResultSet(rs);
                        listaReservas.add(reserva);
                    }
                }
            }
        }

        return listaReservas;
    }

    //al pasarle el cliente que me monte una lista de reservas y desde el menú seleccionaré una para poder operar
    public ArrayList<Reserva> listarReservasActivasPorCliente(Cliente cliente) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE dni_cliente=? AND activa=true ORDER BY fecha_reserva ASC";
        ArrayList<Reserva> listaReservas = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, cliente.getDni());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Reserva reserva = crearReservaDesdeResultSet(rs);
                        listaReservas.add(reserva);
                    }
                }
            }
        }

        return listaReservas;
    }

    public ArrayList<Reserva> listarTodasReservas() throws SQLException {
        String sql = "SELECT * FROM Reservas";
        ArrayList<Reserva> listaReservas = new ArrayList<>();

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    Reserva reserva = crearReservaDesdeResultSet(rs);
                    listaReservas.add(reserva);
                }
            }
        }

        return listaReservas;
    }

    private int calcularSiguientePosicionListaEspera(int idInstrumento) throws SQLException {
        String sql = "SELECT MAX(posicion_lista_espera) AS ultima_posicion FROM Reservas WHERE id_instrumento=? AND activa=true";
        int siguientePosicion = 1;

        try (Connection con = ConexionBD.conexion()) {
            assert con != null;
            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idInstrumento);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        siguientePosicion = rs.getInt("ultima_posicion") + 1;
                    }
                }
            }
        }

        return siguientePosicion;
    }

    // Método privado para no repetir código al crear objetos Reserva desde la base de datos
    private Reserva crearReservaDesdeResultSet(ResultSet rs) throws SQLException {
        ClienteCRUD clienteCRUD = new ClienteCRUD();
        InstrumentoCRUD instrumentoCRUD = new InstrumentoCRUD();

        Cliente cliente = clienteCRUD.listarClientePorDni(rs.getString("dni_cliente"));
        Instrumento instrumento = instrumentoCRUD.listarInstrumentoPorId(rs.getInt("id_instrumento"));

        Reserva reserva = new Reserva(
                cliente,
                instrumento,
                rs.getObject("fecha_reserva", LocalDate.class)
        );

        reserva.setId(rs.getInt("id"));
        reserva.setPosicionListaEspera(rs.getInt("posicion_lista_espera"));
        reserva.setActiva(rs.getBoolean("activa"));

        return reserva;
    }
}