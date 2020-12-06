package TicTacToeWithAi;


public class AiOp {

	int rowMain, colMain;
	Seed board[][] = new Seed[3][3];
	public class Move {
		int row, col;
	}
	public AiOp(Seed[][] boardGet) {
		 Move a = findBestMove(boardGet);
		 rowMain= a.row;
		 colMain = a.col;
	}
	public Move findBestMove(Seed[][] b) {
		int bestVal = -100;
		Move bestMove = new Move();
		bestMove.row = -1;
		bestMove.col = -1;
		for( int i = 0; i < 3; i++) {
			for ( int j = 0; j < 3; j++) {
				if ( b [i][j] == Seed.EMPTY) {
					b [i][j] = Seed.AALU;
					int moveVal = minimax ( b, 0 , false );
					b [i][j] = Seed.EMPTY;
					if ( moveVal > bestVal ) {
						bestMove.row = i; 
						bestMove.col = j; 
						bestVal = moveVal; 
					}
				}
			}
		}
		System.out.printf("The value of the best Move " + "is : %d\n\n", bestVal);
		return bestMove;
	}
	static int minimax( Seed boardb [][], int depth, boolean isMax) {
		int score = evaluate ( boardb , depth );
		if ( score == 10 || ( score < 10 && score > 0 )   ) {
			return score;
		}
		if ( score == -10 || ( score > -10 && score < 0) ) {
			return score;
		}
		if ( isMoveLeft ( boardb ) == false ) {
			return 0;
		}
		if ( isMax ) {
			int best = -100;
			for( int i = 0; i < 3; i++) {
				for ( int j = 0; j < 3; j++) {
					if ( boardb [i][j] == Seed.EMPTY) {
						boardb [i][j] = Seed.AALU;
						int bestVal = minimax ( boardb, depth + 1, !isMax );
						best = Math.max(best, bestVal);
						boardb [i][j] = Seed.EMPTY;
					}
				}
			}
			return best;
		} else {
			int best = 100;
			for( int i = 0; i < 3; i++) {
				for ( int j = 0; j < 3; j++) {
					if ( boardb [i][j] == Seed.EMPTY) {
						boardb [i][j] = Seed.CROSS;
						int bestVal = minimax ( boardb, depth + 1, !isMax );
						best = Math.min(best, bestVal);
						boardb [i][j] = Seed.EMPTY;
					}
				}
			}
			return best;
		}
	}
	static int evaluate ( Seed b[][] , int dep) {
		for(int i = 0; i < 3; i++) {
			if( b[i][0] == b[i][1] && b[i][1] == b[i][2] ) {
				if( b[i][0] == Seed.AALU )
					return 10 - dep;
				else if( b[i][0] == Seed.CROSS )
					return -10 + dep;
			}
		}
		for(int j= 0; j<3; j++) {
			if(b[0][j] == b[1][j] && b[1][j] == b[2][j] ) {
				if(b[0][j] == Seed.AALU )
					return 10 - dep;
				else if( b[0][j] == Seed.CROSS )
					return -10 + dep;
			}
		}
		if( b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
			if( b[0][0] == Seed.AALU )
				return 10 - dep;
			else if ( b[0][0] == Seed.CROSS )
				return -10 + dep;
		}
		if ( b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
			if ( b[0][2] == Seed.AALU)
				return 10 - dep;
			else if ( b[0][2] == Seed.CROSS )
				return -10 + dep;
		}
		return 0;
	}
	static boolean isMoveLeft ( Seed b [][]) {
		for ( int i = 0; i < 3; i++) {
			for ( int j = 0; j < 3; j++) {
				if ( b[i][j] == Seed.EMPTY)
					return true;
			}
		}
		return false;
	}
}
