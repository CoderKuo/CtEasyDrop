package cn.ctcraft.cteasydrop;

import cn.ctcraft.cteasydrop.command.CommandHandler;
import cn.ctcraft.cteasydrop.inventory.DropDataInventoryMonitor;
import cn.ctcraft.cteasydrop.listener.EntityDeathListener;
import cn.ctcraft.cteasydrop.service.DataService;
import cn.ctcraft.cteasydrop.utils.version;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.List;

public class CtEasyDrop extends JavaPlugin {
    public Economy economy = null;
    public YamlConfiguration lang = new YamlConfiguration();

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new DropDataInventoryMonitor(), this);
        this.getCommand("ced").setExecutor(new CommandHandler());
        DataService dataService = DataService.getInstance();
        dataService.loadYaml();
        RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        } else {
            this.getLogger().warning("§e§l初始化Vault失败.");
        }

        File file = new File(this.getDataFolder() + "/dropData");
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (mkdir) {
                this.saveResource("dropData/pigDropData", false);
                this.getLogger().info("§a§l● 数据目录构建成功!");
            }
        }

        File lang = new File(this.getDataFolder() + "/lang.yml");
        if (!lang.exists()) {
            this.saveResource("lang.yml", false);
        }

        try {
            this.lang.load(lang);
        } catch (Exception e) {
            this.getLogger().warning("§c§l■ 语言数据读取失败!");
            e.printStackTrace();
        }

        Metrics metrics = new Metrics(this);

        BukkitTask versionTask = new BukkitRunnable() {
            @Override
            public void run() {
                List<String> versionMsg = version.getVersionMsg();
                versionMsg.forEach(System.out::println);
            }
        }.runTaskAsynchronously(this);

        this.getLogger().info("§a§l● CtEasyDrop启动成功!");
    }

    public void onDisable() {
    }
}
