package data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ConfigData {
  public static boolean scoreboard = true;
  public static boolean sleepy = true;
  public static boolean thirsty = true;
  public static boolean infected = true;
  public static int dsleepy = 12;
  public static int dthirsty = 9;
  public static List<String> msgsleepy = new ArrayList<>();
  public static List<String> msgthirsty = new ArrayList<>();
  public static List<String> msginfected = new ArrayList<>();
  
  public static String boardname = null; 
  public static List<String> scorelist = new ArrayList<>();
  
  public static List<String> esleepy = new ArrayList<>();
  public static List<String> ethirsty = new ArrayList<>();
  public static List<String> einfected = new ArrayList<>();
  
  public static HashMap<String,Boolean> active_enable = new HashMap<>();
  public static HashMap<String,List<String>> active_msg= new HashMap<>();
  public static HashMap<String,Integer> sleepy_effect = new HashMap<>();
  public static HashMap<String,Integer> monster_effect = new HashMap<>();
  public static void saveConfig(){
		File file = new File("./plugins/TrueLife/config.yml");
		YamlConfiguration fd = YamlConfiguration.loadConfiguration(file);
		scoreboard = fd.getBoolean("ScoreBoard.Enable");
		sleepy = fd.getBoolean("Enable.sleepy");
		thirsty = fd.getBoolean("Enable.thirsty");
		infected = fd.getBoolean("Enable.infected");
		dsleepy = fd.getInt("Difficult.sleepy");
		dthirsty = fd.getInt("Difficult.thirsty");
		msgsleepy=fd.getStringList("Message.sleepy");
		msgthirsty=fd.getStringList("Message.thirsty");
		msginfected=fd.getStringList("Message.infected");
		boardname = fd.getString("ScoreBoard.displayname");
		scorelist=fd.getStringList("ScoreBoard.score");
		esleepy=fd.getStringList("Effect.sleepy");
		ethirsty=fd.getStringList("Effect.thirsty");
		einfected=fd.getStringList("Effect.infected");
		
		active_enable.put("Sleep", fd.getBoolean("Sleep.enable"));
		active_enable.put("DamagedBYMonster", fd.getBoolean("DamagedBYMonster.enable"));
		active_msg.put("Sleep", fd.getStringList("Sleep.message"));
		active_msg.put("DamagedBYMonster", fd.getStringList("DamagedBYMonster.message"));
		
		sleepy_effect.put("sleepy", fd.getInt("Sleep.effect.sleepy"));
		sleepy_effect.put("thirsty", fd.getInt("Sleep.effect.thirsty"));
		sleepy_effect.put("infected", fd.getInt("Sleep.effect.infected"));
		
		monster_effect.put("sleepy", fd.getInt("DamagedBYMonster.effect.sleepy"));
		monster_effect.put("thirsty", fd.getInt("DamagedBYMonster.effect.thirsty"));
		monster_effect.put("infected", fd.getInt("DamagedBYMonster.effect.infected"));
  }
  public static void sendMsg(Player p,String type,int points){
	  if(type.equalsIgnoreCase("sleepy")){
		  for(int i=0;i<msgsleepy.size();i++){
			  String[] args = msgsleepy.get(i).split(":");
			  if(points==Integer.parseInt(args[0])){
				  p.sendMessage(replaceAll(args[1],p));
			  }
		  }
	  }else if(type.equalsIgnoreCase("thirsty")){
		  for(int i=0;i<msgthirsty.size();i++){
			  String[] args = msgthirsty.get(i).split(":");
			  if(points==Integer.parseInt(args[0])){
				  p.sendMessage(replaceAll(args[1],p));
			  }
		  }
	  }else if(type.equalsIgnoreCase("infected")){
		  for(int i=0;i<msginfected.size();i++){
			  String[] args = msginfected.get(i).split(":");
			  if(points==Integer.parseInt(args[0])){
				  p.sendMessage(replaceAll(args[1],p));
			  }
		  }
	  }
  }
  public static String replaceAll(String arg,Player p){
	  String name = p.getName();
	    int sleepy = PlayerData.getData(name, "sleepy");
	    int thirsty = PlayerData.getData(name, "thirsty");
	    int infected = PlayerData.getData(name, "infected");
	  arg=arg.replace("<sleepy>", ""+sleepy);
	  arg=arg.replace("<thirsty>", ""+thirsty);
	  arg=arg.replace("<infected>", ""+infected);
	  arg=arg.replace("<player>", p.getName());
	  return arg;
  }
}
