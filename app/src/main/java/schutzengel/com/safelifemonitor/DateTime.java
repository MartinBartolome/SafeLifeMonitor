package schutzengel.com.safelifemonitor;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.time.LocalDate;

public class DateTime {
    public static long getEpochTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getTodayMidnightEpochTimestamp() {
        return (LocalDate.now().toEpochDay() * 86400);
    }
}
