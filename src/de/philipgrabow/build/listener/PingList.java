package de.philipgrabow.build.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.philipgrabow.build.Main;
import de.philipgrabow.build.MySQL;

public class PingList implements Listener, CommandExecutor{
	
	private Main plugin;
	public PingList (Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPing (ServerListPingEvent e) {
		plugin.reloadConfig();
		if (plugin.getConfig().getBoolean("BCP.Motd.Enabled") == true) {
			if(MySQL.con != null) {
				if(MySQL.getString("SELECT `MOTD` FROM `server` WHERE `ServerName`='"+Bukkit.getServerName()+"'") == null) {
					String motd = plugin.getConfig().getString("BCP.Motd.Message");
					MySQL.Update("UPDATE `server` SET `MOTD`='"+motd+"' WHERE `ServerName`='"+Bukkit.getServerName()+"'");
					String Motdfertig = MySQL.getString("SELECT `MOTD` FROM `server` WHERE `ServerName`='"+Bukkit.getServerName()+"'");
					e.setMotd(ChatColor.translateAlternateColorCodes('&', Motdfertig));
				}
				String Motd = MySQL.getString("SELECT `MOTD` FROM `server` WHERE `ServerName`='"+Bukkit.getServerName()+"'");
				e.setMotd(ChatColor.translateAlternateColorCodes('&', Motd));
			} else {
				String Motd = plugin.getConfig().getString("BCP.Motd.Message");
				e.setMotd(ChatColor.translateAlternateColorCodes('&', Motd));
			}
		} else {
			//Nothing
		}
		if (plugin.getConfig().getString("BCP.Motd.Enabled").equalsIgnoreCase("Test")) {
			int SpielerAnzahl = e.getNumPlayers();
			e.setMotd("" + SpielerAnzahl + " von " + e.getMaxPlayers() + " Spielern on !!!");
		}
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setmotd")) {
			if (args.length == 0) {
				if(sender.hasPermission("bcp.setmotd.text")) {
				sender.sendMessage(ChatColor.RED + "Du hast kein MOTD angegeben!");
				return false;
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if(args.length >=1){
				if(sender.hasPermission("bcp.setmotd.motdtext")) {
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						str.append(args[i]+ " ");
					
					}
				if(MySQL.con != null) {
					String motd = str.toString();
					plugin.getConfig().set("BCP.Motd.Message", motd);
					MySQL.Update("UPDATE `server` SET `MOTD`='"+motd+"' WHERE `ServerName`='"+Bukkit.getServerName()+"'");
					String Motdmysql = MySQL.getString("SELECT `MOTD` FROM `server` WHERE `ServerName`='"+Bukkit.getServerName()+"'");
					sender.sendMessage(ChatColor.YELLOW + "Neuer Motd gesetzt");
					sender.sendMessage(ChatColor.DARK_GREEN + "Der neue Motd lautet: §f" + ChatColor.translateAlternateColorCodes('&', Motdmysql));
					plugin.saveConfig();
					return true;
				} else {
					String motd = str.toString();
					plugin.getConfig().set("BCP.Motd.Message", motd);
					sender.sendMessage(ChatColor.YELLOW + "Neuer Motd gesetzt");
					sender.sendMessage(ChatColor.DARK_GREEN + "Der neue Motd lautet: §f" + ChatColor.translateAlternateColorCodes('&', motd));
					plugin.saveConfig();
					return true;
				}
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
				
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("motd")) {
			if(args.length == 0) {
				if(sender.hasPermission("bcp.motd.enabled")) {
					sender.sendMessage(ChatColor.RED + "Zu wenig Argumente!");
					return false;
				}
			}
			if(args[0].equalsIgnoreCase("on")) {
					if(sender.hasPermission("bcp.motd.enabled")) {
					plugin.getConfig().set("BCP.Motd.Enabled", true);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.RED + "Der MOTD wurde angeschaltet!");
					return true;
					} else {
						sender.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
						return true;
					}
			}
			if(args[0].equalsIgnoreCase("off")) {
				if(sender.hasPermission("bcp.motd.enabled")) {
						plugin.getConfig().set("BCP.Motd.Enabled", false);
						plugin.saveConfig();
						sender.sendMessage(ChatColor.RED + "Der MOTD wurde ausgeschaltet!");
						return true;
				} else {
					sender.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if(args.length >=2) {
				sender.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
				
		}
		return false;
	}
	

}
