package de.philipgrabow.build.score;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Score implements Listener{
	
	public static void createScoreboard(Player player) {
		
		ScoreboardManager sm = player.getServer().getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		
		Objective score = board.registerNewObjective("aaa", "bbb");
		score.setDisplayName("§6Statistik:");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		File config = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardConfig.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		String eins = cfg.getString("Scoreboard.Scores.1");
		String zwei = cfg.getString("Scoreboard.Scores.2");
		String drei = cfg.getString("Scoreboard.Scores.3");
		String vier = cfg.getString("Scoreboard.Scores.4");
		String fünf = cfg.getString("Scoreboard.Scores.5");
//		String sechs = cfg.getString("Scoreboard.Scores.6");
		org.bukkit.scoreboard.Score kills = score.getScore(eins);
		org.bukkit.scoreboard.Score deaths = score.getScore(zwei);
		org.bukkit.scoreboard.Score kdr = score.getScore(drei);
		org.bukkit.scoreboard.Score Max = score.getScore(vier);
		org.bukkit.scoreboard.Score Online = score.getScore(fünf);
//		org.bukkit.scoreboard.Score OnlineTime = score.getScore(sechs);
		int onlineplayer = Bukkit.getOnlinePlayers().size();
//		File playerfile = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "OnlineTimes.yml");
//		FileConfiguration ccfg = YamlConfiguration.loadConfiguration(playerfile);
//		int OnlineTimePlayer = ccfg.getInt(player.getName());
		int maxplayer = Bukkit.getMaxPlayers();
		int killss = Score.getKills(player);
		int deathss = Score.getDeaths(player);
		if(killss > 0 && deathss > 0) {
			int kd = (killss)/(deathss);
			kdr.setScore(kd);
		} else {
			kdr.setScore(0);
		}
		
		kills.setScore(Score.getKills(player));
		deaths.setScore(Score.getDeaths(player));
		Max.setScore(maxplayer);
		Online.setScore(onlineplayer);
//		OnlineTime.setScore(OnlineTimePlayer);
		//Scoreboard gesetzt
		player.setScoreboard(board);
	}
//	public static int getKills(Player p) {
//		File kills = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//		FileConfiguration stats = YamlConfiguration.loadConfiguration(kills);
//		int back = stats.getInt(p.getName()+ ".Kills");
//		return back;
//	}
//	public static int getDeaths(Player p) {
//		File deaths = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//		FileConfiguration stats = YamlConfiguration.loadConfiguration(deaths);
//		int back = stats.getInt(p.getName()+ ".Deaths");
//		return back;
//	}
//	public static void addKill(Player p) throws IOException {
//		File kills = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//		FileConfiguration stats = YamlConfiguration.loadConfiguration(kills);
//		int killanz = stats.getInt(p.getName()+ ".Kills");
//		stats.set(p.getName()+ ".Kills", killanz+1);
//		stats.save(kills);
//			//MySQL Kills werden aktualisiert
//				int reellekills = stats.getInt(p.getName() + ".Kills");
//				String name = p.getName();
//				MySQL.Update("UPDATE `scoreboard` SET `Kills`='"+reellekills+"' WHERE `SpielerName`='"+name+"'");
//	}
//	public static void addDeath(Player p) throws IOException {
//		File deaths = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
//		FileConfiguration stats = YamlConfiguration.loadConfiguration(deaths);
//		int deathanz = stats.getInt(p.getName()+ ".Deaths");
//		stats.set(p.getName()+ ".Deaths", deathanz+1);
//		stats.save(deaths);
//			//MySQL Deaths werden aktualisiert
//			int reelledeaths = stats.getInt(p.getName() + ".Deaths");
//			String name = p.getName();
//			MySQL.Update("UPDATE `scoreboard` SET `Deaths`='"+reelledeaths+"' WHERE `SpielerName`='"+name+"'");
//	}
	//MySQL-Ablesung!
	public static int getKills(Player p) {
			File kills = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(kills);
			int back = stats.getInt(p.getName()+ ".Kills");
			return back;
	}
	public static int getDeaths(Player p) {
			File deaths = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(deaths);
			int back = stats.getInt(p.getName()+ ".Deaths");
			return back;
	}
	public static void addKill(Player p) throws IOException {
			File kills = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(kills);
			int killanz = stats.getInt(p.getName()+ ".Kills");
			stats.set(p.getName()+ ".Kills", killanz+1);
			stats.save(kills);
	}
	public static void addDeath(Player p) throws IOException {
			File deaths = new File("plugins/BuildcraftPrivat/Scoreboard", "ScoreboardStats.yml");
			FileConfiguration stats = YamlConfiguration.loadConfiguration(deaths);
			int deathanz = stats.getInt(p.getName()+ ".Deaths");
			stats.set(p.getName()+ ".Deaths", deathanz+1);
			stats.save(deaths);
	}

}
