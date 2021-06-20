package org.java.threads.executorservice;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class DemoExecutorService {

    public static void main(String[] args) {
        // simulateCPUIntensiveTasksWithExecutorService();
        // simulateIOIntensiveTasksWithExecutorService();
        simulateCallableTasksWithExecutorService();
    }

    private static void simulateCPUIntensiveTasksWithExecutorService(){
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);

        for(int i = 0; i < 100; i++){
            executorService.execute(new CPUIntensiveTask(i));
        }
        executorService.shutdown();
    }

    private static void simulateIOIntensiveTasksWithExecutorService(){
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);

        for(int i = 0; i < 100; i++){
            executorService.execute(new IOIntensiveTask(i));
        }
        executorService.shutdown();
    }

    private static void simulateCallableTasksWithExecutorService(){
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadCount);
        List<Future> allFutures = new ArrayList<Future>();
        for(int i = 0; i < 100; i++){
            Future<Integer> integerFuture = executorService.submit(new CallableTask(i));
            allFutures.add(integerFuture);
        }

        for(int i = 0; i < allFutures.size(); i++){
            Future<Integer> future = allFutures.get(i);
            try {
                Integer futureResult = future.get();
                System.out.println(String.format("Task: %d => Result: %d", i, futureResult));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    static class CPUIntensiveTask implements Runnable{

        private int taskNumber;

        public CPUIntensiveTask(int taskNumber){
            this.taskNumber = taskNumber;
        }

        @Override
        public void run() {
            System.out.println("CPU Intensive Task: "+ taskNumber + " on Thread"+ Thread.currentThread());
        }
    }

    static class IOIntensiveTask implements Runnable{

        private int taskNumber;

        public IOIntensiveTask(int taskNumber){
            this.taskNumber = taskNumber;
        }

        @Override
        public void run() {
            System.out.println("IO Intensive Task: " + taskNumber + " on Thread" + Thread.currentThread());
            try {
                System.out.println("[Waiting] IO Intensive Task: " + taskNumber + " on Thread" + Thread.currentThread());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("[Completed] IO Intensive Task: " + taskNumber + " on Thread" + Thread.currentThread());
            }
        }
    }

    static class CallableTask implements Callable<Integer>{

        private int taskNumber;

        public CallableTask(int taskNumber){
            this.taskNumber = taskNumber;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(Math.abs(new Random().nextInt(6)) * 1000);
            return new Random().nextInt();
        }
    }
}
