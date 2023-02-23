import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
	private Game game;
	private ServerSocket server;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	public Server(){
		
	}
	
	public void run(){
		try {
			server = new ServerSocket(7777);
			client = server.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.println("WELCOME");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String clientInput = "";
			while(!clientInput.equals("LOSER") && !clientInput.equals("WINNER") && !clientInput.equals("TIE")){
				try {
					while(in.ready()){
						clientInput = in.readLine();
						parseCommand(clientInput);
					}
				} catch (IOException e) {
					System.out.println("Error reading from client");
					e.printStackTrace();
				}
				
			}
	}
		
		/*//Client side
		    try {
			Socket server = new Socket(InetAddress.getLocalHost() , 7777);
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			System.out.println(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    
    private void parseCommand(String socketCommand){
        String[] command = socketCommand.split(" ");
        if(command.length == 1){
           if(command[0].equals("WELCOME"))
                System.out.println("WELCOME");
           else if(command[0].equals("READY"))
                System.out.println("READY");
           else if(command[0].equals("OK"))
                out.println("OK"); //Echo for now
           else if(command[0].equals("ILLEGAL"))
                System.out.println("ILLEGAL MOVE");
           else if(command[0].equals("TIME"))
                System.out.println("TIME ERROR");
           else
        	   System.out.println("Unrecognized command");
           
        }
        else if(command[0].equals("INFO")){
        	ArrayList<Integer> inputList = new ArrayList<Integer>();
        	int houses, seeds, player;
        	if(command[5].equals("S")){
        		houses = Integer.parseInt(command[1]);
        		seeds = Integer.parseInt(command[2]);
        		for(int i = 0; i < houses*2; i++){
        			inputList.add(seeds);
        		}
        	}
        	else if(command[5].equals("R")){
        		houses = Integer.parseInt(command[1]);
        		seeds = Integer.parseInt(command[2]);
        		for(int i = 0; i < houses; i++){
        			inputList.add(Integer.parseInt(command[i+6]));
        		}
        		inputList.addAll(inputList);
        	}
        	else{
        		
        	}
        	if(command[4].equals("F")){
        		player = 1;
        	}
        	else{
        		player = 2;
        	}
        	game = new Game(Integer.parseInt(command[1]), inputList, player);
        }
        else{
        	if(command[0].equals("P")){
        		//run pie rule switch
        	}
        	else{
        		for(int i = 0; i < command.length; i++){
        			game.move(Integer.parseInt(command[i]));
        		}
        	}
        }
    }
}
