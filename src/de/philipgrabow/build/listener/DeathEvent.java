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
import org.bukkit.event.entity.PlayerDeathEvent;

import de.philipgrabow.build.Main;

public class DeathEvent implements Listener {
	
	private Main plugin;
	public DeathEvent (Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onBlockBreak (PlayerDeathEvent e) {
		Date today1 = Calendar.getInstance().getTime();
		 SimpleDateFormat tm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);
		  String reportTime = tm.format(today1);
		  File playerfile = new File ("plugins/BuildcraftPrivat/Log" , "Log.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(playerfile);
			stats.set("[" + reportTime + "]"," " + e.getEntity().getName() + " ist gestorben");
			try {
				stats.save(playerfile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		Boolean getxp = plugin.getConfig().getBoolean("BCP.Death.KeepXP.Enabled");
		e.setKeepLevel(getxp);
		e.setDroppedExp(0);
		if (plugin.getConfig().getBoolean("BCP.Death.DropXP.Enabled") == true) {
			int dropxp = plugin.getConfig().getInt("BCP.Death.DropXP.Amount");
			e.setDroppedExp(dropxp);
		}
		if (plugin.getConfig().getBoolean("BCP.Death.KeepXP.Enabled") == true) {
			String DeathMessageKeepXP = plugin.getConfig().getString("BCP.Death.KeepXP.DeathMessage");
			DeathMessageKeepXP = DeathMessageKeepXP.replace("%p%", e.getEntity().getName());
			e.setDeathMessage(ChatColor.translateAlternateColorCodes('&',DeathMessageKeepXP));
		}
	}

}
