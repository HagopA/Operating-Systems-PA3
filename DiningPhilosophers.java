/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
/*	Programming Assignment 3
	Name: Hagop Awakian, Bilal Rana
	Student ID: 27747632, 40013408
	COMP 346 Section WW
	Instructor: Kerly Titus
 */
import java.util.InputMismatchException;
import java.util.Scanner;
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */


	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Please enter the number of philosophers greater or equal to 2. For default settings, enter -1: ");
			String iPhilosophers = "";
			try
			{
				iPhilosophers = keyboard.next();
				while(Integer.parseInt(iPhilosophers) < 2)
				{
					if(Integer.parseInt(iPhilosophers) == -1)
					{
						iPhilosophers = Integer.toString(DEFAULT_NUMBER_OF_PHILOSOPHERS);
						break;
					}
					System.out.print("Error: Please enter a number greater or equal to 2: ");
					iPhilosophers = keyboard.next();
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println(e.getMessage());
				System.exit(0);
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(Integer.parseInt(iPhilosophers));

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[Integer.parseInt(iPhilosophers)];

			// Let 'em sit down
			for(int j = 0; j < Integer.parseInt(iPhilosophers); j++)
			{
				aoPhilosophers[j] = new Philosopher();
			}
			soMonitor.copyPhilArr(aoPhilosophers);
			for(int j = 0; j < Integer.parseInt(iPhilosophers); j++)
			{
				aoPhilosophers[j].start();
			}
			System.out.println
					(
							iPhilosophers +
									" philosopher(s) came in for a dinner."
					);

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < Integer.parseInt(iPhilosophers); j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
