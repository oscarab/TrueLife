package listener;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import trueLife.Board;
import data.ConfigData;
import data.Items;
import data.PlayerData;

public class ItemListener implements Listener{
	@EventHandler
	  public void onInteract(PlayerInteractEvent e){
		  if(e.getAction() == Action.RIGHT_CLICK_AIR){
			  Player p = e.getPlayer();
			  ItemStack item = p.getItemInHand();
			  if(Items.isItems(item)){
		        	 if(Items.isEat(item)){return;}
					  int am = item.getAmount();
					  if(am==1){
					  p.setItemInHand(new ItemStack(Material.AIR));
					  }else if(am>1){
						  item.setAmount(am-1);
					  }
					if(ConfigData.sleepy){
						  PlayerData.changeData(p, "sleepy", -Items.getEffect(item, "sleepy"));
				      	}
			      	if(ConfigData.thirsty){
					  PlayerData.changeData(p, "thirsty", -Items.getEffect(item, "thirsty"));
			      	}
			      	if(ConfigData.infected){
					  PlayerData.changeData(p, "infected", -Items.getEffect(item, "infected"));
			      	}
			      	item.setAmount(am-1);
			               new Board(p).update();
							  List<String> msg = Items.getMsg(item);
							  for(int i=0;i<msg.size();i++){
								  String arg = Items.getMsg(item).get(i);
							  p.sendMessage(ConfigData.replaceAll(arg, p));
							  }

			  }
		  }
	  }
	  @EventHandler
	  public void onEat(PlayerItemConsumeEvent e){
		  Player p = e.getPlayer();
		  ItemStack item = p.getItemInHand();
        	 if(Items.isEat(item)){
			      	if(ConfigData.sleepy){
					  PlayerData.changeData(p, "sleepy", -Items.getEffect(item, "sleepy"));
			      	}
			      	if(ConfigData.thirsty){
					  PlayerData.changeData(p, "thirsty", -Items.getEffect(item, "thirsty"));
			      	}
			      	if(ConfigData.infected){
					  PlayerData.changeData(p, "infected", -Items.getEffect(item, "infected"));
			      	}
			        	new Board(p).update();
				  List<String> msg = Items.getMsg(item);
				  for(int i=0;i<msg.size();i++){
					  String arg = Items.getMsg(item).get(i);
				  p.sendMessage(ConfigData.replaceAll(arg, p));
				  }
        	 }
		  }
}