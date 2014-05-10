package LinkSQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LThread extends Thread
{

	@Override
	public void run()
	{
		final boolean show = LPlayerSQL.plugin.getConfig().getBoolean("daily.show");
		final int delay = LPlayerSQL.plugin.getConfig().getInt("daily.delay");
		final int min = LPlayerSQL.plugin.getConfig().getInt("daily.min");
		Player[] players;

		while (LPlayerSQL.plugin.isEnabled()) {
			try {
				sleep(delay * 250);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!LPlayerSQL.plugin.isEnabled()) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + LTranslat.a);
				return;
			}
			players = LPlayerSQL.plugin.getServer().getOnlinePlayers();
			if (players.length > min) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.b + players.length + LTranslat.c);
				for (int i = 0; i < players.length; i++) {
					if (!players[i].isOnline()) {
						continue;
					}
					if (LPlayer.savePlayer(players[i]) && show) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.d + players[i].getName() + LTranslat.f);
						Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + LTranslat.o + (i + 1) + " / " + players.length);
					}
					try {
						sleep(delay * 50);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!LPlayerSQL.plugin.isEnabled()) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + LTranslat.a);
						return;
					}
				}
			}
		}
	}

}
