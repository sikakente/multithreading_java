package org.java.threads.lesson1;

public class ThreadsWIthRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Running Threads with runnable");
    }

    public static void main(String[] args){
        // example 1
        Thread thread1 = new Thread(new ThreadsWIthRunnable());
        thread1.start();

        Thread thread2 = new Thread(() -> System.out.println("Running Threads with Lambdas"));
        thread2.start();
    }
}
