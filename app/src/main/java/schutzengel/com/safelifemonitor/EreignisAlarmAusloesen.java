package schutzengel.com.safelifemonitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.Date;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class EreignisAlarmAusloesen extends Ereignis {

    private static MediaPlayer alarmplayer = null;

    public EreignisAlarmAusloesen()
    {
        AudioManager audioManager = (AudioManager) HauptActivity.context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

        alarmplayer= MediaPlayer.create(HauptActivity.context, R.raw.alarm);
        alarmplayer.setLooping(true);

        alarmplayer.start();
    }

    public static void StopAlarm()
    {
        if(alarmplayer != null)
            alarmplayer.stop();
    }

    @Override
    public int getIdentifier() {
        return 1;
    }
}