package LinkSQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class LCommand
{

	public boolean onPlayer(CommandSender sender, String[] args)
	{
		if (!sender.hasPermission("linksql.admin")) {
			sender.sendMessage(LTranslat.h);
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage("/lp saveall");
			return true;
		}
		if (args.length < 2) {
			if (args[0].equals("saveall")) {
				OnCommandThread thread = new OnCommandThread(sender);
				thread.start();
				return true;
			}
			else {
				sender.sendMessage("/lp saveall");
				return true;
			}
		}
		else {
			sender.sendMessage("/lp saveall");
			return true;
		}
	}

	class OnCommandThread extends Thread
	{
		private CommandSender sender;

		public OnCommandThread(CommandSender commandSender)
		{
			sender = commandSender;
		}

		@Override
		public void run()
		{
			if (LPlayer.saveAllPlayer()) {
				sender.sendMessage(ChatColor.GREEN + LTranslat.a);
			}
		}
	}

}
