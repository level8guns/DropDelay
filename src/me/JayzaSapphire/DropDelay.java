package me.JayzaSapphire;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DropDelay extends JavaPlugin implements Listener {

	//The timer.
	private static int timer = 1;
	
	//Define PluginManager and use it to register events.
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	//Schedule a task to drop the item and then remove it from the person inventory.
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		//Check if the event is cancelled if it is, quit listening to the event.
		//Do the same if the person has permission.
		if (event.isCancelled() || event.getPlayer().hasPermission("dropdelay.bypass")) return;
		
		//Cancel the event ourself.
		event.setCancelled(true);
		
		//Get the player, world, item, location.
		final Player player = event.getPlayer();
		final World world = player.getWorld();
		final Item i = event.getItemDrop();
		final Location loc = i.getLocation();
		final ItemStack stack = i.getItemStack();
		
		getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
			public void run() {
				world.dropItemNaturally(loc, stack);
				player.getInventory().removeItem(stack);
			}
		}, timer * 20); //Timer of 1 second, Multiplied by 20.
	}
	
	public void onDisable() {
		
	}
}
