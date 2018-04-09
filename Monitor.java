
/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
/*	Programming Assignment 3
	Name: Hagop Awakian, Bilal Rana
	Student ID: 27747632, 40013408
	COMP 346 Section WW
	Instructor: Kerly Titus
 */
public class Monitor extends DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private Philosopher everyone[];
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		everyone = new Philosopher[piNumberOfPhilosophers];
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	public synchronized void copyPhilArr(Philosopher phil[])
	{
		for(int i = 0; i < phil.length; i++)
		{
			everyone[i] = phil[i];
		}
	}
	private synchronized boolean canPickUp(int targetPhil)
	{
		int rightPhil = (targetPhil+1) % everyone.length;
		int leftPhil = (everyone.length+targetPhil-1) % everyone.length;
		if(this.everyone[rightPhil].myStatus == Philosopher.PhilStatus.EATING || this.everyone[leftPhil].myStatus == Philosopher.PhilStatus.EATING)
		{
			return false;
		}
		return true;
	}

	private synchronized boolean isTalking()
	{
		for(int i = 0; i < everyone.length; i++)
		{
			if(this.everyone[i].myStatus == Philosopher.PhilStatus.TALKING)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		boolean flag = true;
		while(flag)
		{
			if(canPickUp(piTID-1))
			{
				this.everyone[piTID-1].myStatus = Philosopher.PhilStatus.EATING;
				flag = false;
			}
			else
			{
				try
				{
					this.wait();
				}
				catch(InterruptedException e)
				{
					System.out.println("Thread error.");
				}
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		this.everyone[piTID-1].myStatus = Philosopher.PhilStatus.THINKING;
		this.notifyAll();
	}
	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk(final int piTID)
	{
		if(this.isTalking())
		{
			try
			{
				this.wait();
			}
			catch(InterruptedException e)
			{
				System.out.println("Thread error.");
			}
		}
		this.everyone[piTID-1].myStatus = Philosopher.PhilStatus.TALKING;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk(final int piTID)
	{

		this.everyone[piTID-1].myStatus = Philosopher.PhilStatus.THINKING;
		this.notifyAll();
	}
}

// EOF
