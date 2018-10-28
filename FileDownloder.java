package com;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class FileDownloder {
    
    public static void startDownload(String env,String buildCopyPath,int DOWNLOAD_MODE,DownloadListener downloadListener) {
    	String PORTAL_URL=null;
    	String LOSAPP_URL=null;
    	
		try {
			PORTAL_URL = getPortalURL(env);
		
		    LOSAPP_URL = getBaseProductURL(env);
		} catch (BuildEnvironmentNotFoundException e) {
			downloadListener._onDownloadFailed();
			e.printStackTrace();
		}
        String BUILD_COPY_PATH=buildCopyPath;
        String folder_path = createFolder(BUILD_COPY_PATH);
    	switch (DOWNLOAD_MODE) {
		case 1:
			downloadUsingStream(PORTAL_URL,folder_path+"//"+getFileNameFromURL(PORTAL_URL),downloadListener);
			break;
		case 2:
			downloadUsingStream(LOSAPP_URL,folder_path+"//"+getFileNameFromURL(LOSAPP_URL),downloadListener);
			break;
			
		case 3:
			downloadUsingStream(PORTAL_URL,folder_path+"//"+getFileNameFromURL(PORTAL_URL),downloadListener);
			downloadUsingStream(LOSAPP_URL,folder_path+"//"+getFileNameFromURL(LOSAPP_URL),downloadListener);
			break;
			
		default:
			break;
		}
    }
    
    public static void startDownload(String env,int DOWNLOAD_MODE,String PORTAL_URL,String LOSAPP_URL,String BUILD_COPY_PATH,DownloadListener downloadListener){
        String folder_path = createFolder(BUILD_COPY_PATH);
    	switch (DOWNLOAD_MODE) {
		case 1:
			downloadUsingStream(PORTAL_URL,folder_path+"//"+getFileNameFromURL(PORTAL_URL),downloadListener);
			break;
		case 2:
			downloadUsingStream(LOSAPP_URL,folder_path+"//"+getFileNameFromURL(LOSAPP_URL),downloadListener);
			break;
			
		case 3:
			downloadUsingStream(PORTAL_URL,folder_path+"//"+getFileNameFromURL(PORTAL_URL),downloadListener);
			downloadUsingStream(LOSAPP_URL,folder_path+"//"+getFileNameFromURL(LOSAPP_URL),downloadListener);
			break;
			
		default:
			break;
		}
    }
    
    private static String getFileNameFromURL(String url){
    	return url.substring(url.lastIndexOf("/")+1);
    }

    private static void downloadUsingStream(String urlStr, String file,DownloadListener downloadListener){
        URL url;
		try {
			url = new URL(urlStr);
		
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        downloadListener._onDownloadStarted();
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
		} catch (MalformedURLException e) {
			downloadListener._onDownloadFailed();
			e.printStackTrace();
		} catch (IOException e) {
			downloadListener._onDownloadFailed();
		}
        downloadListener._onDownloadEnded();
    }

    private static void listAllFiles(String filePath){
    	File folder = new File(filePath);
    	File[] listOfFiles = folder.listFiles();

    	    for (int i = 0; i < listOfFiles.length; i++) {
    	      if (listOfFiles[i].isFile()) {
    	        
    	      } else if (listOfFiles[i].isDirectory()) {
    	        System.out.println("Directory " + listOfFiles[i].getName());
    	      }
    	    }
    	
    }
    
    private static String createFolder(String path){
    	File folder = new File(path+"BUILD_"+getMaxBuildVersion(path));
    	if (!folder.exists()) {
             if (folder.mkdir()) {
                 System.out.println("Directory is created! "+folder.getAbsolutePath());
             } else {
                 System.out.println("Failed to create directory!");
             }
         }
    	return folder.getAbsolutePath();
    }
    
    private static int getMaxBuildVersion(String path){
    	ArrayList<Integer> arrList = new ArrayList<Integer>();
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isDirectory()) {
    			if(listOfFiles[i].getName().indexOf("BUILD_")>-1){
    				arrList.add(Integer.parseInt(listOfFiles[i].getName().substring(listOfFiles[i].getName().indexOf("_")+1)));
    			}
    	        
    	      }
    	  }
    	return Collections.max(arrList)+1;
    }
    

    public static String getPortalURL(String environment) throws BuildEnvironmentNotFoundException{
    	if (environment.equalsIgnoreCase("emer")) {
    		return PORTALURL.EMER_PORTAL_URL;
    	}
    	if (environment.equalsIgnoreCase("trunk")) {
    		return PORTALURL.TRUNK_PORTAL_URL;
    	}
    	if (environment.equalsIgnoreCase("uat1")) {
    		return PORTALURL.UAT1_PORTAL_URL;
    	}
    	if (environment.equalsIgnoreCase("uat2")) {
    		return PORTALURL.UAT2_PORTAL_URL;
    	}
    	if (environment.equalsIgnoreCase("uat3")) {
    		return PORTALURL.UAT3_PORTAL_URL;
    	}
    	if (environment.equalsIgnoreCase("uat5")) {
    		return PORTALURL.UAT5_PORTAL_URL;
    	} else {
    		throw new BuildEnvironmentNotFoundException();
    	}
    }


public static String getBaseProductURL(String environment) throws BuildEnvironmentNotFoundException{
	if (environment.equalsIgnoreCase("emer")) {
		return LOS_WEB_APP_URL.EMER_URL;
	}
	if (environment.equalsIgnoreCase("trunk")) {
		return LOS_WEB_APP_URL.TRUNK_URL;
	}
	if (environment.equalsIgnoreCase("uat1")) {
		return LOS_WEB_APP_URL.UAT1_URL;
	}
	if (environment.equalsIgnoreCase("uat2")) {
		return LOS_WEB_APP_URL.UAT2_URL;
	}
	if (environment.equalsIgnoreCase("uat3")) {
		return LOS_WEB_APP_URL.UAT3_URL;
	}
	if (environment.equalsIgnoreCase("uat5")) {
		return LOS_WEB_APP_URL.UAT5_URL;
	} else {
		throw new BuildEnvironmentNotFoundException();
	}
}
}

	
interface PORTALURL{
	String EMER_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/Emergency/job/OriginationsPortal_Emergency/ws/dist/OriginationsPortal.war";
	String UAT1_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/UAT1/job/OriginationsPortal_UAT1/ws/dist/OriginationsPortal.war";
	String UAT2_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/UAT2/job/OriginationsPortal_UAT2/ws/dist/OriginationsPortal.war";
	String UAT3_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/UAT3/job/OriginationsPortal_UAT3/ws/dist/OriginationsPortal.war";
	String UAT5_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/UAT5/job/OriginationsPortal_UAT5/ws/dist/OriginationsPortal.war";
	String TRUNK_PORTAL_URL = "http://3.209.152.38:7504/hudson/view/Trunk/job/OriginationsPortal_Trunk/ws/dist/OriginationsPortal.war";
}

interface LOS_WEB_APP_URL{
	String EMER_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_Emergency/ws/dist/LOSAppEar.ear";
	String UAT1_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_UAT1/ws/dist/LOSAppEar.ear";
	String UAT2_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_UAT2/ws/dist/LOSAppEar.ear";
	String UAT3_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_UAT3/ws/dist/LOSAppEar.ear";
	String UAT5_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_UAT5/ws/dist/LOSAppEar.ear";
	String TRUNK_URL = "http://3.209.152.38:7504/hudson/job/LOSWebApp_Trunk/ws/dist/LOSAppEar.ear";
}




interface DownloadListener{
	public void _onDownloadStarted();
	public void _onDownloadEnded();
	public void _onDownloadFailed();
}