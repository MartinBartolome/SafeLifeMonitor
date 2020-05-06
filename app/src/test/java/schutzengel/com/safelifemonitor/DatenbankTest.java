package schutzengel.com.safelifemonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DatenbankTest extends Datenbank {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Instanz konnte geladen werden", Datenbank.getInstance());
    }

    @Test
    public void testGetApplikationEinstellungen() {
    }

    @Test
    public void testSet() {
    }

    @Test
    public void testOnCreate() {
        Datenbank.getInstance().onCreate(getWritableDatabase()); // Ich hab absolut keinen Plan, wie man die Datenbank vernünftig testen kann.
        ApplikationEinstellungen applikationEinstellungen = Datenbank.getInstance().getApplikationEinstellungen();
        ArrayList<NotfallKontakt> notfallkontakte = Datenbank.getInstance().getNotfallKontakte();
        assertEquals(1,applikationEinstellungen.istMonitorAktiv());
    }

    @Test
    public void testOnUpgrade() {
    }

    @Test
    public void testSet1() {
    }

    @Test
    public void testSet2() {
    }

    @Test
    public void testGetNotfallKontakte() {
    }

    @Test
    public void testGetNotfallKontakt() {
    }
}