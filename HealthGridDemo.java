/*
	Title:  Health Grid Demo (COT 3210 Programming Project)
	Author: Matthew Boyette
	Date:   3/22/2014

	This application demonstrates a class that provides a graphical display to the user that shows a simulation of an infection spreading through
	a population operating under certain fundamental rules.
*/

import api.gui.ApplicationWindow;
import api.gui.HealthGrid;
import api.util.EventHandler;
import api.util.Support;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HealthGridDemo
{
	public static final void main(final String[] args)
	{
		new HealthGridDemo();
	}
	
	private boolean				debugging	= false;
	private HealthGrid			grid		= null;
	private int					iterations	= 1;
	private ApplicationWindow	window		= null;
	
	public HealthGridDemo()
	{
		this.setDebugging(Support.promptDebugMode(this.getWindow()));
		
		// Define a self-contained ActionListener event handler.
		EventHandler myActionPerformed = new EventHandler(this)
		{
			@Override
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
				
				ActionEvent			event	= (ActionEvent)arguments[0];
				HealthGridDemo		parent	= ((HealthGridDemo)this.parent);
				ApplicationWindow	window	= (ApplicationWindow)arguments[1];
				
				/*
					JDK 7 allows string objects as the expression in a switch statement.
					This generally produces more efficient byte code compared to a chain of if statements.
					http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
				*/
				switch (event.getActionCommand())
				{
					case "Initialize":
						
						parent.setIterations(1);
						parent.getGrid().initializeConfiguration();
						window.setTitle("Health Grid Application - Iteration: " + parent.getIterations());
						break;
					
					case "Iterate A":
					case "Iterate B":
					case "Iterate C":
						
						final int NUM_ITERATIONS = Support.getIntegerInputString(window, "How many iterations?", "Number of Iterations");
						
						for (int i = 0; i < NUM_ITERATIONS; i++)
						{
							parent.getGrid().iterateConfiguration(event.getActionCommand());
							
							if ((parent.getIterations() % 1000) == 0)
							{
								if (parent.isDebugging())
								{
									Support.displayDebugMessage(window, "Infection (" + (parent.getIterations() / 1000) + ")\n");
								}
								
								parent.getGrid().injectInfection();
							}
							
							parent.setIterations(parent.getIterations() + 1);
							window.setTitle("Health Grid Application - Iteration: " + parent.getIterations());
						}
						break;
					
					case "Randomize":
						
						parent.setIterations(1);
						parent.getGrid().randomizeConfiguration();
						window.setTitle("Health Grid Application - Iteration: " + parent.getIterations());
						break;
					
					default:
						
						break;
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		EventHandler myDrawGUI = new EventHandler(this)
		{
			@Override
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
				HealthGridDemo		parent		= ((HealthGridDemo)this.parent);
				JButton				randomize	= new JButton("Randomize");
				
				final int rows = Support.getIntegerInputString(window, "How many rows?", "Number of Rows");
				final int cols = Support.getIntegerInputString(window, "How many columns?", "Number of Columns");
				parent.setGrid(new HealthGrid(rows, cols));
				
				buttonPanel.setLayout(new FlowLayout());
				buttonPanel.add(initialize);
				buttonPanel.add(iterateA);
				buttonPanel.add(iterateB);
				buttonPanel.add(iterateC);
				buttonPanel.add(randomize);
				gridPanel.setLayout(new FlowLayout());
				gridPanel.add(parent.getGrid());
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
		
		this.setWindow(new ApplicationWindow(null, "Health Grid Application - Iteration: " + this.getIterations(), new Dimension(640, 480), this.isDebugging(), false,
			myActionPerformed, myDrawGUI));
		this.getWindow().pack();
	}
	
	public final HealthGrid getGrid()
	{
		return this.grid;
	}
	
	public final int getIterations()
	{
		return this.iterations;
	}
	
	public final ApplicationWindow getWindow()
	{
		return this.window;
	}
	
	public final boolean isDebugging()
	{
		return this.debugging;
	}
	
	public final void setDebugging(final boolean debugging)
	{
		this.debugging = debugging;
	}
	
	public final void setGrid(final HealthGrid grid)
	{
		this.grid = grid;
	}
	
	public final void setIterations(final int iterations)
	{
		this.iterations = iterations;
	}
	
	public final void setWindow(final ApplicationWindow window)
	{
		this.window = window;
	}
}