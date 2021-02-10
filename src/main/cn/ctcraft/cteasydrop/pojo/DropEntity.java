package cn.ctcraft.cteasydrop.pojo;

public class DropEntity {

    private String dropID;
    private String entity;
    private String world;
    private String dropData;
    private int money;
    private int exp;

    public DropEntity() {
    }

    public DropEntity(String dropID, String entity, String world, String dropData, int money, int exp) {
        this.dropID = dropID;
        this.entity = entity;
        this.world = world;
        this.dropData = dropData;
        this.money = money;
        this.exp = exp;
    }

    public String getDropID() {
        return this.dropID;
    }

    public void setDropID(String dropID) {
        this.dropID = dropID;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getWorld() {
        return this.world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getDropData() {
        return this.dropData;
    }

    public void setDropData(String dropData) {
        this.dropData = dropData;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExp() {
        return this.exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String toString() {
        return "DropEntity{dropID='" + this.dropID + '\'' + ", entity='" + this.entity + '\'' + ", world='" + this.world + '\'' + ", dropData='" + this.dropData + '\'' + ", money=" + this.money + ", exp=" + this.exp + '}';
    }
}
