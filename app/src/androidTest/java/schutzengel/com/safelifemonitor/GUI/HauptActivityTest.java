package schutzengel.com.safelifemonitor.GUI;


import android.Manifest;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.rule.ServiceTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;
import java.sql.Array;
import java.sql.Time;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import schutzengel.com.safelifemonitor.Bewegungssensor.Bewegungssensor;
import schutzengel.com.safelifemonitor.Datenbank.Datenbank;
import schutzengel.com.safelifemonitor.GUI.HauptActivity;
import schutzengel.com.safelifemonitor.Datenbank.NotfallKontakt;
import schutzengel.com.safelifemonitor.R;
import schutzengel.com.safelifemonitor.Service.Ereignis;
import schutzengel.com.safelifemonitor.Service.EreignisAlarmAufheben;
import schutzengel.com.safelifemonitor.Service.EreignisAlarmAusloesen;
import schutzengel.com.safelifemonitor.Service.MonitorService;
import schutzengel.com.safelifemonitor.Tools.DateTime;

import static android.content.Context.BIND_AUTO_CREATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HauptActivityTest {

    @Rule
    public ActivityTestRule<HauptActivity> mActivityTestRule = new ActivityTestRule<>(HauptActivity.class);

    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.RECEIVE_SMS",
                    "android.permission.SEND_SMS",
                    "android.permission.READ_SMS",
                    "android.permission.READ_PHONE_STATE");

    @Test
    public void T1_T2() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Kontakt-Einstellungen"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withId(R.id.BildPrioritaet1)).perform(click());
        onView(withId(R.id.beschreibungseditor)).perform(replaceText("Tino"),closeSoftKeyboard());
        onView(withId(R.id.telefoneditor)).perform(replaceText("0791111111"),closeSoftKeyboard());

        onView(withId(R.id.BildPrioritaet3)).perform(click());
        onView(withId(R.id.beschreibungseditor)).perform(replaceText("Yara"),closeSoftKeyboard());
        onView(withId(R.id.telefoneditor)).perform(replaceText("0792222222"),closeSoftKeyboard());

        onView(withId(R.id.ButtonSave)).perform(click());


        Assert.assertEquals("Tino",Datenbank.getInstanz().getNotfallKontakte().get(0).getBeschreibung());
        Assert.assertEquals("0791111111",Datenbank.getInstanz().getNotfallKontakte().get(0).getAlarmTelefonNummer());
        Assert.assertEquals(NotfallKontakt.Prioritaet.Prioritaet_1,Datenbank.getInstanz().getNotfallKontakte().get(0).getPrioritaet());

        Assert.assertEquals("Yara", Datenbank.getInstanz().getNotfallKontakte().get(2).getBeschreibung());
        Assert.assertEquals("0792222222", Datenbank.getInstanz().getNotfallKontakte().get(2).getAlarmTelefonNummer());
        Assert.assertEquals(NotfallKontakt.Prioritaet.Prioritaet_3, Datenbank.getInstanz().getNotfallKontakte().get(2).getPrioritaet());
    }

    @Test
    public void T4()
    {

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Einstellungen"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withId(R.id.AktiviereMonitoring)).perform(click());
        onView(withId(R.id.Zeit1_Von)).perform(replaceText("06:00"),closeSoftKeyboard());
        onView(withId(R.id.Zeit1_Bis)).perform(replaceText("17:00"),closeSoftKeyboard());
        onView(withId(R.id.ButtonSave)).perform(click());

        Assert.assertTrue(Datenbank.getInstanz().getApplikationsEinstellungen().getMonitorAktiv());
        Assert.assertEquals("06:00",Datenbank.getInstanz().getApplikationsEinstellungen().getZeiten().get(0));
        Assert.assertEquals("17:00",Datenbank.getInstanz().getApplikationsEinstellungen().getZeiten().get(1));
    }

    @Test
    public void T5() throws TimeoutException {
        // Create the service Intent.
        Intent monitorServiceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        MonitorService.class);


        // Bind the service and grab a reference to the binder.
        IBinder binder = serviceRule.bindService(monitorServiceIntent);

        MonitorService monitorService = ((MonitorService.Binder)binder).getMonitorService();

        Assert.assertTrue(Datenbank.getInstanz().getApplikationsEinstellungen().getMonitorAktiv());

        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        today.setHours(06);
        today.setMinutes(00);
        today.setSeconds(0);
        calendar.setTime(today);
        Assert.assertTrue(calendar.getTimeInMillis()-1000 <= Datenbank.getInstanz().getApplikationsEinstellungen().getSekundenZeit1Von() && Datenbank.getInstanz().getApplikationsEinstellungen().getSekundenZeit1Von() <= calendar.getTimeInMillis()+1000);
        today = new Date();
        today.setHours(17);
        today.setMinutes(00);
        today.setSeconds(0);
        calendar.setTime(today);
        Assert.assertTrue(calendar.getTimeInMillis()-1000 <= Datenbank.getInstanz().getApplikationsEinstellungen().getSekundenZeit1Bis() && Datenbank.getInstanz().getApplikationsEinstellungen().getSekundenZeit1Bis() <= calendar.getTimeInMillis()+1000);


        List<Integer> timearray = Arrays.asList(6,7,8,9,10,11,12,13,14,15,16);

        // Verify that the service is working correctly.
        if(timearray.contains(LocalDateTime.now().getHour())) {
            Assert.assertTrue(monitorService.istInMoitorZeitraum());
        }
        else{
            Assert.assertFalse(monitorService.istInMoitorZeitraum());
        }
    }

    @Test
    public void T6() throws TimeoutException {
        // Create the service Intent.
        Intent monitorServiceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        MonitorService.class);


        // Bind the service and grab a reference to the binder.
        IBinder binder = serviceRule.bindService(monitorServiceIntent);

        MonitorService monitorService = ((MonitorService.Binder)binder).getMonitorService();

        Assert.assertTrue(Datenbank.getInstanz().getApplikationsEinstellungen().getMonitorAktiv());

        monitorService.anzahlInaktiveBewegungen = 1801;

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(MonitorService.Zustand.Alarmieren,monitorService.zustand);

    }
    @Test
    public void T7()
    {
        //Noch nicht herausgefunden, wie man das Smartphone bewegt
    }

    @Test
    public void T8()
    {
        //Noch nicht herausgefunden, wie man das Smartphone bewegt
    }
    @Test
    public void T9()
    {

    }
    @Test
    public void T10()
    {

    }
    @Test
    public void T11()
    {

    }
    @Test
    public void T12()
    {

    }
    @Test
    public void T13()
    {

    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
