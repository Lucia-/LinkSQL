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
	private LCommand doCommand = new LCommand();

	@Override
	public void onEnable()
	{
		plugin = this;
		saveDefaultConfig();
		LTranslat.translat();
		if (getConfig().getBoolean("use")) {
			if (LSQL.openConnect()) {
				getLogger().info(LTranslat.i);
				if (LSQL.createTables()) {
					getServer().getPluginManager().registerEvents(this, this);
					getLogger().info(LTranslat.j);
					//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.k);
					//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.l);
				}
				else {
					getLogger().info("數據表驗證失敗");
					setEnabled(false);
				}
				if (!LPlayer.lockAllPlayer()) {
					getLogger().info("鎖定在線玩家數據失敗");
				}
				if (getConfig().getBoolean("daily.use")) {
					LThread dailySaveThread = new LThread();
					dailySaveThread.start();
				}
			}
			else {
				getLogger().info(LTranslat.m);
				setEnabled(false);
			}
		}
		else {
			getLogger().info(LTranslat.n);
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
				getLogger().info(LTranslat.a);
			}
			if (LSQL.closeConnect()) {
				getLogger().info("關閉數據庫連接成功");
			}
			else {
				getLogger().info("關閉數據庫連接失敗");
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("player")) {
			return doCommand.onPlayer(sender, args);
		}
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
			plugin.getLogger().info(LTranslat.d + player.getName() + LTranslat.f);
			if (!LPlayer.unlockPlayer(player)) {
				plugin.getLogger().info("解鎖上線狀態 玩家 " + player.getName() + LTranslat.g);
			}
		}
		else {
			plugin.getLogger().info(LTranslat.d + player.getName() + LTranslat.g);
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
			LPlayerSQL.plugin.getLogger().info(LTranslat.e + player.getName() + LTranslat.f);
			if (!LPlayer.lockPlayer(player)) {
				LPlayerSQL.plugin.getLogger().info("鎖定玩家 " + player.getName() + LTranslat.g);
			}
		}
		else {
			player.sendMessage("自動載入資料失敗");
			player.sendMessage("請聯繫管理員");
			LPlayerSQL.plugin.getLogger().info(LTranslat.e + player.getName() + LTranslat.g);
		}
	}

}
