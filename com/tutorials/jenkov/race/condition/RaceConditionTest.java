package com.tutorials.jenkov.race.condition;

public class RaceConditionTest {

	public static void main(String[] args) throws InterruptedException {
		/*final ExecutorService service = Executors.newCachedThreadPool();
		final Counter c = new Counter();
		service.submit(() -> c.add(10));
		service.submit(() -> c.add(1));
		service.submit(() -> c.add(5));
		service.submit(() -> c.add(9));
		service.submit(() -> c.add(15));*/
		
		final Counter c = new Counter();
		Runnable command1 = () -> c.add(10);
		Runnable command2 = () -> c.add(1);
		Runnable command3 = () -> c.add(5);
		Runnable command4 = () -> c.add(9);
		Runnable command5 = () -> c.add(15);
		
		Thread thread1 = new Thread(command1);
		Thread thread2 = new Thread(command2);
		Thread thread3 = new Thread(command3);
		Thread thread4 = new Thread(command4);
		Thread thread5 = new Thread(command5);
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		thread5.join();

		System.out.println("Race condition result --> " + c.getCount());
	}

}

class Counter {
	protected long count = 0;

	public void add(long value) {
		this.count = this.count + value;
	}

	public long getCount() {
		return count;
	}
}
