package cn.ctcraft.cteasydrop.service;

import com.google.common.base.Strings;
import cn.ctcraft.cteasydrop.CtEasyDrop;
import cn.ctcraft.cteasydrop.utils.SerializableUtil;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DropDataService {

    private static DropDataService instance = new DropDataService();
    private CtEasyDrop ctEasyDrop = CtEasyDrop.getPlugin(CtEasyDrop.class);

    private DropDataService() {
    }

    public static DropDataService getInstance() {
        return instance;
    }

    public List<ItemStack> getItemStackFromFile(File file) {
        Logger logger = this.ctEasyDrop.getLogger();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();

            while (br.ready()) {
                sb.append(br.readLine());
            }

            SerializableUtil serializableUtil = new SerializableUtil();
            String s = sb.toString();
            String[] split = s.split("\\(fenge\\)");
            List<ItemStack> list = new ArrayList<>();
            for (String s1 : split) {
                ItemStack itemStack = null;
                if (!Strings.isNullOrEmpty(s1) && !s1.equalsIgnoreCase("null")) {
                    itemStack = serializableUtil.singleObjectFromString(s1, ItemStack.class);
                }
                list.add(itemStack);
            }
            return list;
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            boolean b = message.contains("系统找不到指定的文件");
            if (b) {
                int i = message.indexOf("(系统找不到指定的文件。)");
                String substring = message.substring(34, i);
                logger.warning("§c§l■ 找不到掉落物品数据!");
                logger.warning("§c§l■ 请使用/ced drop set " + substring + "设置掉落物品数据!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("§c§l■ 掉落物品数据读取失败!");
        }

        return null;
    }

    public boolean saveDate(List<ItemStack> stacks, String dropDataName) {
        Logger logger = this.ctEasyDrop.getLogger();

        try {
            String rewardDate = this.getDate(stacks);
            File file = new File(this.ctEasyDrop.getDataFolder() + "/dropData/" + dropDataName);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    logger.info("§a§l● 掉落数据创建成功,奖励名为" + dropDataName);
                } else {
                    logger.warning("§c§l■ 掉落数据创建失败,奖励名为" + dropDataName);
                }
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(rewardDate);
            bw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("§c§l■ 掉落数据保存失败!");
            return false;
        }
    }

    public String getDate(List<ItemStack> stacks) throws IOException {
        SerializableUtil serializableUtil = new SerializableUtil();
        String result = "";
        for (ItemStack stack : stacks) {
            String s = serializableUtil.singleObjectToString(stack);
            if (Strings.isNullOrEmpty(result)) {
                result = s;
            } else {
                result = result + "(fenge)" + s;
            }
        }

        return result;
    }
}
