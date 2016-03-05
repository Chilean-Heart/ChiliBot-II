package com.team2576.lib;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Vector;
import com.team2576.lib.util.ChiliConstants;
import com.team2576.lib.util.Informable;
import com.team2576.robot.io.RobotOutput;
import com.team2576.robot.io.SensorInput;

import edu.wpi.first.wpilibj.Timer;

public class ChiliInformer {
	
	private java.util.Timer informerLoop;
	private int period = 25;
	
	private Map<String, Double> data;
	private Vector<Informable> informers;
	
	private SensorInput input;
	private RobotOutput output;
	private static ChiliInformer instance;
	
	private static class InformerTask extends TimerTask {
		private ChiliInformer informer;
		
		public InformerTask (ChiliInformer informer) {
			this.informer = informer;
		}
		
		@Override
		public void run() {
			this.informer.publish();
		}
		
	}
	
	public static ChiliInformer getInstance() {
		if (instance == null) {
			instance = new ChiliInformer();
		}
		return instance;
	}
	
	private ChiliInformer() {
		
		this.input = SensorInput.getInstance();
		this.output = RobotOutput.getInstance();
		
		this.data = Collections.synchronizedMap(new LinkedHashMap<String, Double>());
		
		this.informers = new Vector<Informable>();
		this.informers.add(input);
		this.informers.add(output);
		
		this.informerLoop = new java.util.Timer();		
		this.informerLoop.schedule(new InformerTask(this), 0L, (long) (this.period));
	}
	
	private void publish() {
		for (Informable informer : this.informers) {
			this.data.putAll(informer.inform());
		}
		this.data.put(ChiliConstants.iDateTimeStamp, Timer.getFPGATimestamp());
	}
	
	public Map<String, Double> getData() {
		return this.data;		
	}
	
}
