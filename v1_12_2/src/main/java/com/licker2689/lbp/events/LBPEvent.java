package com.licker2689.lbp.events;

import com.licker2689.lbp.BackPack;
import com.licker2689.lbp.functions.LBPFunction;
import com.licker2689.lpc.utils.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@SuppressWarnings("all")
public class LBPEvent implements Listener {
    private final BackPack plugin = BackPack.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(plugin.currentBackPack.contains(p.getUniqueId())) {
            if(e.getClick() == ClickType.NUMBER_KEY) {
                e.setCancelled(true);
                return;
            }
        }
        if(e.getCurrentItem() != null) {
            if(NBT.hasTagKey(e.getCurrentItem(), "lbp_size")) {
                if(plugin.currentBackPack.contains(p.getUniqueId())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if(e.getView().getTitle().contains("백팩 쿠폰 설정")) {
            if(e.getSlot() != 13 && e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(plugin.currentBackPack.contains(e.getPlayer().getUniqueId())) {
            LBPFunction.saveBackPack((Player) e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand(), e.getInventory());
            plugin.currentBackPack.remove(e.getPlayer().getUniqueId());
            return;
        }
        if(e.getView().getTitle().contains("백팩 쿠폰 설정")) {
            LBPFunction.saveCoupon((Player) e.getPlayer(), e.getInventory());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getHand() == EquipmentSlot.OFF_HAND) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getItem() != null) {
                if(NBT.hasTagKey(e.getItem(), "lbp_size")) {
                    LBPFunction.openBackPack(e.getPlayer(), e.getItem());
                    plugin.currentBackPack.add(e.getPlayer().getUniqueId());
                    e.setCancelled(true);
                }
            }
        }
    }
}
