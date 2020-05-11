package schutzengel.com.safelifemonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsClient extends BroadcastReceiver {
    protected static Boolean wurdeEmpfangen = false;

    public SmsClient() {
    }

    /**
     * Senden einer Sms and eine Telefonnummer
     * @param telefonNummer
     * @param text
     */
    public static synchronized void senden(String telefonNummer, String text) {
        try
        {
            wurdeEmpfangen = false;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendMultipartTextMessage(telefonNummer, null, smsManager.divideMessage(text), null, null);
            Log.i("SmsClient","Sms wurde gesendet");
        } catch (Exception e) {
            Log.e("SmsClient", "Sms konnte nicht gesendet werden. Fehlermeldung: " + e.getMessage());
        }
    }

    /**
     * Abfrage, ob SMS Empfangen wurde
     * @return
     */
    public static synchronized boolean wurdeEmpfangen() {
        final Boolean benachrichtigungEmpfangen = wurdeEmpfangen;
        wurdeEmpfangen = false;
        return benachrichtigungEmpfangen;
    }

    /**
     * Wenn eine SMS Empfangen wurde, wird geprüft, ob diese von einem Notfallkontakt stammt
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle data = intent.getExtras();

                Object[] pdus = (Object[]) data.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    String sender = smsMessage.getDisplayOriginatingAddress();
                    for (NotfallKontakt n : Datenbank.getInstanz().getNotfallKontakte()) {
                        if (n.getAlarmTelefonNummer().equals(sender)) {
                            wurdeEmpfangen = true;
                            Log.i("SmsClient", "Sms wurde von " + sender + " empfangen");
                        }
                    }
                    if (!wurdeEmpfangen) {
                        Log.i("SmsClient", "Sms wurde von empfangen, aber von keinem Notfallkontakt");
                    }
                }
            }
        }
        catch (Exception e)
        {
            Log.e("SmsClient", "Sms konnte nicht empfangen werden. Fehlermeldung: " + e.getMessage());
        }
    }
}