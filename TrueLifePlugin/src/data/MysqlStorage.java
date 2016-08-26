package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlStorage {
  private Connection connect = null;
  public MysqlStorage(){
	  String url="jdbc:mysql://"+ConfigData.sqlip+":"+ConfigData.port+"/"+ConfigData.database; 
	  String user=ConfigData.user; 
	  String password=ConfigData.password; 
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.connect = conn;
  }
  public void createTable(){
	  PreparedStatement pst;
	  try {
		pst = connect.prepareStatement("create table if not exists TrueLife(Name varchar(20),Sleepy int(4),Thirsty int(4),Infected int(4))");
		pst.executeUpdate();
	} catch (SQLException e) {e.printStackTrace();}
  }
  public void insertData(String name,int sleepy,int thirsty,int infected){
	  PreparedStatement pst;
	  try {
		if(isFirst(name)){
			pst = connect.prepareStatement("insert into Truelife values('"+name+"',"+sleepy+","+thirsty+","+infected+")");
			pst.executeUpdate();
		}else{
			updateData(name, sleepy, thirsty, infected);
		}
	} catch (SQLException e) {e.printStackTrace();}
  }
  public void updateData(String name,int sleepy,int thirsty,int infected){
	  PreparedStatement pst;
	  try {
		pst = connect.prepareStatement("update Truelife set Sleepy = "+sleepy+" where Name = '"+name+"'");
		pst.executeUpdate();
		
		pst = connect.prepareStatement("update Truelife set Thirsty = "+thirsty+" where Name = '"+name+"'");
		pst.executeUpdate();
		
		pst = connect.prepareStatement("update Truelife set Infected = "+infected+" where Name = '"+name+"'");
		pst.executeUpdate();
	} catch (SQLException e) {e.printStackTrace();}
  }
  public boolean isFirst(String name){
	  PreparedStatement pst;
		try {
			pst = connect.prepareStatement("select * from TrueLife where Name = '"+name+"'");
			ResultSet res = pst.executeQuery();
			if(!res.next()){
				return true;
			}
		} catch (SQLException e) {e.printStackTrace();}
	  return false;
  }
  public int getData(String name,String type){
	  PreparedStatement pst;
	  try {
		pst = connect.prepareStatement("select "+type+" from TrueLife where Name = '"+name+"'");
		ResultSet res = pst.executeQuery();
		while(res.next()){
			return Integer.parseInt(res.getString(1));
		}
	} catch (SQLException e) {e.printStackTrace();}
	  return 0;
  }
  public void stop(){
	  try {
		connect.close();
	} catch (SQLException e) {e.printStackTrace();}
  }
}
