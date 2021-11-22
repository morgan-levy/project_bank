"""
Minesweeper Game
by Morgan Levy
"""
from javax.swing import JOptionPane
from game_controller import GameController as gc
from board import Board as b

SIZE = 40
rows_opts = [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
cols_opts = [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
rows = JOptionPane.showInputDialog(None, 'Please choose the number of rows:',
                                             'Rows:', JOptionPane.QUESTION_MESSAGE, None,
                                             rows_opts, rows_opts[0])
cols = JOptionPane.showInputDialog(None, 'Please choose the number of columns:',
                                             'Columns:', JOptionPane.QUESTION_MESSAGE, None,
                                             cols_opts, cols_opts[0])
levels = ['easy', 'medium', 'hard', 'expert']
DIFFICULTY = JOptionPane.showInputDialog(None, 'Please choose a level of difficulty:',
                                             'Difficulty', JOptionPane.QUESTION_MESSAGE, None,
                                             levels, levels[0])
SPACE = {'w': SIZE * cols, 'h': SIZE * rows}
game_controller = gc(rows, cols, SIZE, DIFFICULTY)
board = b(rows, cols, SIZE, DIFFICULTY)

def setup():
    size(SPACE['w'], SPACE['h'])
    
def draw():
    background(204)
    game_controller.update(mouseX, mouseY)

    if game_controller.game_over:
        game_controller.end_the_game()

def mousePressed():
    game_controller.handle_mouse_press()
    
def keyPressed():
    game_controller.handle_key_press(key)
