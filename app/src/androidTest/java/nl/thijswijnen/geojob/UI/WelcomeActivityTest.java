package nl.thijswijnen.geojob.UI;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import nl.thijswijnen.geojob.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;


/**
 * Created by Maarten on 14/12/2017.
 */
@LargeTest
public class WelcomeActivityTest {

    private Context context;
    @Rule
    public IntentsTestRule<WelcomeActivity> mActivity = new IntentsTestRule<>(WelcomeActivity.class);

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void SelectRoute(){
        onView(withId(R.id.welcome_chooose_route_btn)).check(matches(withText(context.getResources().getString(R.string.WelcomeActivity_SelectRoute))));
        onView(withId(R.id.welcome_chooose_route_btn)).perform(click());

        intended(hasComponent(ChooseRouteActivity.class.getName()));
    }

    @Test
    public void SelectLanguage(){
        onView(withId(R.id.welcome_TaalKiezen_btn)).check(matches(withText(context.getResources().getString(R.string.WelcomeActivity_SelectLanguage))));
        onView(withId(R.id.welcome_TaalKiezen_btn)).perform(click());

        intended(hasComponent(LanguageActivity.class.getName()));
    }

    @Test
    public void SelectHelp(){

    }
}