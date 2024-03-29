package com.team2576.lib;

/**
*
* @author Lucas
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


import edu.wpi.first.wpilibj.DriverStation;

public class Logger {
	
	private String directory = "/home/lvuser/logs";
	private String file_path;
	
	private Date logger_time;
	private SimpleDateFormat time_format;
	private BufferedWriter writer;
	private int index;

	private DriverStation driver;
	
	private static Logger instance;
		
	private String loggables = "time";
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	private Logger() {
		
		this.driver = DriverStation.getInstance();
		this.logger_time = new Date();
		this.time_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSSS");
		
		
		File dir = new File(this.directory);
		if(!dir.exists()) {
			dir.mkdir();
		}
		File[] files = new File(directory).listFiles();
		if(files != null) {
			for(File file : files) {
				if(file.isFile()) {
					try {
						int count = Integer.parseInt(file.getName().split("_")[0]);
						if (count > this.index) {
							this.index = count;
						}
					} catch (Exception err) {
						err.printStackTrace();
					}
				}
			}
		} else {
			this.index = 0;
		}
	}
	
	public boolean addLog() {
		boolean successful;
		try {
			//TODO get all buttons values in a array within DriverInput and print to writer with Arrays.toString(array);
			Map<String, Double> data = ChiliInformer.getInstance().getData();
			
			this.writer.write(String.format("%s", this.generateTimeStamp(this.logger_time) ));
			
			for (Map.Entry<String, Double> entry : data.entrySet()) {
				this.writer.write(String.format(", %.4f", entry.getValue()));
			}			
			this.writer.write(String.format(", %s", "\n"));
			this.writer.newLine();
			successful = true;
		} catch (IOException | NullPointerException err) {
			successful = false;
			err.printStackTrace();
		}
		return successful;
	}
	
	public void openLog() {
		try {
			this.file_path = this.generatePath();
			this.writer = new BufferedWriter(new FileWriter(this.file_path));
			
			Iterator<String> keys = ChiliInformer.getInstance().getData().keySet().iterator();
			
			while (keys.hasNext()) {
				loggables +=",";
				loggables += keys.next();
			}
			
			this.writer.write(this.loggables);
			this.writer.newLine();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
	
	public void closeLog() {
		if(this.writer != null) {
			try {
				this.writer.close();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	}
	
	private String generatePath() {		
		if(this.driver.isFMSAttached()) {
			return String.format("%s/%d_%s_%s_position%d_log.csv", this.directory, ++this.index,
					this.generateTimeStamp(this.logger_time), this.driver.getAlliance().toString(),
					this.driver.getLocation());
		} 
		return String.format("%s/%d_%s_log.csv", this.directory, ++this.index, this.generateTimeStamp(this.logger_time));
	}

	private String generateTimeStamp(Date time) {
		time = null;
		time = new Date();
		return (this.time_format.format(time)).toString();
	}
}
