package nl.thijswijnen.geojob.UI;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import nl.thijswijnen.geojob.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class ChooseRouteActivityTest {

    private Context context;

    @Rule
    public IntentsTestRule<ChooseRouteActivity> mActivity = new IntentsTestRule<>(ChooseRouteActivity.class);

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void SetSearch(){
        onView(withId(R.id.activity_choose_route_search_edittext)).check(matches(withHint("\uD83D\uDD0D "+ context.getString(R.string.ChooseRouteActivity_Search))));
        onView(withId(R.id.activity_choose_route_search_edittext)).perform(replaceText("hist"));

        RecyclerView recyclerView = mActivity.getActivity().findViewById(R.id.activity_choose_route_recycleview);
        assertThat(recyclerView.getAdapter().getItemCount(),is(1));

        onView(withId(R.id.activity_choose_route_recycleview)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        intended(hasComponent(DetailRouteActivity.class.getName()));
    }

}