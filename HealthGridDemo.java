/*
	Title:  COT 3210 Programming Project
	Author: Matthew Boyette
	Date:   3/22/2014
	
	This application demonstrates a class that provides a graphical display to the user that shows a simulation of an infection spreading through
	a population operating under certain fundamental rules.
*/

import api.gui.*;
import api.util.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HealthGridDemo
{
	private static boolean		debugMode	= false;
	private static HealthGrid	grid		= null;
	private static int			iterations	= 1;
	
	public static final void main(final String[] args)
	{
		ApplicationWindow mainWindow = null;
		int               choice     = Support.promptDebugMode(mainWindow);
		
		debugMode = (choice == JOptionPane.YES_OPTION);
		
		// Define a self-contained ActionListener event handler.
		EventHandler myActionPerformed = new EventHandler()
		{
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if ((arguments.length <= 1) || (arguments.length > 2))
				{
					throw new IllegalArgumentException("myActionPerformed Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ActionEvent))
				{
					throw new IllegalArgumentException("myActionPerformed Error : argument[0] is of incorrect type.");
				}
				else if (!(arguments[1] instanceof ApplicationWindow))
				{
					throw new IllegalArgumentException("myActionPerformed Error : argument[1] is of incorrect type.");
				}
				
				ActionEvent			event  = (ActionEvent)arguments[0];
				ApplicationWindow	window = (ApplicationWindow)arguments[1];
				
				/*
					JDK 7 allows string objects as the expression in a switch statement.
					This generally produces more efficient byte code compared to a chain of if statements.
					http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
				*/
				switch (event.getActionCommand())
				{
					case "Initialize":
						
						HealthGridDemo.iterations = 1;
						HealthGridDemo.grid.initializeConfiguration();
						window.setTitle("Health Grid Application - Iteration: " + HealthGridDemo.iterations);
						break;
						
					case "Iterate A":
					case "Iterate B":
					case "Iterate C":
						
						final int NUM_ITERATIONS = Support.getIntegerInputString(window, "How many iterations?", "Number of Iterations");
						for (int i = 0; i < NUM_ITERATIONS; i++, HealthGridDemo.iterations++)
						{
							HealthGridDemo.grid.iterateConfiguration(event.getActionCommand());
							
							if ((HealthGridDemo.iterations % 1000) == 0)
							{
								if (HealthGridDemo.debugMode)
								{
									Support.displayDebugMessage(window, "Infection.\n");
								}
								
								HealthGridDemo.grid.injectInfection();
							}
							
							window.setTitle("Health Grid Application - Iteration: " + HealthGridDemo.iterations);
						}
						break;
						
					case "Randomize":
						
						HealthGridDemo.iterations = 1;
						HealthGridDemo.grid.randomizeConfiguration();
						window.setTitle("Health Grid Application - Iteration: " + HealthGridDemo.iterations);
						break;
						
					default:
						
						break;
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		EventHandler myDrawGUI = new EventHandler()
		{
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if (arguments.length <= 0)
				{
					throw new IllegalArgumentException("myDrawGUI Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ApplicationWindow))
				{
					throw new IllegalArgumentException("myDrawGUI Error : argument[0] is of incorrect type.");
				}
				
				ApplicationWindow	window		= (ApplicationWindow)arguments[0];
				JPanel				buttonPanel	= new JPanel();
				Container			contentPane	= window.getContentPane();
				JPanel				gridPanel	= new JPanel();
				JButton				initialize	= new JButton("Initialize");
				JButton				iterateA	= new JButton("Iterate A");
				JButton				iterateB	= new JButton("Iterate B");
				JButton				iterateC	= new JButton("Iterate C");
				JButton				randomize	= new JButton("Randomize");
				
				final int rows = Support.getIntegerInputString(window, "How many rows?", "Number of Rows");
				final int cols = Support.getIntegerInputString(window, "How many columns?", "Number of Columns");
				HealthGridDemo.grid = new HealthGrid(rows,cols);
				
				buttonPanel.setLayout(new FlowLayout());
				buttonPanel.add(initialize);
				buttonPanel.add(iterateA);
				buttonPanel.add(iterateB);
				buttonPanel.add(iterateC);
				buttonPanel.add(randomize);
				gridPanel.setLayout(new FlowLayout());
				gridPanel.add(HealthGridDemo.grid);
				contentPane.setLayout(new BorderLayout());
				contentPane.add(buttonPanel, BorderLayout.NORTH);
				contentPane.add(gridPanel, BorderLayout.CENTER);
				initialize.addActionListener(window);
				iterateA.addActionListener(window);
				iterateB.addActionListener(window);
				iterateC.addActionListener(window);
				randomize.addActionListener(window);
			}
		};
		
		mainWindow = new ApplicationWindow(null, "Health Grid Application - Iteration: " + HealthGridDemo.iterations, new Dimension(640, 480), debugMode, false, 
			myActionPerformed, myDrawGUI);
		mainWindow.pack();
	}
}