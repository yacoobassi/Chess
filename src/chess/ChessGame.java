package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGame extends JFrame {
  // Board dimensions
  private static final int ROWS = 8;
  private static final int COLS = 8;

  // Board squares
  private JButton[][] board;

  // Current player (white or black)
 

  // Piece icons
  private Icon whitePawn;
  private Icon blackPawn;
  private Icon whiteRook;
  private Icon blackRook;
  private Icon whiteKnight;
  private Icon blackKnight;
  private Icon whiteBishop;
  private Icon blackBishop;
  private Icon whiteQueen;
  private Icon blackQueen;
  private Icon whiteKing;
  private Icon blackKing;
  
  Point toMove;
  
  
  boolean ifWhiteKingMoved;
  boolean ifBlackKingMoved;
  boolean ifRightBlackRookMoved;
  boolean ifLeftBlackRookMoved;
  boolean ifRightWhiteRookMoved;
  boolean ifLeftWhiteRookMoved;
  boolean whiteTurn; 
  boolean check;
  boolean blackKingMoved; 
  boolean whiteKingMoved; 
  
  Point blackKingPoint=new Point(0,4),whiteKingPoint=new Point(7,4);


 
  
  public ChessGame() {
	toMove = new Point();  
	whiteTurn=true;  
    setSize(650, 650);
    setResizable(false);
    setTitle("Chess Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel(new GridLayout(ROWS, COLS));
    panel.setSize(650, 650);

    // Initialize board squares
    board = new JButton[ROWS][COLS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        board[i][j] = new JButton();
        board[i][j].setPreferredSize(new Dimension(50, 50));
        if ((i + j) % 2 == 0) {
          board[i][j].setBackground(new Color(255,206,158));
        } else {
          board[i][j].setBackground(new Color(209,139,70));
        }
        panel.add(board[i][j]);
      }
    }

    
    for (int i = 0; i < ROWS; i++) 
	      for (int j = 0; j < COLS; j++) {
	    	  
	    	 if(isKing(new Point(i,j)))continue; 
	  
	        if ((i + j) % 2 == 0) 
	          board[i][j].setBackground(new Color(255,206,158));
	        else  
	          board[i][j].setBackground(new Color(209,139,70));
	      }
	       
    // Load piece icons
    whitePawn = new ImageIcon("white_pawn.png");
    blackPawn = new ImageIcon("black_pawn.png");
    whiteRook = new ImageIcon("white_rook.png");
    blackRook = new ImageIcon("black_rook.png");
    whiteKnight = new ImageIcon("white_knight.png");
    blackKnight = new ImageIcon("black_knight.png");
    whiteBishop = new ImageIcon("white_bishop.png");
    blackBishop = new ImageIcon("black_bishop.png");
    whiteQueen = new ImageIcon("white_queen.png");
    blackQueen = new ImageIcon("black_queen.png");
    whiteKing = new ImageIcon("white_king.png");
    blackKing = new ImageIcon("black_king.png");

  
    for (int i = 0; i < ROWS; i++) {
      board[1][i].setIcon(blackPawn);
      board[6][i].setIcon(whitePawn);
    }
    
    board[0][0].setIcon(blackRook);
    board[0][1].setIcon(blackKnight);
    board[0][2].setIcon(blackBishop);
    board[0][3].setIcon(blackQueen);
    board[0][4].setIcon(blackKing);
    board[0][5].setIcon(blackBishop);
    board[0][6].setIcon(blackKnight);
    board[0][7].setIcon(blackRook);
    board[7][0].setIcon(whiteRook);
    board[7][1].setIcon(whiteKnight);
    board[7][2].setIcon(whiteBishop);
    board[7][3].setIcon(whiteQueen);
    board[7][4].setIcon(whiteKing);
    board[7][5].setIcon(whiteBishop);
    board[7][6].setIcon(whiteKnight);
    board[7][7].setIcon(whiteRook);
    
    

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
   
        Point p=new Point(i,j);
        board[i][j].setBorder(null);
        board[i][j].addMouseListener(new MouseAdapter() {
        	
         public void mouseEntered(MouseEvent e) {
        	
        	   if(board[p.x][p.y].getBackground().equals(Color.red))
        		 	board[p.x][p.y].setBackground(new Color(150,0,0));
        	   
        	   
        	else if(whiteTurn && isWhite(p)||!whiteTurn&&isBlack(p))
        		  board[p.x][p.y].setBackground(new Color(50,50,50));	
        	
        	}

         public void mouseExited(MouseEvent e) {
        	
           if(board[p.x][p.y].getBackground().equals(new Color(150,0,0))) 
        	   board[p.x][p.y].setBackground(Color.red);
        	
        	 if(board[p.x][p.y].getBackground().equals(new Color(50,50,50)))
           	 
        		 if ((p.x + p.y) % 2 == 0) 
		          board[p.x][p.y].setBackground(new Color(255,206,158));
		        else  
		          board[p.x][p.y].setBackground(new Color(209,139,70));
           }

		 public void mousePressed(MouseEvent e) {
        	 
          if (board[p.x][p.y].getIcon() != null) {
            
        	if(whiteTurn && isWhite(p) || !whiteTurn && isBlack(p)) 
        		toMove=p;
        	
        	else if(validPosition(toMove, p)) 
        		move(toMove, p);
         
              }      	
          
          else {
        	 
        	  if(validPosition(toMove, p) && (whiteTurn && isWhite(toMove) ||
        			  !whiteTurn && isBlack(toMove))) move(toMove, p);
      
              }
            }
        
          }
        );
      }
    }
    
    


  
    add(panel);
    setVisible(true);
    setResizable(false);
    setLocationRelativeTo(null);
      }

  
    private boolean isKing(Point p) {
	 
	  return p==blackKingPoint||p==whiteKingPoint;
  }
  
    private boolean ifCheck(Point king) {
		
	  Point p ;
	  int x,y;
	
	   boolean cantMove=false;
		for(int i=0;i<8;i++) {
			
			if(i==0||i==1||i==2) x=-1; 
			else if(i==4||i==5||i==6) x=1;
			else x=0;
			
			if(i==0||i==7||i==6)y=1;
			else if(i==2||i==3||i==5)y=-1;
			else y=0;
			
			p = new Point(king.x,king.y);
			while(true) {
			
			p.x+=x; p.y+=y;
			if(notvalidPoint(p))break;
			
			if(whiteTurn&&isWhite(p)||!whiteTurn&&isBlack(p))break;
			
			else if((x!=0 && y!=0 && digonalPeice(p,king)) || (x==0||y==0)&& linePeice(p))  {
				if(isKing(king))check=true;cantMove=true;}
			
		
			else if(!isEmpty(p))break;
			
			}
		
		}
		
		// check for knights
	  
	    if(     knightCheck(king, new Point(1,2))||knightCheck(king, new Point(1,-2))||
	    		knightCheck(king, new Point(-1,2))||knightCheck(king, new Point(-1,-2))||
	    		knightCheck(king, new Point(2,1))||knightCheck(king, new Point(2,-1))||
	    		knightCheck(king, new Point(-2,1))||knightCheck(king, new Point(-2,-1))) {
	    
	    	if(isKing(king)) check=true;
	    	
	    	cantMove=true;
	    }
		
	    if(!check) 
	    	 clear(king);
	    	
	    
		return cantMove;
	}
  
    private boolean knightCheck(Point king,Point p) {
	  
	  if(notvalidPoint(new Point(king.x+p.x , king.y+p.y)))return false;
	  
	  return  (!whiteTurn&&getPeice(new Point(king.x+p.x,king.y+p.y))==whiteKnight)||
			  (whiteTurn&&getPeice(new Point(king.x+p.x,king.y+p.y))==blackKnight);
  }

    private void clear(Point p) {
	   
	 
		  
		        if ((p.x + p.y) % 2 == 0) 
		          board[p.x][p.y].setBackground(new Color(255,206,158));
		        else  
		          board[p.x][p.y].setBackground(new Color(209,139,70));
		      
		       
	 }
   
    private boolean notvalidPoint(Point p) {
	  return p.x<0||p.x>7||p.y<0||p.y>7;
  }
  
    private boolean linePeice(Point p) {
	  return getPeice(p)==whiteRook||getPeice(p)==whiteQueen||
			  getPeice(p)==blackRook||getPeice(p)==blackQueen;
  }
  
    private boolean digonalPeice(Point p,Point king) {
	  return ((getPeice(p)==whitePawn|| getPeice(p)==blackPawn) && Math.abs(king.x-p.x)==1)||
	    	   getPeice(p)==whiteBishop||getPeice(p)==blackBishop||
	    	   getPeice(p)==whiteQueen||getPeice(p)==blackQueen; 
  }
  
    private boolean isWhite(Point p) {
     	
    	return getPeice(p)==whitePawn||getPeice(p)==whiteRook||getPeice(p)==whiteKnight||
    	   getPeice(p)==whiteBishop||getPeice(p)==whiteQueen||getPeice(p)==whiteKing;
    
    }
    
    private boolean isBlack(Point p) {
     	
    	return getPeice(p)==blackPawn||getPeice(p)==blackRook||getPeice(p)==blackKnight||
    	   getPeice(p)==blackBishop||getPeice(p)==blackQueen||getPeice(p)==blackKing;
    	
    }
    
    private Icon getPeice(Point p) {
    	
    	return board[p.x][p.y].getIcon();
    }
    
    private void move(Point from , Point to) {
	  
     check=false;
     Icon save =getPeice(to);
  
	  
	  board[to.x][to.y].setIcon(getPeice(from));
	  board[from.x][from.y].setIcon(null);

	  ifkingMoved(from, to);
  	 
		    if((whiteTurn&&ifCheck(whiteKingPoint)) || 
		      (!whiteTurn&&ifCheck(blackKingPoint))) {
		    	
		    	  board[from.x][from.y].setIcon(getPeice(to));
		    	  board[to.x][to.y].setIcon(save);
		    	  ifkingMoved(to, from);	 
		    	  
		    }
		    
		    else {
		    
		    	clear(from);
		       if(Math.abs(from.y-to.y)>1 && (to==whiteKingPoint||to==blackKingPoint))
               {
		    	   
		    	   if(castling(from,to)==0)move(new Point(7,7), new Point(7,5));
		    	   else if(castling(from,to)==1)move(new Point(7,0), new Point(7,3));
		    	   else if(castling(from,to)==2)move(new Point(0,7), new Point(0,5));
		    	   else if(castling(from,to)==3)move(new Point(0,0), new Point(0,3));
		    	   whiteTurn=!whiteTurn;
		       }
		       
		       whiteTurn=!whiteTurn;
		       CastlingPeciesMoved(); 
		       pawnToQueen(to);
		       
	    	  if(!whiteTurn) {
	    		 if(ifCheck(blackKingPoint))
	    		  board[blackKingPoint.x][blackKingPoint.y].setBackground(Color.red);
	    	  }
	    	  
	    	  
	    	  else 
	    		  if(ifCheck(whiteKingPoint))

	    		  board[whiteKingPoint.x][whiteKingPoint.y].setBackground(Color.red);
	    		  
	    	
		    }
        
  
  }

    private void pawnToQueen(Point to) {

   if( (getPeice(to).equals(blackPawn) || getPeice(to).equals(whitePawn))&&
		   (to.x==7 || to.x==0) )
	   if(!whiteTurn)board[to.x][to.y].setIcon(whiteQueen);
	   else board[to.x][to.y].setIcon(blackQueen);
	   
	   
	
}

    private void CastlingPeciesMoved() {
	  
	   if(getPeice(new Point(0,0))!=blackRook)ifLeftBlackRookMoved=true;
		  if(getPeice(new Point(0,7))!=blackRook)ifRightBlackRookMoved=true;
		  if(getPeice(new Point(7,0))!=whiteRook)ifLeftWhiteRookMoved=true;
	      if(getPeice(new Point(7,7))!=whiteRook)ifRightWhiteRookMoved=true;
	      if(getPeice(new Point(0,4))!=blackKing)blackKingMoved=true; 
	      if(getPeice(new Point(7,4))!=whiteKing)whiteKingMoved=true;
  }
  
    private void ifkingMoved(Point from, Point to) {
	  
	  if(from.equals(whiteKingPoint))  whiteKingPoint=to;
  	  else if(from.equals(blackKingPoint)) blackKingPoint=to;
	  
  }
 
    private boolean isEmpty(Point p) {
	  
	  return getPeice(p)==null;
  }
 
    private boolean checkValidLine(Point p1, Point p2, int ifrow) {
	
	
	  Point from ,to;
	  int col = 0,row = 0;
	  
	  if(ifrow==0) {
	  if(p1.x>p2.x) {from =p1; to=p2;}else {from=p2;to=p1;}
	  
	  row=-1;
	  
	  }
	  
	  else {
	  if(p1.y<p2.y){from =p1; to=p2;}else {from=p2;to=p1;}
	 
	  col=1;
	  
	    }
	  
	   while(true) {
		  
		  from=new Point(from.x+row ,from.y+col);

		  if(getPeice(from)!=null&&!from.equals(to))return false;
			if(from.equals(to))return true;
	
	    }
  }
 
    private boolean isVaildDigonal(Point p1, Point p2) {
		
		  Point from , to;
		  int direction;
		  
		 if( Math.abs( p1.x-p2.x )!= Math.abs( p1.y-p2.y))return false;
		   
			if(p1.x>p2.x) {from=p1; to=p2;}else { to=p1; from=p2;}
			if(from.y<to.y)direction=1;else direction=-1;
			
		while(true) {
			
			if(from.x<0||from.x>8||to.y<0||to.y>8)return false;
			
			from=new Point(from.x-1,from.y+direction);
			
			if(getPeice(from)!=null&&!from.equals(to))return false;
			if(from.equals(to))return true;
			
		}
	}

    private boolean validPosition(Point from,Point to) {
	
	if(isWhite(from) && isWhite(to)) return false;  
	
	else if(isBlack(from) && isBlack(to)) return false;
	
	else 
		if(getPeice(from)==whitePawn || getPeice(from)==blackPawn)return validPositionPawn(from,to);
		else if(getPeice(from)==whiteRook || getPeice(from)==blackRook)return validPositionRook(from,to);
		else if(getPeice(from)==whiteKnight || getPeice(from)==blackKnight)return validPositionKnight(from,to);
		else if(getPeice(from)==whiteBishop || getPeice(from)==blackBishop)return validPositionBishop(from,to);
		else if(getPeice(from)==whiteQueen || getPeice(from)==blackQueen)return validPositionQueen(from,to);
		else if(getPeice(from)==whiteKing || getPeice(from)==blackKing)return validPositionKing(from,to);
		
		return false;

	
  }
    
    private boolean validPositionKing(Point from,Point to) {
     
        if(castling(from,to)!=-1)return true;
    	return (Math.abs(from.x-to.x)<=1&& Math.abs(from.y-to.y)<=1);     	
}

	private boolean validPositionQueen(Point from ,Point to) {

        if(from.x==to.x)return checkValidLine(from, to, 1);
        else if(from.y==to.y) return checkValidLine(from, to, 0);
        
 
 		return isVaildDigonal(from,to);
 		}
	
	private boolean validPositionBishop(Point from, Point to) {

		return isVaildDigonal(from,to);
		
      
	
}

	private boolean validPositionKnight(Point from, Point to) {

		return Math.abs(from.x-to.x)==2 && Math.abs(from.y-to.y)==1 ||
			   Math.abs(from.x-to.x)==1 && Math.abs(from.y-to.y)==2;

	}

	private boolean validPositionRook(Point from ,Point to) {

     
         
         if(from.x==to.x)return checkValidLine(from, to, 1);
         else if(from.y==to.y) return checkValidLine(from, to, 0);
          
         return false;
	
}

	private boolean validPositionPawn(Point from, Point to) {
	
		int forward;
		if(isWhite(from))forward=1; else forward=-1;
	
		if(isWhite(from) && from.x==6 && to.x==4 && from.y==to.y)return true;
		
		else if(isBlack(from) && from.x==1 && to.x==3 && from.y==to.y)return true;
		
		else if(from.x-to.x!=forward )return false;
		
		else if(Math.abs(from.y-to.y)==1 && 
				(isWhite(from)&&isBlack(to) || isBlack(from)&&isWhite(to)))return true;
		
		return from.y==to.y ;

	
 }
	
	private int castling(Point from , Point to) {
	
		if(to.y-from.y== 2 && to.x==7 && !ifRightWhiteRookMoved &&
				!whiteKingMoved &&!ifCheck(new Point(7,5))) return 0;

		if( to.y-from.y==-2 && to.x==7 && !ifLeftWhiteRookMoved
				&& !whiteKingMoved&&!ifCheck(new Point(7,3)))return 1;
		
		if(to.y-from.y== 2 && to.x==0 && !ifRightBlackRookMoved &&
				!blackKingMoved&&!ifCheck(new Point(0,5)))return 2;
		
		if(to.y-from.y==-2 && to.x==0 && !ifLeftBlackRookMoved &&
				!blackKingMoved&&!ifCheck(new Point(0,3))) return 3;
		 
		return -1;
		  
	}


	public static void main(String[] args) {
    new ChessGame();
      }
    }
