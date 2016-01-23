package de.philipgrabow.build.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.philipgrabow.build.Main;

public class Starterkit implements Listener {
	
	private Main plugin;
	public Starterkit (Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.NORMAL)
	public void onFirstJoin (PlayerJoinEvent e) {
		if (!(e.getPlayer().hasPlayedBefore())) {
			//
			String FirstJoinMessage = plugin.getConfig().getString("BCP.FirstJoin.Message");
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', FirstJoinMessage));
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.Item.1.Enabled") == true) {
				int FirstJoinItem1 = plugin.getConfig().getInt("BCP.FirstJoin.Item.1.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinItem1, 1));
			} else {
				//
			}
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.Item.2.Enabled") == true) {
				int FirstJoinItem2 = plugin.getConfig().getInt("BCP.FirstJoin.Item.2.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinItem2, 1));
			} else {
				//
			}
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.Item.3.Enabled") == true) {
				int FirstJoinItem3 = plugin.getConfig().getInt("BCP.FirstJoin.Item.3.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinItem3, 1));
			} else {
				//
			}
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.Item.4.Enabled") == true) {
				int FirstJoinItem4 = plugin.getConfig().getInt("BCP.FirstJoin.Item.4.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinItem4, 1));
				} else {
					//
				}
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.Item.5.Enabled") == true) {
				int FirstJoinItem5 = plugin.getConfig().getInt("BCP.FirstJoin.Item.5.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinItem5, 1));
			} else {
				//
			}
			//
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.R�stung.Kopf.Enabled") == true) {
				int FirstJoinR�stung1 = plugin.getConfig().getInt("BCP.FirstJoin.R�stung.Kopf.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinR�stung1, 1));
			} else {
				//
			}
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.R�stung.BauchPanzer.Enabled") == true) {
				int FirstJoinR�stung2 = plugin.getConfig().getInt("BCP.FirstJoin.R�stung.BauchPanzer.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinR�stung2, 1));
			} else {
				//
			}
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.R�stung.Hose.Enabled") == true) {
				int FirstJoinR�stung3 = plugin.getConfig().getInt("BCP.FirstJoin.R�stung.Hose.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinR�stung3, 1));
			} else {
				//
			}
			if (plugin.getConfig().getBoolean("BCP.FirstJoin.R�stung.Schuhe.Enabled") == true) {
				int FirstJoinR�stung4 = plugin.getConfig().getInt("BCP.FirstJoin.R�stung.Schuhe.ID");
				e.getPlayer().getInventory().addItem(new ItemStack(FirstJoinR�stung4, 1));
			} else {
				//
			}
		}
	}

}
