package com;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BuildStarter {

	public static void startBuild(String env,BuildListener buildListener) {
		HttpURLConnection con = null;
		URL urlObj;
		try {
			urlObj = new URL(_getHudsonBuildURL(env));
	
				con = (HttpURLConnection) urlObj.openConnection();
		
			int resp = con.getResponseCode();
			if(resp == 200){
				buildListener._onBuildStarted();
			}
			if (resp != 200) {
				System.out.println("Hudson returned error response code " + resp);
				buildListener._on_BuildError();
			}
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (BuildEnvironmentNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public static String _getHudsonBuildURL(String environment)
			throws BuildEnvironmentNotFoundException {
		if (environment.equalsIgnoreCase("emer")) {
			return BuildEnv.emer;
		}
		if (environment.equalsIgnoreCase("trunk")) {
			return BuildEnv.trunk;
		}
		if (environment.equalsIgnoreCase("uat1")) {
			return BuildEnv.UAT1;
		}
		if (environment.equalsIgnoreCase("uat2")) {
			return BuildEnv.UAT2;
		}
		if (environment.equalsIgnoreCase("uat3")) {
			return BuildEnv.UAT3;
		}
		if (environment.equalsIgnoreCase("uat5")) {
			return BuildEnv.UAT5;
		} else {
			throw new BuildEnvironmentNotFoundException();
		}
	}
}

interface BuildEnv {
	String emer = "http://3.209.152.38:7504/hudson/view/Emergency/job/framework_Emergency/build?delay=0sec";
	String trunk = "http://3.209.152.38:7504/hudson/view/Trunk/job/framework_trunk/build?delay=0sec";
	String UAT1 = "http://3.209.152.38:7504/hudson/view/UAT1/job/framework_UAT1/build?delay=0sec";
	String UAT2 = "http://3.209.152.38:7504/hudson/view/UAT2/job/framework_UAT2/build?delay=0sec";
	String UAT3 = "http://3.209.152.38:7504/hudson/view/UAT3/job/framework_UAT3/build?delay=0sec";
	String UAT5 = "http://3.209.152.38:7504/hudson/view/UAT5/job/framework_UAT5/build?delay=0sec";

}

interface BuildListener{
	
	void _onBuildStarted();
	void _onBuildEnded();
	void _on_BuildError();
	
}
