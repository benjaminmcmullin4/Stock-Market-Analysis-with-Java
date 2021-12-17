package cs2420FinalProject;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


class QueueExecutionMultithreading extends Thread {

	private ExecutionQueue queue;

	public QueueExecutionMultithreading(ExecutionQueue queue) {
		this.queue = queue;
	}

	public void run()
	{
		System.out.println("QueueExecutionMultithreading has Started");

		int sleepCounter = 0;
		int secondsOfSleep = 10;

		// while true, loop inside here
		while (true) {


			// wait 10 milliseconds if the Queue is empty
			if (this.queue.isEmpty()) {

				if (sleepCounter > 100*secondsOfSleep) {
					queue.finish();
					break;
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				sleepCounter++;
				continue;
			}

			sleepCounter = 0;

			QueueNode node = queue.pop();
			node.execute();

		}
	}
}

public class ExecutionQueue {

	private int length = 0;
	public QueueNode firstNode, lastNode;

	public QueueNode first() {
		return firstNode;
	}
	public QueueNode last() {
		return lastNode;
	}
	public boolean isEmpty() {
		return this.first() == null;
	}
	public int size() {
		return length;
	}

	/*
		Returns the first QueueNode
			and moves the second to the first
		Returns null is Queue is Empty
	*/
	public QueueNode pop() {

		if (this.isEmpty()) {
			return null;
		}

		// Find firstNode and return
		QueueNode oldFirstNode = firstNode;
		
		// Make the first element, the second element
		firstNode = firstNode.next;

		if (this.isEmpty()) {
			lastNode = null;
		}

		length--;

		System.out.println("Pop: "+oldFirstNode+" -> Length: "+this.size()+"\n\t -> Next: "+firstNode);
		return oldFirstNode;
    }

    /*
    Throws a NoSuchElementException is the Queue is Empty
     */
    public QueueNode remove() {
		QueueNode node = this.pop();
		if (node == null) {
			throw new NoSuchElementException("Queue is empty");
		}
		return node;
	}
	
	// maybe use node
	public void add(QueueNode node) {
		System.out.println("Add: "+node+" -> Length: "+this.size()+"\n");

		if (this.isEmpty()) {
			firstNode = node;
		} else {
			lastNode.next = node;
		}
		lastNode = node;
		length++;
	}

	public void run() {
		QueueExecutionMultithreading thread = new QueueExecutionMultithreading(this);
		thread.start();
	}

	public void finish() {
		Account.queueFinish();
	}
}