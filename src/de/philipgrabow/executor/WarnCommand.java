package de.philipgrabow.executor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.philipgrabow.build.Main;

public class WarnCommand implements CommandExecutor {
	private Main plugin;
	public WarnCommand (Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Date zeitstempel = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[dd/MM/yyyy HH-mm-ss]");
		String datum = simpleDateFormat.format(zeitstempel);
		Player p = (Player) sender;
		
		
		if (cmd.getName().equalsIgnoreCase("warn")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.warn.info")) {
					p.sendMessage("Mache /warn [Spieler] [Grund] damit du einen Spieler verwarnst!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (args.length >= 2) {
				File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
				String UUIDplayer = cfg.getString(args[0]);
				Player ziel = plugin.getServer().getPlayer(UUID.fromString(UUIDplayer));
				if(cfg.contains(args[0])) {
					if (p.hasPermission("bcp.warn.player")) {
					//
						String grund = args[1] + " ";
						for (int i = 2; i < args.length; i++) {
							grund = grund + args[i] + " ";
						}
						//
						if (ziel == null) {
							p.sendMessage("Der Spieler ist nicht Vorhanden!!");
							return true;
						}
						String WarnMessage = ChatColor.GREEN + ziel.getName() +  ChatColor.RED +  " du wurdest Verwarnt!!!";
						plugin.getServer().broadcastMessage(WarnMessage);
						plugin.getServer().broadcastMessage(ChatColor.RED + "Der Grund war: " + ChatColor.AQUA +  grund);
						ziel.sendMessage(ChatColor.GREEN + "Verhalte dich Angemessen der Server Regeln!!!");
						//LastWarn
						//plugin.getConfig().set("BCP.LastWarn.Player", target.getName());
						//plugin.getConfig().set("BCP.LastWarn.Reason", grund);
						//plugin.saveConfig();
						File warnlog = new File("plugins/BuildcraftPrivat/Warns/Log.yml");
						FileConfiguration logyml = YamlConfiguration.loadConfiguration(warnlog);
						File warnplayerlog = new File("plugins/BuildcraftPrivat/Warns/Player/" + p.getName() + ".yml");
						@SuppressWarnings("unused")
						FileConfiguration playerlog = YamlConfiguration.loadConfiguration(warnplayerlog);
						logyml.set(datum, p.getName() + " hat den Spieler " + ziel.getName() + " verwarnt wegen " + grund);
						try {
							logyml.save(warnlog);
						} catch (IOException e) {
							e.printStackTrace();
						}
						//for (int i = 1; i < 10; i++) {
							//playerlog.set("Anzahl", i);
							//target.sendMessage(ChatColor.GREEN + "Du hast jetzt " + i + " Verwarnungen!");
							//try {
								//playerlog.save(warnplayerlog);
							//} catch (IOException e) {
								//e.printStackTrace();
							//}
						//} 						
						//
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Der gewählte Spieler ist nicht in der Datenbank vorhanden!");
					return true;
				}
			}
			else if (args.length == 1 || p.hasPermission("bcp.warn.info")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				p.sendMessage("Du hast keinen Grund angegeben!!");
				return false;
			}
		}
		return false;
	}
	

}
