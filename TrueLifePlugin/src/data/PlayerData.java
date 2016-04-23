package data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import trueLife.Board;

public class PlayerData{
private static HashMap<String,Integer> sleepy = new HashMap<String, Integer>();
private static HashMap<String,Integer> thirsty =  new HashMap<String, Integer>();
private static HashMap<String,Integer> infected = new HashMap<String, Integer>();

public static void Join(Player p){
	File file = new File("./plugins/TrueLife/player.yml");
	YamlConfiguration fd = YamlConfiguration.loadConfiguration(file);
	String name = p.getName();
	if(fd.getString(name)==null){
		sleepy.put(name, 0);
		thirsty.put(name, 0);
		infected.put(name, 0);
	}
	sleepy.put(name, fd.getInt(name+".sleepy"));
	thirsty.put(name, fd.getInt(name+".thirsty"));
	infected.put(name, fd.getInt(name+".infected"));
	new Board(p).openBoard();
}
public static void Quit(Player p){
	File file = new File("./plugins/TrueLife/player.yml");
	YamlConfiguration fd = YamlConfiguration.loadConfiguration(file);
	String name = p.getName();
	fd.set(name+".sleepy", sleepy.get(name));
	fd.set(name+".thirsty", thirsty.get(name));
	fd.set(name+".infected", infected.get(name));
	try {
		fd.save(file);
	} catch (IOException e) {e.printStackTrace();}
	sleepy.remove(name);
	thirsty.remove(name);
	infected.remove(name);
}
public static void changeData(Player p,String n,int a){
	if(n.equalsIgnoreCase("sleepy")){
		int sl = sleepy.get(p.getName())-a;
		if(sl<0){sl=0;}else if(sl>100){sl=100;}
		sleepy.put(p.getName(), sl);
	}else if(n.equalsIgnoreCase("thirsty")){
		int sl = thirsty.get(p.getName())-a;
		if(sl<0){sl=0;}else if(sl>100){sl=100;}
		thirsty.put(p.getName(), sl);
	}else if(n.equalsIgnoreCase("infected")){
		int sl = infected.get(p.getName())-a;
		if(sl<0){sl=0;}else if(sl>100){sl=100;}
		infected.put(p.getName(), sl);
	}
}
public static int getData(String name,String k){
	if(k.equalsIgnoreCase("sleepy")){
		return sleepy.get(name);
	}else if(k.equalsIgnoreCase("thirsty")){
		return thirsty.get(name);
	}else if(k.equalsIgnoreCase("infected")){
		return infected.get(name);
	}else{
		return 0;
	}
}
}
