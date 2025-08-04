package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Threadpool {
    private static final int threadPoolSize = 5;  // core pool size  explain he
    private static final int maxThreadPoolSize = 10; // max pool size
    private static final int keepAliveTime = 10; // keep alive time
    private static ExecutorService executorService; // thread pool


   public static ExecutorService getExecutorService() {
        if(executorService == null) {
            executorService = new ThreadPoolExecutor(threadPoolSize, maxThreadPoolSize, keepAliveTime, java.util.concurrent.TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }
        return executorService;
    }
    // thread pool executor
}
