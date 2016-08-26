package trueLife;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import listener.ItemListener;
import listener.PlayerListen;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import data.ConfigData;
import data.Items;
import data.MysqlStorage;
import data.PlayerData;

public class TrueLife extends JavaPlugin implements Listener{
  public void onEnable(){
	if(!new File("./plugins/TrueLife/config.yml").exists()){
		  saveDefaultConfig();
		  getLogger().info("配置文件创建成功！");
	  }
	File file1 = new File("./plugins/TrueLife/items.yml");
	YamlConfiguration fd1 = YamlConfiguration.loadConfiguration(file1);
	if(!file1.exists()){
		try {fd1.save(file1);
		} catch (IOException e) {e.printStackTrace();}
		getLogger().info("物品文件创建成功！");
	}
	File file = new File("./plugins/TrueLife/player.yml");
	YamlConfiguration fd = YamlConfiguration.loadConfiguration(file);
	if(!file.exists()){
		try {fd.save(file);
		} catch (IOException e) {e.printStackTrace();}
		getLogger().info("玩家文件创建成功！");
	}
	getServer().getPluginManager().registerEvents(new ItemListener(), this);
	getServer().getPluginManager().registerEvents(new PlayerListen(), this);
	getServer().getPluginManager().registerEvents(this, this);
	ConfigData.saveConfig();
	if(!ConfigData.version.equalsIgnoreCase("0.2.5")){
		this.saveDefaultConfig();
		ConfigData.saveConfig();
		getLogger().info("配置文件更新成功！");
	}
	if(ConfigData.storage.equalsIgnoreCase("sql")){
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        getLogger().info("数据库驱动完成！");
			new MysqlStorage().createTable();
			
		} catch (ClassNotFoundException e) {
	        getLogger().info("数据库驱动失败！");
		} 
	}
	Items.saveItems();
	Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
	while(plist.hasNext()){
		Player p = plist.next();
		  PlayerData.Join(p);
	  }
	startLife();
	getLogger().info("成功运行！  作者：oscarab  版本：v0.2.5");
  }
  public void onDisable(){
		Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
		while(plist.hasNext()){
			Player p = plist.next();
		  PlayerData.Quit(p);
	  }
  }
  public boolean onCommand(CommandSender sender,Command cmd,String Label,String[] args){
	  if(cmd.getName().equalsIgnoreCase("truelife")){
		  if(args.length == 0){
			  if(sender instanceof Player){
		    sender.sendMessage("§5§l"+sender.getName()+"§b的身体状况统计：");
		    if(ConfigData.sleepy){
		    	int sleepy = PlayerData.getData(sender.getName(), "sleepy");
		    sender.sendMessage("§2疲劳值：    §b"+sleepy+"/100");
		    }
		    if (ConfigData.thirsty) {
		    	int thirsty = PlayerData.getData(sender.getName(), "thirsty");
				sender.sendMessage("§2口渴值：    §b"+ thirsty+"/100");
			}
		    if(ConfigData.infected){
		    	int infected = PlayerData.getData(sender.getName(), "infected");
			sender.sendMessage("§2病毒值：    §b"+infected+"/100");
		    }
		    return true;
			  }else{
				  sender.sendMessage("该命令只能玩家使用！");
				  return true;
			  }
		  }else if(args.length == 1&&args[0].equalsIgnoreCase("help")){
			  sender.sendMessage("§b作者：oscarab  版本：v0.2.5");
			  sender.sendMessage("§b§l帮助：");
			  sender.sendMessage("§2疲劳值：随时间推移不断增加");
			  sender.sendMessage("§2口渴值：随时间推移不断增加");
			  sender.sendMessage("§2病毒值：被怪物攻击时或吃腐肉会增加");
			  sender.sendMessage("§b/truelife 查看身体状况");
			  sender.sendMessage("§b/truelife set <玩家> <sleepy或thirsty或infected> <数值> 改变玩家的身体状况");
              sender.sendMessage("§b/truelife down <玩家> <sleepy或thirsty或infected> <数值> 降低玩家的某个数值");
			  sender.sendMessage("§b/truelife board <true|false>    显示或隐藏计分板");
			  sender.sendMessage("§b/truelife reload    重载配置文件");
			  return true;
		  }else if(args[0].equalsIgnoreCase("set") && args.length == 4){
			  if(!sender.hasPermission("truelife.set")){
				  sender.sendMessage("§4你没有使用该命令的权限！");
				  return true;
			  }
			  Player p = getServer().getPlayer(args[1]);
			  if(p != null && Integer.parseInt(args[3]) >= 0 && Integer.parseInt(args[3]) <= 100){
				  new Board(p).update();
				  switch(args[2]){
				  case "sleepy":
					  if(ConfigData.sleepy){
					  PlayerData.changeData(p, "sleepy", PlayerData.getData(p.getName(), "sleepy")-Integer.parseInt(args[3]));
					  sender.sendMessage("§b设置成功！");
					  p.sendMessage("§2你的疲劳值被设置为:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  case "thirsty":
					  if(ConfigData.thirsty){
						  PlayerData.changeData(p, "thirsty", PlayerData.getData(p.getName(), "thirsty")-Integer.parseInt(args[3]));
					  sender.sendMessage("§b设置成功！");
					  p.sendMessage("§2你的口渴值被设置为:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  case "infected":
					  if(ConfigData.infected){
						  PlayerData.changeData(p, "infected", PlayerData.getData(p.getName(), "infected")-Integer.parseInt(args[3]));
					  sender.sendMessage("§b设置成功！");
					  p.sendMessage("§2你的病毒值被设置为:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  default:
					  sender.sendMessage("§4你只能设置玩家的sleepy,thirsty,infected的数值！");
					  return true;
				  }
			  }
			  if(p == null){
				  sender.sendMessage("§4该玩家不在线！");
				  return true;
			  }
			  if(Integer.parseInt(args[3])<0 || Integer.parseInt(args[3])>100){
				  sender.sendMessage("§4不能设置这个数值！");
				  return true;
			  }
		  }else if(args[0].equalsIgnoreCase("board")&&args.length==2){
			  if(!sender.hasPermission("truelife.enable")){
				  sender.sendMessage("§4你没有使用该命令的权限！");
				  return true;
			  }
			  if(sender instanceof Player){
				  if(args[1].equalsIgnoreCase("true")||args[1].equalsIgnoreCase("false")){
					  Player p = (Player) sender;
					  if(!ConfigData.scoreboard){
						  p.sendMessage("§4记分板未开启！请联系管理员在配置文件开启！");
					  return true;
					  }
					  if(args[1].equalsIgnoreCase("false")){
						  new Board(p).clear();
					  sender.sendMessage("§b计分板已隐藏！");
				
					  return true;
					  }else if(args[1].equalsIgnoreCase("true")){
				        	new Board(p).openBoard();
						  sender.sendMessage("§b计分板已显示！");
						  return true;
					  }
					  }else{
						  sender.sendMessage("§4不能设置这个数值！");
						  return true;
					  }
			  }else{
				  sender.sendMessage("该命令只能玩家使用！");
				  return true;
			  }
		  }else if(args.length==1&&args[0].equalsIgnoreCase("reload")){
			  if(!sender.hasPermission("truelife.reload")){
				  sender.sendMessage("§4你没有使用该命令的权限！");
				  return true;
			  }
			  ConfigData.saveConfig();
			  Items.saveItems();
			  sender.sendMessage("§c重载成功！");
			  return true;
		  }else if(args.length==4 && args[0].equalsIgnoreCase("down")){
			  if(!sender.hasPermission("truelife.down")){
				  sender.sendMessage("§4你没有使用该命令的权限！");
				  return true;
			  }
			  Player p = Bukkit.getPlayer(args[1]);
			  if(p!=null){
				  switch(args[2]){
				  case "sleepy":{
					  if(ConfigData.sleepy){
						  PlayerData.changeData(p, args[2], Integer.parseInt(args[3]));
						  sender.sendMessage("§b成功降低"+p.getName()+"的疲劳值"+args[3]+"点");
						  p.sendMessage("§a你的疲劳值下降了"+args[3]+"点");
						  new Board(p).update();
						  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  }
				  case "thirsty":{
					  if(ConfigData.thirsty){
						  PlayerData.changeData(p, args[2], Integer.parseInt(args[3]));
						  sender.sendMessage("§b成功降低"+p.getName()+"的口渴值"+args[3]+"点");
						  p.sendMessage("§a你的口渴值下降了"+args[3]+"点");
						  new Board(p).update();
						  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  }
				  case "infected":{
					  if(ConfigData.infected){
						  PlayerData.changeData(p, args[2], Integer.parseInt(args[3]));
						  sender.sendMessage("§b成功降低"+p.getName()+"的病毒值"+args[3]+"点");
						  p.sendMessage("§a你的病毒值下降了"+args[3]+"点");
						  new Board(p).update();
						  return true;
					  }else{
						  sender.sendMessage("§4该数值未开启！请在配置文件开启！"); 
						  return true;
					  }
				  }
				  default:{
					  sender.sendMessage("§4你只能设置玩家的sleepy,thirsty,infected的数值！");
					  return true;
				  }
				  }
			  }else{
				  sender.sendMessage("§4此玩家不在线！");
			  }
		  }else{
			  sender.sendMessage("§b请输入/truelife help 查看帮助！");
			  return true;
		  	}
		  }
	return false;
  }
  public void startLife(){
			if(ConfigData.sleepy){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Sleep(),0,ConfigData.dsleepy*20);
			}
			if(ConfigData.thirsty){
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Thirsty(),0,ConfigData.dthirsty*20);
			}
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Effect(),0,20);
			   
  }
}