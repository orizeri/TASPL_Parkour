/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.oriserver.taspl_parkour;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author orizeri
 */
public class Com_Pk implements CommandExecutor {
/////////////////////////////////////////////////
    private final Main main;

    public Com_Pk(Main main_) {
        main = main_;
    }
/////////////////////////////////////////////////

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefix = ChatColor.GREEN + "[Parkour]" + ChatColor.AQUA + "たる鯖" + ">> " + ChatColor.RESET;
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("pk")) {
            String cmnd;
            String txt;
            Player target;
            if (args.length == 0) {
                sender.sendMessage(prefix + ChatColor.RED + "引数が足りません");
                sender.sendMessage(prefix + ChatColor.RED + "/pk add <ParkourName>" + " : " + "Parkourを登録します");
                sender.sendMessage(prefix + ChatColor.RED + "/pk remove <ParkourName>" + " : " + "登録されているParkourを削除します");
                sender.sendMessage(prefix + ChatColor.RED + "/pk list" + " : " + "登録されているParkour一覧を表示します");
                return true;
            }
            if (args.length == 1) {
                cmnd = args[0].toString();
                if (cmnd.equals("add") || cmnd.equals("remove")) {//[/pk add] 又 [/pk remove] を入力したとき
                    player.sendMessage(prefix + ChatColor.RED + "引数が足りません" + "/pk [add,remove] <ParkourName>");
                } else {//それ以外
                    sender.sendMessage(prefix + ChatColor.RED + "引数が違います");
                }
                return true;
            }
            if (args.length == 2) {
                txt = args[1].toString();
                cmnd = args[0].toString();
                if (cmnd.equals("add")) {
                    if(main.data.getConfig().get(txt) == txt){
                        player.sendMessage(prefix + ChatColor.RED + "その名前は存在してます、別の名前をご利用ください");
                        return true;
                    }
                    int x = player.getLocation().getBlockX();
                    int y = player.getLocation().getBlockY();
                    int z = player.getLocation().getBlockZ();
                    String world = player.getWorld().getName();
                    Location lc = new Location(player.getWorld(), x + 0.5, y + 0.3, z + 0.5);
                    ArmorStand as = player.getWorld().spawn(lc, ArmorStand.class);
                    as.setCustomName(ChatColor.GREEN + "Parkour" + ChatColor.AQUA + "[" + txt + "]");
                    as.setVisible(false);
                    as.setCustomNameVisible(true);
                    as.setGravity(false);
                    Location l = new Location(player.getWorld(), x, y, z);
                    Block b = l.getBlock();
                    b.setType(Material.GOLD_PLATE);
                    main.data.getConfig().set(world + "." + txt + ".start.x", x);
                    main.data.getConfig().set(world + "." + txt + ".start.y", y);
                    main.data.getConfig().set(world + "." + txt + ".start.z", z);
                    main.data.getConfig().set(world + "." + txt + ".start.world", world);
                    main.config.getConfig().set("Parkour." + world + "." + x + y + z + ".name", txt);
                    main.data.saveConfig();
                    main.config.saveConfig();
                } else if (cmnd.equals("remove")) {
                    //消すパルクールの座標データーの取得
                    String world = player.getWorld().getName();
                    int x = main.data.getConfig().getInt(world + "." + txt + ".start.x");
                    int y = main.data.getConfig().getInt(world + "." + txt + ".start.y");
                    int z = main.data.getConfig().getInt(world + "." + txt + ".start.z");
                    //アーマースタンドの削除
                    for (Entity entity : player.getWorld().getEntities()) {
                        if (entity.getCustomName() != null) {
                            if (entity.getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Parkour" + ChatColor.AQUA + "[" + txt + "]")) {
                                entity.remove();
                            }
                        }
                    }
                    //金の感圧板の削除
                    Location l = new Location(player.getWorld(), x, y, z);
                    Block b = l.getBlock();
                    b.setType(Material.AIR);
                    //configから削除
                    /*
                    String x1 = main.data.getConfig().getString(world + "." + txt + ".start.x");
                    String y1 = main.data.getConfig().getString(world + "." + txt + ".start.y");
                    String z1 = main.data.getConfig().getString(world + "." + txt + ".start.z");
                    main.config.getConfig().getString("Parkour." + world).replace(x1 + y1 + z1, "");
                    */
                    main.config.getConfig().set("Parkour." + world + "." + x + y + z , null);
                    main.config.saveConfig();
                    main.data.getConfig().set(world + "." +txt, null);
                    main.data.saveConfig();
                    sender.sendMessage(prefix + txt + "を削除しました");
                } else if (cmnd.equals("list")) {
                    sender.sendMessage("未実装です|:3ミ");
                }
            } else {
                player.sendMessage(prefix + ChatColor.RED + ": " + "引数が違います");
            }
            return true;
        }
        return false;
    }
}
