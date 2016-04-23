package trueLife;

import java.util.ArrayList;
import java.util.List;

import me.winterguardian.easyscoreboards.ScoreboardUtil;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import data.ConfigData;
import data.PlayerData;

public class Board {
  public int sleepy= 0;
  public int thirsty = 0;
  public int infected= 0;
  public Player p;
  public Board(Player p){
	  String name = p.getName();
    sleepy = PlayerData.getData(name, "sleepy");
    thirsty = PlayerData.getData(name, "thirsty");
    infected = PlayerData.getData(name, "infected");
    this.p=p;
  }
  public void openBoard(){
	  if(ConfigData.scoreboard){
	  List<String> list = new ArrayList<>();
	  list.add(ConfigData.boardname.replace("<player>", p.getName()));
	  for(int i=0;i<ConfigData.scorelist.size();i++){
		  String arg = ConfigData.scorelist.get(i);
		  arg=arg.replace("<sleepy>", ""+sleepy);
		  arg=arg.replace("<thirsty>", ""+thirsty);
		  arg=arg.replace("<infected>", ""+infected);
		  arg=arg.replace("<player>", p.getName());
		  list.add(arg);
	  }
	  String[] score = new String[list.size()];
	  for(int i=0;i<score.length;i++){
		  score[i]=list.get(i);
	  }
	  ScoreboardUtil.unrankedSidebarDisplay(p, score);
	  }else{
		  clear();
	  }
  }
  public void update(){
	  if(ConfigData.scoreboard){
	      openBoard();
	  }else{
		  clear();
	  }
  }
  public void clear(){
	  if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR)!=null){
	  p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
		  p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	  }
  }
}
