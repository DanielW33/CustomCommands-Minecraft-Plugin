import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class RunCommands implements Listener {
    CustomCommandsConfigManager Commands;
    CustomCommands plugin;
    String Message;

    public RunCommands(CustomCommands Plugin){
        plugin = Plugin;
        Commands = Plugin.getCommandConfig();
    }

    @EventHandler
    public void CustomCommands(AsyncPlayerChatEvent Event) {

        this.Commands.getConfig().getConfigurationSection("Commands").getKeys(false).forEach(Cmds -> {
            String FileCommand = this.Commands.getConfig().getString("Commands." + Cmds + ".Command");
            String CommandSent = Event.getMessage();
            if (CommandSent.equalsIgnoreCase(FileCommand)) {
                isCanceled(Event, Cmds);
                Player player = Event.getPlayer();
                String Mode = this.Commands.getConfig().getString("Commands." + Cmds + ".Mode");

                if (Mode.equalsIgnoreCase("Text")) {
                
                    List toChat = this.Commands.getConfig().getList("Commands." + Cmds + ".SendToChat");
                    ArrayList<String> Messages = new ArrayList<>();
                    Messages.addAll(toChat);

                    for (int i = 0; i < Messages.size(); i++) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.get(i)));
                    }
                } else if (Mode.equalsIgnoreCase("Command")) {
                
                    List toChat = this.Commands.getConfig().getList("Commands." + Cmds + ".SendToChat");
                    ArrayList<String> Messages = new ArrayList<>();
                    Messages.addAll(toChat);
                    
                    for (int i = 0; i < Messages.size(); i++) {
                        Message = Messages.get(i);
                        Message = Message.replace("%player%", player.getName());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), Message);
                                cancel();
                            }
                        }.runTaskTimer(plugin, 1, 1);
                    }
                }
            }
        });
    }

    public void isCanceled(AsyncPlayerChatEvent Event,String Cmds){
        boolean Canceled = this.Commands.getConfig().getBoolean("Commands." + Cmds + ".SetCanceled");
        Event.setCancelled(Canceled);
    }
}
