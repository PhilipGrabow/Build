package de.philipgrabow.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.philipgrabow.build.Main;

public class MainCommand implements CommandExecutor {
	private Main plugin;
	public MainCommand (Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("bcp")) {
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.bcp")) {
					p.sendMessage(ChatColor.AQUA + "Das ist der HauptCommand von BuildcraftPrivat!!");
					p.sendMessage("Hallo " + p.getName() + " du hast jetzt folgende Möglichkeiten: ");
					p.sendMessage(ChatColor.GREEN + "        reload - Zum reloaden der Config von BuildcraftPrivat!");
					p.sendMessage(ChatColor.GREEN + "        renew  - Zum reloaden des Servers");
					p.sendMessage(ChatColor.GREEN + "        update - Zum Updaten des Plugins");
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("reload")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.reload")) {
					plugin.reloadConfig();
					p.sendMessage(ChatColor.RED + "Config erfolgreich reloaded!!");
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("renew")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.renew")) {
					plugin.getServer().reload();
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Du hast nicht die Berechtigung diesen Befehl auszuführen!");
					return true;
				}
			}
			else if (args[0].equalsIgnoreCase("update")) {
				if (!(sender instanceof Player)) {
					System.out.println("Dieser Befehl ist nur für Spieler!");
					return true;
				}
				if (p.hasPermission("bcp.update")) {
					sender.sendMessage(ChatColor.BLUE + "Willkommen im Update Bereich!!");
					sender.sendMessage(ChatColor.YELLOW + "Aktuelle Version: " + plugin.getDescription().getVersion());
					sender.sendMessage(ChatColor.YELLOW + "Neueste Version : " + "1.8.6");
					sender.sendMessage(ChatColor.YELLOW + "Aktuelle Version der Config: " + plugin.getConfig().getString("BCP.Version"));
					if (plugin.getConfig().getString("BCP.Version") == plugin.getDescription().getVersion()) {
						p.sendMessage(ChatColor.RED + "Du hast die aktuellste Version von dem Plugin");
						//Updater kommt noch!
					}
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
