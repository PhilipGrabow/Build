package de.philipgrabow.build.listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import de.philipgrabow.build.Main;

public class KickReason implements Listener {
	
	private Main plugin;
	public KickReason (Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler (priority = EventPriority.LOW)
	public void onKick (PlayerKickEvent e) {
		Date today1 = Calendar.getInstance().getTime();
		 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
		  String reportTime = tm.format(today1);
		  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
			stats.set("[" + reportTime + "]"," " + e.getPlayer().getName() + " wurde gekickt");
			try {
				stats.save(playerfile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		String kickReason = plugin.getConfig().getString("Kick.Reason");
		e.setReason(ChatColor.translateAlternateColorCodes('&', kickReason));
		e.setLeaveMessage("Der Spieler " + e.getPlayer() + " wurde vom Server gekickt. Grund: ");// + kickReason);
	}
	

}
