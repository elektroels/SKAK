
import java.util.ArrayList;
import java.util.Stack;

public class MoveGenerator {

    /**
     * Generates moves on a Board Object
     *
     * 1 = White Pawn(s) 2 = White Rook(s) 3 = White Knight(s) 4 = White
     * Bishop(s) 5 = White Queeen 6 = White King 7 left blank as pivot with
     * white below, black above 8 = Black Pawn(s) 9 = Black Rook(s) 10 = Black
     * Knight(s) 11 = Black Bishop(s) 12 = Black Queen 13 = Black King
     */
    int[] knightMoves = {0x21, // two up one right
        0x1F, // two up one left
        0x12, // one up two right
        0x0E, // one up two left
        -0x21, // two down one left
        -0x1F, // two down one right
        -0x12, // one down two left
        -0x0E};  // one down two right

    int[] kingMoves = {0x01, // one right
        -0x0F, // one down one right
        -0x10, // one down
        -0x11, // one down one left
        -0x01, // one left
        0x0F, // one up one left
        0x10, // one up
        0x11};     // one up one right
    
    static final int BLACK_QUEEN = 0x0F;
    static final int BLACK_BISHOP = 0x0D;
    static final int BLACK_KNIGHT = 0x0A;
    static final int BLACK_PAWN = 0x09;
    static final int BLACK_ROOK = 0x0E;
    static final int BLACK_KING = 0x0B;
    
    
    static final int WHITE_PAWN = 0x01;
    static final int WHITE_KNIGHT = 0x02;
    static final int WHITE_KING = 0x03;
    static final int WHITE_BISHOP = 0x05;
    static final int WHITE_ROOK = 0x06;
    static final int WHITE_QUEEN = 0x07;
    
    // er de samme som kongens bare med voksende offset :-D 
    int[] queenMoves = {0x01, // one right
        -0x0F, // one down one right
        -0x10, // one down
        -0x11, // one down one left
        -0x01, // one left
        0x0F, // one up one left
        0x10, // one up
        0x11};// one up one right

    int[] pawnMovesBLACK = {0x10, // one up
        //0x11, // one up one right
        //0x0F, // one up one left
        //0x20
        };  // two up
    
    int[] pawnMovesWHITE = {-0x10, // one up
        //0x11, // one up one right
        //0x0F, // one up one left
        //0x20
        };  // two up
    
    int[] bishopMoves = {-0x0F, // one down one right
        -0x11, // one down one left
        0x0F, // one up one left
        0x11};     // one up one right};

    int[] rookMoves = {0x01, // one right
        -0x10, // one down
        -0x01, // one left
        0x10};    // one up

    int[][] allMoves = {queenMoves,bishopMoves,rookMoves,knightMoves,pawnMovesBLACK,kingMoves,pawnMovesWHITE};
    
    //int COMP_COLOR = 1; //0 = white 1 = black
        
    public boolean isValidMove(Board board, int currentPos, int offset, Piece me, boolean isFirst, int color) {
        int lastMove = currentPos - offset;

        if (!isFirst){  
            if (((lastMove) & 0x88) == 0) { // inside board
                if((board.board[lastMove] != 0) && (me.getPosition() == lastMove) ){// der stod en på sidste move
                    return false;
                }
            }
        }
        if (((currentPos + offset) & 0x88) == 0) { // inside board
            
            //BLACK
            if ( color == 1 && ((board.board[(currentPos + offset)] & 0x8) != 0)) { // det næste move er en af vores egne
                return false;
            } 
            
            //WHITE
            if ( color == 0 && ((board.board[(currentPos + offset)] & 0x8) == 0) && (board.board[(currentPos + offset)] != 0)) { // det næste move er en af vores egne
                return false;
            }  
            
            return true;
         }
        
         return false;
    }
    
    
    public int[] convertPiceTypeToIntList(int type) {
     
        switch (type) {
            case BLACK_QUEEN: 
            case WHITE_QUEEN: 
                return allMoves[0];
            case BLACK_BISHOP:  
            case WHITE_BISHOP:
                return allMoves[1];
            case BLACK_ROOK:
            case WHITE_ROOK:
                return allMoves[2];
            case BLACK_KNIGHT:
            case WHITE_KNIGHT:
                return allMoves[3];
            case BLACK_PAWN:  
                return allMoves[4];
            case WHITE_PAWN: 
                return allMoves[6];
            case BLACK_KING:  
            case WHITE_KING:  
                return allMoves[5];                
        }
        //BIIIGG ERROR!!
        return allMoves[3];
    }
    
    //Funktion som også tjekker i forhold til andres brikker
    //  Den funktion skal også tage højde for en passant (hvis nu Boardet kan huske om det var der)

    //funktion som tjekker den er indenfor pladen
    //tjek om den slår sin egen brik.
    //Tjek udenfor
    // KOM SÅ GITHUBBBB
    
    
    public Stack<Move> generateMovesPiece(Board board, boolean player, Piece CurrPeace, int offset, boolean isFirst, boolean isSliding, int color) { //0 = white 1 = black
        Stack<Move> moves = new Stack<>();
      
                int currentPos = CurrPeace.getPosition();
                Piece tempPeace = new Piece(CurrPeace.type,CurrPeace.position);
                if (isValidMove(board, currentPos, offset, tempPeace, isFirst, color)) {
                    int newpos = currentPos+offset;
                    moves.add(new Move(currentPos, newpos,CurrPeace));
                    tempPeace.setPosition(newpos);
                    if(isSliding){
                        moves.addAll(generateMovesPiece(board, player,tempPeace, offset, false, true, color));
                    }
                }
        return moves;
    }
    
    public Stack<Move> generateMoves(Board board, boolean player, int color) { //0 = white 1 = black
        Stack<Move> moves = new Stack<>();
        
        for (int index = 0; index < board.returnPiecesSize(color); index++) {
            Piece currentPiece = board.getPiece(index,color);
            for (Integer offset : convertPiceTypeToIntList(currentPiece.type)) {
                if ((currentPiece.getType() & 0x4) != 0) { // Is sliding pice
                    moves.addAll(generateMovesPiece(board, player,currentPiece, offset, true, true, color));
                }else{
                    moves.addAll(generateMovesPiece(board, player,currentPiece, offset, true,false, color));
                }  
            }  
        }
        
        return moves;
    }
      
           
public static void main (String[] args) {
System.out.println( "WHAT!?" + (BLACK_PAWN & 0x8));
Stack<Move> moves = new Stack();
        
MoveGenerator tester = new MoveGenerator();
Board myBoard = new Board();
Move tempMove;

moves = tester.generateMoves(myBoard, true, 1);

System.out.println("moves.size():" + moves.size());
while (moves.size() > 0){
  tempMove = moves.pop();
  System.out.println(Integer.toHexString(tempMove.getPositionTo()));  
}
 
}

}
