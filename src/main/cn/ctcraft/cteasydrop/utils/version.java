package cn.ctcraft.cteasydrop.utils;

import cn.ctcraft.cteasydrop.CtEasyDrop;
import org.bukkit.configuration.file.YamlConfiguration;

import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

public class version {
    public static List<String> getVersionMsg(){
        List<String> versionMsg = new ArrayList<>();
        String version = "获取失败！";
        try {
            Path tempFile = Files.createTempFile("version", ".yml");
            Files.copy(new URL("https://cdn.jsdelivr.net/gh/dkinging/CtEasyDrop@master/version.yml").openStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            tempFile.toFile().deleteOnExit();
            String str = new String(Files.readAllBytes(tempFile), "UTF-8");
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.loadFromString(str);
            version = yamlConfiguration.getString("version");
        }catch (Exception e){
            e.printStackTrace();
        }
        CtEasyDrop plugin = CtEasyDrop.getPlugin(CtEasyDrop.class);
        versionMsg.add("§6===========[CtEasyDrop]============");
        if(plugin.getDescription().getVersion().equalsIgnoreCase(version)){
            versionMsg.add("欢迎您使用CtEasyDrop最新版本! 版本号:"+version);
            versionMsg.add("§6=======================================");
            return versionMsg;
        }
        versionMsg.add("CtEasyDrop不是最新版本!最新版本: §b"+version+"§6!你的版本: §b"+ plugin.getDescription().getVersion());
        versionMsg.add("§6=======================================");
        return versionMsg;
    }
}
