package trueLife;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;

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
import data.PlayerData;

public class TrueLife extends JavaPlugin implements Listener{
  public void onEnable(){
	if(!new File("./plugins/TrueLife/config.yml").exists()){
		  saveDefaultConfig();
		  getLogger().info("�����ļ������ɹ���");
	  }
	File file1 = new File("./plugins/TrueLife/items.yml");
	YamlConfiguration fd1 = YamlConfiguration.loadConfiguration(file1);
	if(!file1.exists()){
		try {fd1.save(file1);
		} catch (IOException e) {e.printStackTrace();}
		getLogger().info("��Ʒ�ļ������ɹ���");
	}
	File file = new File("./plugins/TrueLife/player.yml");
	YamlConfiguration fd = YamlConfiguration.loadConfiguration(file);
	if(!file.exists()){
		try {fd.save(file);
		} catch (IOException e) {e.printStackTrace();}
		getLogger().info("����ļ������ɹ���");
	}
	getServer().getPluginManager().registerEvents(new ItemListener(), this);
	getServer().getPluginManager().registerEvents(new PlayerListen(), this);
	getServer().getPluginManager().registerEvents(this, this);
	ConfigData.saveConfig();
	Items.saveItems();
	Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
	while(plist.hasNext()){
		Player p = plist.next();
		  PlayerData.Join(p);
	  }
	startLife();
	getLogger().info("�ɹ����У�  ���ߣ�oscarab  �汾��v0.2.1");
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
		    sender.sendMessage("��5��l"+sender.getName()+"��b������״��ͳ�ƣ�");
		    if(ConfigData.sleepy){
		    	int sleepy = PlayerData.getData(sender.getName(), "sleepy");
		    sender.sendMessage("��2ƣ��ֵ��    ��b"+sleepy+"/100");
		    }
		    if (ConfigData.thirsty) {
		    	int thirsty = PlayerData.getData(sender.getName(), "thirsty");
				sender.sendMessage("��2�ڿ�ֵ��    ��b"+ thirsty+"/100");
			}
		    if(ConfigData.infected){
		    	int infected = PlayerData.getData(sender.getName(), "infected");
			sender.sendMessage("��2����ֵ��    ��b"+infected+"/100");
		    }
		    return true;
			  }else{
				  sender.sendMessage("������ֻ�����ʹ�ã�");
				  return true;
			  }
		  }else if(args.length == 1&&args[0].equalsIgnoreCase("help")){
			  sender.sendMessage("��b���ߣ�oscarab  �汾��v0.2.1");
			  sender.sendMessage("��b��l������");
			  sender.sendMessage("��2ƣ��ֵ����ʱ�����Ʋ�������");
			  sender.sendMessage("��2�ڿ�ֵ����ʱ�����Ʋ�������");
			  sender.sendMessage("��2����ֵ�������﹥��ʱ��Ը��������");
			  sender.sendMessage("��b/truelife �鿴����״��");
			  if(sender.hasPermission("truelife.set")){
			  sender.sendMessage("��b/truelife set <���> <sleepy��thirsty��infected> <��ֵ> �ı���ҵ�����״��");
			  }
			  sender.sendMessage("��b/truelife board <true|false>    ��ʾ�����ؼƷְ�");
			  return true;
		  }else if(args[0].equalsIgnoreCase("set") && args.length == 4){
			  if(!sender.hasPermission("truelife.set")){
				  sender.sendMessage("��4��û��ʹ�ø������Ȩ�ޣ�");
				  return true;
			  }
			  Player p = getServer().getPlayer(args[1]);
			  if(p != null && Integer.parseInt(args[3]) >= 0 && Integer.parseInt(args[3]) <= 100){
				  new Board(p).update();
				  switch(args[2]){
				  case "sleepy":
					  if(ConfigData.sleepy){
					  PlayerData.changeData(p, "sleepy", PlayerData.getData(p.getName(), "sleepy")-Integer.parseInt(args[3]));
					  sender.sendMessage("��b���óɹ���");
					  p.sendMessage("��2���ƣ��ֵ������Ϊ:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("��4����ֵδ���������������ļ�������"); 
						  return true;
					  }
				  case "thirsty":
					  if(ConfigData.thirsty){
						  PlayerData.changeData(p, "thirsty", PlayerData.getData(p.getName(), "thirsty")-Integer.parseInt(args[3]));
					  sender.sendMessage("��b���óɹ���");
					  p.sendMessage("��2��Ŀڿ�ֵ������Ϊ:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("��4����ֵδ���������������ļ�������"); 
						  return true;
					  }
				  case "infected":
					  if(ConfigData.infected){
						  PlayerData.changeData(p, "infected", PlayerData.getData(p.getName(), "infected")-Integer.parseInt(args[3]));
					  sender.sendMessage("��b���óɹ���");
					  p.sendMessage("��2��Ĳ���ֵ������Ϊ:"+Integer.parseInt(args[3]));
					  new Board(p).update();
					  return true;
					  }else{
						  sender.sendMessage("��4����ֵδ���������������ļ�������"); 
						  return true;
					  }
				  default:
					  sender.sendMessage("��4��ֻ��������ҵ�sleepy,thirsty,infected����ֵ��");
					  return true;
				  }
			  }
			  if(p == null){
				  sender.sendMessage("��4����Ҳ����ߣ�");
				  return true;
			  }
			  if(Integer.parseInt(args[3])<0 || Integer.parseInt(args[3])>100){
				  sender.sendMessage("��4�������������ֵ��");
				  return true;
			  }
		  }
		  else if(args[0].equalsIgnoreCase("board")&&args.length==2){
			  if(!sender.hasPermission("truelife.enable")){
				  sender.sendMessage("��4��û��ʹ�ø������Ȩ�ޣ�");
				  return true;
			  }
			  if(sender instanceof Player){
				  if(args[1].equalsIgnoreCase("true")||args[1].equalsIgnoreCase("false")){
					  Player p = (Player) sender;
					  if(!ConfigData.scoreboard){
						  p.sendMessage("��4�Ƿְ�δ����������ϵ����Ա�������ļ�������");
					  return true;
					  }
					  if(args[1].equalsIgnoreCase("false")){
						  new Board(p).clear();
					  sender.sendMessage("��b�Ʒְ������أ�");
				
					  return true;
					  }else if(args[1].equalsIgnoreCase("true")){
				        	new Board(p).openBoard();
						  sender.sendMessage("��b�Ʒְ�����ʾ��");
						  return true;
					  }
					  }else{
						  sender.sendMessage("��4�������������ֵ��");
						  return true;
					  }
			  }else{
				  sender.sendMessage("������ֻ�����ʹ�ã�");
				  return true;
			  }
		  }else{
			  sender.sendMessage("��b������/truelife help �鿴������");
			  return true;
		  	}
		  }
	return false;
  }
  public void startLife(){
			Timer timer = new Timer();
			if(ConfigData.sleepy){
			timer.scheduleAtFixedRate(new Sleep(), 1000, ConfigData.dsleepy*1000);
			}
			if(ConfigData.thirsty){
			timer.scheduleAtFixedRate(new Thirsty(), 1000, ConfigData.dthirsty*1000);
			}
			   Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Effect(),0,20);
			   
  }
}