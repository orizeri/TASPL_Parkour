/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.oriserver.taspl_parkour;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 *
 * @author orizeri
 */
public class Wg implements Listener {

    //ブロックの破壊をパーミッションの未取得者はキャンセル
    @EventHandler
    public void BlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("tas.admin")) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }
    //ブロックの設置をパーミッションの未取得者はキャンセル

    @EventHandler
    public void BlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("tas.admin")) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }
    /*
    @EventHandler
    public void paintbevent(PaintingBreakEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }
    //額縁の破壊をパーミッションの未取得者はキャンセル
    */

    @EventHandler
    public void HangingBreak(HangingBreakEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }
    //額縁の設置をパーミッションの未取得者はキャンセル

    @EventHandler
    public void HangingPlace(HangingPlaceEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerBucketFill(PlayerBucketFillEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.ArmorStand) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("tas.admin")) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        } else if (event.getEntity() instanceof org.bukkit.entity.Painting) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("tas.admin")) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        } else if (event.getEntity() instanceof org.bukkit.entity.ItemFrame) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("tas.admin")) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("tas.admin")) {
            e.setCancelled(false);
        } else if (e.getRightClicked() instanceof ArmorStand && player.getItemInHand() != null) {
            e.setCancelled(true);
        } else if (e.getRightClicked() instanceof Painting && player.getItemInHand() != null) {
            e.setCancelled(true);
        } else if (e.getRightClicked() instanceof ItemFrame && player.getItemInHand() != null) {
            e.setCancelled(true);
        }
    }
    //爆発でブロックが破壊されるのをキャンセル

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }
    //ブロックが解けるのをキャンセル

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        event.setCancelled(true);
    }
    //爆発をなかったことにする

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        event.setCancelled(true);
    }
    /**
     * //ブロックが燃えるのをキャンセル
     *
     * @EventHandler public void onBlockIgnite(BlockIgniteEvent event){
     * event.setCancelled(true); }
	*
     */

}
