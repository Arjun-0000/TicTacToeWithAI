package TicTacToeWithAi;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainGame {
		
		private static final int ROW = 3 , COL = 3;
		private DrawCanvas canvas;
		private JFrame jframe;
		private BottomStatus status;
		public AiOp boMain;
		
		public Seed currentPlayer;
		public GameState currentState;
		
		Seed[][] board = new Seed[ROW][COL];
		
		public MainGame() {
			/* testing of AI purpose
			for( int i = 0; i < 3; i++) {
				for( int j=0; j<3; j++ ) {
					System.out.print(board[i][j]);
				}
				System.out.println();
			}
			*/
			jframe = new JFrame();

			status = new BottomStatus();
			
			canvas = new DrawCanvas();
			
			canvas.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent ae) {
					
					if ( currentState == GameState.PLAYING ) {
						
						if( currentPlayer == Seed.AALU ) {
							boMain = new AiOp(board);
							if( boMain.rowMain >= 0 && boMain.rowMain < ROW && boMain.colMain >= 0
				                     && boMain.colMain < COL && board[boMain.rowMain][boMain.colMain] == Seed.EMPTY ) {
								
								//status.setText(boMain.rowMain + "  " + boMain.colMain );
									board[boMain.rowMain][boMain.colMain] = currentPlayer;
									gameUpdate(currentPlayer, boMain.rowMain, boMain.colMain );
									currentPlayer = Seed.CROSS;
								}
						}else if ( currentPlayer == Seed.CROSS ) {
							int mouseX = ae.getY();
							int mouseY = ae.getX();
							int rowSelected = mouseX/100;
							int colSelected = mouseY/100;
							if( rowSelected >= 0 && rowSelected < ROW && colSelected >= 0
				                     && colSelected < COL && board[rowSelected][colSelected] == Seed.EMPTY ) {
									board[rowSelected][colSelected] = currentPlayer;
									gameUpdate(currentPlayer, rowSelected, colSelected );
									currentPlayer = Seed.AALU;
								}
						}
						
					} else initGame();
					canvas.repaint();
				}
			});
			jframe.add(canvas);
			jframe.add(status, BorderLayout.AFTER_LAST_LINE);
			jframe.setSize(300,350);
			jframe.setVisible(true);
			
			
		}
		
		public class BottomStatus extends JLabel {
			public BottomStatus() {
				setBounds(0,320,320,40);
				setOpaque(true);
				setBackground(Color.red);
				setForeground(Color.pink);
				setText("click to start");
			}
		}
		
		public class DrawCanvas extends JPanel {
			
			public DrawCanvas() {
				setSize(300,300);
			}
			
			public void paintComponent ( Graphics g) {
				
				super.paintComponent(g);
				
				setBackground(Color.gray);
				
				g.setColor(Color.black);
				for( int i = 1; i<ROW; i++) {
					g.fillRoundRect(100*i  , 0, 1, 300, 0, 0);
				}
				for( int j = 1; j< COL;j++) {
					g.fillRoundRect(0, 100*j , 300, 1, 0, 0);
				}
				
				Graphics2D g2d = (Graphics2D)g;
		         g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));  // Graphics2D only
		         for (int row = 0; row < ROW; ++row) {
		            for (int col = 0; col < COL; ++col) {
		               int x1 = col * 100  ;
		               int y1 = row * 100;
		               if (board[row][col] == Seed.CROSS) {
		                  g2d.setColor(Color.blue);
		                  int x2 = (col + 1) * 100 ;
		                  int y2 = (row + 1) * 100 ;
		                  g2d.drawLine(x1, y1, x2, y2);
		                  g2d.drawLine(x2, y1, x1, y2);
		               } else if (board[row][col] == Seed.AALU) {
		                  g2d.setColor(Color.green);
		                  g2d.drawOval(x1, y1, 90, 90);
		               }
		            }
		         }
		         if( currentState == GameState.PLAYING ) {
						if ( currentPlayer  == Seed.CROSS ) {
							status.setBackground(Color.blue);
							status.setText (" X Turn ");
						}
						
						else if ( currentPlayer == Seed.AALU ) {
							status.setBackground(Color.green);
							status.setText (" O Turn - Computer. Click Anywhere"); 
						}
						
					} else if ( currentState == GameState.AALU_WON) {
						status.setBackground(Color.red);
						status.setText (" O won! Play Again");
					}
					else if ( currentState == GameState.CROSS_WON) {
						status.setBackground(Color.red);
						status.setText(" X won! Play Again");
					}
					else if ( currentState == GameState.DRAW) {
						status.setBackground(Color.yellow);
						status.setText(" Its Draw ");
					}
			}
		}
		public void initGame() {
		      for (int row = 0; row < ROW; ++row) {
		         for (int col = 0; col < COL; ++col) {
		            board[row][col] = Seed.EMPTY; // all cells empty
		         }
		      }
		      currentState = GameState.PLAYING; // ready to play
		      currentPlayer = Seed.CROSS;       // cross plays first
		   }
		public void gameUpdate ( Seed theSeed , int rowSelected, int colSelected ) {
			if( hasWon(theSeed, rowSelected, colSelected) ) {
				currentState = (theSeed == Seed.CROSS)?GameState.CROSS_WON:GameState.AALU_WON;
			} else if ( isDraw() )
				currentState = GameState.DRAW;
		}
		public boolean hasWon( Seed s , int rowSelected, int colSelected ) {
			return (
					board[rowSelected][0] == s &&
					board[rowSelected][1] == s &&
					board[rowSelected][2] == s
					||
					board[0][colSelected] == s &&
					board[1][colSelected] == s &&
					board[2][colSelected] == s
					||
					board[0][0] == s &&
					board[1][1] ==s &&
					board[2][2] == s
					||
					board[0][2] == s &&
					board[1][1] == s &&
					board[2][0] == s
					);
		}
		public boolean isDraw() {
			for(int i = 0; i<ROW; i++) {
				for(int j = 0; j<COL; j++ ) {
					if(board[i][j] == Seed.EMPTY )
						return false;
				}
			}
			return true;
		}
		
		public static void main(String[] args) {
			new MainGame();
		}

}
