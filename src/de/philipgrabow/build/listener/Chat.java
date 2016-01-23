package de.philipgrabow.build.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

	@EventHandler (priority = EventPriority.LOW)
	public void onChat (AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String Admin = "ADMIN";
		if (p.hasPermission("bcp.adminchat") && p.isOp()) {
			e.setFormat(ChatColor.BOLD +""+ ChatColor.LIGHT_PURPLE + "[ " + ChatColor.RED +""+ ChatColor.UNDERLINE + Admin + ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + " ]" +ChatColor.RESET+ e.getPlayer().getName() +": "+ e.getMessage());
		} else {
			//
		}
	}
		
	
	
	
}
