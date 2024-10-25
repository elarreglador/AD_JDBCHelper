import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main {

	public static void main(String[] args) {
		String urlJDBC = "jdbc:sqlite:sample.db"; // Ruta de la base de datos

		// Usamos try-with-resources para manejar conexiones y declaraciones
		// automáticamente
		try (	Connection con = DriverManager.getConnection(urlJDBC);
				PreparedStatement pstmt = con.prepareStatement("SELECT nombre, apellido FROM personas WHERE cp = ?;")) {
			
			// Código postal a buscar
            String codigoPostal = "28005";
            pstmt.setString(1, codigoPostal);

			// Ejecutar la consulta
			try (ResultSet rs = pstmt.executeQuery()) {
				// Procesar los resultados
				while (rs.next()) {
					String nombre = rs.getString("nombre");
					String apellido = rs.getString("apellido");
					System.out.println(nombre + " | " + apellido);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al conectar a la base de datos o ejecutar la consulta: " + e.getMessage());
		}
	}

	
	
	
	// Método para conectar (actualmente no implementado)
	public static void containsTable(Connection con, String tableName) {
		
	}

}
