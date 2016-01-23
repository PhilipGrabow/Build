package de.philipgrabow.build;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class MySQL {
	
	@SuppressWarnings("unused")
	private Main plugin;
	public MySQL(Main plugin) {
		this.plugin = plugin;
	}
	
	
	public static String user = "";
	public static String pass = "";
	public static String host = "";
	public static String port = "";
	public static String db = "";
	public static Connection con;
	
	public static void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":"+port+"/" + db + "?autoReconnect=true", user, pass);
			System.out.println("Connected to MySQL successfully!!");
		} catch (SQLException e) {
			System.err.println("Kann keine Verbindung herstellen!");
		}
		
	}
	
	public static void close() {
		
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
	}
	public static void Update(String qry) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(qry);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	public static void CreateTable(String qry) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(qry);
			System.out.println("Die Tabellen wurden erstellt!");
		} catch (SQLException e) {
			System.err.println("Die Tabellen existieren bereits!");
		}
	}
	public static ResultSet Query(String qry) {
		ResultSet rs = null;
		try {
			Statement stmt = con.createStatement();
			stmt.executeQuery(qry);
		} catch (SQLException e) {
			System.err.println(e);
		}
		
		return rs;
		
	}
	public static String getString(String qry) {
		Connection conn = MySQL.con;
		ResultSet rs = null;
		PreparedStatement st = null;
		String variable = null;
		try {
			st = conn.prepareStatement(qry);
			rs = st.executeQuery();
			rs.last();
			variable = rs.getString(1);
			if(rs.getRow() != 0) {
				variable = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return variable;
	}
	public static int getInt(String qry) {
		Connection conn = MySQL.con;
		ResultSet rs = null;
		PreparedStatement st = null;
		int variable = 1;
		try {
			st = conn.prepareStatement(qry);
			rs = st.executeQuery();
			rs.last();
			variable = rs.getInt(1);
			if(rs.getRow() != 0) {
				variable = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return variable;
	}
	public static List<String> getList(String qry) {
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = MySQL.Query(qry);
			
			while(rs.next()) {
				list.add(rs.getString(1));
			}
			rs.close();
			
		} catch(SQLException e) {
			System.err.println(e);
		}
		return list;
	}
	public static void CreateTable() throws SQLSyntaxErrorException, SQLException {
		MySQL.CreateTable("CREATE TABLE server (ServerName VARCHAR(30) UNIQUE KEY, IP VARCHAR(20), DefaultGamemode VARCHAR(30), MOTD VARCHAR(100))");
		MySQL.CreateTable("CREATE TABLE playerinfo (SpielerName VARCHAR(25) UNIQUE KEY, IP VARCHAR(50), Gamemode VARCHAR(30), OP VARCHAR(20), Loginanzahl INT)");
		MySQL.CreateTable("CREATE TABLE uuid (SpielerName VARCHAR(25) UNIQUE KEY, Date VARCHAR(50), UUID VARCHAR(100))"); 
	}
	public static void CreateServer(String servername) throws com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException{
		MySQL.Update("INSERT INTO `server`(`ServerName`) VALUES ('"+servername+"')");
	}
	public static void Create(String name) {
		MySQL.Update("INSERT INTO `playerinfo` (`SpielerName`) VALUES ('"+name+"')");
		MySQL.Update("INSERT INTO `uuid` (`SpielerName`) VALUES ('"+name+"')");
	}
	public static void PlayerInfoUpdate(String name,Player p, String IP, GameMode Gamemode) {
		boolean op = p.isOp();
		int loginsvor = MySQL.getInt("SELECT `Loginanzahl` FROM `playerinfo` WHERE `SpielerName`='"+name+"'");
		MySQL.Update("UPDATE `playerinfo` SET `SpielerName`='"+name+"',`IP`='"+IP+"', `Gamemode`='"+Gamemode.toString()+"', `OP`='"+op+"', `Loginanzahl`='"+(loginsvor +1)+"' WHERE `Spielername`='"+name+"'");
	}
//	public static void CoordUpdate(String name, double x, double y, double z) {
//		MySQL.Update("UPDATE `server1` SET `Koordinate_X`='"+x+"', `Koordinate_Y`='"+y+"', `Koordinate_Z`='"+z+"' WHERE `SpielerName`='"+name+"'");
//	}
//	public static void LastLogoutUpdate(String name) {
//		Date zeitstempel = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String datum = simpleDateFormat.format(zeitstempel);
//		MySQL.Update("UPDATE `server1` SET `Letzter_Logout`='"+datum+"' WHERE `SpielerName`='"+name+"'");
//	}
//	public static void LastLoginUpdate(String name) {
//		Date zeitstempel = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String datum = simpleDateFormat.format(zeitstempel);
//		MySQL.Update("UPDATE `server1` SET `Letzter_Login`='"+datum+"' WHERE `SpielerName`='"+name+"'");
//	}
//	public static void onDeathUpdate(String name, double x, double y, double z) {
//		Date zeitstempel = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String deathtime = simpleDateFormat.format(zeitstempel);
//		MySQL.Update("UPDATE `deaths` SET `SpielerName`='"+name+"' WHERE `SpielerName`='"+name+"'");
//		MySQL.Update("UPDATE `deaths` SET `Letzter_Death`='"+deathtime+"' WHERE `SpielerName`='"+name+"'");
//		MySQL.Update("UPDATE `deaths` SET `x_Koordinate`='"+x+"' WHERE `SpielerName`='"+name+"'");
//		MySQL.Update("UPDATE `deaths` SET `y_Koordinate`='"+y+"' WHERE `SpielerName`='"+name+"'");
//		MySQL.Update("UPDATE `deaths` SET `z_Koordinate`='"+z+"' WHERE `SpielerName`='"+name+"'");
//	}
//	public static void onEnabledServer() {
//		Date zeitstempel = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String datum = simpleDateFormat.format(zeitstempel);
//		String ip = Bukkit.getServerName().toString();
//		MySQL.Update("UPDATE `serverstatus` SET `Online`='Online' WHERE `ServerName`='"+ip+"'");
//		MySQL.Update("UPDATE `serverstatus` SET `Server_ging_Online`='"+datum+"' WHERE `ServerName`='"+ip+"'");
//		MySQL.Update("UPDATE `serverstatus` SET `ServerName`='"+ip+"' WHERE `ServerName`='"+ip+"'");
//	}
//	public static void onDisabledServer() {
//		Date zeitstempel = new Date();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//		String datum = simpleDateFormat.format(zeitstempel);
//		String ip = Bukkit.getServerName().toString();
//		MySQL.Update("UPDATE `serverstatus` SET `Online`='Offline' WHERE `ServerName`='"+ip+"'");
//		MySQL.Update("UPDATE `serverstatus` SET `Server_ging_Offline`='"+datum+"' WHERE `ServerName`='"+ip+"'");
//		MySQL.Update("UPDATE `serverstatus` SET `ServerName`='"+ip+"' WHERE `ServerName`='"+ip+"'");
//	}
	public static void UUIDupdate(Player p) {
		Date zeitstempel = Calendar.getInstance().getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MMMM.yyyy HH:mm:ss");
		String datum = simpleDateFormat.format(zeitstempel);
		java.util.UUID id = p.getPlayer().getUniqueId();
		String name = p.getName();
//		Bukkit.broadcastMessage(name);
//		Bukkit.broadcastMessage(datum);
//		Bukkit.broadcastMessage(id.toString());
		MySQL.Update("UPDATE `uuid` SET `UUID`='"+id.toString()+"' WHERE `SpielerName`='"+name+"'");
		MySQL.Update("UPDATE `uuid` SET `Date`='"+datum+"' WHERE `SpielerName`='"+name+"'");
	}
	public static void ServerUpdate(String servername, String ip, String Gamemode) {
		if(ip.equals("")) {
			ip = "Localhost/127.0.0.1";
		}
		MySQL.Update("UPDATE `server` SET `IP`='"+ip+"' WHERE `ServerName`='"+servername+"'");
		MySQL.Update("UPDATE `server` SET `DefaultGamemode`='"+Gamemode+"' WHERE `ServerName`='"+servername+"'");
//		MySQL.Update("UPDATE `server` SET `MOTD`='"+MOTD+"' WHERE `ServerName`='"+servername+"'");
	}
//	public static String getPlayerxCoord(String name) {
//		Connection conn = MySQL.con;
//		ResultSet rs = null;
//		PreparedStatement st = null;
//		String coordx = null;
//		try {
//			st = conn.prepareStatement("SELECT `Koordinate_X` FROM `server1` WHERE `SpielerName`='"+name+"'");
//			rs = st.executeQuery();
//			rs.last();
//			coordx = rs.getString(1);
//			if(rs.getRow() != 0) {
////				rs.last();
//				coordx = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return coordx;
//	}
//	public static String getPlayeryCoord(String name) {
//		Connection conn = MySQL.con;
//		ResultSet rs = null;
//		PreparedStatement st = null;
//		String coordx = null;
//		try {
//			st = conn.prepareStatement("SELECT `Koordinate_Y` FROM `server1` WHERE `SpielerName`='"+name+"'");
//			rs = st.executeQuery();
//			rs.last();
//			coordx = rs.getString(1);
//			if(rs.getRow() != 0) {
////				rs.last();
//				coordx = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return coordx;
//	}
//	public static String getPlayerzCoord(String name) {
//		Connection conn = MySQL.con;
//		ResultSet rs = null;
//		PreparedStatement st = null;
//		String coordx = null;
//		try {
//			st = conn.prepareStatement("SELECT `Koordinate_Z` FROM `server1` WHERE `SpielerName`='"+name+"'");
//			rs = st.executeQuery();
//			rs.last();
//			coordx = rs.getString(1);
//			if(rs.getRow() != 0) {
////				rs.last();
//				coordx = rs.getString(1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return coordx;
//	}
}
