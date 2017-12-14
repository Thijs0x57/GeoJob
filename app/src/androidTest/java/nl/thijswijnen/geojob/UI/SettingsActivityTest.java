package nl.thijswijnen.geojob.UI;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import nl.thijswijnen.geojob.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class SettingsActivityTest {
    private Context context;

    @Rule
    public IntentsTestRule<SettingsActivity> mActivity = new IntentsTestRule<>(SettingsActivity.class);

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void SetSwitch(){
        onView(withId(R.id.settings_notifications_swtch)).check(matches(isDisplayed()));
    }

    @Test
    public void SetLanguage(){
        onView(withId(R.id.settings_chooselanguage_btn)).check(matches(withText(context.getResources().getString(R.string.SettingsActivity_ChooseLanguage))));
        onView(withId(R.id.settings_chooselanguage_btn)).perform(click());

        intended(hasComponent(LanguageActivity.class.getName()));
    }

}