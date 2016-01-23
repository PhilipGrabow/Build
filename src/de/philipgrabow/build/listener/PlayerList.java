package de.philipgrabow.build.listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.philipgrabow.build.Main;

public class PlayerList implements Listener {
	
	private Main plugin;
	public PlayerList (Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onFirstJoin1 (PlayerJoinEvent e) {
		if (!(e.getPlayer().hasPlayedBefore())) {
			Date today1 = Calendar.getInstance().getTime();
			 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
			  String reportTime = tm.format(today1);
			  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
				FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
				stats.set("[" + reportTime + "]"," " + e.getPlayer().getName() + " hat den Server zum ersten Mal betreten.");
				try {
					stats.save(playerfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		plugin.getConfig().set("BCP.Player.LatestOnline", e.getPlayer().getName());
	}
	

	
}
