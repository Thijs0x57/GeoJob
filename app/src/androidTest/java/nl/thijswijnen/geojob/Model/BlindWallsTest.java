package nl.thijswijnen.geojob.Model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class BlindWallsTest {
    private BlindWalls blindWalls;
    private Context context;
    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        blindWalls = new BlindWalls();
    }


    @Test
    public void load() throws Exception {
        blindWalls.load(context);
        assertThat(blindWalls.getRouteTitle(),is("Blindwalls"));
        assertThat(blindWalls.getAllPointsOfInterest().size(),is(67));
        assertThat(blindWalls.getDescriptionEN(),is("The blindwalls route is a route "));
    }

    @Test
    public void getBlindWallsRoutes() throws Exception {
        List<Route> routes = BlindWalls.getBlindWallsRoutes(context);
        assertThat(routes.size(),is(3));
        Route r =  routes.get(0);
        assertThat(r.getAllPointsOfInterest().size(),is(11));
        PointOfInterest p = r.getAllPointsOfInterest().get(0);
        assertThat(p.getTitle(),is("Mike Perry"));
    }

}