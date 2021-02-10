package cn.ctcraft.cteasydrop.command;

import cn.ctcraft.cteasydrop.CtEasyDrop;
import cn.ctcraft.cteasydrop.inventory.DropDataInventory;
import cn.ctcraft.cteasydrop.service.DataService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandHandler implements CommandExecutor {

    private CtEasyDrop ctEasyDrop = CtEasyDrop.getPlugin(CtEasyDrop.class);


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("ced")) {
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(this.ctEasyDrop.getDescription().getName());
            sender.sendMessage(this.ctEasyDrop.getDescription().getVersion());
            return true;
        } else {
            if (args.length == 3 && args[0].equalsIgnoreCase("drop")) {
                if (!sender.hasPermission("CtEasyDrop.set")) {
                    sender.sendMessage("§c§l■ 权限不足!");
                    return true;
                }

                if (!args[1].equalsIgnoreCase("set")) {
                    sender.sendMessage("§c§l■ 参数错误,正确使用格式为:/ced drop set [掉落物品数据名称]");
                }

                DropDataInventory dropDataInventory = new DropDataInventory();
                dropDataInventory.OpenInventory((Player)sender, args[2]);
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("CtEasyDrop.reload")) {
                    sender.sendMessage("§c§l权限不足!");
                }

                DataService dataService = DataService.getInstance();
                dataService.loadYaml();
                File file = new File(ctEasyDrop.getDataFolder() + "/dropData");
                if (!file.exists()) {
                    boolean mkdir = file.mkdir();
                    if (mkdir) {
                        ctEasyDrop.saveResource("dropData/pigDropData", false);
                        ctEasyDrop.getLogger().info("§a§l● 数据目录构建成功!");
                    }
                }

                sender.sendMessage("§c§l● 重载成功!");
            }

            return true;
        }
    }
}
