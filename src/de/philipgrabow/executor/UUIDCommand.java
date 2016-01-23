package de.philipgrabow.executor;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.philipgrabow.build.Main;

public class UUIDCommand implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Main plugin;
	public UUIDCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("UUID")) {
			if(args.length == 0) {
				p.sendMessage("Hier ist deine UUID: " + p.getUniqueId());
				return true;
			}
			if(args.length == 1){
				File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
				String UUIDplayer = cfg.getString(args[0]);
				if(cfg.contains(args[0])) {
					p.sendMessage("Hier die UUID von Spieler " + args[0] + " : " + UUIDplayer);
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Kein Spieler vorhanden in der Datenbank!");
					return true;
				}
			}
			if(args.length > 1) {
				p.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
		}
		return false;
	}
	

}
