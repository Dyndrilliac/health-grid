/*
 * Title: Health Grid Demo (COT 3210 Programming Project)
 * Author: Matthew Boyette
 * Date: 3/22/2014
 * 
 * This application demonstrates a class that provides a graphical display to the user that shows a simulation of an infection spreading through a
 * population operating under certain fundamental rules.
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import api.gui.draw.HealthGrid;
import api.gui.swing.ApplicationWindow;
import api.util.EventHandler;
import api.util.Support;

public class HealthGridDemo
{
    public final static void main(final String[] args)
    {
        new HealthGridDemo(args);
    }
    
    private HealthGrid        grid        = null;
    private boolean           isDebugging = false;
    private int               iterations  = 1;
    private ApplicationWindow window      = null;
    
    public HealthGridDemo(final String[] args)
    {
        this.setDebugging(Support.promptDebugMode(this.getWindow()));
        
        // Define a self-contained ActionListener event handler.
        // @formatter:off
        EventHandler<HealthGridDemo> myActionPerformed = new EventHandler<HealthGridDemo>(this)
        {
            private final static long serialVersionUID = 1L;

            @Override
            public final void run(final AWTEvent event)
            {
                ActionEvent actionEvent = (ActionEvent)event;
                HealthGridDemo parent = this.getParent();
                ApplicationWindow window = parent.getWindow();

                /*
                 * JDK 7 allows string objects as the expression in a switch statement. This generally produces more efficient byte code compared to a
                 * chain of if statements. http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
                 */
                switch (actionEvent.getActionCommand())
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
                            parent.getGrid().iterateConfiguration(actionEvent.getActionCommand());

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
        EventHandler<HealthGridDemo> myDrawGUI = new EventHandler<HealthGridDemo>(this)
        {
            private final static long serialVersionUID = 1L;

            @Override
            public final void run(final ApplicationWindow window)
            {
                HealthGridDemo parent = this.getParent();
                JPanel buttonPanel = new JPanel();
                Container contentPane = window.getContentPane();
                JPanel gridPanel = new JPanel();
                JButton initialize = new JButton("Initialize");
                JButton iterateA = new JButton("Iterate A");
                JButton iterateB = new JButton("Iterate B");
                JButton iterateC = new JButton("Iterate C");
                JButton randomize = new JButton("Randomize");
                final int rows = Support.getIntegerInputString(window, "How many rows?", "Number of Rows");
                final int cols = Support.getIntegerInputString(window, "How many columns?", "Number of Columns");

                parent.setGrid(new HealthGrid(rows, cols));
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.add(initialize);
                buttonPanel.add(randomize);
                buttonPanel.add(iterateA);
                buttonPanel.add(iterateB);
                buttonPanel.add(iterateC);
                gridPanel.setLayout(new FlowLayout());
                gridPanel.add(parent.getGrid());
                contentPane.setLayout(new BorderLayout());
                contentPane.add(buttonPanel, BorderLayout.NORTH);
                contentPane.add(gridPanel, BorderLayout.CENTER);
                initialize.addActionListener(window);
                initialize.setFont(Support.DEFAULT_TEXT_FONT);
                iterateA.addActionListener(window);
                iterateA.setFont(Support.DEFAULT_TEXT_FONT);
                iterateB.addActionListener(window);
                iterateB.setFont(Support.DEFAULT_TEXT_FONT);
                iterateC.addActionListener(window);
                iterateC.setFont(Support.DEFAULT_TEXT_FONT);
                randomize.addActionListener(window);
                randomize.setFont(Support.DEFAULT_TEXT_FONT);
            }
        };

        this.setWindow(new ApplicationWindow(null,
            "Health Grid Application - Iteration: " + this.getIterations(),
            new Dimension(800, 600),
            this.isDebugging(),
            false,
            myActionPerformed,
            myDrawGUI));
        this.getWindow().pack();
        // @formatter:on
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
        return this.isDebugging;
    }
    
    public final void setDebugging(final boolean isDebugging)
    {
        this.isDebugging = isDebugging;
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