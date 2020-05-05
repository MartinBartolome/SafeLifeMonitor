package schutzengel.com.safelifemonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsClient extends BroadcastReceiver {
    protected static Boolean wurdeEmpfangen = false;

    public SmsClient() {
    }

    public static synchronized void senden(String telefonNummer, String text) {
        try
        {
            wurdeEmpfangen = false;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendMultipartTextMessage(telefonNummer, null, smsManager.divideMessage(text), null, null);
        } catch (Exception e) {
            Log.d("SmsClient", e.getMessage());
        }
    }

    public static synchronized boolean wurdeEmpfangen() {
        final Boolean benachrichtigungEmpfangen = wurdeEmpfangen;
        wurdeEmpfangen = false;
        return benachrichtigungEmpfangen;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            wurdeEmpfangen = true;
        }
    }
}