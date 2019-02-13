/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.oriserver.taspl_parkour;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author orizeri
 */
public class Main extends JavaPlugin implements Listener {

    BukkitScheduler scheduler;
    HashMap<String, Long> startTime;
    HashMap<String, String> ParkourName;

    BukkitTask t;
    int taskID;

    CustomConfig config;
    CustomConfig data;

    @Override
    public void onEnable() {
        //getLogger().info("プラグインを有効にしました");
        getLogger().info(
                "\n" + "ababababababababababababababababababababa"
                + "\n" + "abab////////ababa///bababa//////babababab"
                + "\n" + "abababa//bababab//a//baba//bababababababa"
                + "\n" + "abababa//bababa///////abab/////ababababab"
                + "\n" + "abababa//babab//ababa//babababa//babababa"
                + "\n" + "abababa//baba//bababab//ab//////ababababa"
                + "\n" + "ababababababababababababababababababababa");
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Tabms(), this);
        getServer().getPluginManager().registerEvents(new ComLog(), this);
        getServer().getPluginManager().registerEvents(new Wg(), this);
        getCommand("parkour").setExecutor(new Com_Parkour(this));
        getCommand("pk").setExecutor(new Com_Pk(this));

        startTime = new HashMap<String, Long>();
        ParkourName = new HashMap<String, String>();

        config = new CustomConfig(this);
        config.getConfig();
        config.saveConfig();

        data = new CustomConfig(this, "data.yml");
        data.getConfig();
        data.saveConfig();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("プラグインを無効にしました");
    }

    /**
     * パルクールスタートストップ 金の感圧板で計測開始 鉄の感圧板で計測終了
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        String prefix = ChatColor.GREEN + "[Parkour]" + ChatColor.YELLOW + "たる鯖" + ">> " + ChatColor.RESET;
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (event.getAction() == Action.PHYSICAL) {
            //パルクール開始の処理
            if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {//金の感圧板を踏んだ時の処理
                //踏んだ感圧板の座標を取得
                int x = block.getLocation().getBlockX();
                int y = block.getLocation().getBlockY();
                int z = block.getLocation().getBlockZ();
                String world = player.getWorld().getName();
                //踏んだ座標のパルクールの名前を取得
                Object pn = config.getConfig().get("Parkour." + world + "." + x + y + z + ".name");
                //パルクールが動いてる場合はリセット
                startTime.remove(event.getPlayer().getName());
                ParkourName.remove(event.getPlayer().getName());
                //パルクールの発動するかどうかの確認
                if (pn != null) {//configに登録される時
                    //パルクールの座標を取得
                    Player pa = event.getPlayer();
                    String name = config.getConfig().getString("Parkour." + world + "." + x + y + z + ".name");//パルクールの名前をconfigから取得
                    long start = System.currentTimeMillis();
                    startTime.put(event.getPlayer().getName(), start);
                    ParkourName.put(event.getPlayer().getName(), name);//プレイヤーがやっているパルクールの名前を保存
                    //long time = System.currentTimeMillis() - startTime.get(event.getPlayer().getName());
                    //double dtime = (double) time;
                    //ループ
                    scheduler = Bukkit.getServer().getScheduler();
                    taskID = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                        @Override
                        public void run() {
                            if (!startTime.containsKey(event.getPlayer().getName())) {//パルクールが開始してない場合は除外
                                return;
                            }
                            if (startTime.containsKey(pa.getName())) {
                                long time = System.currentTimeMillis() - startTime.get(pa.getName());
                                double dtime = (double) time;
                                long hour = (time / (1000 * 60 * 60)) % 24;
                                long minute = (time / (1000 * 60)) % 60;
                                long second = (time / 1000) % 60;
                                long millisSec = time % 1000;
                                String stime = String.format("%02d:%02d:%02d:%03d", hour, minute, second, millisSec);
                                ActionBar.sendActionBar(pa, ChatColor.GREEN + "ParkourName" + ChatColor.RESET + ":" + ChatColor.AQUA + name + ChatColor.YELLOW + "||" + ChatColor.RESET + stime);
                            }
                        }
                    }, 0L, 1L);
                }
                //パルクール終了の処理
            } else if (event.getClickedBlock().getType() == Material.IRON_PLATE) {//鉄の感圧板を踏んだ時の処理
                if (!startTime.containsKey(event.getPlayer().getName())) {//パルクールが開始してない場合は除外
                    return;
                }
                //タイマーの時間の計算(現在の時間と開始時の時間を計算)
                String name = ParkourName.get(event.getPlayer().getName());
                long time = System.currentTimeMillis() - startTime.get(event.getPlayer().getName());
                double dtime = (double) time;
                long hour = (time / (1000 * 60 * 60)) % 24;
                long minute = (time / (1000 * 60)) % 60;
                long second = (time / 1000) % 60;
                long millisSec = time % 1000;
                String stime = String.format("%02d:%02d:%02d:%03d", hour, minute, second, millisSec);
                ActionBar.sendActionBar(player, ChatColor.GREEN + "ParkourName" + ChatColor.RESET + ":" + ChatColor.AQUA + name + ChatColor.GOLD + "Clear!!" + ChatColor.YELLOW + "||" + ChatColor.RESET + stime);
                Bukkit.broadcastMessage(prefix + player.getName() + "さんが、" + ChatColor.GREEN + "Parkour" + ChatColor.RESET + ":" + name + ChatColor.RESET + "を" + stime + "でクリアしました");
                startTime.remove(event.getPlayer().getName());
                ParkourName.remove(event.getPlayer().getName());
            } else if (event.getClickedBlock().getType() == Material.JUKEBOX) {
                Player pa = event.getPlayer();
                scheduler = Bukkit.getServer().getScheduler();
                taskID = scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        if (!startTime.containsKey(event.getPlayer().getName())) {//パルクールが開始してない場合は除外
                            return;
                        }
                        if (startTime.containsKey(pa.getName())) {
                            long time = System.currentTimeMillis() - startTime.get(pa.getName());
                            double dtime = (double) time;
                            long hour = (time / (1000 * 60 * 60)) % 24;
                            long minute = (time / (1000 * 60)) % 60;
                            long second = (time / 1000) % 60;
                            long millisSec = time % 1000;
                            String stime = String.format("%02d:%02d:%02d:%03d", hour, minute, second, millisSec);
                            ActionBar.sendActionBar(pa, ChatColor.GREEN + "Parkour" + ChatColor.YELLOW + "||" + ChatColor.RESET + stime);
                        }
                    }
                }, 0L, 1L);
            } else if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                if (!startTime.containsKey(event.getPlayer().getName())) {//パルクールが開始してない場合は除外
                    return;
                }
                //タイマーの時間の計算(現在の時間と開始時の時間を計算)
                String name = ParkourName.get(event.getPlayer().getName());
                long time = System.currentTimeMillis() - startTime.get(event.getPlayer().getName());
                double dtime = (double) time;
                long hour = (time / (1000 * 60 * 60)) % 24;
                long minute = (time / (1000 * 60)) % 60;
                long second = (time / 1000) % 60;
                long millisSec = time % 1000;
                String stime = String.format("%02d:%02d:%02d:%03d", hour, minute, second, millisSec);
                ActionBar.sendActionBar(player, ChatColor.GREEN + "ParkourName" + ChatColor.RESET + ":" + ChatColor.AQUA + name + ChatColor.GOLD + "Clear!!" + ChatColor.YELLOW + "||" + ChatColor.RESET + stime);
                //Bukkit.broadcastMessage(prefix + player.getName() + "さんが、" + ChatColor.GREEN + "Parkour" + ChatColor.RESET + ":" + name + ChatColor.RESET + "を" + stime + "でクリアしました");
                startTime.remove(event.getPlayer().getName());
                ParkourName.remove(event.getPlayer().getName());
            }
        }
    }
}
