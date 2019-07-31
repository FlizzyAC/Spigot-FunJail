package stu.wulinworks;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class fuckidiot extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.toLowerCase().equals("wl")) return false;
        if (!sender.hasPermission("minecraft.op")) {
            sender.sendMessage(ChatColor.RED.toString() + "No permissions to do.");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "jail":
                if (args[1] != "") {
                    Player p = Bukkit.getServer().getPlayer(args[1]);
                    if (p == null || !isOnline(args[1])) {
                        sender.sendMessage(args[1] + " is not online!");
                        return true;
                    }
                    World w = p.getWorld();

                    w.getBlockAt(p.getLocation().add(0, 10, 0).add(0, -1, 0)).setType(Material.BARRIER);
                    for (int i = 0; i < 5; i++) {
                        w.getBlockAt(p.getLocation().add(0, 10, 0).add(1, i, 0)).setType(Material.BARRIER);
                        w.getBlockAt(p.getLocation().add(0, 10, 0).add(-1, i, 0)).setType(Material.BARRIER);
                        w.getBlockAt(p.getLocation().add(0, 10, 0).add(0, i, 1)).setType(Material.BARRIER);
                        w.getBlockAt(p.getLocation().add(0, 10, 0).add(0, i, -1)).setType(Material.BARRIER);
                    }

                    p.teleport(p.getLocation().add(0, 10, 0));
                    if (sender instanceof Player) sender.sendMessage("Jailed " + p.getName());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            w.getBlockAt(p.getLocation().add(0, -1, 0)).setType(Material.AIR);
                            for (int i = 0; i < 5; i++) {
                                w.getBlockAt(p.getLocation().add(1, i, 0)).setType(Material.AIR);
                                w.getBlockAt(p.getLocation().add(-1, i, 0)).setType(Material.AIR);
                                w.getBlockAt(p.getLocation().add(0, i, 1)).setType(Material.AIR);
                                w.getBlockAt(p.getLocation().add(0, i, -1)).setType(Material.AIR);
                            }
                        }
                    }, Long.valueOf(args[2]) );

                    return true;
                }
            default: return false;
        }
    }

    public boolean isOnline(String name){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
