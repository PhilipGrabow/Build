package de.philipgrabow.build.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.philipgrabow.build.Main;

public class FoodLevelEvent implements Listener {
	
	private Main plugin;
	public FoodLevelEvent (Main plugin) {
		this.plugin = plugin;
	}
	
	String configoption = "BCP.FoodLevelMaximizer.Enabled";
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onFoodChange (FoodLevelChangeEvent e) {
		if(plugin.getConfig().getBoolean(configoption) == true) {
		int foodlevel = 10;
		e.setFoodLevel(foodlevel);
		} else {
			//
		}
	}

}
