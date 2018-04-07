/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
import common.BaseThread;
public class Monitor extends DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	Philosopher everyone[] = new Philosopher[4];
	public synchronized void copyPhilArr(Philosopher phil[])
	{
		everyone = phil;
	}
	private synchronized boolean canPickUp(int targetPhil)
	{
		if(this.everyone[targetPhil+1].myStatus == Philosopher.PhilStatus.EATING || this.everyone[targetPhil-1].myStatus == Philosopher.PhilStatus.EATING)
		{
			return false;
		}
		return true;
	}
	 /**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{

		if(canPickUp(piTID))
		{
			this.notifyAll();
			this.everyone[piTID].myStatus = Philosopher.PhilStatus.EATING;
		}
		else
		{
			try
			{
				this.wait();
			}
			catch(InterruptedException e)
			{

			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		this.everyone[piTID].myStatus = Philosopher.PhilStatus.THINKING;
		this.notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF
