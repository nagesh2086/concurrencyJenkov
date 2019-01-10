package com.tutorials.jenkov.signalling;

//missed signals testing 
public class MissedSignalsTest {

	public static void main(String args[]) throws InterruptedException {
		MyWaitNotify ms = new MyWaitNotify();
		ms.doNotify();
		ms.doWait();
	}
}

//comment out hassignalled and it's usage and see missing signals scenario
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