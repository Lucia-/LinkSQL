package LinkSQL;

//import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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
			sender.sendMessage("12");
		}
	}
}