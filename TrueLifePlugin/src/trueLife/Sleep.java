package trueLife;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import data.ConfigData;
import data.PlayerData;

public class Sleep extends java.util.TimerTask {
	public void run() {
		Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
		while(plist.hasNext()){
			Player p = plist.next();
        	if(p.hasPermission("truelife.admin")){return;}
        	if(PlayerData.getData(p.getName(), "sleepy")==100){
        		break;
        	}
        	PlayerData.changeData(p, "sleepy", -1);
        	ConfigData.sendMsg(p, "sleepy", PlayerData.getData(p.getName(), "sleepy"));
        	new Board(p).update();
        }
        	}
}