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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.philipgrabow.build.Main;

public class JoinListener implements Listener {
	
	private  Main plugin;
	public JoinListener (Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onJoin (PlayerJoinEvent e) throws IOException {
		//Join Listener
		if (plugin.getConfig().getBoolean("BCP.Join.Enabled") == true) {
		String joinMessage = plugin.getConfig().getString("BCP.Join.Message");
		joinMessage = joinMessage.replace("%p%", e.getPlayer().getName());
		e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
		File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
		FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
		Date today1 = Calendar.getInstance().getTime();
		SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
		  String reportTime = tm.format(today1);
		stats.set("["+ reportTime + "]",e.getPlayer().getName() + " Server betreten");
		stats.save(playerfile);
		}
		//Nichts
	}
	@EventHandler (priority = EventPriority.NORMAL)
	public void onLeave (PlayerQuitEvent e) {
		//Leave Listener
		if (plugin.getConfig().getBoolean("BCP.Leave.Enabled") == true) {
			String quitMessage = plugin.getConfig().getString("BCP.Leave.Message");
			quitMessage = quitMessage.replace("%p%", e.getPlayer().getName());
			e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', quitMessage));
			Date today1 = Calendar.getInstance().getTime();
			 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
			  String reportTime = tm.format(today1);
			  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
				FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
				stats.set("[" + reportTime + "]"," " + e.getPlayer().getName() + " Server verlassen");
				try {
					stats.save(playerfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		//Nichts
	}

}
