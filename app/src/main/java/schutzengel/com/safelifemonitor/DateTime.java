package schutzengel.com.safelifemonitor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class DateTime {
    /**
     * Lesen der aktuellen Zeit in Sekunden seit 01.01.1970
     *
     * @return Sekunden
     */
    public static long getEpochTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * Lesen des heutigen Mitternachtzeit in Sekunden seit 01.01.1970
     *
     * @return Sekunden
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getTodayMidnightEpochTimestamp() {
        return (LocalDate.now().toEpochDay() * 86400);
    }
}
