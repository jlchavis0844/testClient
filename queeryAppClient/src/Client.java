

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
//import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client {
	private static Socket sock;
	private static String temp;

	public static void main(String[] args) {
		
		InetAddress host;
		for(String s: args){
			System.out.print(s + " ");
		}
		System.out.println();
		try {
			host = InetAddress.getLocalHost();
			sock = new Socket(host, 4910);
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			Random rand = new Random();
			
			
			switch(args[0]){
			case "addRandUser":
				if(args.length != 1){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				addRandUser(out, rand);
				break;
				
			case "setPSlider":
				setPSlider(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "updatePSlider":
				updatePSlider(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "setLocation":
				setLocation(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "updateLocation":
				updateLocation(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "kill":
				new Thread(){
					public void run(){
						out.println("kill");
						try {
							System.out.println("starting sleep");
							sleep(2000);
							System.out.println("ending sleep");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							sock.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							sock = new Socket(host, 4910);
							PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
							BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						out.println("kill");
					}
				}.start();
				break;
				
			case "setSSlider":
				setSSlider(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "updateSSlider":
				updateSSlider(out, rand, args[1]);
				if(args.length != 2){
					System.out.println("wrong params");
					sock.close();
					System.exit(1);//kill
				}
				break;
				
			case "makeNewUser":
				int tries = Integer.valueOf(args[1]);
				for(int i = 0; i < tries; i++){
					makeNewUsers(out, rand);
					System.out.println("adding user " + i);
				}
				break;
			
			case "login":
				out.println("login, " + args[1] + ", " + args[2]);
				break;
				
			case "text":
				out.println(args[1]);
				break;
				
			default:
				System.out.println("Wrong params, usually \"command userName\"");
				sock.close();
				out.close();
				in.close();
				System.exit(1);//kill
			}
			//System.out.println(in.readLine());
			while((temp = in.readLine()) != null && !in.ready()){;
				System.out.println(temp);
				//out.close();
				//in.close();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String addRandUser(PrintWriter out, Random rand){
		int userNum = rand.nextInt(99999);
		String command = "addUser, " + "user" + userNum + ", password" + userNum + ", " +
				"First" + userNum + ", " + "Last" + userNum + ", user" + userNum + "@mail.com" + 
				", " + (rand.nextInt(90)+10);
		System.out.println(command);
		out.println(command);
		return "user" + userNum;
	}
	public static void setPSlider(PrintWriter out, Random rand, String userName){
		String command = "setPSlider, " + userName + ", " + rand.nextInt(20) + ", " + rand.nextInt(20)
						+ ", " + rand.nextInt(20);
		out.println(command);
		System.out.println(command);
	}
	
	public static void updatePSlider(PrintWriter out, Random rand, String userName){
		String command = "updatePSlider, " + userName + ", " + rand.nextInt(20) + ", " + rand.nextInt(20)
						+ ", " + rand.nextInt(20);
		out.println(command);
		System.out.println(command);
	}
	
	public static void setLocation(PrintWriter out, Random rand, String userName){
		Double longitude = new Double((33 + rand.nextDouble()));
		Double longRnd = new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double latitude = new Double(-118 - rand.nextDouble());
		Double latRnd = new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		
		String command = "setLocation, " + userName + ", " + longRnd + ", " + latRnd;
		out.println(command);
		System.out.println(command);
	}
	
	public static void updateLocation(PrintWriter out, Random rand, String userName){
		Double longitude = new Double((33 + rand.nextDouble()));
		Double longRnd = new BigDecimal(longitude).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		Double latitude = new Double(-118 - rand.nextDouble());
		Double latRnd = new BigDecimal(latitude).setScale(6, BigDecimal.ROUND_HALF_DOWN).doubleValue();
		
		String command = "updateLocation, " + userName + ", " + longRnd + ", " + latRnd;
		out.println(command);
		System.out.println(command);
	}
	
	public static void setSSlider(PrintWriter out, Random rand, String userName){
		int gMin = rand.nextInt(20);
		int gMax = gMin + rand.nextInt(20-gMin);
		int eMin = rand.nextInt(20);
		int eMax = eMin + rand.nextInt(20-eMin);
		int oMin = rand.nextInt(20);
		int oMax = oMin + rand.nextInt(20-oMin);
		
		String command = "setSSlider, " + userName + ", " + gMin + ", " + gMax + ", " +
						  eMin + ", " + eMax + ", " + oMin + ", " + oMax;
		out.println(command);
		System.out.println(command); 
	}
	
	public static void updateSSlider(PrintWriter out, Random rand, String userName){
		int gMin = rand.nextInt(20);
		int gMax = gMin + rand.nextInt(20-gMin);
		int eMin = rand.nextInt(20);
		int eMax = eMin + rand.nextInt(20-eMin);
		int oMin = rand.nextInt(20);
		int oMax = oMin + rand.nextInt(20-oMin);
		
		String command = "updateSSlider, " + userName + ", " + gMin + ", " + gMax + ", " +
						  eMin + ", " + eMax + ", " + oMin + ", " + oMax;
		out.println(command);
		System.out.println(command); 
	}
	
	public static void makeNewUsers(PrintWriter out, Random rand){
		String userName = null;

		userName = addRandUser(out, rand);
		setLocation(out, rand, userName);
		setPSlider(out, rand, userName);
		setSSlider(out, rand, userName);
	}
	
}

