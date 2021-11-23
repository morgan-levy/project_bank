# Morgan Levy
import board as b
BLACK = 0
X_INDEX = 0
Y_INDEX = 1
ZERO_VAL = 0
INCREMENT = 1

class GameController:
    ''' A class representing a controller for a game of Minesweeper '''
    def __init__(self, rows, cols, SIZE, difficulty):
        self.rows = rows
        self.cols = cols
        self.SIZE = SIZE
        self.board = b.Board(rows, cols, SIZE, difficulty)
        self.difficulty = difficulty
        self.win = self.game_is_won()
        self.loss = self.game_is_lost()
        self.game_over = self.win or self.loss
        self.revealed_squares = []
        self.flagging_mode = False
        self.new_sqr_chosen = False

    def update(self, mouse_x, mouse_y):
        '''Method to update the board'''
        self.board.draw()
        for row in self.board.board_representation:
            for sqr in row:
                sqr.draw()

        self.game_over = self.check_for_game_over()

        if not self.game_over and self.new_sqr_chosen:
            (x, y) = self.determine_chosen_square(mouse_x, mouse_y)
            if not self.flagging_mode:
                self.reveal_a_square(x, y)
            else:
                self.handle_flag_mode(x, y)
            self.new_sqr_chosen = False

    def check_for_game_over(self):
        if self.game_is_lost() or self.game_is_won():
            return True
        return False

    def game_is_lost(self):
        '''Check if game has been lost'''
        for x in range(self.cols):
            for y in range(self.rows):
                if (self.board.board_representation[x][y].is_mine and
                    self.board.board_representation[x][y].is_revealed):
                    return True
        return False

    def game_is_won(self):
        '''Check if game has been won'''
        for x in range(self.cols):
            for y in range(self.rows):
                if (not self.board.board_representation[x][y].is_mine and 
                   (self.board.board_representation[x][y].is_flagged or 
                    not self.board.board_representation[x][y].is_revealed)):
                    return False
        return True

    def handle_mouse_press(self):
        '''Method to handle a mouse press'''
        self.new_sqr_chosen = True

    def handle_key_press(self, key):
        '''Method to handle a key press'''
        if (key == ' ') or (str(key).lower() == 'f'):
            if self.flagging_mode is False:
                self.flagging_mode = True
            else:
                self.flagging_mode = False
    
    def handle_flag_mode(self, x, y):
        if not self.board.board_representation[x][y].is_revealed:
            if self.board.board_representation[x][y].is_flagged:
                self.remove_a_flag(x, y)
            else:
                self.place_a_flag(x, y)

    def place_a_flag(self, x, y):
        '''Place a flag at x, y'''
        self.board.board_representation[x][y].flag_square()

    def remove_a_flag(self, x, y):
        '''Remove a flag at x, y'''
        self.board.board_representation[x][y].unflag_square()

    def reveal_a_square(self, x, y):
        '''Reveals a new square at position (x, y)'''
        if not self.board.board_representation[x][y].is_revealed:
            self.board.board_representation[x][y].reveal_square()
            self.revealed_squares.append((x,y))
            if self.board.board_representation[x][y].value == ZERO_VAL:
                self.clear_adj_sqs(x, y)

    def determine_chosen_square(self, mouse_x, mouse_y):
        '''Method to determine the indices of the clicked square'''
        chosen_x = 0
        chosen_y = 0
        for i in range(self.cols):
            if (self.SIZE * i) <= mouse_x <= (self.SIZE * (i+INCREMENT)):
                chosen_x = i
                continue
        for j in range(self.rows):
            if (self.SIZE * j) <= mouse_y <= (self.SIZE * (j+INCREMENT)):
                chosen_y = j
                continue

        return (chosen_x, chosen_y)

    def clear_adj_sqs(self, x, y):
        '''Clears all squares not touching any mines adjacent to a
           newly revealed mine'''
        moves = [(-1, -1),
                 (0, -1),
                 (1, -1),
                 (-1, 0),
                 (1, 0),
                 (-1, 1),
                 (0, 1),
                 (1, 1)]
        for move in moves:
            new_x = x + move[X_INDEX]
            new_y = y + move[Y_INDEX]
            if (not self.board.is_valid_location(new_x, new_y) or
                ((new_x, new_y) in self.revealed_squares)):
                continue
            elif self.board.board_representation[new_x][new_y].value == ZERO_VAL:
                self.board.board_representation[new_x][new_y].reveal_square()
                self.revealed_squares.append((new_x, new_y))
                self.clear_adj_sqs(new_x, new_y)
            else:
                self.board.board_representation[new_x][new_y].reveal_square()
                self.revealed_squares.append((new_x, new_y))

    def end_the_game(self):
        '''Method to end the game'''
        if self.win:
            message = "YOU WON"
        else:
            message = "YOU LOST"

        fill(BLACK)
        textSize(self.SIZE*min(self.rows, (self.cols/len(message))))
        textAlign(CENTER)
        text(message, self.SIZE * self.cols/2, self.SIZE * self.rows/2)










