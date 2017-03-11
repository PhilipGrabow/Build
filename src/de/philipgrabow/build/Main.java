package de.philipgrabow.build;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import de.philipgrabow.build.UUID.UUID;
import de.philipgrabow.build.listener.DeathEvent;
import de.philipgrabow.build.listener.JoinListener;
import de.philipgrabow.build.listener.OnlineTime;
import de.philipgrabow.build.listener.PingList;
import de.philipgrabow.build.listener.PlayerList;
import de.philipgrabow.build.listener.ProjectileHit;
import de.philipgrabow.build.listener.Starterkit;
import de.philipgrabow.build.score.ScoreboardCommand;
import de.philipgrabow.build.score.ScoreboardListener;
import de.philipgrabow.executor.Blind;
import de.philipgrabow.executor.GodwithNightVisionMode;
import de.philipgrabow.executor.HealCommand;
import de.philipgrabow.executor.HilfeCommand;
import de.philipgrabow.executor.HungerCommand;
import de.philipgrabow.executor.Info;
import de.philipgrabow.executor.MainCommand;
import de.philipgrabow.executor.OnlineTimeCommand;
import de.philipgrabow.executor.OpCommand;
import de.philipgrabow.executor.PotionCommand;
import de.philipgrabow.executor.SprungCommand;
import de.philipgrabow.executor.StatusCommand;
import de.philipgrabow.executor.TimeCommand;
import de.philipgrabow.executor.UUIDCommand;
import de.philipgrabow.executor.WarnCommand;




public class Main extends JavaPlugin implements Listener ,CommandExecutor{

	
	File file = new File("plugins/BuildcraftPrivat/Scoreboard" , "ScoreboardStats.yml");
	File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
	
	
	@Override
	public void onDisable() {
		//MySQL
		if(MySQL.con != null) {
			MySQL.close();
			}
		/////////////////////////
		Date today1 = Calendar.getInstance().getTime();
		 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
		  String reportTime = tm.format(today1);
		  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
			stats.set("[" + reportTime + "]"," "+" Server gestoppt");
			try {
				stats.save(playerfile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		System.out.println("[" + getName() + "]" + " Disabled!" + " v" + getDescription().getVersion());
		System.out.print("[" + getName() + "]" + "Ist das dein Ernst???");
	}
	@Override
	public void onEnable() {
		//MySQL
		//
		//File OnlinePlayerTime Config!!!!
		//
		//
		File config = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "Config.yml");
		FileConfiguration configcfg = YamlConfiguration.loadConfiguration(config);
		configcfg.addDefault("OnlineTime.Aktualisierungsrate", 1);
		configcfg.addDefault("OnlineTime.Belohnung_1.Minuten", 10);
		configcfg.addDefault("OnlineTime.Belohnung_1.Item_ID", 264);
		configcfg.addDefault("OnlineTime.Belohnung_1.Item_Anzahl", 1);
		configcfg.addDefault("OnlineTime.Belohnung_2.Minuten", 60);
		configcfg.addDefault("OnlineTime.Belohnung_2.Item_ID_1", 388);
		configcfg.addDefault("OnlineTime.Belohnung_2.Item_Anzahl_1", 15);
		configcfg.addDefault("OnlineTime.Belohnung_2.Item_ID_2", 264);
		configcfg.addDefault("OnlineTime.Belohnung_2.Item_Anzahl_2", 3);
		configcfg.options().copyDefaults(true);
		try {
			configcfg.save(config);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		//
		try {
			loadMySQLServerData();
			File mysqldata = new File("plugins/BuildcraftPrivat", "MySQL.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqldata);
			String Host = cfg.getString("MySQL.BCP.Server.Host");
			String Port = cfg.getString("MySQL.BCP.Server.Port");
			String user = cfg.getString("MySQL.BCP.Server.Username");
			String pass = cfg.getString("MySQL.BCP.Server.Passwort");
			String db = cfg.getString("MySQL.BCP.Server.Database");
			
			//MySQL-Server Daten
			MySQL.host = Host;
			MySQL.port = Port;
			MySQL.user = user;
			MySQL.pass = pass;
			MySQL.db = db;
			
			if(cfg.getBoolean("MySQL.BCP.Server.Enabled") == false) {
				getLogger().log(Level.INFO, "Es wird kein MySQL genutzt weil keine Daten vorhanden sind!");
			} else {
			try {
				MySQL.connect();
				if(MySQL.con != null) {
				MySQL.CreateTable();
				}
			} catch (SQLException e) {
				System.out.println("Tabelle existiert bereits!");
			}
			}
		} catch (NullPointerException e) {
			getLogger().log(Level.WARNING, "Kann keine Verbindung mit MySQL-Datenbank aufbauen!");
		}
		if(MySQL.con != null) {
		} else {
			File mysqldata = new File("plugins/Buildcraft-MySQL", "MySQL.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqldata);
			cfg.set("MySQL.BCP.Server.Enabled", false);
		}
			//////////////////
//		Listener
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new JoinListener(this), this);
//		pm.registerEvents(new KickReason(this), this);
		pm.registerEvents(new PingList(this), this);
		pm.registerEvents(new Starterkit(this), this);
		pm.registerEvents(new DeathEvent(this), this);
		pm.registerEvents(new ProjectileHit(), this);
		pm.registerEvents(new PlayerList(this), this);
		pm.registerEvents(new ScoreboardListener(this), this);
		pm.registerEvents(new UUID(), this);
		pm.registerEvents(this, this);
		pm.registerEvents(new OnlineTime(this), this);
		pm.registerEvents(this, this);
//		Executor
		getCommand("potion").setExecutor(new PotionCommand());
		getCommand("blind").setExecutor(new Blind(this));
		getCommand("sprung").setExecutor(new SprungCommand(this));
		getCommand("heal").setExecutor(new HealCommand(this));
		getCommand("bcp").setExecutor(new MainCommand(this));
		getCommand("hunger").setExecutor(new HungerCommand());
		getCommand("information").setExecutor(new Info(this));
		getCommand("status").setExecutor(new StatusCommand());
		getCommand("time").setExecutor(new TimeCommand());
		getCommand("hilfe").setExecutor(new HilfeCommand());
		getCommand("warn").setExecutor(new WarnCommand(this));
		getCommand("scoreboard").setExecutor(new ScoreboardCommand());
		getCommand("setmotd").setExecutor(new PingList(this));
		getCommand("motd").setExecutor(new PingList(this));
		getCommand("UUID").setExecutor(new UUIDCommand(this));
		getCommand("op").setExecutor(new OpCommand(this));
		getCommand("onlinetime").setExecutor(new OnlineTimeCommand());
		getCommand("godmode").setExecutor(new GodwithNightVisionMode());
		//Files
				File ScoreboardDir = new File("plugins/BuildcraftPrivat/Scoreboard");
				if(!ScoreboardDir.exists()) {
					//Create Dir
					ScoreboardDir.mkdir();
				}
				File Playerdir = new File("plugins/BuildcraftPrivat/Log");
				if(!Playerdir.exists()) {
					//Create Dir
					Playerdir.mkdir();
				}
				File Scoreboarddir1 = new File("plugins/BuildcraftPrivat/Scoreboard");
				if(Scoreboarddir1.exists()) {
					if(!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							System.out.println("[BuildcraftPrivat] Der Ordner konnte nicht gefunden werden!");
							e.printStackTrace();
						}
					}
				}
				File Playerdir1 = new File("plugins/BuildcraftPrivat/Log");
				if(Playerdir1.exists()) {
					if(!playerfile.exists()) {
						try {
							playerfile.createNewFile();
						} catch (IOException e) {
							System.out.println("[BuildcraftPrivat] Der Ordner konnte nicht gefunden werden!");
							e.printStackTrace();
						}
					}
				}
		//Enabling
		loadConfig();
		loadScoreboardStats();
		getConfig().set("BCP.Version", getDescription().getVersion());
		Date today1 = Calendar.getInstance().getTime();
		 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
		  String reportTime = tm.format(today1);
		  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
			stats.set("[" + reportTime + "]"," " + " Server gestartet");
			try {
				stats.save(playerfile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//MySQL
			//Server-Daten Create und Update
			if(!(MySQL.con != null)){
				
			} else {
			//
			//
			try {
				//				if
////				String motdholen = MySQL.getString("SELECT `MOTD` FROM `server` WHERE `ServerName`='"+Bukkit.getServerName()+"'");
////				cfg.set("BCP.Motd.Message", motdholen);
//				cfg.save(config);
//				reloadConfig();
//				String motd = cfg.getString("BCP.Motd.Message");
				MySQL.CreateServer(Bukkit.getServerName());
				MySQL.ServerUpdate(Bukkit.getServerName(), Bukkit.getIp(), Bukkit.getDefaultGameMode().toString());
			} catch (MySQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}
			System.out.println("[" + getDescription().getFullName() + "]" + " enabled!");
			}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		if(!(MySQL.con != null)){
			
		} else {
		loadMySQLPlayerConfig(e.getPlayer());
		File mysqldata = new File("plugins/BuildcraftPrivat", "MySQLPlayerConfig.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqldata);
		File mysql = new File("plugins/BuildcraftPrivat", "MySQL.yml");
		FileConfiguration cfg1 = YamlConfiguration.loadConfiguration(mysql);
		//Erstellen des Benutzers in Datenbank
		if(cfg.getBoolean("MySQL.BCP.Player."+e.getPlayer().getName()+".FirstJoin") == true) {
			if(cfg1.getBoolean("MySQL.BCP.Server.Enabled") == true) {
			MySQL.Create(e.getPlayer().getName());
			cfg.set("MySQL.BCP.Player." + e.getPlayer().getName() + ".FirstJoin", false);
			try {
				cfg.save(mysqldata);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
		}
		if(cfg.getBoolean("MySQL.BCP.Player." + e.getPlayer().getName() + ".FirstJoin") == false) {
			MySQL.UUIDupdate(e.getPlayer());
			MySQL.PlayerInfoUpdate(e.getPlayer().getName(), e.getPlayer(), e.getPlayer().getAddress().toString(), e.getPlayer().getGameMode());
		}
		}
	}
		public void loadMySQLServerData() {
			File mysqldata = new File("plugins/BuildcraftPrivat", "MySQL.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqldata);
			if(cfg.contains("MySQL.BCP.Server.Host") && (cfg.getBoolean("MySQL.BCP.Server.Enabled") == true)) {
				
			} else {
			cfg.set("MySQL.BCP.Server.Enabled", false);
			cfg.set("MySQL.BCP.Server.Host", "localhost");
			cfg.set("MySQL.BCP.Server.Port", "3306");
			cfg.set("MySQL.BCP.Server.Username", "root");
			cfg.set("MySQL.BCP.Server.Passwort", "Hier kommt euer Passwort...");
			cfg.set("MySQL.BCP.Server.Database", "root");
			try {
				cfg.save(mysqldata);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
			getLogger().log(Level.INFO,"MySQL Daten geladen.....");
		}
		public void loadMySQLPlayerConfig(Player p) {
			if(!(MySQL.con != null)) {
				
			} else {
			File mysqldata = new File("plugins/BuildcraftPrivat", "MySQLPlayerConfig.yml");
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqldata);
			
			cfg.addDefault("MySQL.BCP.Player." + p.getName() + ".FirstJoin" , true);
			
			cfg.options().copyDefaults(true);
			try {
				cfg.save(mysqldata);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		}
	public void loadConfig() {
		
		System.out.println("[" + getName() +"]" + " Config geladen!!!");
		
		String ScoreboardEnabled = "BCP.Scoreboard.Enabled";
		getConfig().addDefault(ScoreboardEnabled, false);
		
		String JoinMessageenabled = "BCP.Join.Enabled";
		getConfig().addDefault(JoinMessageenabled, true);
		
		String JoinMessage = "BCP.Join.Message";
		getConfig().addDefault(JoinMessage,"&6%p%&d betritt den Server");
		
		String LeaveMessageenabled = "BCP.Leave.Enabled";
		getConfig().addDefault(LeaveMessageenabled, true);
		
		String LeaveMessage = "BCP.Leave.Message";
		getConfig().addDefault(LeaveMessage, "&6%p%&d verlÃ¤sst den Server!!");
		
		String Motd = "BCP.Motd.Enabled";
		getConfig().addDefault(Motd, false);
		
		String MotdMessage = "BCP.Motd.Message";
		getConfig().addDefault(MotdMessage, "&6&l&k:::&dStandart Motd Nachricht&6&l&k:::");
		
		String FoodLevelEventEnabled = "BCP.FoodLevelMaximizer.Enabled";
		getConfig().addDefault(FoodLevelEventEnabled, false);
		
		String kickReason = "BCP.Kick.Reason";
		getConfig().addDefault(kickReason, "Kicked from this Server");
		
		String firstjoinmessage = "BCP.FirstJoin.Message";
		getConfig().addDefault(firstjoinmessage, "&dHallo und Herzlich Willkommen auf unserem Server!!");
		
		String FirstJoinItem1Enabled = "BCP.FirstJoin.Item.1.Enabled";
		getConfig().addDefault(FirstJoinItem1Enabled, false);
		
		String FirstJoinItem1 = "BCP.FirstJoin.Item.1.ID";
		getConfig().addDefault(FirstJoinItem1, 256);
		
		String FirstJoinItem2Enabled = "BCP.FirstJoin.Item.2.Enabled";
		getConfig().addDefault(FirstJoinItem2Enabled, false);
		
		String FirstJoinItem2 = "BCP.FirstJoin.Item.2.ID";
		getConfig().addDefault(FirstJoinItem2, 257);
		
		String FirstJoinItem3Enabled = "BCP.FirstJoin.Item.3.Enabled";
		getConfig().addDefault(FirstJoinItem3Enabled, false);
		
		String FirstJoinItem3 = "BCP.FirstJoin.Item.3.ID";
		getConfig().addDefault(FirstJoinItem3, 258);
		
		String FirstJoinItem4Enabled = "BCP.FirstJoin.Item.4.Enabled";
		getConfig().addDefault(FirstJoinItem4Enabled, false);
		
		String FirstJoinItem4 = "BCP.FirstJoin.Item.4.ID";
		getConfig().addDefault(FirstJoinItem4, 267);
		
		String FirstJoinItem5Enabled = "BCP.FirstJoin.Item.5.Enabled";
		getConfig().addDefault(FirstJoinItem5Enabled, false);
		
		String FirstJoinItem5 = "BCP.FirstJoin.Item.5.ID";
		getConfig().addDefault(FirstJoinItem5, 292);
		
		String FirstJoinRüstungHeadEnabled = "BCP.FirstJoin.Rüstung.Kopf.Enabled";
		getConfig().addDefault(FirstJoinRüstungHeadEnabled, false);
		
		String FirstJoinRüstungHead = "BCP.FirstJoin.Rüstung.Kopf.ID";
		getConfig().addDefault(FirstJoinRüstungHead, 306);
		
		String FirstJoinRüstungBauchPanzerEnabled = "BCP.FirstJoin.Rüstung.BauchPanzer.Enabled";
		getConfig().addDefault(FirstJoinRüstungBauchPanzerEnabled, false);
		
		String FirstJoinRüstungPanzer = "BCP.FirstJoin.Rüstung.BauchPanzer.ID";
		getConfig().addDefault(FirstJoinRüstungPanzer, 307);
		
		String FirstJoinRüstungHoseEnabled = "BCP.FirstJoin.Rüstung.Hose.Enabled";
		getConfig().addDefault(FirstJoinRüstungHoseEnabled, false);
		
		String FirstJoinRüstungLeggings = "BCP.FirstJoin.Rüstung.Hose.ID";
		getConfig().addDefault(FirstJoinRüstungLeggings, 308);
		
		String FirstJoinRüstungSchuheEnabled = "BCP.FirstJoin.Rüstung.Schuhe.Enabled";
		getConfig().addDefault(FirstJoinRüstungSchuheEnabled, false);
		
		String FirstJoinRüstungBoots = "BCP.FirstJoin.Rüstung.Schuhe.ID";
		getConfig().addDefault(FirstJoinRüstungBoots, 309);
		
		String DropXPEnabled = "BCP.Death.DropXP.Enabled";
		getConfig().addDefault(DropXPEnabled, false);
		
		String DropXPAmount = "BCP.Death.DropXP.Amount";
		getConfig().addDefault(DropXPAmount, 10);
		
		String KeepXPEnabled = "BCP.Death.KeepXP.Enabled";
		getConfig().addDefault(KeepXPEnabled, false);
		
		String KeepXPDeathMessage = "BCP.Death.KeepXP.DeathMessage";
		getConfig().addDefault(KeepXPDeathMessage, "&2 %p% &bdu bist gestorben hast aber deine XP-Level behalten!!");
		
		//
		FileConfiguration cfg = this.getConfig();
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	public void loadScoreboardStats() {
		File config = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardConfig.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		cfg.set("Scoreboard.Scores.1", "Kills:");
		cfg.set("Scoreboard.Scores.2", "Deaths:");
		cfg.set("Scoreboard.Scores.3", "KDR:");
		cfg.set("Scoreboard.Scores.4", "Max:");
		cfg.set("Scoreboard.Scores.5", "Online:");
//		cfg.set("Scoreboard.Scores.6", "Online-Zeit:");
		try {
			cfg.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}