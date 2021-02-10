package cn.ctcraft.cteasydrop.service;

import cn.ctcraft.cteasydrop.CtEasyDrop;
import cn.ctcraft.cteasydrop.data.YamlData;
import cn.ctcraft.cteasydrop.pojo.DropEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Set;

public class DataService {

    private static DataService instance = new DataService();
    private CtEasyDrop ctEasyDrop = CtEasyDrop.getPlugin(CtEasyDrop.class);

    private DataService() {
    }

    public static DataService getInstance() {
        return instance;
    }

    public void loadYaml() {
        File dataFolder = this.ctEasyDrop.getDataFolder();
        File file = new File(dataFolder + "/drop.yml");
        if (!file.exists()) {
            this.ctEasyDrop.saveResource("drop.yml", false);
            this.loadYaml();
        } else {
            try {
                YamlData.dropYaml.load(file);
            } catch (Exception e) {
                this.ctEasyDrop.getLogger().warning("§c§l■ drop.yml配置文件读取失败!");
                e.printStackTrace();
            }

            YamlConfiguration dropYaml = YamlData.dropYaml;
            Set<String> keys = dropYaml.getKeys(false);
            this.loadDropData();
            this.ctEasyDrop.getLogger().info("§a§l● 掉落配置读取成功,共读取" + keys.size() + "条配置!");
        }
    }

    public void loadDropData() {
        YamlConfiguration dropYaml = YamlData.dropYaml;
        Set<String> keys = dropYaml.getKeys(false);
        for (String key : keys) {
            ConfigurationSection configurationSection = dropYaml.getConfigurationSection(key);
            Set<String> keys1 = configurationSection.getKeys(false);
            if (keys1.contains("entity")) {
                String entity = configurationSection.getString("entity");
                String world1 = configurationSection.getString("world");
                String dropData = configurationSection.getString("dropData");
                int money = configurationSection.getInt("money");
                int exp = configurationSection.getInt("exp");
                DropEntity dropEntity = new DropEntity(key, entity, world1, dropData, money, exp);
                List<DropEntity> dropEntityList = YamlData.dropEntityList;
                dropEntityList.add(dropEntity);
            }
        }
    }
}
