package cn.ctcraft.cteasydrop.inventory;

import cn.ctcraft.cteasydrop.CtEasyDrop;
import cn.ctcraft.cteasydrop.service.DropDataService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class DropDataInventory {

    private CtEasyDrop ctEasyDrop = CtEasyDrop.getPlugin(CtEasyDrop.class);
    private DropDataService dropDataService = DropDataService.getInstance();

    public DropDataInventory() {
    }

    public void OpenInventory(Player player, String dropDataName) {
        DropDataInventoryHolder dropDataInventoryHolder = new DropDataInventoryHolder();
        dropDataInventoryHolder.setDropDataName(dropDataName);
        Inventory inventory = Bukkit.createInventory(dropDataInventoryHolder, 45, "§7§l掉落物设置[" + dropDataName + "]");
        Map<Integer, ItemStack> frameItemStackMap = this.getFrameItemStackMap();
        Set<Integer> integers = frameItemStackMap.keySet();
        for (Integer integer : integers) {
            inventory.setItem(integer,frameItemStackMap.get(integer));
        }

        File file = new File(this.ctEasyDrop.getDataFolder() + "/dropData/" + dropDataName);
        if (file.exists()) {
            List<ItemStack> itemStackFromFile = this.dropDataService.getItemStackFromFile(file);
            if (itemStackFromFile != null) {
                for (ItemStack itemStack : itemStackFromFile) {
                    inventory.setItem(inventory.firstEmpty(), itemStack);
                }
            }
        }

        player.openInventory(inventory);
    }

    private Map<Integer, ItemStack> getFrameItemStackMap() {
        Map<Integer, ItemStack> map = new HashMap<>();
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        itemMeta.setDisplayName("-");
        itemStack.setItemMeta(itemMeta);

        for (int i = 36; i < 45; ++i) {
            if (i != 40) {
                map.put(i, itemStack);
            }
        }

        ItemStack saveItem = new ItemStack(Material.REDSTONE);
        ItemMeta saveItemMeta = saveItem.hasItemMeta() ? saveItem.getItemMeta() : Bukkit.getItemFactory().getItemMeta(saveItem.getType());
        saveItemMeta.setDisplayName("§c§l▲保存配置");
        saveItem.setItemMeta(saveItemMeta);
        map.put(40, saveItem);
        return map;
    }
}
