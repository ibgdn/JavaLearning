/**
 * Created by Administrator on 2017/7/7.
 *  entity.
 */

@MyEntity
@MyTable(name="human_") // 对应的数据表
public class Human {
    private int id;
    private String name;
    private int damage;
    private int armor;

    @MyId
    @MyGeneratedValue(strategy = "identity")
    @MyColumn("id_")    // 当名称是 value 的时候可以简写
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @MyColumn("name_")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MyColumn("damage_")
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @MyColumn("armor_")
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
