Requirements

The board should start in the standard chess starting state with all the 16 pieces lined up for each player.
Play starts with player 1 (white) and on each valid move alternates to the other player.   On an invalid move the existing player stays in control.
The moves must be read in using the supplied UserInputFile class until there are no more moves
All moves must have a piece on the starting square and either an opponent piece or nothing on the destination square   Anything else is invalid.
Validate the move according to the moves allowed by the piece on the starting square:
The king can move only 1 square but in any direction
The bishop can move any number of squares but only diagonally
The rook can move any number of squares but only horizontally or vertically
The queen can move any number of squares horizontally, vertically or diagonally.
The knight can move in an L shape with sides of 2 and 1 squares respectively.  That is 8 different possible moves.   Unlike other pieces it jumps over other pieces.
The pawn can move one or two squares forward on its first move (when not taking an opponent piece)
The pawn can move one square forward on subsequent moves (when not taking an opponent piece)
The pawn can move one square forward diagonally if taking an opponent piece
After each successful move render the board in simple ASCII form.  It is suggested that player 1 is represented by upper-case characters and player 2 by lower-case characters.  The conventional characters to use here are:   Rook, kNight, Bishop, King, Queen, Pawn.  
If the destination square contains an opponent piece then that piece is removed from the board.  Unless that piece is a King where rules around check apply (see later)
For pieces other than the knight disallow the move if there are any other pieces in the way between the start and end square.
If a move ends with a player’s king under attack that is “check”
A player cannot end their own move in check
If a player starts their move in check this should be displayed as “in check”

