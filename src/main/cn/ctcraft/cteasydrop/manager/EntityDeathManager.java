package cn.ctcraft.cteasydrop.manager;

import com.google.common.base.Strings;
import cn.ctcraft.cteasydrop.CtEasyDrop;
import cn.ctcraft.cteasydrop.pojo.DropEntity;
import cn.ctcraft.cteasydrop.service.DropDataService;
import cn.ctcraft.cteasydrop.service.DropService;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public class EntityDeathManager {

    private EntityDeathEvent e;
    private DropService dropService = DropService.getInstance();
    private DropDataService dropDataService = DropDataService.getInstance();
    private CtEasyDrop ctEasyDrop = CtEasyDrop.getPlugin(CtEasyDrop.class);

    public EntityDeathManager(EntityDeathEvent e) {
        this.e = e;
    }

    public void handlerEntityDeathEvent() {
        LivingEntity entity = e.getEntity();
        Player player = entity.getKiller();
        if (!(entity instanceof Player)) {
            if (player != null) {
                List<DropEntity> dropEntityByEntity = dropService.getDropEntityByEntity(entity, entity.getWorld().getName());
                for (DropEntity dropEntity : dropEntityByEntity) {
                    String dropData = dropEntity.getDropData();
                    if (!Strings.isNullOrEmpty(dropData)) {
                        File file = new File(ctEasyDrop.getDataFolder() + "/dropData/" + dropData);
                        if (file.exists()) {
                            List<ItemStack> itemStackFromFile = dropDataService.getItemStackFromFile(file);
                            e.getDrops().clear();
                            e.getDrops().addAll(itemStackFromFile);
                        }
                    }

                    int exp = dropEntity.getExp();
                    e.setDroppedExp(exp);
                    if (ctEasyDrop.economy != null) {
                        int money = dropEntity.getMoney();
                        YamlConfiguration lang = ctEasyDrop.lang;
                        String dropMoneyMessage = lang.getString("dropMoneyMessage").replace("&", "§");
                        String replaceEntity = dropMoneyMessage.replace("%Entity%", entity.getName());
                        String replaceMoney = replaceEntity.replace("%money%", String.valueOf(money));
                        player.sendMessage(replaceMoney);
                        ctEasyDrop.economy.depositPlayer(player, money);
                    }
                }

            }
        }
    }
}
