package de.philipgrabow.build.score;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.philipgrabow.build.Main;

public class ScoreboardListener implements Listener {
	
	private Main plugin;
	public ScoreboardListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeathEvent (PlayerDeathEvent e) throws IOException {
		Player toter = e.getEntity();
		Score.addDeath(toter);
		 
		 if ((e.getEntity().getKiller() instanceof Player)) {
			 Player killer = (Player) e.getEntity().getKiller();
			 Score.addKill(killer);
			 for (Player p: e.getEntity().getServer().getOnlinePlayers()) {
					Score.createScoreboard(p);
				}
		 }
	}
	@EventHandler (priority = EventPriority.NORMAL)
	public void onJoin (PlayerJoinEvent e) {
		plugin.reloadConfig();
		if(plugin.getConfig().getBoolean("BCP.Scoreboard.Enabled") == true) {
//			Score.createScoreboard(e.getPlayer());
			for (Player p: e.getPlayer().getServer().getOnlinePlayers()) {
				Score.createScoreboard(p);
			}
		}
		else if (e.getPlayer().isOp()== true) {
		e.getPlayer().sendMessage(ChatColor.RED +"Du hast die Möglichkeit in der Config von Buildcraftprivat das Scoreboard zu aktivieren!");
		}
	}
	@EventHandler (priority = EventPriority.NORMAL)
	public void onLeave(PlayerQuitEvent e1) {
		plugin.reloadConfig();
		if(plugin.getConfig().getBoolean("BCP.Scoreboard.Enabled") == true) {
			for (Player p: e1.getPlayer().getServer().getOnlinePlayers()) {
				Score.createScoreboard(p);
			}
			
		} else {
			//Nichts
		}
	}
	
	
}
