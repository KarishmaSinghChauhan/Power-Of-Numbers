package game;

import java.util.Scanner;

public class PowerOfNumbersGame {

    // To start the game. 
	public static void main(String[] args)  {
		start();
	}

	// To take the Player's input.
	public static Player getPlayer(){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Name : ");
		String str = s.nextLine();
		Player p = new Player(str);

		return p;
	}
	
	public static void start()  {

	    Player p = getPlayer(); 
	    Scanner s = new Scanner(System.in);
	    System.out.println("Enter board size:"); 
	    Board b = new Board(p);
	    b.printboard();

	    // To retrieve status of Player according to Board.
	    while(b.getStatus() == b.INCOMPLETE){

		    // Enter desired movement.
		    System.out.println("Enter the desired movement (left, right, up, down).");
		    String pos = s.next();

		    try{
			    b.move(pos);
			    b.printboard();
		    }
		    catch(InvalidMoveException e) {
			    System.out.println("Invalid Move !! ");
		    }
	    }
	    
	    // Check status of board for final result.
	    if(b.getStatus() == b.PLAYERWON) {
		    System.out.println("Player Won");
	    }
	    else {
		    System.out.println("GAMEOVER");
	    }
	}
}
