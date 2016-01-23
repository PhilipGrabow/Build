package de.philipgrabow.executor;

import java.io.File;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.philipgrabow.build.Main;

public class HealCommand implements CommandExecutor {
	
	private Main plugin;
	public HealCommand (Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("heal")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.heal.self")) {
					p.setHealth(20.0);
					p.setFoodLevel(20);
					p.sendMessage(ChatColor.GREEN + "Du hast dich offiziell Geheilt und dein Essen aufgefüllt!!");
					p.sendMessage(ChatColor.AQUA + "Und hast jetzt "+ p.getFoodLevel() + " von 20 Hungerlevel");
					p.getServer().broadcastMessage(ChatColor.RESET + "[BuildcraftPrivat]" + ChatColor.MAGIC +":::" + ChatColor.RED + p.getName() + " hat sich geheilt!" + ChatColor.RESET + ChatColor.MAGIC + ":::");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.heal.other")) {
					File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
					String UUIDplayer = cfg.getString(args[0]);
					Player ziel = plugin.getServer().getPlayer(UUID.fromString(UUIDplayer));
					if (ziel == null ) {
						p.sendMessage("Der Spieler ist nicht Online!!");
						return true;
					}
					ziel.setHealth(20.0);
					ziel.sendMessage("Du wurdest geheilt von " + sender.getName());
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("alle")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (plugin.getServer().getOnlinePlayers().size() == 0) {
					if (p.hasPermission("bcp.heal.all")) {
						System.out.println("Langeweile?");
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
						return true;
					}
				}
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					player.setHealth(20.0);
				}
				plugin.getServer().broadcastMessage(ChatColor.GREEN + sender.getName() + " hat alle Spieler geheilt");
				return true;
			}
			if (plugin.getServer().getOnlinePlayers().size() == 0) {
				p.sendMessage("Wen willst du heilen?" + ChatColor.RED + "Es ist keiner On");
				return true;
			}
				
		}
		return false;
	}
	

}
