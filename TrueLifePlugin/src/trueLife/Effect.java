package trueLife;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import data.ConfigData;
import data.PlayerData;

public class Effect implements Runnable{
	public void run() {
		Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
		while(plist.hasNext()){
			Player p = plist.next();
        	if(p.hasPermission("truelife.admin")){continue;}
			for(int a=0;a<ConfigData.esleepy.size();a++){
				String[] arg = ConfigData.esleepy.get(a).split(" ");
				String[] effect = arg[0].split(",");
				if(isOK(PlayerData.getData(p.getName(), "sleepy"),arg[1])){
					p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect[0]), 100, Integer.parseInt(effect[1])));
				}
			}
			for(int a=0;a<ConfigData.ethirsty.size();a++){
				String[] arg = ConfigData.ethirsty.get(a).split(" ");
				String[] effect = arg[0].split(",");
				if(isOK(PlayerData.getData(p.getName(), "thirsty"),arg[1])){
                p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect[0]), 100, Integer.parseInt(effect[1])));
				}
			}
			for(int a=0;a<ConfigData.einfected.size();a++){
				String[] arg = ConfigData.einfected.get(a).split(" ");
				String[] effect = arg[0].split(",");
				if(isOK(PlayerData.getData(p.getName(), "infected"),arg[1])){
                p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect[0]), 100, Integer.parseInt(effect[1])));
				}
			}
		}
	}
	private boolean isOK(int arg1,String arg2){
		if(arg2.contains("<")){
			if(arg1<Integer.parseInt(arg2.substring(1))){
				return true;
			}
		}else if(arg2.contains(">")){
			if(arg1>Integer.parseInt(arg2.substring(1))){
				return true;
			}
		}else if(arg2.contains("=")){
			if(arg1==Integer.parseInt(arg2.substring(1))){
				return true;
			}
		}
		return false;
	}
}
