package cn.ctcraft.cteasydrop.listener;

import cn.ctcraft.cteasydrop.manager.EntityDeathManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    public EntityDeathListener() {
    }

    @EventHandler
    public void EntityDeath(EntityDeathEvent e) {
        (new EntityDeathManager(e)).handlerEntityDeathEvent();
    }
}
