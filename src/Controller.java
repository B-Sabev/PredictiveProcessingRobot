import java.io.IOException;

import lejos.nxt.remote.InputValues;
import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.NXTProtocol;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTCommBluecove;


public class Controller {
	private NXTCommand cmd;
	private RemoteMotor[] motors;
	private int sensorPort;
	public static final int DEFAULT_SPEED = 300;
	
	public Controller(NXTCommBluecove link){
		this.cmd = new NXTCommand(link);
		this.motors = new RemoteMotor[2];
		this.sensorPort = -1;
	}
	
	public void initMotors(int rightMotorPort, int leftMotorPort){
		this.motors[0] = new RemoteMotor(cmd, rightMotorPort);
		this.motors[1] = new RemoteMotor(cmd, leftMotorPort);
		for(RemoteMotor m : motors){
			m.setSpeed(DEFAULT_SPEED);
		}
	}
	
	public void rotate(int[] rotation){
		for(int i=0; i<motors.length; i++){
			motors[i].rotate(rotation[i], true);
		}
	}
	
	public void updateSpeed(int[] speeds){
		for(int i=0; i<motors.length; i++){
			motors[i].setSpeed(speeds[i]);
			motors[i].forward();
		}
	}
	
	public void initLightSensor(int lightSensorPort){
		try {
			cmd.setInputMode(lightSensorPort, NXTProtocol.LIGHT_ACTIVE, NXTProtocol.RAWMODE);
			sensorPort = lightSensorPort;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed attempt to initialize lightSensor, " +
							   "check if the port is correct");
			e.printStackTrace();
		}
	}
	
	public InputValues readSensorVal(){
		try {
			return cmd.getInputValues(sensorPort);
		} catch (IOException e) {
			System.out.println("Attempt to read sensor values, most likely incorrect port");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void close(){
		for(RemoteMotor m : motors){
			m.stop();
		}
		try {
			cmd.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
