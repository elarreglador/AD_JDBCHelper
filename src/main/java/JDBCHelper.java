import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCHelper {

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

		// Ejecutar la consulta y cerrar consulta
		ResultSet rs = pstmt.executeQuery();
		pstmt.close();

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
		
		// Ejecuta la consulta (rs declarado arriba)
		rs = stmt.executeQuery("SELECT * FROM personas");

		// Muestra el ResultSet obtenido
		showResultSet(rs);
		
		
		// Cierra la conexion a la bd
		con.close();

	}
	
	
	
	
	
	
	
	
	
	

	// INDICA SI LA TABLA EXISTE EN NUESTRA BD
	// Unidad 3.2 - JDBC Statements
	// implementa una función estática para saber si
	// el nombre de una tabla existe en una base de datos
	public static boolean containsTable(Connection con, String tableName) throws Exception {
		// Connection con = DriverManager.getConnection("jdbc:sqlite:sample.db");
		// String tableName = "personas";
		
		if(con.isClosed() == true) { // verificacion de estar conectado a bd
			throw new Exception("Excepcion en containsTable(): Se requiere estar conectado a la BD para llamar a esta funcion.");
		}

		boolean retorno = false;
		ResultSet respuesta = con.getMetaData().getTables(null, null, tableName, null);
		if (respuesta.next()) {
			do {
				retorno = true;
			} while (respuesta.next());
		}
		return retorno;
	}


	// MOSTRAR EL CONTENIDO DE UN ResultSet POR COLUMNAS
	// Unidad 3.2 - JDBC Statements
	// Implementa una función estática para mostrar el contenido de un
	// ResultSet. Para cada fila del conjunto de resultados,
	// imprime el contenido de cada columna
	public static void showResultSet(ResultSet res) throws SQLException {
		ResultSetMetaData metadata = res.getMetaData();
		int numColumnas = metadata.getColumnCount();

		// titulos columna
		for (int i=1; i < numColumnas+1; i++) {
			System.out.print("  |  " + metadata.getColumnName(i) );
		}
		System.out.println("  |  ");
		System.out.println();
		
		// campos
		while (res.next()) {
			for (int i=1; i < numColumnas+1; i++) {
				System.out.print("  |  "+res.getString(i) ) ;
			}
			System.out.println("  |  ");
		}
	}

}
