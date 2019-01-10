package com.tutorials.jenkov.signalling;

//Signalling via shared resource
//Busy waiting
public class SignallingViaSharedResourceTest {

	public static void main(String args[]) throws InterruptedException {
		MySignal ms = new MySignal();

		new Thread(new ThreadBCosumer(ms), "THREAD-B-CONSUMER").start();
		Thread.sleep(1000);

		Runnable r1 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " ACCESS-> " + ms.hasDataToProcess());
		};
		Runnable r3 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " ACCESS-> " + ms.hasDataToProcess());
		};
		Runnable r4 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " ACCESS-> " + ms.hasDataToProcess());
		};

		Runnable r2 = () -> {
			String name = Thread.currentThread().getName();
			System.out.println(name + " SET-> " + ms.setHasDataToProcess(!ms.hasDataToProcess()));
		};

		new Thread(r1, "RCommand_1").start();
		Thread.sleep(2000);
		new Thread(r3, "RCommand_3").start();
		Thread.sleep(2000);
		new Thread(r4, "RCommand_4").start();
		Thread.sleep(1000);
		new Thread(r2, "RCommand_2").start();

	}
}

//Busy waiting
class ThreadBCosumer implements Runnable {
	private final MySignal sharedSignal;

	public ThreadBCosumer(MySignal signal) {
		this.sharedSignal = signal;
	}

	@Override
	public void run() {
		while (!sharedSignal.hasDataToProcess()) {
			try {
				System.out.println(Thread.currentThread().getName() + " Busy waiting...");
				// sharedSignal.wait(1000);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// processData
		String threadName = Thread.currentThread().getName();
		System.out.println("Data has been processed by " + threadName + " " + sharedSignal.hasDataToProcess());

		// set signal back to false
		sharedSignal.setHasDataToProcess(false);

		// notify all data producing threads
		// sharedSignal.notifyAll();
	}

}

class MySignal {

	private boolean hasDataToProcess = false;

	public synchronized boolean hasDataToProcess() {
		return this.hasDataToProcess;
	}

	public synchronized boolean setHasDataToProcess(boolean hasData) {
		this.hasDataToProcess = hasData;
		return true;
	}

}