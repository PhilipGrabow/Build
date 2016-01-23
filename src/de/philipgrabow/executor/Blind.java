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

public class Blind implements CommandExecutor {
	
	private Main plugin;
	public Blind(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("blind")) {
			int secondsleft = 200/2/10;
			if(args.length == 0) {
				if(p.hasPermission("bcp.blind.self")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 5));
					p.sendMessage("§2Du hast jetzt offiziell Blindheit für " + secondsleft + " Sekunden");
					return true;
				} else {
					p.sendMessage("§4Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if(args.length == 1) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				
				if(p.hasPermission("bcp.blind.other")) {
					File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
					String UUIDplayer = cfg.getString(args[0]);
					Player ziel = plugin.getServer().getPlayer(UUID.fromString(UUIDplayer));
					if(ziel == null) {
						p.sendMessage("Der Spieler " + ziel + " ist nicht Online");
						return true;
					} else {
						ziel.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 5));
						p.sendMessage("Â§2Der Spieler " + ziel.getName() + " ist für " + secondsleft + " Sekunden Blind");
						return true;
					}
				} else {
					p.sendMessage("Â§4Du hast nicht die Berechtigung diesen Befehl auszuführen!");
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
