package net.yasite.exit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class Exit extends Application{
	private List<Activity> activities = new ArrayList<Activity>();
	 private static Exit instance;
	 private Exit(){
	  
	 }
	//单例模式中获取唯一的application
	 public static Exit getInstance(){
	  if(null==instance){
	   instance=new Exit();
	  }
	  return instance;
	 }
	//存放Activity到list中
	 public  void addActivity(Activity activity) {
	  activities.add(activity);
	 
	 }
	//遍历存放在list中的Activity并退出
	 public void onTerminate() { 
	  super.onTerminate();
	  for (Activity activity : activities) {
	   activity.finish();
	  }
	  android.os.Process.killProcess(android.os.Process.myPid()) ;
	 }
	 
}
