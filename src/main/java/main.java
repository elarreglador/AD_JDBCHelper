import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main {

	public static void main(String[] args) throws SQLException {
		
		//CONECTAMOS A LA BD Y CONSULTAMOS LA PERSONA DE UN CODIGO POSTAL
		System.out.println("CONECTAMOS A LA BD Y CONSULTAMOS LA PERSONA DE UN CODIGO POSTAL");
		String urlJDBC = "jdbc:sqlite:sample.db"; 
		Connection con = DriverManager.getConnection(urlJDBC);

		// preparamos la consulta
		PreparedStatement pstmt = con.prepareStatement("SELECT nombre, apellido FROM personas WHERE cp = ?;");
        String codigoPostal = "28005";
        pstmt.setString(1, codigoPostal);

		// Ejecutar la consulta
		ResultSet rs = pstmt.executeQuery();
		// Listar el resultado
		while (rs.next()) {
			String nombre = rs.getString("nombre");
			String apellido = rs.getString("apellido");
			System.out.println(codigoPostal + " : " + nombre + " " + apellido);
		}
		System.out.println();
		
		
		
		
		// CONSULTAMOS SI LA TABLA personas EXISTE EN NUESTRA BD
		System.out.println("CONSULTAMOS SI LA TABLA personas EXISTE");
		System.out.println(containsTable(con, "personas"));
		
		
	}

	
	// INDICA SI LA TABLA EXISTE EN NUESTRA BD
	public static boolean containsTable(Connection con, String tableName) {
		// Connection con = DriverManager.getConnection("jdbc:sqlite:sample.db");
		// String tableName = "personas";

		boolean retorno = false;

		try (Connection conexion = con;) {
			ResultSet respuesta = con.getMetaData().getTables(null, null, tableName, null);

			if (respuesta.next()) {
				// La tabla "+ tableName +" existe en la base de datos y movemos el cursor a la
				// primera fila del ResultSet
				do {
					retorno = true;
					// System.out.println("Catalogo: " + respuesta.getString(1));
					// System.out.println("esquema: " + respuesta.getString(2));
					// System.out.println("tabla: " + respuesta.getString(3));
					// System.out.println("tipo: " + respuesta.getString(4));
				} while (respuesta.next());
			} else {
				// La tabla "+ tableName +" no existe en la base de datos
			}

		} catch (SQLException e) {
			System.err.println("EXCEPCION: " + e.getMessage());
		}

		return retorno;
	}

}
