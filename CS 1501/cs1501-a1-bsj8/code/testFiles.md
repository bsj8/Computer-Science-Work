# CS 1501 Assignment 1 Test Files

__*Note: You are only required to find a single solution for each test file.*__

- `test3a.txt`: Example solutions Simple 3x3 board. This has `392586` solutions. Included is some of them in the order they were found by one example program.

- `test3b.txt`: Restricted 3x3 board. This should have ONE solution only. 

- `test4a.txt`: Simple 4x4 board. This one has `1621736` solutions. 

- `test4b.txt`: Another 4x4 board. This one has 10872485 solutions. 

- `test4c.txt`: Restricted version of example from Assignment Sheet. This one has `47845` solutions. 

- `test4d.txt`: No solution (should be determined quickly if a good pruning algorithm is used). 

- `test4e.txt`: No solution but may take MUCH longer to determine due to the backtracking required. Think about why. Experiment if you'd like with placements of a single x on the board. Some will yield solutions, and others will not, with greatly varying runtimes based on the location of the x. 

- `test4f.txt`: More restricted version of example from Assignment Sheet. This should have ONE solution only. 

- `test5a.txt`: Simple 5x5 board. This one has `53399` solutions. 

- `test6a.txt`: Simple 6x6 board.

- `test6b.txt` This also has a LOT of solutions. For MyDictionary it took about 20 minutes to find the first solution. 

- `test6c.txt`: Board with no solution but that still requires a lot of backtracking. With MyDictionary it will take more than 2.5 hours. 

- `test7a.txt`:
- `test8a.txt`: 
The above are basic 7x7 and 8x8 boards. The run-times for these should be extremely high given the MyDictionary implementation of DictInterface. One thing to keep in mind is that because the words must all be longer, there may in fact be fewer possible solutions than there would be for the smaller board sizes. In fact, neither of these boards has a solution. 

- `test8b.txt` Partial list of solutions This restricted 8x8 board has many solutions but it may take some time to find even one. I have included a partial list of solutions for you to examine. 

- `test8c.txt`: This restricted 8x8 board is a combination of boards test3b.txt and test4f.txt. It has only one solution. With MyDictionary it took an example program about 10 minutes. 
