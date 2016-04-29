package me.jonasxpx.pin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MSQLConnection {

	
	private Connection con = null;
	private Statement state = null;
	
	protected MSQLConnection(String address, String db, String user, String password) {
		try{
			StringBuilder data = new StringBuilder();
			data.append("jdbc:mysql://");
			data.append(address);
			data.append("/");
			data.append(db);
			con = DriverManager.getConnection(data.toString(), user, password);
			state = con.createStatement();
			state.execute("CREATE TABLE IF NOT EXISTS xpxpin(username VARCHAR(32) PRIMARY KEY, pin INT(10))");
			XPXPin.getInstance().getLogger().log(Level.FINE, "Conectado.");
		}catch(SQLException e){
			e.printStackTrace();
			XPXPin.getInstance().getLogger().log(Level.SEVERE, "MYSQL ERROR [" + e.getMessage() + "]");
			XPXPin.getInstance().getPluginLoader().disablePlugin(XPXPin.getInstance());
		}
	}
	
	public void registerPin(String username, int pin){
		try{
			PreparedStatement ps = con.prepareStatement("INSERT INTO xpxpin VALUES(?,?)");
			ps.setString(1, username);
			ps.setInt(2, pin);
			ps.execute();
		}catch(SQLException e){
			e.printStackTrace();
			XPXPin.getInstance().getLogger().log(Level.SEVERE, "MYSQL ERROR [" + e.getMessage() + "]");
		}
	}
	
	public boolean isRegistred(String username){
		try {
			ResultSet rs = state.executeQuery("SELECT username FROM xpxpin WHERE username = '" + username + "'");
			if(rs.next()){
				rs.getString(1);
				return true;
			}else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			XPXPin.getInstance().getLogger().log(Level.SEVERE, "MYSQL ERROR [" + e.getMessage() + "]");
		}
		return false;
	}
	
	public boolean equalsPin(String username, int pin){
		try {
			ResultSet rs = state.executeQuery("SELECT * FROM xpxpin WHERE username = '" + username +"' AND pin = " + pin);
			if(rs.next()){
				return true;
			}else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			XPXPin.getInstance().getLogger().log(Level.SEVERE, "MYSQL ERROR [" + e.getMessage() + "]");
		}
		return false;
	}
	
	public int unregisterPin(String username){
		try {
			return state.executeUpdate("DELETE FROM xpxpin WHERE username = '" + username + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			XPXPin.getInstance().getLogger().log(Level.SEVERE, "MYSQL ERROR [" + e.getMessage() + "]");
		}
		return -1;
	}
	
}
