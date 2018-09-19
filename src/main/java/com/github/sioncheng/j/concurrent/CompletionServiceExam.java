package com.github.sioncheng.j.concurrent;

import java.util.concurrent.*;

public class CompletionServiceExam {

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        ExecutorService service = Executors.newCachedThreadPool();
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(service);
        for (int i = 0; i < 5; i++) {
            completionService.submit(new TaskInteger(i));
        }
        service.shutdown();
        //will block
        for (int i = 0; i < 5; i++) {
            Future<Integer> future = completionService.take();
            System.out.println(future.get());
        }
    }

    static class TaskInteger implements Callable<Integer> {
        private final int sum;

        TaskInteger(int sum)  {
            this.sum = sum;
        }

        @Override
        public Integer call() throws Exception {
            TimeUnit.SECONDS.sleep(sum);
            return sum * sum;
        }
    }
}
