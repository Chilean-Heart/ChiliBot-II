package com.team2576.lib;

import java.util.Vector;

import com.team2576.lib.util.ChiliConstants;

public class ChiliServerManager {
	
	private Vector<ChiliServers> servers;
	private static ChiliServerManager instance;
	
	private ChiliServerManager () {
		this.servers = new Vector<ChiliServers>(ChiliConstants.kServers);
		
	}
	
	public static ChiliServerManager getInstance() {
		if (instance == null) {
			instance = new ChiliServerManager();
		}
		return instance;
	}
	
	public void initializeServers() {
		for(int i = 0 ; i < this.servers.size(); i++) {
			
			ChiliServers server = this.servers.elementAt(i);
			
			Thread serverThread = new Thread(new Runnable() {
				public void run() {
					try {
						server.load();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			serverThread.setPriority(Thread.MIN_PRIORITY);
			serverThread.setDaemon(true);
		    serverThread.start();
		}
	}
	
	public void addServer(ChiliServers server) {
		this.servers.add(server);		
	}

}
