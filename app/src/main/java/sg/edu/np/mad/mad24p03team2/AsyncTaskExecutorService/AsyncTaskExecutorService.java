package sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService;

import android.os.Handler;
import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.DietPlanClass;
import sg.edu.np.mad.mad24p03team2.DatabaseFunctions.FoodItemClass;

public abstract class AsyncTaskExecutorService < Params, Progress, Result > {

    private ExecutorService executor;
    private Handler handler;

    protected AsyncTaskExecutorService() {
        executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public Handler getHandler() {
        if (handler == null) {
            synchronized(AsyncTaskExecutorService.class) {
                handler = new Handler(Looper.getMainLooper());
            }
        }
        return handler;
    }

    protected void onPreExecute() {
        // Override this method wherever you want to perform task before background execution get started
    }

    protected abstract Result doInBackground(Params... params);

    protected abstract ArrayList<FoodItemClass> doInBackground();

    protected abstract ArrayList<FoodItemClass> doInBackground(String name);

    protected abstract DietPlanClass doInBackground(String name, String trackBloodSugar);

    protected abstract void onPostExecute(Result result);

    protected void onProgressUpdate(@NotNull Progress value) {
        // Override this method wherever you want update a progress result
    }

    // used for push progress report to UI
    public void publishProgress(@NotNull Progress value) {
        getHandler().post(() -> onProgressUpdate(value));
    }

    public void execute() {
        execute(null);
    }

    public void execute(Params... params) {
        getHandler().post(() -> {
            onPreExecute();
            executor.execute(() -> {
                Result result = doInBackground(params);
                getHandler().post(() -> onPostExecute(result));
            });
        });
    }

    public void shutDown() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public boolean isCancelled() {
        return executor == null || executor.isTerminated() || executor.isShutdown();
    }
}
