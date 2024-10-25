import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class main {

	public static void main(String[] args) throws Exception {

		// CONECTAMOS A LA BD
		System.out.println("CONECTAMOS A LA BD Y CONSULTAMOS LA PERSONA DE UN CODIGO POSTAL");
		String urlJDBC = "jdbc:sqlite:sample.db";
		Connection con = DriverManager.getConnection(urlJDBC);

		// CONSULTAMOS LA PERSONA DE UN CODIGO POSTAL
		// preparamos la consulta
		PreparedStatement pstmt = con.prepareStatement("SELECT nombre, apellido FROM personas WHERE cp = ?;");
		String codigoPostal = "28002";
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
		System.out.println();

		// MUESTRA LA TABLA personas
		// prepara la consulta
		Statement stmt = con.createStatement();

		// Ejecuta la consulta
		// rs declarado arriba
		rs = stmt.executeQuery("SELECT * FROM personas");

		// Muestra el ResultSet obtenido
		showResultSet(rs);

	}
	
	
	
	
	
	
	
	
	
	

	// INDICA SI LA TABLA EXISTE EN NUESTRA BD
	public static boolean containsTable(Connection con, String tableName) throws Exception {
		// Connection con = DriverManager.getConnection("jdbc:sqlite:sample.db");
		// String tableName = "personas";
		
		if(con.isClosed() == true) {
			throw new Exception("Excepcion en containsTable(): Se requiere estar conectado a la BD para llamar a esta funcion.");
		}

		boolean retorno = false;

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

		return retorno;
	}


	// MOSTRAR EL CONTENIDO DE UN ResultSet por columnas
	public static void showResultSet(ResultSet res) throws SQLException {
		ResultSetMetaData metadata = res.getMetaData();
		int columnas = metadata.getColumnCount();

		for (int i=1; i < columnas+1; i++) {
			System.out.print("  |  " + metadata.getColumnName(i) );
		}
		System.out.println("  |  ");
		System.out.println();
		
		while (res.next()) {
			for (int i=1; i < columnas+1; i++) {
				System.out.print("  |  "+res.getString(i) ) ;
			}
			System.out.println("  |  ");
		}
	}

}
