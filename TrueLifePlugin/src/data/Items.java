package data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Items {
public static List<ItemStack> items = new ArrayList<>();
public static HashMap<ItemStack,List<String>> msg = new HashMap<>();
public static HashMap<ItemStack,Boolean> use = new HashMap<>();

public static HashMap<ItemStack,Integer> sleepy = new HashMap<>();
public static HashMap<ItemStack,Integer> thirsty = new HashMap<>();
public static HashMap<ItemStack,Integer> infected = new HashMap<>();
public static HashMap<ItemStack,PotionEffect> potion = new HashMap<>();

public static HashMap<ItemStack,String> shape = new HashMap<>();
public static HashMap<ItemStack,String> shapeid = new HashMap<>();
public static void saveItems(){
	File file = new File("./plugins/TrueLife/items.yml");
	YamlConfiguration it = YamlConfiguration.loadConfiguration(file);
	Iterator<String> itn = it.getKeys(false).iterator();
	while(itn.hasNext()){
		String arg = itn.next();
		ItemStack item = new ItemStack(Material.getMaterial(it.getString(arg+".type")),1,(short) 0,(byte) it.getInt(arg+".data"));
		ItemMeta meta = item.getItemMeta();
		if(it.getString(arg+".displayname") != null){
			meta.setDisplayName(it.getString(arg+".displayname"));	
		}
		if(it.getString(arg+".lore") != null){
			List<String> lore = it.getStringList(arg+".lore");
			meta.setLore(lore);	
		}
		item.setItemMeta(meta);
		items.add(item);
		if(it.getString(arg+".msg") != null){
			msg.put(item, it.getStringList(arg+".msg"));	
		}
		if(it.getString(arg+".consume") != null){
			use.put(item, it.getBoolean(arg+".consume"));	
		}
		if(it.getString(arg+".recipe") != null){
			List<String> a = it.getStringList(arg+".recipe");
			shape.put(item, a.get(0));
			shapeid.put(item, a.get(1));
		}
		if(it.getString(arg+".effect") != null){
			sleepy.put(item, it.getInt(arg+".effect.sleepy"));
			thirsty.put(item, it.getInt(arg+".effect.thirsty"));
			infected.put(item, it.getInt(arg+".effect.infected"));
		}
	}
	createItems();
}
public static void createItems(){
	for(int i=0;i<items.size();i++){
		ItemStack item = items.get(i);
		if(shape.get(item)==null){
			continue;
		}
		  ShapedRecipe be = new ShapedRecipe(item);
		  String[] sh = shape.get(item).split(",");
		  be.shape(sh[0],sh[1],sh[2]);
		  String[] id = null;
		  if(shapeid.get(item).contains(",")){
			  id = shapeid.get(item).split(",");
		  }else{
			  id = new String[]{shapeid.get(item)};
		  }
		  for(int a=0;a<id.length;a++){
			  String[] k = id[a].split(":");
		  be.setIngredient(k[0].charAt(0), Material.getMaterial(k[1]));
		  }
		  Bukkit.getServer().addRecipe(be);
	}
}
public static boolean isItems(ItemStack item){
	int arg = item.getAmount();
	item.setAmount(1);
	if(items.contains(item)){
		item.setAmount(arg);
		return true;
	}
	item.setAmount(arg);
	return false;
}
public static List<String> getMsg(ItemStack item){
	int arg = item.getAmount();
	item.setAmount(1);
	List<String> list = msg.get(item) ;
	item.setAmount(arg);
	return list;
}
public static Integer getEffect(ItemStack item,String type){
	item.setAmount(1);
	if(type.equalsIgnoreCase("sleepy")){
		return sleepy.get(item);
	}else if(type.equalsIgnoreCase("thirsty")){
		return thirsty.get(item);
	}else if(type.equalsIgnoreCase("infected")){
		return infected.get(item);
	}else{
		return 0;
	}
}
public static boolean isEat(ItemStack item){
	int arg = item.getAmount();
	item.setAmount(1);
	if(use.get(item)!=null){
		boolean a = use.get(item);
		item.setAmount(arg);
		return a;
	}
	item.setAmount(arg);
	return false;
}
}
