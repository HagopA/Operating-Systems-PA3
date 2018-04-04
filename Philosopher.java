import common.BaseThread;

import java.util.Random;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;
	int methodToExe;
	enum PhilStatus {EATING, THINKING, TALKING}
	PhilStatus myStatus = PhilStatus.THINKING;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			System.out.println("TID " + getTID() + " has started eating.");
			myStatus = PhilStatus.EATING;
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("TID " + getTID() + " is done eating.");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		try
		{
			System.out.println("TID " + getTID() + " has started thinking.");
			myStatus = PhilStatus.THINKING;
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("TID " + getTID() + " is done thinking.");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}
	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */
	public void talk()
	{
		// ...
		System.out.println("TID " + getTID() + " started talking.");
		myStatus = PhilStatus.TALKING;
		yield();
		saySomething();
		yield();
		System.out.println("TID " + getTID() + " is done talking.");

		// ...
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			DiningPhilosophers.soMonitor.pickUp(getTID());

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID());

			think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			Random ranNum = new Random();
			methodToExe = ranNum.nextInt(3) + 1;

			if(methodToExe == 2)
			{
				// Some monitor ops down here...
				DiningPhilosophers.soMonitor.requestTalk();
				talk();
				DiningPhilosophers.soMonitor.endTalk();
				// ...
			}

			yield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
	int rightPi;
	int leftPi;
	int current;

//	private void getLeftandRightPi(Philosopher rightPi, Philosopher leftPi, Philosopher current, int piTID)
// {
//		for(Philosopher i : DiningPhilosophers.allPhilosophers)
// 		{
//			if (i.getTID() == ((piTID + 1)%DiningPhilosophers.allPhilosophers.length))
// 			{
//				rightPi = i;
//			}
// 			else if (i.getTID() == ((piTID + 2)%DiningPhilosophers.allPhilosophers.length))
// 			{
//				leftPi = i;
//			}
// 			else if (i.getTID() == piTID)
// 			{
//				current = i;
//			}
//		}
//	}
}

// EOF