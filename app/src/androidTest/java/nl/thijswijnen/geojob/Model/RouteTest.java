package nl.thijswijnen.geojob.Model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class RouteTest {
    Route blindwall;
    private Context context;
    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        blindwall = new BlindWalls();
        blindwall.load(context);
    }

    @Test
    public void getAllPointsOfInterest() throws Exception {
        assertThat(blindwall.getAllPointsOfInterest().size(),is(66));
    }

    @Test
    public void setAllPointOfInterests() throws Exception {
        List<PointOfInterest> pointOfInterests = new ArrayList<>();
        blindwall.setAllPointOfInterests(pointOfInterests);
        assertThat(blindwall.getAllPointsOfInterest().size(),is(0));
    }

    @Test
    public void setRouteTitle() throws Exception {
        blindwall.setRouteTitle("test");
        assertThat(blindwall.getRouteTitle(),is("test"));
    }

    @Test
    public void getRouteTitle() throws Exception {
        assertThat(blindwall.getRouteTitle(),is("Blindwalls"));
    }

    @Test
    public void getDescriptionEN() throws Exception {
        assertThat(blindwall.getDescriptionEN(),is("The blindwalls route is a route "));
    }

    @Test
    public void setDescriptionEN() throws Exception {
        blindwall.setDescriptionEN("test");
        assertThat(blindwall.getDescriptionEN(),is("test"));
    }

    @Test
    public void getDescriptionNL() throws Exception {
        assertThat(blindwall.getDescriptionNL(),is("The blindwalls route is een route"));
    }

    @Test
    public void setDescriptionNL() throws Exception {
        blindwall.setDescriptionNL("test");
        assertThat(blindwall.getDescriptionNL(),is("test"));
    }

}