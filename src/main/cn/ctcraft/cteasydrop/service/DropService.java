package cn.ctcraft.cteasydrop.service;

import cn.ctcraft.cteasydrop.data.YamlData;
import cn.ctcraft.cteasydrop.pojo.DropEntity;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class DropService {

    private static DropService instance = new DropService();

    private DropService() {
    }

    public static DropService getInstance() {
        return instance;
    }

    public List<DropEntity> getDropEntityByEntity(LivingEntity entity, String world) {
        List<DropEntity> list = new ArrayList<>();

        for (DropEntity dropEntity : YamlData.dropEntityList) {
            String entity1 = dropEntity.getEntity();
            if (entity1.equalsIgnoreCase(entity.getName())) {
                String world1 = dropEntity.getWorld();
                if (world.equalsIgnoreCase(world1)) {
                    list.add(dropEntity);
                }
            }
        }

        return list;
    }
}
