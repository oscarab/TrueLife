package trueLife;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import data.ConfigData;
import data.PlayerData;

public class Thirsty implements Runnable{
	public void run() {
		Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
		while(plist.hasNext()){
			Player p = plist.next();
        	if(p.hasPermission("truelife.admin")){return;}
        	if(PlayerData.getData(p.getName(), "thirsty")==100){
        		break;
        	}
        	PlayerData.changeData(p, "thirsty", -1);
        	ConfigData.sendMsg(p, "thirsty", PlayerData.getData(p.getName(), "thirsty"));
        	new Board(p).update();
        		}
        	}
}
