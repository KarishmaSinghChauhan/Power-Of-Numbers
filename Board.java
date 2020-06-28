package game;

import java.util.Scanner;

public class Board {

    int[][] board;
	Scanner s = new Scanner(System.in);
	
	int boardsize = s.nextInt();
	Player player;

	public Board(Player player) {

	    this.player = player;
	    board = new int[boardsize][boardsize];

	    for(int i=0 ; i<boardsize ; i++) {
	    	for(int j=0 ; j<boardsize ; j++) {
	    		board[i][j] = 0;
	    	}
	    }
	}

	// Specifies status of Player.
	public int PLAYERWON = 1;
	public int INCOMPLETE = 2;
	public int GAME_OVER = 3;

	// Returns current status of Player according to Board.
	public int getStatus() {
	    for (int i=0 ; i<boardsize ; i++) {
	    	for (int j=0 ; j<boardsize ; j++) {
	    		// When player reaches 2048, player wins
	    		if (board[i][j] == 2048) {
                   	return PLAYERWON;
                }
	    	}
	    }
	    
	    // Check if gameover (if no more moves can be made).
	    int flag1 = 0; 
	    for (int i=0 ; i<boardsize ; i++) {
	    	for (int j=0 ; j<boardsize ; j++) {
	    		if (board[i][j] == 0 || (j != boardsize-1 && board[i][j] == board[i][j+1])) {
	    			flag1 = 1;
	    			break;
	    		}
	    	}
	    	
	    	if (flag1 == 0) {
	    		break;
	    	}
	    	else {
	    		if (i != boardsize-1) {
	    			flag1 = 0;
	    		}
	    	}
	    }
	    
	    int flag2 = 0; 
	    for (int j=0 ; j<boardsize ; j++) {
	    	for (int i=0 ; i<boardsize ; i++) {
	    		if (board[i][j] == 0 || (i != boardsize-1 && board[i][j] == board[i+1][j])) {
	    			flag2 = 1;
	    			break;
	    		}	
	    	}
	    	
	    	if (flag2 == 0) {
	    		break;
	    	}
	    	else {
	    		if (j != boardsize-1) {
	    			flag2 = 0;
	    		}
	    	}
	    }
	    
	    if(flag1 == 0 && flag2 == 0 ) {
	    	return GAME_OVER;
	    }
	    		

	    for(int i=0 ; i < boardsize ; i++) {
	    	for(int j=0 ; j < boardsize-1 ; j++) {
			    // Whenever 2048 is not made and there are still matching numbers in consecutive columns in same row, left Game is Incomplete. 
				return INCOMPLETE;
			}
		}
	    
	    for(int i=0 ; i<boardsize ; i++) {
	    	for(int j=0 ; j<boardsize-1 ; j++) {
			    // Whenever 2048 is not made and there are still matching numbers in consecutive rows in same column, left Game is Incomplete.
				if(board[j][i] == board[j+1][i]) {
					return INCOMPLETE;
				}
			}
		}		
	    		
		// Whenever 2048 is not made and there is space to make moves Game is Incomplete.
		for(int i=0 ; i<boardsize ; i++) {
			for(int j=0 ; j<boardsize ; j++) {
				if(board[i][j] == 0) {
					return INCOMPLETE;
				}
			}
		}
		
        // When all the above conditions fail Game gets Over.
		return GAME_OVER;
	}

	// Check for Valid Move.
	public void move(String position) throws InvalidMoveException {
		if(position.equals("left")) {
			leftMove();
		}
		else if(position.equals("right")) {
			rightMove();
		}
		else if(position.equals("up")) {
		    upMove();
		}
		else if(position.equals("down")) {
			downMove();
		}
		else {
			InvalidMoveException e = new InvalidMoveException();
			throw e;
		}	
	}

	// Player selects - left move.
	private void leftMove() throws InvalidMoveException {
	
		int flag = 1;
		
		for(int i = 0; i<= boardsize-1; i++) {
			for(int j = 0; j<= boardsize-1;j++) {
				if(board[i][j] == 0 || (j != boardsize-1 && board[i][j] == board[i][j+1])) {	
					flag = 0;
					break;
				}				
			}
			
			if(flag == 1) {
				// Move is invalid.
				InvalidMoveException e = new InvalidMoveException();
				throw e;
			}
			else {
				flag = 1;
			}	
		}
				
	    int k = 0; 

        // Check if there is a row in which either matching number is found or empty cell(i,k) before non empty cell(i,j)
        for(int i=0 ; i<boardsize ; i++) {
			for(int j=1 ; j<boardsize ; j++) {
				if((board[i][k] == board[i][j] ) || (board[i][k] == 0 && board[i][j]!=0)) {
					board[i][k] += board[i][j];
					board[i][j] = 0;
				}

				k++;
			}
			
			k = 0;
		}
	}

    // Player selects - right move.
    private void rightMove() throws InvalidMoveException {
    	
    	int flag = 1;
		
		for(int i = 0; i<= boardsize-1; i++) {
			for(int j = 0; j<= boardsize-1;j++) {
				if(board[i][j] == 0 || (j != boardsize-1 && board[i][j] == board[i][j+1])) {
					flag = 0;
					break;
				}
			}
			
			if(flag == 1) {
				// Move is invalid.
				InvalidMoveException e = new InvalidMoveException();
				throw e;
			}
			else {
				flag = 1;
			}
		}
			
        int k = boardsize-1;

        // Check if there is a row in which either matching number is found or empty cell(i,k) before non empty cell(i,j).
        for(int i=0 ; i<boardsize ; i++) {
			for(int j=k-1 ; j>=0 ;j-- ) {
				if(((board[i][k] == board[i][j])) || ((board[i][k] == 0) && (board[i][j] != 0))) {
					board[i][k] += board[i][j];
					board[i][j] = 0;
				}
				
				k--;
			}
			
			k = boardsize-1;
		}
	}

    // Player selects - up move.
    private void upMove() throws InvalidMoveException {
    
    	int flag = 1; 
		
		for(int i = 0; i<= boardsize-1; i++) {
			for(int j = 0; j<= boardsize-1;j++) {
				if(board[j][i] == 0 || (j != boardsize-1 && board[j][i] == board[j+1][i])) {
					flag = 0;
					break;
				}
			}
			
			if(flag == 1) {
				// Move is invalid.
				InvalidMoveException e = new InvalidMoveException();
				throw e;
			}
			else {
				flag = 1;
			}	
		}
			
		int k = 0;

        // Check if there is a column in which either matching number is found or empty cell(k,i) before non empty cell(j,i).
        for(int i=0 ; i<boardsize ; i++) {
			for(int j=1 ; j<boardsize ; j++) {
				if((board[k][i] == board[j][i]) || (board[k][i] == 0 && board[j][i]!=0)) {
					board[k][i] += board[j][i];
					board[j][i] = 0;
				}
				
				k++;
			}
			
			k = 0;
		}
	}

    // Player selects - left move.
	private void downMove() throws InvalidMoveException {
	
		int flag = 1;
		
		for(int i = 0; i<= boardsize-1; i++) {
			for(int j = 0; j<= boardsize-1;j++) {
				if(board[j][i] == 0 || (j != boardsize-1 && board[j][i] == board[j+1][i])) {
					flag = 0;
					break;
				}
			}
			
			if(flag == 1) {
				// Move is invalid.
				InvalidMoveException e = new InvalidMoveException();
				throw e;
			}
			else {
				flag = 1;
			}
		}
		
		int k=boardsize-1;

		// Check if there is a column in which either matching number is found or empty cell(k,i) before non empty cell(j,i).
		for(int i=0 ; i<boardsize ; i++) {
			for(int j=k-1 ; j>=0 ; j--) {
				if((board[k][i] == board[j][i] ) || (board[k][i] == 0 && board[j][i]!=0)) {
					board[k][i] += board[j][i];
					board[j][i] = 0;
				}

				k--;
			}
			
			k = boardsize-1;
		}
	}

	// To print Board before each move.
	public void printboard() {

	    int i,j,no;

	    do {
			no = Math.random() > 0.1 ? 2 : 4;
			i =  ((int)((Math.random() )*10)) %boardsize; 
			j =  ((int)((Math.random() )*10)) %boardsize; 
		} while(board[i][j] != 0);

	    board[i][j] = no;

	    for( i=0 ; i<boardsize ; i++) {
			System.out.println();
			for(j = 0 ; j<boardsize ; j++) {
				System.out.print(" | "+ board[i][j] +" | ");
			}
			System.out.println();
	    }
	}
}
