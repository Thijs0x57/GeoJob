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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class LanguageActivityTest {

    @Rule
    public IntentsTestRule<LanguageActivity> mActivity = new IntentsTestRule<>(LanguageActivity.class);
    @Before
    public void setUp() throws Exception {

    }

   @Test
    public void SelectDutch(){
        onView(withId(R.id.language_dutch_imgButt)).check(matches(isDisplayed()));
        onView(withId(R.id.language_dutch_imgButt)).perform(click());

        assertTrue(mActivity.getActivity().isDestroyed());
    }

    @Test
    public void SelectEnglisch(){
        onView(withId(R.id.language_english_imgButt)).check(matches(isDisplayed()));
        onView(withId(R.id.language_english_imgButt)).perform(click());

        assertTrue(mActivity.getActivity().isDestroyed());
    }

}