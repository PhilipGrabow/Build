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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.philipgrabow.build.Main;

public class SprungCommand implements CommandExecutor {
	
	private Main plugin;
	public SprungCommand(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("sprung")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.sprung.self")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 80));
					p.sendMessage(ChatColor.GREEN + "Du hast jetzt offiziell Sprungkraft für 10 Sekunden!");
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
				if (p.hasPermission("bcp.sprung.other")) {
					File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
					String UUIDplayer = cfg.getString(args[0]);
					Player ziel = plugin.getServer().getPlayer(UUID.fromString(UUIDplayer));
					if (ziel == null) {
						p.sendMessage("Der Spieler ist zurzeit Offline!!!");
						return true;
					} else {
					ziel.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 80));
					ziel.sendMessage(ChatColor.GREEN + "Du hast jetzt offiziell Sprungkraft für 10 Sekunden von " + sender.getName());
					sender.sendMessage("Der Spieler " + ziel.getName() + " hat jetzt seine Sprungkraft für 10 Sekunden!");
					return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if(args.length >1) {
				p.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
		}
		return false;
	}
	

}
