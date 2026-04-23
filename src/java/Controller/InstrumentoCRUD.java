package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class InstrumentoCRUD {

    /* Para reo */
    /*
    int filasAfectadas = ps.executeUpdate();

    if (filasAfectadas > 0) {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                System.out.println("ID generado: " + idGenerado);
            }
        }
    }
    */

    public void eliminar(int id){

        String sql = "DELETE FROM Instrumentos WHERE id=? ";

        try{
            Connection conn = ConexionBD.conexion();
            if (conn == null) return;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            int fA = ps.executeUpdate();

            if(fA > 0){
                System.out.println("Instrumento eliminado");
            } else {
                System.out.println("No hay ningún instrumento con esta id/ Este ordenador no existe");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

}
