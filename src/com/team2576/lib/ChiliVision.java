package com.team2576.lib;

import java.util.TimerTask;

import com.ni.vision.NIVision.Image;
import com.team2576.lib.util.ChiliConstants;
import com.team2576.robot.io.SensorInput;
import com.team2576.robot.io.SensorInput.Cameras;

import edu.wpi.first.wpilibj.CameraServer;

@SuppressWarnings("unused")
public class ChiliVision {
	
	private class VisionTask extends TimerTask {
		private VisionCamera visor;
		
		public VisionTask (VisionCamera visor) {
			this.visor = visor;
		}
		
		@Override
		public void run() {
			this.visor.view();
		}
	}
	
	private class VisionCamera {
		
		private java.util.Timer visionLoop;
		private SensorInput sensor;
		private Cameras cam;
		private boolean is_ready;
		private Image frame;
		private int delay, period;
		
		private VisionCamera (Cameras descriptor, int delay, int period) {
			this.sensor = SensorInput.getInstance();
			this.cam = descriptor;
			this.delay = delay;
			this.period = period;
			this.visionLoop = new java.util.Timer();		
			this.visionLoop.schedule(new VisionTask(this), (long) (this.delay), (long) (this.period));
			this.is_ready = false;
		}
		
		private void view() {
			if (sensor.abort_vision) {
				this.visionLoop.cancel();
				
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				this.visionLoop.schedule(new VisionTask(this), (long) (this.delay), (long) (this.period));
				sensor.abort_vision = false;
			}
			
			this.frame = sensor.getImage(cam);
			this.is_ready = true;
		}
		
		public synchronized boolean isReady() {
			return this.is_ready;
		}
		
		public synchronized Image get() {
			this.is_ready = false;
			return this.frame;
		}
		
	}
	
	private class ChiliVisionTask extends TimerTask {
		private ChiliVision vision;
		
		public ChiliVisionTask (ChiliVision vision) {
			this.vision = vision;
		}
		
		@Override
		public void run() {
			this.vision.serve();
		}
	}
	
	private VisionCamera camLeft, camMid, camRight;
	private java.util.Timer visiontask;
	private static ChiliVision instance;
	
	public static ChiliVision getInstance() {
		if (instance == null) {
			instance = new ChiliVision();
		}
		return instance;
	}
	
	private ChiliVision() {
		camLeft = new VisionCamera(Cameras.LEFT_CAM, 0, 66);
		//camMid = new VisionCamera(Cameras.MID_CAM, 0, 33);
		//camRight = new VisionCamera(Cameras.RIGHT_CAM, 15, 66);
		
		this.visiontask = new java.util.Timer();
		this.visiontask.schedule(new ChiliVisionTask(this), 0L, (long) 10);
		
	}
	
	public synchronized void serve() {
		while (true) {
			
			Image l, m, r;
			
			l = camLeft.isReady() ? camLeft.get() : null;
			//m = camMid.isReady() && ChiliConstants.kCustomVisionDashboard ? camMid.get() : null;
			//r = camRight.isReady() && ChiliConstants.kCustomVisionDashboard ? camRight.get() : null;
			
			try {
				if (l != null) {
					CameraServer.getInstance().setImage(l);
					Thread.sleep(5);
				}
				/*
				if (m != null) {
					CameraServer.getInstance().setImage(m);
					Thread.sleep(5);
				}
				
				if (r != null) {
					CameraServer.getInstance().setImage(r);
					Thread.sleep(5);
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
