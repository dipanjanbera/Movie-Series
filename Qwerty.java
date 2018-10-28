package com;

import java.util.Map;

public class Qwerty {
	
	public static void main(String args[]){
		 String BUILD_COPY_PATH = "C://Users//1028657//GE_TCS_CONFIDENTIAL//1.IMPORTANT_DOCUMENTS//MIS//LOTUS_MIS_REPORT//";
		 String ENV = "emer";
		BuildStarter.startBuild("emer", new BuildListener() {
			
			@Override
			public void _on_BuildError() {
				System.out.println("Build Error");
				
			}
			
			@Override
			public void _onBuildStarted() {
				System.out.println("Build Started..for .. "+ENV);
				
			}
			
			@Override
			public void _onBuildEnded() {
				// TODO Auto-generated method stub
				
			}
		});
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HudsonBuildListener hudsonBuildListener= new HudsonBuildListener();
		hudsonBuildListener.setHudsonBuildListener(ENV,"XYZ", hudsonBuildListener, new HudsonBuildListener.BuildListener() {
			
			@Override
			public void _onHudsonBuildStated() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void _onHudsonBuildFail() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void _onHudsonBuildCompletionStatusChanged(
					Map<String, String> buildStatus) {
				//System.out.print(buildStatus.get(hudsonBuildListener.BUILD_SUCCESSFUL));
				System.out.print(".");
				
			}
			
			@Override
			public void _onHudsonBuildCompleted() {
				FileDownloder.startDownload(ENV,BUILD_COPY_PATH,1, new DownloadListener() {
					
					@Override
					public void _onDownloadStarted() {
						System.out.println("Download Started..");
						
					}
					
					@Override
					public void _onDownloadFailed() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void _onDownloadEnded() {
						System.out.println("Download Completed..");
						
					}
				});
				
			}
			
			@Override
			public void _currentJobFinished(String jobname) {
				System.out.println("Finished : "+jobname);
				
			}
		});
		
	}
	
}
