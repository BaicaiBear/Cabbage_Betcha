import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;


public class Main extends PluginBase implements Listener {

    public int[] num;
    public boolean st = true;

    public Item loadData(String key){

        Item data;
        String da = this.getConfig().getString(key);
        data = Item.get(Integer.valueOf(da.split(":")[0]).intValue());
        data.setDamage(Integer.valueOf(da.split(":")[1]).intValue());
        return data;
    }

    @Override
    public void onLoad(){
        saveDefaultConfig();
        this.getLogger().info("加载中12%...");
        return;
    }

    @Override
    public void onEnable(){

        //注册监听器
        this.getServer().getPluginManager().registerEvents(this,this);

        //读取物品种类数
        num = new int[this.getConfig().getInt("物品种类数")];
        if(num.length == 0){
            this.getLogger().alert("1-请检查配置文件，插件将不会生效");
            st = false;
            return;
        }
        //读取物品数量
        for(int i=0;i<num.length;i++){
            num[i] = this.getConfig().getInt("第{"+String.valueOf(i+1)+"}种");
            if( num[i]==0){
                this.getLogger().alert("2-请检查配置文件，插件将不会生效");
                st = false;
                return;
            }
        }

        this.getLogger().info("加载成功！来自小熊白菜");
        return;
    }

    @Override
    public void onDisable(){
        this.getLogger().info("（悄悄溜走...）");
        return;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        //判定玩家第一次进入
        if(st && !event.getPlayer().playedBefore){
            Item[] ite;
            ite = new Item[num.length];
            for(int i=0;i<num.length;i++){
                ite[i]=loadData("第["+String.valueOf(i+1)+"]种");
                ite[i].setCount(num[i]);
            }

            //给予玩家物品
            event.getPlayer().getInventory().addItem(ite);

            //显示底部提示语
            for(int i=0;i<100;i++){event.getPlayer().sendTip(this.getConfig().getString("提示语"));}

        }
        return;
    }
}
