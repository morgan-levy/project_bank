# Morgan Levy
import random
import math
import square as sq
X_INDEX = 0
Y_INDEX = 1
MINE_VALUE = 'M'
SQ_COLOR = (0,0,0)

EASY_DIFF_FACTOR = 0.1
MEDIUM_DIFF_FACTOR = 0.15
HARD_DIFF_FACTOR = 0.2
EXPERT_DIFF_FACTOR = 0.225

TERMINAL_SPACING_FACTOR = 4
LINE_SIZE_FACTOR = 0.01

class Board:
    ''' A class representing a Minesweeper Game Board'''
    def __init__(self, rows, cols, SIZE, difficulty):
        self.rows = rows
        self.cols = cols
        self.SIZE = SIZE
        self.difficulty = difficulty
        # Add all squares to the board
        self.board_representation = []
        for i in range(self.cols):
            self.board_representation.append([])
            for j in range(self.rows):
                self.board_representation[i].append(sq.Square(SQ_COLOR, SIZE, (i*self.SIZE,j*self.SIZE)))
        # Denote squares that contain mines and set values of all others
        self.mine_xys = self.determine_mine_locations()
        for (x, y) in self.mine_xys:
            self.board_representation[x][y].set_value(MINE_VALUE)
            self.board_representation[x][y].set_as_mine()
        self.set_space_values()

    def determine_mine_locations(self):
        ''' Method to randomly set the mines on the game board '''
        # Set number of mines based on level of difficulty and board size chosen by player
        number_spaces = self.rows * self.cols
        if self.difficulty == 'easy':
            mine_factor = EASY_DIFF_FACTOR
        elif self.difficulty == 'medium':
            mine_factor = MEDIUM_DIFF_FACTOR
        elif self.difficulty == 'hard':
            mine_factor = HARD_DIFF_FACTOR
        elif self.difficulty == 'expert':
            mine_factor = EXPERT_DIFF_FACTOR
        num_mines = math.floor(number_spaces * mine_factor)

        # Randomly set the location of the determined number of mines
        mine_locations = []
        while len(mine_locations) <= num_mines:
            (x, y) = random.randint(0, self.cols-1), random.randint(0, self.rows-1)
            if (x, y) not in mine_locations:
                mine_locations.append((x, y))
        
        return mine_locations
    
    def set_space_values(self):
        ''' Method to set the values of the grid spaces '''
        for x in range(self.cols):
            for y in range(self.rows):
                if self.board_representation[x][y].value != MINE_VALUE:
                    self.board_representation[x][y].set_value(self.calculate_value(x, y))

    def calculate_value(self, x, y):
        ''' Method to determine the number of adjacent mines to a single grid space '''
        moves = [(-1, -1),
                 (0, -1),
                 (1, -1),
                 (-1, 0),
                 (1, 0),
                 (-1, 1),
                 (0, 1),
                 (1, 1)]
        value = 0

        # Set the value for all squares based on number of mines in all valid adjacent squares
        for move in moves:
            new_x, new_y = x + move[0], y + move[1]
            
            if self.is_valid_location(new_x, new_y) and self.board_representation[new_x][new_y].is_mine:
                value += 1

        return value
    
    def is_valid_location(self, x, y):
        '''Check if a coordinate exists on the board'''
        return ((0 <= x <= self.cols-1) and (0 <= y <= self.rows-1))

    def draw(self):
        ''' Renders the game board per provided dimensions '''
        for i in range(self.rows + 1):
            strokeWeight(self.SIZE * LINE_SIZE_FACTOR)
            stroke(0)
            line(0, self.SIZE * i, self.SIZE * (self.cols), self.SIZE * i)

        for j in range(self.cols + 1):
            strokeWeight(self.SIZE * LINE_SIZE_FACTOR)
            stroke(0)
            line(self.SIZE * j, 0, self.SIZE * j, self.SIZE * (self.rows))
