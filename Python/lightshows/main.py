#!usr/bin/python3.11

class Lightshows:
    ROWS = 1000
    COLS = 1000

    def __init__(self):
        self.lightshows = [[0 for _ in range(self.COLS)] for _ in range(self.ROWS)]

    def turn(self, row_start: int, row_end: int, col_start: int, col_end: int, val: int) -> None:
        '''
        This function turns the light off or on at each position in the specified range of rows and 
        columns in the lightshows attribute of the object to the specified value. 
        '''
        for row in range(row_start, row_end + 1):
            for col in range(col_start, col_end + 1):
                if self.lightshows[row][col] != val:
                    self.lightshows[row][col] = val

    def toggle(self, row_start: int, row_end: int, col_start: int, col_end: int) -> None:
        '''
        This function toggles the light at each position in the specified range of rows and columns in the 
        lightshows attribute of the object. 
        '''
        for row in range(row_start, row_end + 1):
            for col in range(col_start, col_end + 1):
                self.lightshows[row][col] = not self.lightshows[row][col]

    def lights_on(self) -> int:
        '''
        This function prints the number of lights that are currently turned on in the lightshows attribute of the object.
        '''
        return sum(map(sum, self.lightshows))

    def _process_format(self, instruction: list) -> list:
        '''
        This function takes in a list instruction and processes it to extract the command and start and end coordinates,
        where a certain format is implicit.
        '''
        if not instruction:
            raise ValueError("Empty instruction line!")

        if len(instruction) < 4 or len(instruction) > 5:
            raise ValueError("Invalid format!")
        
        is_turn = instruction[1].isalpha()

        command = ''.join([instr for instr in instruction[:2]]) if is_turn else instruction[0]
        if command not in ('turnon', 'turnoff', 'toggle'):
            raise ValueError("Invalid input format. Possible commands are 'turn on', 'turn off', and 'toggle'.")

        start, end = (instruction[2], instruction[4]) if is_turn else (instruction[1], instruction[3])
        start = [int(val) for val in start.split(',')]
        end = [int(val) for val in end.split(',')]

        row_start, row_end = start[0], end[0]
        col_start, col_end = start[1], end[1]

        return [command, row_start, row_end, col_start, col_end]

    def from_file(self, filepath: str) -> None:
        '''
        Feed instructions from a file.
        '''
        with open(filepath, mode='r') as f:
            instructions = f.readlines()

        self.process_instructions(instructions)

    def from_instruction(self, instruction: str) -> None:
        '''
        Feed instruction(s) directly from the argument of this method's call.
        '''
        self.process_instructions([instruction])

    def process_instructions(self, instructions: list) -> None:
        '''
        The function calls the appropriate method (turn or toggle) with the extracted start and end coordinates 
        and the value to turn the lights to (for the turn method).
        '''
        instructions = [instruction.strip().split() for instruction in instructions]
        
        for instruction in instructions:
            processed_instr = self._process_format(instruction)
            command, row_start, row_end, col_start, col_end = processed_instr[0], *processed_instr[1:]

            if command == 'turnon':
                self.turn(row_start, row_end, col_start, col_end, 1)
            elif command == 'turnoff':
                self.turn(row_start, row_end, col_start, col_end, 0)
            elif command == 'toggle':
                self.toggle(row_start, row_end, col_start, col_end)

    def __str__(self):
        return f'In this {self.ROWS}x{self.COLS} grid of lights, there are {self.lights_on()} lights on.'

class Upgraded_Lightshows(Lightshows):
    ''' We can now control brightness. Cool! '''

    def lights_on(self) -> int:
        '''This function prints the sum of brightness units in the lightshows attribute of the object.'''
        return super().lights_on()

    def turn(self, row_start: int, row_end: int, col_start: int, col_end: int, val: int) -> None:
        '''
        This function decreases or increases brightness by 1 unit at each position in the specified range of rows and 
        columns in the lightshows attribute of the object to the specified value.

        The lower boundary is 0. There is no upper boundary.
        '''
        for row in range(row_start, row_end + 1):
            for col in range(col_start, col_end + 1):
                if val == 1:
                    self.lightshows[row][col] += 1
                else:
                    self.lightshows[row][col] -= 1 if self.lightshows[row][col] > 0 else 0

    def toggle(self, row_start: int, row_end: int, col_start: int, col_end: int) -> None:
        '''
        This function increases brightness by 2 units at each position in the specified range of rows and columns in the 
        lightshows attribute of the object.

        There is no upper boundary.
        '''
        for row in range(row_start, row_end + 1):
            for col in range(col_start, col_end + 1):
                self.lightshows[row][col] += 2

    def __str__(self):
        return f'In this {self.ROWS}x{self.COLS} grid of lights, there are {self.lights_on()} brightness units.'


if __name__ == '__main__':
    lightshow_event = Lightshows()
    lightshow_event.from_file('coding_challenge_input.txt')
    print(lightshow_event.lights_on())

    upgraded_lightshow_event = Upgraded_Lightshows()
    upgraded_lightshow_event.from_file('coding_challenge_input.txt')
    print(upgraded_lightshow_event.lights_on())