package cn.ctcraft.cteasydrop.data;

import cn.ctcraft.cteasydrop.pojo.DropEntity;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class YamlData {
    public static YamlConfiguration dropYaml = new YamlConfiguration();
    public static List<DropEntity> dropEntityList = new ArrayList<>();

    public YamlData() {
    }
}
