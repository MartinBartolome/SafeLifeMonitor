package schutzengel.com.safelifemonitor.HMI;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import androidx.appcompat.app.AppCompatActivity;
import schutzengel.com.safelifemonitor.Workflows.Startup.Workflow;

public abstract class Activity extends AppCompatActivity {
    protected schutzengel.com.safelifemonitor.Core.Workflows.IWorkflow workflow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void runService(Class<?> classType){
        if (null != this.workflow) {
            this.workflow.stop();
            this.workflow = null;
            unbindService(this.workflowConnection);
        }
        try {
            Intent intent = new Intent(this, classType);
            intent.putExtra("Messenger", new Messenger(this.observer));
            bindService(intent, this.workflowConnection, BIND_AUTO_CREATE);
            startService(intent);
        } catch (Exception e) {
            String test = e.getMessage();
        }
    }

    private ServiceConnection workflowConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Workflow.Binder binder = (Workflow.Binder)(service);
            workflow = binder.getWorkflow();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != this.workflow) {
            this.workflow.stop();
            this.workflow = null;
        }
        if (null != this.workflowConnection) {
            unbindService(this.workflowConnection);
        }
        this.workflowConnection = null;
    }

    protected Handler observer = new Handler() {
        @Override
        public void handleMessage(Message message) {
            update(message);
            super.handleMessage(message);
        }
    };

    protected void update(Message message) {
    }
}
