package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("time")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if(p.hasPermission("bcp.time.list")) {
					p.sendMessage(ChatColor.WHITE + "Es ist gerade: " + p.getPlayerTime() + "Ticks");
					p.sendMessage("Mache /time day für Tag und /time night für Nacht");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("day")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if(p.hasPermission("bcp.time.day")) {
					p.sendMessage("Es ist jetzt Tag");
					p.getWorld().setTime(0L);
					p.getServer().broadcastMessage(ChatColor.RED + p.getName() + " hat es zu Tag gemacht!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("night")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if(p.hasPermission("bcp.time.night")) {
					p.sendMessage("Es ist jetzt Nacht");
					p.getWorld().setTime(18000L);
					p.getServer().broadcastMessage(ChatColor.RED + p.getName() + " hat es zu Nacht gemacht!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
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
