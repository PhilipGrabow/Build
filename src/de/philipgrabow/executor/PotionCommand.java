package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionCommand implements CommandExecutor {
	
	//private Main plugin;
	//public Potion (Main plugin) {
		//this.plugin = plugin;
	//}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("potion")) {
			if(args.length == 0) {
				if(p.hasPermission("bcp.potion.0")) {
					p.sendMessage(ChatColor.GREEN + "Potion 1 - Direkt Heilung 1 Minute");
					p.sendMessage(ChatColor.GREEN + "Potion 2 - Regeneration 20 Sekunden");
					p.sendMessage(ChatColor.GREEN + "Potion 3 - Sofortiger Schaden(ACHTUNG TOT!!!)");
					p.sendMessage(ChatColor.GREEN + "Potion 4 - Unsichtbarkeit");
					p.sendMessage(ChatColor.GREEN + "Potion 5 - Schnelles Abbauen(Achtung wirklich schnell!!)");
					p.sendMessage(ChatColor.AQUA + "Wähle eines von den Potion aus in dem du die Ziffer hinter /potion schreibst");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}

			}
			else if(args[0].equalsIgnoreCase("1")) {
				if(p.hasPermission("bcp.potion.1")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,1200, 20 ));
					p.sendMessage(ChatColor.GREEN + "Du hast dich für Die Potion 1 entschieden!");
					p.sendMessage(ChatColor.RED + "Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
			} 
			}
			else if(args[0].equalsIgnoreCase("2")) {
				if(p.hasPermission("bcp.potion.2")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,400, 20 ));
					p.sendMessage(ChatColor.GREEN + " Du hast dich für Die Potion 2 entschieden!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("3")) {
				if(p.hasPermission("bcp.potion.3")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HARM,400, 1 ));
					p.sendMessage(ChatColor.GREEN + " Du hast dich für Die Potion 3 entschieden!");
					p.sendMessage(" Warum bist du nur von uns gegangen???!!!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("4")) {
				if(p.hasPermission("bcp.potion.4")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,7200, 50));
					p.sendMessage(ChatColor.GREEN + " Du hast dich für Die Potion 4 entschieden!");
					p.sendMessage(ChatColor.AQUA + p.getName() + " du bist jetzt sehr lange Unsichtbar!!!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("5")) {
				if(p.hasPermission("bcp.potion.5")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,3600, 50));
					p.sendMessage(ChatColor.GREEN + " Du hast dich für Die Potion 5 entschieden!");
					p.sendMessage(ChatColor.AQUA + p.getName() + " du bist jetzt lange Feuerresistent!!!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("6")) {
				if(p.hasPermission("bcp.potion.6")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,1200, 100));
					p.sendMessage(ChatColor.GREEN + " Du hast dich für Die Potion 6 entschieden!");
					p.sendMessage(ChatColor.AQUA + p.getName() + " du kannst jetzt Schnell Farmen!!!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + " Du hast nicht die Permissions diesen Befehl auszuführen!!!");
					return true;
				}
			}
			else if(args[0].equalsIgnoreCase("7")) {
				if(p.hasPermission("bcp.potion.7")) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 75));
					p.sendMessage(ChatColor.AQUA + p.getName() + " dir wird jetzt Schwindelig!!!");
					p.sendMessage(ChatColor.RED + " Für die Umkehrung trinke Milch!!!");
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			if (!(sender instanceof Player)) {
				System.out.println("Dieser Befehl ist nur für Spieler!");
				return true;
			}
			if(args.length >1) {
				p.sendMessage(ChatColor.RED + "Zu viele Argumente!");
				return false;
			}
		}
		return false;
	}
	
	

}
