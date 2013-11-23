
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

    // er de samme som kongens bare med voksende offset :-D 
    int[] queenMoves = {};

    int[] pawnMoves = {0x10, // one up
        0x11, // one up one right
        0x0F, // one up one left
        0x20};     // two up

    int[] bishopMoves = {-0x0F, // one down one right
        -0x11, // one down one left
        0x0F, // one up one left
        0x11};     // one up one right};

    int[] rookMoves = {0x01, // one right
        -0x10, // one down
        -0x01, // one left
        0x10};    // one up

    public boolean isValidMove() {
        return true;
    }
    //Funktion som også tjekker i forhold til andres brikker
    //  Den funktion skal også tage højde for en passant (hvis nu Boardet kan huske om det var der)

    //funktion som tjekker den er indenfor pladen
    //tjek om den slår sin egen brik.
    //Tjek udenfor
    // KOM SÅ GITHUBBBB
    public Stack<Move> generateMoves(Board board, boolean player) {
        Stack<Move> moves = new Stack<>();
        /**
         * Calculate Moves Pieces Implemented so far: NONE DONE :X
         */

        // if white : 
        if (player) {
        
        for (int a = 0; a < board.PiecesWhite.size(); a++) {
            int currentType = board.PiecesWhite.get(a).getType();
            // Generates White King Moves
            if (currentType == 6) { // (12|6)) { // if its a king
                int currentPos = board.PiecesWhite.get(a).getPosition();
                for (Integer offset : kingMoves) {
                    if (((currentPos + offset) & 0x88) == 0) { // Piece is inside the board!
                        int nextPosContains = board.board[(currentPos + offset)];
                        int nextPos = currentPos + offset;
                        if (nextPosContains < 7) {
                            /** Own piece do nothing */ } 
                        else if (nextPosContains > 7) {
                            //nextPos
                            //ArrayList
                            }
                        else {
                            moves.add(new Move(currentPos, (currentPos + offset))); }
                    }
                }
            }
        }
        }
        // al den samme logik bare for sort
        else {}
        return moves;
    }

}
