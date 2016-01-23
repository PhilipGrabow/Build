package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HilfeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("hilfe")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur f�r Spieler!");
					return true;
				}
				if(p.hasPermission("bcp.help.list")) {
					p.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Das ist der Help-Command von BuildcraftPrivat");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "Gebe /help Buildcraftprivat f�r Hilfe ein dort stehen alle Commands von diesem Plugin!!!");
					p.getServer().broadcastMessage(p.getName() + " hat den Command /hilfe ausgef�hrt!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuf�hren!!!");
					return true;
				}
			}
			if(args.length >0) {
				p.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
		}
		return false;
	}
	

}
