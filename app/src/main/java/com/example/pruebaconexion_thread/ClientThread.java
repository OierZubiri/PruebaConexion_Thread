package com.example.pruebaconexion_thread;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientThread implements Runnable {

	private String sResultado = "";

	public ClientThread() {}

	@Override
	public void run() {
		ResultSet rs 			= null;
		PreparedStatement st 	= null;
		Connection con 			= null;
		try{
			Class.forName("com.mysql.jdbc.Driver");

			String url	=  "jdbc:mysql://192.168.2.91:3306/test?serverTimezone=UTC";
			con			= DriverManager.getConnection( url, "root", "");
			// Consulta sencilla en este caso.
			String sql 	= "SELECT * FROM ubicacion";
			st 			= con.prepareStatement(sql);
			rs 			= st.executeQuery();

			while (rs.next()) {
				String var1	= rs.getString("Nombre");
				Log.i("XXXXXXX", var1);
				sResultado = var1;
			}
		} catch (ClassNotFoundException e) {
			Log.e("ClassNotFoundException", "");
			e.printStackTrace();
		} catch (SQLException e) {
			Log.e("SQLException", "");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("Exception", "");
			e.printStackTrace();
		} finally {
			// We have to close EVERYTHING, even if one fails
			try {
				// Cerrar ResultSet
				if(rs!=null) {
					rs.close();
				}
				// Cerrar PreparedStatement
				if(st!=null) {
					st.close();
				}
				// Cerrar Connection
				if(con!=null) {
					con.close();
				}
			} catch (Exception e) {
				Log.e("Exception_cerrando todo", "");
				e.printStackTrace();
			}
		}
	}

	public String getResponse() {
		return sResultado;
	}
}

