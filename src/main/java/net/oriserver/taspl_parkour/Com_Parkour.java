/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.oriserver.taspl_parkour;

import java.util.HashMap;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author orizeri
 */
public class Com_Parkour implements CommandExecutor {

    private final Main main;

    public Com_Parkour(Main main_) {
        main = main_;
    }

    //private HashMap<String, Long> startTime;
    //private HashMap<String, String> ParkourName;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefix = ChatColor.GREEN + "[Parkour]" + ChatColor.AQUA + "たる鯖" + ">> " + ChatColor.RESET;
        Player player = (Player) sender;
        // parkour コマンドの処理
        if (cmd.getName().equalsIgnoreCase("parkour")) {
            if (args.length == 0) {
                sender.sendMessage(prefix + "/parkour reset");
                sender.sendMessage(prefix + "/parkour checkpoint");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reset")) {
                    ActionBar.sendActionBar(player, ChatColor.RED + "ParkourReSet");
                    main.startTime.remove(player.getName());
                    main.ParkourName.remove(player.getName());
                    
                    return true;
                }
                if (args[0].equalsIgnoreCase("checkpoint")) {
                    sender.sendMessage(prefix + "未実装です");
                    return true;
                }
            }
        }
        // 該当するコマンド無し
        return false;
    }
}
