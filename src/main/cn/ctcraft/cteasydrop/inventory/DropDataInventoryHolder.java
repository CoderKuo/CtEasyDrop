package cn.ctcraft.cteasydrop.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DropDataInventoryHolder implements InventoryHolder {
    private String dropDataName;

    public DropDataInventoryHolder() {
    }

    public String getDropDataName() {
        return this.dropDataName;
    }

    public void setDropDataName(String dropDataName) {
        this.dropDataName = dropDataName;
    }

    public Inventory getInventory() {
        return null;
    }
}
