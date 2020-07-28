import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunHelpCmds implements CommandExecutor {
    CustomCommandsConfigManager Commands;
    CustomCommands plugin;

    public RunHelpCmds(CustomCommands Plugin){
        plugin = Plugin;
        Commands = Plugin.getCommandConfig();
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String Label, String[] args){
        if(Label.equalsIgnoreCase("CustomCommands")){
            if(args.length < 1){
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7Type &b/CustomCommands reload &7to reload the commands config."));
                return true;
            }
            else if(args[0].equalsIgnoreCase("reload")){
                Commands.reloadConfig();
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cReloading Custom Commands Config!"));
                return true;
            }
            else{
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cInvalid: type &b/CustomCommands &7to see a list of commands."));
                return true;
            }
        }
        return false;
    }
}
