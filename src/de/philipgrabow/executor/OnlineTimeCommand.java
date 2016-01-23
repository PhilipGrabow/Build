package de.philipgrabow.executor;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class OnlineTimeCommand implements CommandExecutor {
	
	File playerfile = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "OnlineTimes.yml");
	FileConfiguration cfg = YamlConfiguration.loadConfiguration(playerfile);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("onlinetime")) {
			if(sender instanceof Player) {
				if(args.length == 0) {
					//OnlineZeit von dir angezeigt
					if(cfg.contains("OnlineTime." + sender.getName())) {
					int onlinezeit = cfg.getInt("OnlineTime." + sender.getName());
					sender.sendMessage(ChatColor.YELLOW + "Deine OnlineZeit beträgt: " + onlinezeit + " Minuten auf diesem Server");
					sender.sendMessage(ChatColor.BLUE + "Das sind etwa " + onlinezeit/60 + " Stunde/n die du hier verbracht hast!");
					sender.sendMessage(ChatColor.RED + "VIELEN DANK FÜR DEINE ANWESENHEIT!!!");
					return true;
					} else {
						sender.sendMessage("Du besitzt keine OnlineZeit auf diesem Server. Wende dich an ein Admin!");
						return true;
					}
				}
				if(args.length == 1) {
					if(cfg.contains("OnlineTime." + args[0])) {
						int OnlineZeit = cfg.getInt("OnlineTime." + args[0]);
						sender.sendMessage(ChatColor.YELLOW + "Der Spieler " + args[0] + " hat " + OnlineZeit + " Minuten hier verbracht.");
						sender.sendMessage(ChatColor.BLUE + "Das sind " + OnlineZeit/60 + " Stunde/n!");
						return true;
					} else {
						sender.sendMessage("Der Spieler" + args[0] + " besitzt keine OnlineZeit auf diesem Server. Wende dich an ein Admin!");
						return true;
					}
				}
				if(args.length >1) {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

}
