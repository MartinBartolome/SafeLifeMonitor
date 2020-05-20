package schutzengel.com.safelifemonitor;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import schutzengel.com.safelifemonitor.Datenbank.Datenbank;
import schutzengel.com.safelifemonitor.GUI.HauptActivity;
import schutzengel.com.safelifemonitor.Datenbank.NotfallKontakt;
import schutzengel.com.safelifemonitor.Service.Ereignis;
import schutzengel.com.safelifemonitor.Service.EreignisAlarmAufheben;
import schutzengel.com.safelifemonitor.Service.EreignisAlarmAusloesen;
import schutzengel.com.safelifemonitor.Service.MonitorService;

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
                allOf(withContentDescription("Weitere Optionen"),
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


        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(0).getBeschreibung(),"Tino");
        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(0).getAlarmTelefonNummer(),"0791111111");
        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(0).getPrioritaet(), NotfallKontakt.Prioritaet.Prioritaet_1);

        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(2).getBeschreibung(),"Yara");
        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(2).getAlarmTelefonNummer(),"0792222222");
        Assert.assertEquals(Datenbank.getInstanz().getNotfallKontakte().get(2).getPrioritaet(), NotfallKontakt.Prioritaet.Prioritaet_3);
    }

    @Test
    public void T4()
    {

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("Weitere Optionen"),
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

        Assert.assertEquals(Datenbank.getInstanz().getApplikationsEinstellungen().getZeiten().get(0),"06:00");
        Assert.assertEquals(Datenbank.getInstanz().getApplikationsEinstellungen().getZeiten().get(1),"17:00");
    }

    private Intent monitorServiceIntent = null;
    private MonitorService monitorService = null;

    private ServiceConnection monitorServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MonitorService.Binder binder = (MonitorService.Binder) (service);
            monitorService = binder.getMonitorService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    protected Handler observer = new Handler() {
        public void handleMessage(Message message) {
            switch (Ereignis.EventIdentifierMap[message.what]) {
                case AlarmAufheben:
                    onEvent(new EreignisAlarmAufheben());
                    break;
                case AlarmAusloesen:
                    onEvent(new EreignisAlarmAusloesen());
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };

    /**
     * der MediaPlayer wird gestopt
     *
     * @param ereignisAlarmAufheben
     */
    private void onEvent(EreignisAlarmAufheben ereignisAlarmAufheben) {

    }

    private void onEvent(EreignisAlarmAusloesen event) {

    }

    @Test
    public void T5() throws TimeoutException {
        // Create the service Intent.
        monitorServiceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        MonitorService.class);

        // Data can be passed to the service via the Intent.
        monitorServiceIntent.putExtra("Messenger", new Messenger(this.observer));

        // Bind the service and grab a reference to the binder.
        IBinder binder = serviceRule.bindService(this.monitorServiceIntent, this.monitorServiceConnection, BIND_AUTO_CREATE);

        // Verify that the service is working correctly.
        Assert.assertEquals(monitorService.zustand, MonitorService.Zustand.Ueberwachen);
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
