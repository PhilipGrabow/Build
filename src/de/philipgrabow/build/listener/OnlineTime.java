package de.philipgrabow.build.listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import de.philipgrabow.build.Main;

public class OnlineTime implements Listener {
	
	public static HashMap<String, Integer> sekunden = new HashMap<String, Integer>();
	
	private Main plugin;
	public OnlineTime(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onJoin(final PlayerJoinEvent e) {
		final File playerfile = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "OnlineTimes.yml");
		final FileConfiguration cfg = YamlConfiguration.loadConfiguration(playerfile);
		File config = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "Config.yml");
		FileConfiguration configcfg = YamlConfiguration.loadConfiguration(config);
		//Aktualsierungsrate f�r die OnlineZeit
		//Von 1-90........Minuten!!!
		final int akturate = configcfg.getInt("OnlineTime.Aktualisierungsrate");
		if(cfg.getInt("OnlineTime." + e.getPlayer().getName()) >=60) {
			e.getPlayer().sendMessage("�bDeine OnlineZeit betr�gt zurzeit : " + cfg.getDouble("OnlineTime." + e.getPlayer().getName())/60 + " Stunde/n !");
		} else {
			e.getPlayer().sendMessage("�bDeine OnlineZeit betr�gt zurzeit : " + cfg.getInt("OnlineTime." + e.getPlayer().getName()) + " Minute/n !");
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (e.getPlayer().isOnline()){
				int onlinetime = cfg.getInt("OnlineTime." + e.getPlayer().getName());
				cfg.set("OnlineTime." + e.getPlayer().getName(), onlinetime + akturate);
				try {
					cfg.save(playerfile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				} else {
					return;
				}
				final File playerfile = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "OnlineTimes.yml");
				final FileConfiguration cfg = YamlConfiguration.loadConfiguration(playerfile);
				final File configfile = new File("plugins/BuildcraftPrivat/PlayerOnlineTime", "Config.yml");
				final FileConfiguration configcfg = YamlConfiguration.loadConfiguration(configfile);
				//10Minuten OnlineZeit Belohnung!!!
				//
				//
				int belohnung1time = configcfg.getInt("OnlineTime.Belohnung_1.Minuten");
				int belohnung1itemid = configcfg.getInt("OnlineTime.Belohnung_1.Item_ID");
				int belohnung1itemanzahl = configcfg.getInt("OnlineTime.Belohnung_1.Item_Anzahl");
				if(cfg.getInt("OnlineTime." + e.getPlayer().getName()) == belohnung1time) {
					e.getPlayer().sendMessage("SUPER! Du hast es auf "+belohnung1time+" Minuten gebracht!");
					Material Item1 = e.getPlayer().getInventory().getItem(belohnung1itemid).getType();
					e.getPlayer().getInventory().addItem(new ItemStack(Item1, belohnung1itemanzahl));
					e.getPlayer().sendMessage("Spiele weiterhin so treu auf unserem Server!");
					int belohnung2time = configcfg.getInt("OnlineTime.Belohnung_2.Minuten");
					e.getPlayer().sendMessage("N�chste Belohnung bei " + belohnung2time + " Minuten !");
//					ru.tehkode.permissions.bukkit.PermissionsEx.getUser(e.getPlayer().getName()).setGroups(ru.tehkode.permissions.bukkit.PermissionsEx.getPermissionManager().getGroup("Mitarbeiter").getUsers().contains(e.getPlayer().getName()));
				}
				//60Minuten OnlineZeit Belohnung!!!
				//
				//
				int belohnung2time = configcfg.getInt("OnlineTime.Belohnung_2.Minuten");
				int belohnung2itemid = configcfg.getInt("OnlineTime.Belohnung_2.Item_ID_1");
				int belohnung2itemanzahl = configcfg.getInt("OnlineTime.Belohnung_2.Item_Anzahl_1");
				int belohnung2itemid2 = configcfg.getInt("OnlineTime.Belohnung_2.Item_ID_2");
				int belohnung2itemanzahl2 = configcfg.getInt("OnlineTime.Belohnung_2.Item_Anzahl_2");
				if(cfg.getInt("OnlineTime." + e.getPlayer().getName()) == 60) {
					e.getPlayer().sendMessage("SUPER! Du hast es auf "+belohnung2time+" Minuten gebracht!");
					Material Item1 = e.getPlayer().getInventory().getItem(belohnung2itemid).getType();
					Material Item2 = e.getPlayer().getInventory().getItem(belohnung2itemid2).getType();
					e.getPlayer().getInventory().addItem(new ItemStack(Item1, belohnung2itemanzahl));
					e.getPlayer().getInventory().addItem(new ItemStack(Item2, belohnung2itemanzahl2));
					//Bsp der erneuerung nach dem ItemStack(ID) weg ist!!!
					//
					//e.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
					//ODER
					//Material Item1 = e.getPlayer().getInventory().getItem(belohnung2itemid).getType();
					//e.getPlayer().getInventory().addItem(new ItemStack(Item1, belohnung2itemanzahl));
					e.getPlayer().sendMessage("Spiele weiterhin so treu auf unserem Server!");
				}
			}
			//1200*5
		}, akturate*1200, akturate*1200);
	}
}
