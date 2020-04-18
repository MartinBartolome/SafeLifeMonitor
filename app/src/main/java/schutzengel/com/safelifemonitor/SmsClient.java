package schutzengel.com.safelifemonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsClient extends BroadcastReceiver {

    private SmsBenachrichtigung message;

    public SmsBenachrichtigung senden(String telefonNummer, String text) {
        message = new SmsBenachrichtigung(telefonNummer, text);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(message.getTelefonNummer(), null, message.getText(), null, null);

        return message;
    }

    public Boolean smsEmpfangen(String Message) {
        Toast.makeText(HauptActivity.context, "New Message Received: " + Message, Toast.LENGTH_SHORT).show();
        return false;
    }

    //interface
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            String messageBody = smsMessage.getMessageBody();
            mListener.messageReceived(sender, messageBody);
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public interface SmsListener {
        public void messageReceived(String Sender,String messageText);
    }


}