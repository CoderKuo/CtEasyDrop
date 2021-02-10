package cn.ctcraft.cteasydrop.inventory;


import cn.ctcraft.cteasydrop.service.DropDataService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropDataInventoryMonitor implements Listener {
    private DropDataService dropDataService = DropDataService.getInstance();

    public DropDataInventoryMonitor() {
    }

    @EventHandler
    public void Monitor(InventoryClickEvent e) {
        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory != null) {
            InventoryHolder holder = clickedInventory.getHolder();
            if (holder instanceof DropDataInventoryHolder) {
                int rawSlot = e.getRawSlot();
                if (rawSlot <= 44 && rawSlot >= 36) {
                    e.setCancelled(true);
                }

                if (rawSlot == 40) {
                    List<ItemStack> itemStacks = new ArrayList<>();

                    for (int i = 0; i < 36; ++i) {
                        itemStacks.add(clickedInventory.getItem(i));
                    }

                    String dropDataName = ((DropDataInventoryHolder) holder).getDropDataName();
                    boolean b = this.dropDataService.saveDate(itemStacks, dropDataName);
                    if (b) {
                        e.getWhoClicked().sendMessage("§a§l● " + dropDataName + "掉落数据保存成功!");
                    }

                    e.getWhoClicked().closeInventory();
                }

            }
        }
    }
}