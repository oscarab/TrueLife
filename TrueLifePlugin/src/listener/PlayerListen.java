package listener;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import trueLife.Board;
import data.ConfigData;
import data.PlayerData;

public class PlayerListen implements Listener{
@EventHandler
public void onJoin(PlayerJoinEvent e){
	PlayerData.Join(e.getPlayer());
}
@EventHandler
public void onQuit(PlayerQuitEvent e){
	PlayerData.Quit(e.getPlayer());
}
@EventHandler
public void onLeaveBed(PlayerBedLeaveEvent e){
	Player p = e.getPlayer();
	      	if(!ConfigData.active_enable.get("Sleep")){return;}
	      	if(ConfigData.sleepy){
			PlayerData.changeData(p, "sleepy", -ConfigData.sleepy_effect.get("sleepy"));
	      	}
	      	if(ConfigData.thirsty){
			PlayerData.changeData(p, "thirsty", -ConfigData.sleepy_effect.get("thirsty"));
	      	}
	      	if(ConfigData.infected){
			PlayerData.changeData(p, "infected", -ConfigData.sleepy_effect.get("infected"));
	      	}
			new Board(p).update();
		List<String> arg = ConfigData.active_msg.get("Sleep");
		for(String a:arg){
			p.sendMessage(ConfigData.replaceAll(a, p));
		}
}
@EventHandler
public void onDamage(EntityDamageByEntityEvent e){
	  if(e.getEntity().getType() == EntityType.PLAYER && e.getDamager() instanceof Monster){
		 Player p = ((Player) e.getEntity());
      	if(!ConfigData.active_enable.get("DamagedBYMonster")){return;}
      	if(ConfigData.sleepy){
		PlayerData.changeData(p, "sleepy", -ConfigData.monster_effect.get("sleepy"));
      	}
      	if(ConfigData.thirsty){
		PlayerData.changeData(p, "thirsty", -ConfigData.monster_effect.get("thirsty"));
      	}
      	if(ConfigData.infected){
		PlayerData.changeData(p, "infected", -ConfigData.monster_effect.get("infected"));
      	}
			 new Board(p).update();
		List<String> arg = ConfigData.active_msg.get("DamagedBYMonster");
		for(String a:arg){
			p.sendMessage(ConfigData.replaceAll(a, p));
		}
	  }
}
@EventHandler
public void onDeath(PlayerDeathEvent evt){
		 if(evt.getEntityType() == EntityType.PLAYER){
			 Player p = evt.getEntity();
			 String name = p.getName();
			 if(ConfigData.clear_data){
			PlayerData.changeData(p, "sleepy", PlayerData.getData(name, "sleepy"));
			PlayerData.changeData(p, "thirsty", PlayerData.getData(name, "thirsty"));
			PlayerData.changeData(p, "infected", PlayerData.getData(name, "infected"));
			 }
      	new Board(p).update();
		 }
}
}
