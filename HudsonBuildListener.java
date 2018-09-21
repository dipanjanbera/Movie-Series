package com;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class HudsonBuildListenerHelper { 
	
	static int _webRefreshRate = 400;
	
   public static void _getHudsonBuildStatus(String environment,String appName,HudsonBuildListener hudsonBuildListener, HudsonBuildListener.BuildListener hunsonBuildStatus,int refreshRate){
		 
	   if(refreshRate!=0){
		   _webRefreshRate = refreshRate;
	   }
	   _getHudsonBuildStatus(environment,appName,hudsonBuildListener,hunsonBuildStatus);
    }
	
    public static void _getHudsonBuildStatus(String environment,String appName,HudsonBuildListener hudsonBuildListener, HudsonBuildListener.BuildListener hunsonBuildStatus){
    	
    	 Document doc;
         boolean flag = true;
         LinkedList<String> completedJobList = new LinkedList<String>();
         Map<String,String> buildStatus = new HashMap<String,String>();
         boolean skipOnHudsonBuildStatedListener = false;
         int previousJobListCount = 0;
         try {
         	
         	while(flag){
         		
         		_setDefaultBuildStatus(buildStatus);
         		
         		 String url = hudsonBuildListener._getHudsonBuildURL(environment)+_getMaxBuildVersion(environment,hudsonBuildListener)+"/downstreambuildview/";
                 doc = Jsoup.connect(url).get();
                 if(doc!=null){
                	 if(!skipOnHudsonBuildStatedListener)
                	 hunsonBuildStatus._onHudsonBuildStated();
                	 skipOnHudsonBuildStatedListener = true;
                 }
                 
                
                 Element element = doc.getElementById("main-panel");
                 Elements tds = element.getElementsByTag("span");
                 for (Element td : tds) {
                 	if(td.text().indexOf("SUCCESS")>0){
                 		completedJobList.add(td.text().substring(0, td.text().indexOf(" ")));
                 		
                 	}
                 	
                 }
                 
                 int currentBuild = completedJobList.size()-previousJobListCount;  
                 if(currentBuild>0){
                	for(int index =completedJobList.size();index>previousJobListCount ; index--){
                		hunsonBuildStatus._currentJobFinished(completedJobList.get(index-1));
                	}
                 }
                
               
                 
                 Elements redPngs = doc.select("img[src$=red.png]");
                 if(redPngs.size()>0){
                	 hunsonBuildStatus._onHudsonBuildFail();
                	 flag=false;
                	 break;
                 }
                 
                 Elements gifs = doc.select("img[src$=grey_anime.gif]");
                 
                 
                 buildStatus.put(hudsonBuildListener.BUILD_SUCCESSFUL,""+completedJobList.size());
                 buildStatus.put(hudsonBuildListener.BUILD_REMAINING,""+gifs.size());
                 buildStatus.put(hudsonBuildListener.BUILD_FAILED,""+redPngs.size());
                 
                 hunsonBuildStatus._onHudsonBuildCompletionStatusChanged(buildStatus);
                 previousJobListCount = completedJobList.size();
                 completedJobList.clear();
                 
				if (appName.equals("PORTAL")) {
					if (gifs.size() == 1) {
						flag = false;
						hunsonBuildStatus._onHudsonBuildCompleted();
					}
				} else {
					if (gifs.size() == 0) {
						flag = false;
						hunsonBuildStatus._onHudsonBuildCompleted();
					}
				}
                
                 
                 try {
 					Thread.sleep(_webRefreshRate);
 				} catch (InterruptedException e) {
 					e.printStackTrace();
 				}
         	}
            
         	
         } catch (IOException | BuildEnvironmentNotFoundException e) {
             e.printStackTrace();
         }

    	
    }
    
    public static int _setCountToZero(int count){
    	count=0;
    	return count;
    }
    
    public static void _setDefaultBuildStatus(Map<String,String> buildStatus){
    	if(buildStatus!=null)buildStatus.clear();
    }
    
    public static boolean checkIfWebSiteUp(String environment,HudsonBuildListener hudsonBuildListener ){
    	 Document doc=null;
    	 String url;
    	 int count=0;
		try {
			url = hudsonBuildListener._getHudsonBuildURL(environment)+_getMaxBuildVersion(environment,hudsonBuildListener)+"/downstreambuildview/";
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				count++;
				try {
					if(count<10){
						Thread.sleep(1000);
						checkIfWebSiteUp(environment, hudsonBuildListener );
					}else{
						return false;
					}
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (BuildEnvironmentNotFoundException e) {
			
			e.printStackTrace();
			return false;
		}
		if(doc!=null){
			return true;
		}
		return false;
         
    } 
    
	public static int _getMaxBuildVersion(String environment,HudsonBuildListener hudsonBuildListener) {

		Document doc;
		int maxBuildVersion = 0;
		try {
			doc = Jsoup
					.connect(
							hudsonBuildListener._getHudsonBuildURL(environment))
					.get();
			Element table = doc.getElementById("buildHistory");
			Elements tds = table.getElementsByTag("td");
			Set<Integer> buiSet = new HashSet<Integer>();
			for (Element td : tds) {
				Element element = td.firstElementSibling();
				if (element != null) {
					String html = element.html();
					if (html.substring(html.indexOf("#") + 1) != null) {
						try {
							int a = Integer.parseInt(html.substring(html.indexOf("#") + 1));
							buiSet.add(a);
						} catch (NumberFormatException numberFormatException) {
							
						}

					}
				}

			}
			maxBuildVersion = Collections.max(buiSet);
			
		} catch (IOException | BuildEnvironmentNotFoundException e) {
			e.printStackTrace();
		}
		return maxBuildVersion;
	}
	
	
	
}

public class HudsonBuildListener{
	
	interface BuildListener{
		
		public void _onHudsonBuildCompleted();
		
		public void _onHudsonBuildFail();
		
		public void _onHudsonBuildStated();
		
		public void _currentJobFinished(String jobname);
		
		public void _onHudsonBuildCompletionStatusChanged(Map<String, String> buildStatus);
		
	}
	
	
	public String BUILD_SUCCESSFUL = "success";
	public String BUILD_REMAINING = "remaining";
	public String BUILD_FAILED = "failed";
	

	private BuildListener buildListener=null;
	
	
	public HudsonBuildListener() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HudsonBuildListener(BuildListener buildListener) {
		super();
		this.buildListener = buildListener;
	}

	public void setHudsonBuildListener(String environment, String appName,HudsonBuildListener hudsonBuildListener, BuildListener buildListener){
		this.buildListener = buildListener;
		if(HudsonBuildListenerHelper.checkIfWebSiteUp(environment, hudsonBuildListener)){
			HudsonBuildListenerHelper._getHudsonBuildStatus(environment,appName,hudsonBuildListener,this.buildListener);
		}else{
			System.out.println("Website not Responding");
		}
		
	}
	
	public void setHudsonBuildListener(String environment, String appName,HudsonBuildListener hudsonBuildListener, BuildListener buildListener,int refreshRate){
		this.buildListener = buildListener;
		if(HudsonBuildListenerHelper.checkIfWebSiteUp(environment, hudsonBuildListener)){
			HudsonBuildListenerHelper._getHudsonBuildStatus(environment,appName,hudsonBuildListener,this.buildListener,refreshRate);
		}else{
			System.out.println("Website not Responding");
		}
		
	}
	
	
	
	public String _getHudsonBuildURL(String environment) throws BuildEnvironmentNotFoundException{
		if(environment.equalsIgnoreCase("emer")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.EMERGENCY_EVN_BUILD;
		}
		if(environment.equalsIgnoreCase("trunk")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.TRUNK_EVN_BUILD;
		}
		if(environment.equalsIgnoreCase("uat1")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.UAT1_EVN_BUILD;
		}
		if(environment.equalsIgnoreCase("uat2")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.UAT2_EVN_BUILD;
		}
		if(environment.equalsIgnoreCase("uat3")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.UAT3_EVN_BUILD;
		}
		if(environment.equalsIgnoreCase("uat5")){
			return AppConstant.BUILD_URL_FOR_MAX_VER.UAT5_EVN_BUILD;
		}
		else{
			throw new BuildEnvironmentNotFoundException();
		}
	}
	
}

interface AppConstant{
	
	interface BUILD_URL_FOR_MAX_VER{
	String EMERGENCY_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/Emergency/job/framework_Emergency/";
	String TRUNK_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/Trunk/job/framework_trunk/";
	String UAT1_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/UAT1/job/framework_UAT1/";
	String UAT2_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/UAT2/job/framework_UAT2/";
	String UAT3_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/UAT3/job/framework_UAT3/";
	String UAT5_EVN_BUILD = "http://3.209.152.38:7504/hudson/view/UAT5/job/framework_UAT5/";
	}
}


