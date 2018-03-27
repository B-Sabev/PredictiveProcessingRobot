import java.io.IOException;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.remote.InputValues;
import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.NXTProtocol;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;


public class PCmain {

	/**
	 * @param args
	 * @throws NXTCommException 
	 * @throws IOException 
	 */
	
	/*
	 * TODO make a github repository and put your code there
	 * 		decide on sensor Type, Mode, and Reading and a place of the light sensor\
	 * 		update readSensorVal to give you the appropriate int
	 * 		test the behavior of motors
	 * 		close the sensor on exit
	 * 		make a method for calibration of the light sensor
	 * 
	 * 		check how to access the toolbox and how to use it within Java
	 */
	
	/*
	 * Sensors Type - 	LIGHT_ACTIVE 
	 * 				 vs LIGHT_INACTIVE - should be light inactive, detecting the light present in the env
	 * Sensor Mode - NXTProtocol.RAWMODE 
	 * 			  vs MODE_TRANSITIONCNT = 0x40 
	 * 			  vs MODE_PCTFULLSCALE = 0x80
	 * Reading - 	calibratedValue 
	 * 			vs normalizedADValue
	 * 			vs rawADValue
	 * 			vs scaledValue
	 */
	
	/*
	 * Is there a difference between setting to different speed and rotating for the same time (NXT.rotate)
	 * and rotating different degree (NXT.updateSpeed) ???
	 */
	public static void main(String[] args){
		
		Connector conn = new Connector();
		Controller NXT = new Controller(conn.getLink());
		NXT.initMotors(0, 2); // Right motor at A, Left motor at B
		NXT.initLightSensor(0); // Light sensor at 1
		
		
		
		InputValues in;
		
		
		
		
		Random rand = new Random();
		
		while(true){
			
			NXT.rotate(new int[]{rand.nextInt(300), rand.nextInt(300)});
			NXT.updateSpeed(new int[]{rand.nextInt(300), rand.nextInt(300)});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(Button.ENTER.isDown())
				break;
			
			
			in = NXT.readSensorVal();
			System.out.println("Calibrated value = " + in.calibratedValue);
			System.out.println("NormalizedADValue = " + in.normalizedADValue);
			System.out.println("Raw AD Value = " + in.rawADValue);
			System.out.println("Scaled value = " + in.scaledValue);
			System.out.println();
		}
		
		// stop all commands, disconnect from bluetooth
		NXT.close();
		conn.close();
	
	}

}
