package LinkSQL;

//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LPlayerSQL extends JavaPlugin implements Listener
{
	public static Plugin plugin;

	@Override
	public void onEnable()
	{
		plugin = this;
		saveDefaultConfig();
		if (getConfig().getBoolean("use")) {
			if (LSQL.openConnect()) {
				getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SQLConnect"));
				if (LSQL.createTables()) {
					getServer().getPluginManager().registerEvents(this, this);
					getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SQLConnect2"));
					//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.k);
					//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.l);
				}
				else {
					getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SQLConnectError"));
					setEnabled(false);
				}
				if (!LPlayer.lockAllPlayer()) {
					getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.LockOnlinePlayerError"));
				}
				if (getConfig().getBoolean("daily.use")) {
					LThread dailySaveThread = new LThread();
					dailySaveThread.start();
				}
			}
			else {
				getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SQLConnectError2"));
				setEnabled(false);
			}
		}
		else {
			getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.NoSetConfig"));
			setEnabled(false);
		}
	}

	@Override
	public void onDisable()
	{
		if (!getConfig().getBoolean("use")) {
			return;
		}
		if (LSQL.openConnect()) {
			if (LPlayer.saveAllPlayer() && LPlayer.unlockAllPlayer()) {
				getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SaveOnlinePlayerComplate"));
			}
			if (LSQL.closeConnect()) {
				getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.DatabaseClose"));
			}
			else {
				getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.DatabaseCloseError"));
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		/*if (command.getName().equalsIgnoreCase("lp reload") && sender.hasPermission("command.ex")) {
			reloadConfig();
		}else{
			sender.sendMessage(LPlayerSQL.plugin.getConfig().getString("Messages.NoPermission"));
		}*/
		return true;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		PlayerQuitThread playerQuitThread = new PlayerQuitThread(event);
		playerQuitThread.start();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		PlayerJoinThread playerJoinThread = new PlayerJoinThread(event);
		playerJoinThread.start();
	}
}

class PlayerQuitThread extends Thread
{
	private Player player;

	public PlayerQuitThread(PlayerQuitEvent event)
	{
		player = event.getPlayer();
	}

	@Override
	public void run()
	{
		Plugin plugin = LPlayerSQL.plugin;
		if (LPlayer.savePlayer(player)) {
			plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SavePlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Ok")); //+ player.getName() +
			if (!LPlayer.unlockPlayer(player)) {
				plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.UnlockPlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Error"));
			}
		}
		else {
			plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.SavePlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Error"));
		}
	}
}

class PlayerJoinThread extends Thread
{
	private Player player;

	public PlayerJoinThread(PlayerJoinEvent event)
	{
		player = event.getPlayer();
	}

	@Override
	public void run()
	{
		try {
			Thread.sleep(LPlayerSQL.plugin.getConfig().getLong("delay") * 50);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (LPlayer.loadPlayer(player)) {
			LPlayerSQL.plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.LoadPlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Ok"));
			if (!LPlayer.lockPlayer(player)) {
				LPlayerSQL.plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.LockPlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Error"));
			}
		}
		else {
			player.sendMessage(LPlayerSQL.plugin.getConfig().getString("Messages.AutoSavePlayerError"));
			//player.sendMessage("請聯繫管理員");
			LPlayerSQL.plugin.getLogger().info(LPlayerSQL.plugin.getConfig().getString("Messages.LoadPlayer") + " " + player.getName() + " " + LPlayerSQL.plugin.getConfig().getString("Messages.Error"));
		}
	}

}
