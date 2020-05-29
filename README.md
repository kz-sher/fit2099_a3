# FIT2099 Assignment 3
A simple starwars game built using `Java`.

## Important files

The design documentation is located under /design-docs/design-documentation.pdf

New UML class and sequence diagrams are located under /design-docs/added_design

Work Breakdown Agreement is located under /design-docs/WBA

## Extra Features

#### Boss Level

In the map, there is a location with the symbol 'E' on it. This is an entrance to a boss level. The boss inside moves randomly each turn, and drops a grenade on its location which damages entities around it. The only way to exit this level is to kill the boss. Once the boss is killed, the boss will then act as the gateway to exit back into the outside world.

#### GUI Interface

To enable this feature, 2 lines must be uncommented :

Line 27 in starwars/Application.java<br />
Line 187 in starwars.swinterfaces/SWGridTextInterface.java<br />

Once this is uncommented, running the code will result in a new window popping out. Instead of entering numerics as commands, movement commands are now done using the following keys :

q - Move northwest <br />
w - Move north <br />
e - Move northeast <br />
a - Move west <br />
d - Move east <br />
z - Move southwest <br />
x - Move south <br />
c - Move southeast <br />

If the Player cannot move in a chosen direction, then nothing will occur when pressing the corresponding buttons.

Any other instructions can be done by clicking the buttons provided at the bottom of the window.
