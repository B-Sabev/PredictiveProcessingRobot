import java.io.IOException;
import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;


public class Connector {
	
	// add the correct address and name of the robot
	public static final NXTInfo INFO = new NXTInfo(NXTCommFactory.BLUETOOTH,
												   "trainUnit",
											 	   "00165306559B");
	private NXTCommBluecove link;
	
	public Connector(){
		try {
			connect();
		} catch (NXTCommException e) {
			System.out.println("Opening Bluetooth connection was unsuccessful " +
					"- check address and name of the brink and it it is powered on");
			e.printStackTrace();
		}
	}
	
	private void connect() throws NXTCommException{
		link = new NXTCommBluecove();
		if(link.open(INFO))
			System.out.println("Connection successful!");
		else
			System.out.println("Connection failed, check NXT info");
	}
	
	public NXTCommBluecove getLink(){
		return link;
	}
	
	public void close(){
		try {
			link.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
