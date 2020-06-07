package schutzengel.com.safelifemonitor.smsclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

import schutzengel.com.safelifemonitor.datenbank.Datenbank;
import schutzengel.com.safelifemonitor.datenbank.NotfallKontakt;

public class SmsClient extends BroadcastReceiver {
    protected static Boolean wurdeEmpfangen = false;

    /**
     * Default Konstruktor
     */
    public SmsClient() {
    }

    /**
     * Senden einer Sms and eine Telefonnummer
     *
     * @param telefonNummer Telefonnummer
     * @param text Text
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
     * @return benachrichtigung empfangen?
     */
    public static synchronized boolean wurdeEmpfangen() {
        final Boolean benachrichtigungEmpfangen = wurdeEmpfangen;
        wurdeEmpfangen = false;
        return benachrichtigungEmpfangen;
    }

    /**
     * Wenn eine SMS Empfangen wurde, wird geprüft, ob diese von einem Notfallkontakt stammt
     * @param context Applikationscontext
     * @param intent Intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (Objects.requireNonNull(intent.getAction()).equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle data = intent.getExtras();
                assert data != null;
                Object[] pdus = (Object[]) data.get("pdus");
                assert pdus != null;
                for (Object o : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) o, data.getString("format"));
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    for (NotfallKontakt notfallKontakt : Datenbank.getInstanz(context).getNotfallKontakte()) {
                        if (notfallKontakt.getAlarmTelefonNummer().equals(sender)) {
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