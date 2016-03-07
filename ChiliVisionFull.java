package com.team2576.lib;

import java.util.TimerTask;

import com.ni.vision.NIVision.Image;
import com.team2576.lib.util.ChiliConstants;
import com.team2576.robot.io.SensorInput;
import com.team2576.robot.io.SensorInput.Cameras;

import edu.wpi.first.wpilibj.CameraServer;

@SuppressWarnings("unused")
public class ChiliVisionFull {
	
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
		private USBCamera camera;
		//private SensorInput sensor;
		//private Cameras cam;
		private boolean is_ready;
		private Image frame;
		private int delay, period;
		private boolean abort_vision;
		
		/*
		private VisionCamera (Cameras descriptor, int delay, int period) {
			this.sensor = SensorInput.getInstance();
			this.cam = descriptor;
			this.delay = delay;
			this.period = period;
			this.visionLoop = new java.util.Timer();		
			this.visionLoop.schedule(new VisionTask(this), (long) (this.delay), (long) (this.period));
			this.is_ready = false;
		}
		*/
		
		private VisionCamera (USBCamera cam, int delay, int period) {
			this.camera = cam;
			this.delay = delay;
			this.period = period;
			this.visionLoop = new java.util.Timer();		
			this.visionLoop.schedule(new VisionTask(this), (long) (this.delay), (long) (this.period));
			this.is_ready = false;
			this.abort_vision = false;
		}
		
		private Image getImage() {
		
			Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

			try {
				this.camera.getImage(image);
			} catch (VisionException ve) {
				ve.printStackTrace();
				this.abort_vision = true;
			}
			return image;
		}

		private void view() {
			if (this.abort_vision) {
				this.visionLoop.cancel();
				
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				this.visionLoop.schedule(new VisionTask(this), (long) (this.delay), (long) (this.period));
				this.abort_vision = false;
			}
			
			this.frame = this.getImage();
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
		private ChiliVisionFull vision;
		
		public ChiliVisionTask (ChiliVisionFull vision) {
			this.vision = vision;
		}
		
		@Override
		public void run() {
			this.vision.serve();
		}
	}
	
	private VisionCamera camThread;
	private java.util.Timer visiontask;
	private static ChiliVisionFull instance;
	
	private USBCamera cam;
	
	public static ChiliVisionFull getInstance() {
		if (instance == null) {
			instance = new ChiliVisionFull();
		}
		return instance;
	}
	
	private ChiliVisionFull() {
		
		//puede que sea "cam1"
		this.cam = USBCamera("cam0");
		this.camThread = new VisionCamera(Cameras.LEFT_CAM, 0, 66);
		
		this.visiontask = new java.util.Timer();
		this.visiontask.schedule(new ChiliVisionTask(this), 0L, (long) 10);
		
	}
	
	public synchronized void serve() {
		while (true) {
			
			Image img;
			
			img = camThread.isReady() ? camThread.get() : null;

			try {
				if (img != null) {
					CameraServer.getInstance().setImage(img);
					Thread.sleep(5);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}