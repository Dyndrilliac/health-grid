*******************************************************************

* Title:     Health Grid (COT 3210 Programming Project)
* Author:    [Matthew Boyette](mailto:Dyndrilliac@gmail.com)
* Class:     Computability Theory & Automata (COT 3210)
* Professor: Dr. William 'Chip' Klostermeyer
* Term:      Spring 2014

*******************************************************************

This code makes use of my [Custom Java API](https://github.com/Dyndrilliac/java-custom-api). In order to build this source, you should clone the repository for the API using your Git client, then import the project into your IDE of choice (I prefer Eclipse), and finally modify the build path to include the API project. For more detailed instructions, see the README for the API project.

This repository contains the application that was written for the programming project assigned in COT 3210. The application demonstrates a class that provides a graphical display to the user showing a simulation of an initial infection vector spreading through a population operating under certain fundamental rules.

The grid contains squares, each of which can have one of three states: Alive, Empty, or Infected. Alive squares are green, Empty squares are black, and Infected squares are red. There are five buttons above the grid which are used to control the simulation. The button 'Initialize' clears out all of the grid data from memory and creates a new grid with all squares set to the Empty state. The button 'Randomize' creates a random distribution of Empty/Alive squares.

The three 'Iterate' buttons run the simulation. Iterations can be executed one at a time (single stepping) or multiple times in rapid succession. When you activate one of these buttons, a dialogue box will ask you how many iterations you want to execute.

For more information, please see the original project instructions available [here](http://www.unf.edu/~wkloster/3210/prog.txt).

A pre-compiled JAR binary can be downloaded from [this link](https://www.dropbox.com/s/8ttgqf24izo08yq/COT3210Project_N00868808_MBoyette.jar).
