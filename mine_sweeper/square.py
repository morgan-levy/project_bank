# Morgan Levy
X_INDEX = 0
Y_INDEX = 1
FLAG_OFFSET_1 = 0.375
FLAG_OFFSET_2 = 0.125
FLAG_OFFSET_3 = 0.25
SQUARE_OFFSET = 0.05
TEXT_SCALE_FACTOR = 0.5
RED_INDEX, GREEN_INDEX, BLUE_INDEX = 0, 1, 2
ZERO_VAL = 0
RED = (255, 0, 0)

class Square:
    ''' A class representing a Board Square '''
    def __init__(self, color, SIZE, position):
        self.color = color
        self.SIZE = SIZE
        self.value = 0
        self.x_coord = position[X_INDEX]
        self.y_coord = position[Y_INDEX]
        self.is_mine = False
        self.is_flagged = False
        self.is_revealed = False

    def set_value(self, value):
        '''Method to set the value of a square'''
        self.value = value

    def set_as_mine(self):
        '''Method to set a square as containing a mine'''
        self.is_mine = True

    def flag_square(self):
        '''Method to flag a square'''
        self.is_flagged = True

    def unflag_square(self):
        '''Method to unflag a flagged square'''
        self.is_flagged = False

    def reveal_square(self):
        '''Method to reveal a covered square'''
        self.is_revealed = True

    def draw(self):
        '''Method to draw contents a square'''
        if self.is_revealed and self.value != ZERO_VAL:
            # Handle case of a revealed square
            self.display_space_value(self.x_coord, self.y_coord)
        elif self.is_flagged:
            # Handle case of a flagged square
            self.display_flag(self.x_coord, self.y_coord)
        elif not self.is_revealed:
            # Handle case of a covered square
            self.draw_unrevealed_sqr(self.x_coord, self.y_coord)

    def display_space_value(self, x, y):
        text_color = self.color_text(self.value)
        fill(text_color[RED_INDEX], text_color[GREEN_INDEX], text_color[BLUE_INDEX])
        textSize(self.SIZE*TEXT_SCALE_FACTOR)
        textAlign(CENTER, CENTER)
        text(str(self.value), x + self.SIZE/2, y + self.SIZE/2)

    def draw_unrevealed_sqr(self, x, y):
        fill(150)
        noStroke()
        square(x + self.SIZE*SQUARE_OFFSET, y + self.SIZE*SQUARE_OFFSET, self.SIZE*(1 - 2*SQUARE_OFFSET))
    
    def display_flag(self, x, y):
        fill(RED[RED_INDEX], RED[GREEN_INDEX], RED[BLUE_INDEX])
        point1 = (x + self.SIZE*FLAG_OFFSET_1, y + self.SIZE*FLAG_OFFSET_2)
        point2 = (x + self.SIZE*FLAG_OFFSET_1, y + self.SIZE*FLAG_OFFSET_1)
        point3 = (x + self.SIZE*(1-FLAG_OFFSET_3), y + self.SIZE*FLAG_OFFSET_3)
        noStroke()
        triangle(point1[X_INDEX], point1[Y_INDEX], point2[X_INDEX], point2[Y_INDEX], point3[X_INDEX], point3[Y_INDEX])
        stroke(0)
        line(x + self.SIZE*FLAG_OFFSET_1, y + self.SIZE*FLAG_OFFSET_2, x + self.SIZE*FLAG_OFFSET_1, y + self.SIZE*(1-FLAG_OFFSET_2))
        line(x + self.SIZE*FLAG_OFFSET_3, y + self.SIZE*(1-FLAG_OFFSET_2), x + self.SIZE*2*FLAG_OFFSET_3, y + self.SIZE*(1-FLAG_OFFSET_2))

    def color_text(self, value):
        '''Assign color to text for displaying number of adj. mines value'''
        if value == 1:
            return (25, 22, 170)
        elif value == 2:
            return (22, 105, 170)
        elif value == 3:
            return (22, 170, 144)
        elif value == 4:
            return (10, 142, 50)
        elif value == 5:
            return (170, 157, 14)
        elif value == 6:
            return (170, 118, 14)
        elif value == 7:
            return (170, 71, 14)
        elif value == 8:
            return (98, 42, 10)
        else:
            return (0, 0, 0)