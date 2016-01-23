package de.philipgrabow.build.score;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("scoreboard")) {
			if (!(sender instanceof Player)) {
				System.out.println("Der Command kann nur im Spiel genutzt werden!");
				
			}
			if (args.length == 0) {
				if(p.hasPermission("bcp.scoreboard.enabled")) {
					sender.sendMessage(ChatColor.GREEN + "Mache /scoreboard [on|off] zum ein- und ausschalten des Scoreboards!");
					return true;
				} else {
					p.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("on")) {
				if(p.hasPermission("bcp.scoreboard.enabled")) {
				File ScoreboardStats = new File ("plugins/BuildcraftPrivat" , "config.yml");
				FileConfiguration stats = YamlConfiguration.loadConfiguration(ScoreboardStats);
				stats.set("BCP.Scoreboard.Enabled", true);
				try {
					stats.save(ScoreboardStats);
					sender.sendMessage(ChatColor.RED + "Scoreboard erfolgreich aktiviert!");
					sender.sendMessage(ChatColor.YELLOW + "Verbinde dich einmal neu mit dem Server dann ist es da!");
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return true;
				}
				} else {
					p.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("off")) {
				if(p.hasPermission("bcp.scoreboard.enabled")) {
				File ScoreboardStats = new File ("plugins/BuildcraftPrivat" , "config.yml");
				FileConfiguration stats = YamlConfiguration.loadConfiguration(ScoreboardStats);
				stats.set("BCP.Scoreboard.Enabled", false);
				try {
					stats.save(ScoreboardStats);
					sender.sendMessage(ChatColor.RED + "Scoreboard erfolgreich deaktiviert!");
					sender.sendMessage(ChatColor.YELLOW + "Verbinde dich einmal neu mit dem Server dann ist es weg!");
					return true;
				} catch (IOException e) {
					System.out.println("Fehler beim Scoreboard!");
					e.printStackTrace();
					return true;
				}
				} else {
					p.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
//			if(args[0].equalsIgnoreCase("clearall")) {
//				if(p.hasPermission("bcp.scoreboard.clearall")) {
//				File scoreboardclearall = new File ("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//				@SuppressWarnings("unused")
//				FileConfiguration stats = YamlConfiguration.loadConfiguration(scoreboardclearall);
//				if (scoreboardclearall.exists() ==true) {
//					scoreboardclearall.delete();
//					sender.sendMessage(ChatColor.DARK_GREEN + "Das Scoreboard wurde von allen gelöscht!");
//					return true;
//				}
//				} else {
//					p.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
//					return true;
//				}
//			}
//			if(args[0].equalsIgnoreCase("clear")) {
//				if(p.hasPermission("bcp.scoreboard.clear")) {
//				File scoreboardclearall = new File ("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//				FileConfiguration stats = YamlConfiguration.loadConfiguration(scoreboardclearall);
//				stats.set(p.getName() + ".Deaths", 0);
//				stats.set(p.getName() + ".Kills", 0);
//				try {
//					stats.save(scoreboardclearall);
//					p.sendMessage(ChatColor.DARK_GREEN + "Das Scoreboard wurde von: " + sender.getName() + " geleert!");
//					return true;
//				} catch (IOException e) {
//					System.err.println("Die Datei konnte nicht gespeichert werden!");
//					return true;
//				}
//				} else {
//					p.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung diesen Befehl auszuführen!");
//					return true;
//				}
//			}
			
		}
		return false;
	}
	

}
