import org.bukkit.plugin.java.JavaPlugin;

public class CustomCommands extends JavaPlugin {
    CustomCommandsConfigManager Commands;

    @Override
    public void onEnable(){
        this.Commands = new CustomCommandsConfigManager(this);
        this.Commands.getConfig().options().copyDefaults(true);
        this.Commands.saveDefaultConfig();

        Commands = new CustomCommandsConfigManager(this);

        this.getCommand("CustomCommands").setExecutor(new RunHelpCmds(this));
        this.getServer().getPluginManager().registerEvents(new RunCommands(this), this);

    }

    public CustomCommandsConfigManager getCommandConfig(){
        return Commands;
    }
}
