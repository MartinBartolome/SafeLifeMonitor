package schutzengel.com.safelifemonitor.Core.Threading;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

public abstract class Thread extends Service implements IThread {
    protected IBinder binder = null;

    public Thread() {
        this.binder = new Binder();
    }

    public class Binder extends android.os.Binder {
        public Thread getThread() {
            return Thread.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        super.stopSelf();
        finalize();
    }

    @Override
    public void onCreate() {
        prepare();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new java.lang.Thread(new Runnable() {
            public void run() {
                runningThreadLoop();
            }
        }).start();
        finalize();
        return START_NOT_STICKY;
    }

    protected void prepare() {
    }

    protected abstract void runningThreadLoop();

    protected void finalize() {
    }

    protected void wait(int timeoutMilliseconds) {
        synchronized (this) {
            try {
                super.wait(timeoutMilliseconds);
            } catch (InterruptedException exception) {
            }
        }
    }
}
