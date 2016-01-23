package de.philipgrabow.executor;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.philipgrabow.build.Main;

public class OpCommand implements CommandExecutor {
	
	private Main plugin;
	public OpCommand(Main plugin) {
		this.plugin = plugin;
	}

	//@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//		Player p = (Player) sender;
		if(plugin.getConfig().getBoolean("BCP.OpCommand.enabled")== true) {
		
		if(cmd.getName().equalsIgnoreCase("op")) {
			if(sender.hasPermission("bcp.op")) {
				if(args.length == 0) {
					if(Bukkit.getOperators().size() == 0) {
						sender.sendMessage("Es sind keine Operatoren auf diesem Server gesetzt!");
						return true;
					} else {
					sender.sendMessage("Der Operator-Command wird von dem Plugin Buildcraft-Privat gesteuert!");
					sender.sendMessage("Momentan sind es: " + Bukkit.getOperators().size() + " Operatoren!");
					return true;
					}
				}
				if(args.length == 1) {
					File UUIDfile = new File("plugins/BuildcraftPrivat/UUID", "UUID.yml");
					FileConfiguration cfg = YamlConfiguration.loadConfiguration(UUIDfile);
					String UUIDplayer = cfg.getString(args[0]);
					Player ziel = Bukkit.getServer().getPlayer(UUID.fromString(UUIDplayer));
					if(ziel.isOp()) {
						ziel.setOp(false);
						sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ziel.getName() + " hat jetzt keine OP-Rechte mehr!");
						ziel.sendMessage(ChatColor.YELLOW + "Du hast deinen Posten als Operator verloren!");
						return true;
					}
					ziel.setOp(true);
					sender.sendMessage(ChatColor.GREEN + "Der Spieler " + ziel.getName() + " hat jetzt OP-Rechte!");
					ziel.sendMessage(ChatColor.YELLOW + "Du bist absofort Operator auf diesem Server!");
					return true;
				}
				if(args.length >1) {
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "Du hast keine Berechtigung für diesen Command!");
				return true;
			}
			return true;
		}
		} else {
			sender.sendMessage(ChatColor.RED + "Der OpCommand ist zurzeit ausgeschaltet!");
			return true;
		}
		return false;
	}
	
	

}
