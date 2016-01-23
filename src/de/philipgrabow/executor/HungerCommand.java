package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HungerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("hunger")) {
			if(args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.hunger")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 20 ));
					p.sendMessage(ChatColor.GREEN + "Du musst jetzt Hungern!");
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
