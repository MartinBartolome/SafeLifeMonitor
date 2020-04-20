package schutzengel.com.safelifemonitor;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;

public class EreignisAlarmAufheben extends Ereignis {
    private  String text = "";

    protected enum Key {
        Text
    }

    public EreignisAlarmAufheben(Message message) {
        Bundle bundle = message.getData();
        this.text = bundle.getString(Key.Text.toString());
        EreignisAlarmAusloesen.StopAlarm();
    }

    @Override
    public int getIdentifier() {
        return 0;
    }

    @Override
    Message toMessage()
    {
        Message message = super.toMessage();
        Bundle bundle = new Bundle();
        bundle.putString(Key.Text.toString(), this.text);
        message.setData(bundle);
        return message;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}