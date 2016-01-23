package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("status")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if(p.hasPermission("bcp.status")) {
					p.sendMessage("Das Plugin heißt: " + ChatColor.GOLD + "Buildcraft-Privat");
					p.sendMessage(p.getName() +ChatColor.AQUA +" du hast den Command /bcp ausgeführt mit genügend Permissions!!!:D");
					p.getServer().getMotd();
					p.getServer().getPluginCommand("information");
					p.sendMessage(ChatColor.RED + "Das ist die Bukkit-Version vom Server: ");
					p.sendMessage(p.getServer().getBukkitVersion());
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
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
