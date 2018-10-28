package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Test {
	public static void main(String args[]){
		puttyConnect();
	}
	
	
	public static void puttyConnect(){
		System.out.println("trying to connect .... ");
		JSch jsch = new JSch();
		String host = "3.235.241.160" ;
		try {
			Session session = jsch.getSession("s2058316", host, 22);
			
			 session.setConfig("StrictHostKeyChecking", "no");
			 session.setPassword("soas@2011");
			 session.connect();
			 
			 ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
			 try {
				InputStream in = channelExec.getInputStream();
				channelExec.setCommand("cd /glassfish/SUN1/glassfish/nodes/localhost-DOMAIN1/LOSINS1/logs;tail -f server.log");
				channelExec.connect();
				
				
				byte[] tmp = new byte[1024];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);
						if (i < 0)
							break;
						System.out.print(new String(tmp, 0, i));
					}
				}
				
				
				/* BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				 while(!channelExec.isClosed()) {
				    	System.out.println(reader.readLine());
				    	try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }*/
			} catch (IOException e) {
				
				e.printStackTrace();
			}  
		} catch (JSchException e) {
			
			e.printStackTrace();
		}
	}
}
