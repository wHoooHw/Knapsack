3D knapsack solver
Welcome to the Pentris 3D game! This JavaFX application is a like a "simulator" as a company that needs to ship boxes into a truck. This application helps you fit the different shaped boxes (in our case in Pentominoes shape or Parcels) to fill the container with the most valuable boxes. It is also used to minimize the empty space using bigger boxes (Parcels) to fill as much as possible in the container.

Getting Started
To run the Application, follow these steps:

Set Up JavaFX:

Ensure that you have JavaFX installed on your system. You may need to configure the launch settings in your development environment to include the path to JavaFX.

Run Application:

Navigate to the "Phase3" folder and locate the "Main3.java" class.
Run the "Main3" file to start the Pentris game.
VM Args (Optional):

If required, add the necessary VM arguments to your development environment's launch configuration to specify the path to the JavaFX library.

Application Overview
The game present a Container in 3D that using pentominos or parcels and by choosing which algorithm you want to perform the action and then press start

Application Mechanics

Once you start the program, you will be shown a GUI. On the left side will be the vizualization and on the right side will be your option. 

First, you will choose if you want to fill the container with pentominos or shapes and than you need to choose which algoritm you want to perfome the action and press start.

Greedy:
This algorithm is pretty slow but after some time will fill the container with pieces.

Dancing links:
This is our fastest algorithm and will fill the container in less than a second.

TO KNOW:
It is mathematical imposible to fully fill the container with parcels. The result will be a container with empty spaces. But our algorithm is correct.


Enjoy the creativity and inovativity of Group 40