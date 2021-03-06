package com.tutorials.jenkov.race.condition;

//missed signals testing 
public class MissedSignalsTest {

	public static void main(String args[]) throws InterruptedException {
		MyWaitNotify ms = new MyWaitNotify();
		ms.doNotify();
		ms.doWait();
	}
}

class MyWaitNotify {

	private MyMonitorObject monitor = new MyMonitorObject();
	private boolean hasSignalled = false;

	public void doWait() throws InterruptedException {
		synchronized (monitor) {
			if (!hasSignalled) {
				monitor.wait();
			}
		}
	}

	public void doNotify() {
		synchronized (monitor) {
			hasSignalled = true;
			monitor.notify();
		}
	}

}

class MyMonitorObject {
}