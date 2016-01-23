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

public class Info implements CommandExecutor {
	
	private Main plugin;
	public Info (Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("information")) {
			if(args.length >1) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				p.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.information.self")) {
				p.sendMessage(ChatColor.GREEN + "Das sind die Informationen von" +" "+ p.getDisplayName() +" "+ "!!!");
				p.sendMessage(ChatColor.GREEN + "Darfst du Fliegen guck in die Klammer [ " + ChatColor.RED + p.getAllowFlight() + ChatColor.GREEN + " ]");
				p.sendMessage(ChatColor.GREEN + "Du hast den Gamemode [ " + ChatColor.RED + p.getGameMode() + ChatColor.GREEN + " ]" );
				p.sendMessage(ChatColor.GREEN + "Darfst du hier Items aufheben? Guck in die Klammer [ " + ChatColor.RED + p.getCanPickupItems() + ChatColor.GREEN + " ]");
				p.sendMessage(ChatColor.GREEN + "Das hier ist dein Displayname---->[ " + ChatColor.RED + p.getDisplayName() + ChatColor.GREEN + " ]");
				p.sendMessage(ChatColor.GREEN + "Das ist die Server-Adresse [ " + ChatColor.RED + p.getAddress() + ChatColor.GREEN + " ]");
				p.sendMessage(ChatColor.GREEN + "Das hier ist deine Gehgeschwindigkeit!!---->[ " + ChatColor.RED + p.getWalkSpeed() + ChatColor.GREEN + " ]");
				p.sendMessage(ChatColor.GREEN + "UUID: §c" + p.getUniqueId());
				p.sendMessage(ChatColor.GREEN + "Das waren deine Informationen!!!");
				return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
				}
			}
			if (args.length == 1) {
				File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
				String UUIDplayer = cfg.getString(args[0]);
				Player ziel = plugin.getServer().getPlayer(UUID.fromString(UUIDplayer));
				if (p.hasPermission("bcp.information.other")) {
					if (ziel == null) {
						p.sendMessage(ChatColor.RED + "Der Spieler ist Offline!");
						return true;
					} else {
					p.sendMessage(ChatColor.GREEN + "Das sind die Informationen von" +" "+ ziel.getDisplayName() +" "+ "!!!");
					p.sendMessage(ChatColor.GREEN + "Darf derjenige Fliegen guck in die Klammer [ " + ChatColor.RED + ziel.getAllowFlight() + ChatColor.GREEN + " ]");
					p.sendMessage(ChatColor.GREEN + "Derjenige hat den Gamemode [ " + ChatColor.RED + ziel.getGameMode() + ChatColor.GREEN + " ]" );
					p.sendMessage(ChatColor.GREEN + "Darf er hier Items aufheben? Guck in die Klammer [ " + ChatColor.RED + ziel.getCanPickupItems() + ChatColor.GREEN + " ]");
					p.sendMessage(ChatColor.GREEN + "Das hier ist sein Displayname---->[ " + ChatColor.RED + ziel.getDisplayName() + ChatColor.GREEN + " ]");
					p.sendMessage(ChatColor.GREEN + "Das ist die Server-Adresse [ " + ChatColor.RED + ziel.getAddress() + ChatColor.GREEN + " ]");
					p.sendMessage(ChatColor.GREEN + "Das hier ist seine Gehgeschwindigkeit!!---->[ " + ChatColor.RED + ziel.getWalkSpeed() + ChatColor.GREEN + " ]");
					p.sendMessage(ChatColor.GREEN + "UUID: §c" + ziel.getUniqueId());
					p.sendMessage(ChatColor.GREEN + "Das waren seine Informationen!!!");
					return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
				}
			}
			return true;
		}
		return false;
	}

}
