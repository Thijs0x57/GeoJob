package nl.thijswijnen.geojob.Model;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Maarten on 14/12/2017.
 */
public class PointOfInterestTest {
    BlindWall wall;
    @Before
    public void setUp() throws Exception {
        wall = new BlindWall("testwall","description nl","description en" ,new LatLng(0,0),
                "artist","photographer","materialEn","materialNL");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDescriptionNL() throws Exception {
        assertThat(wall.getDescriptionNL(),is("description nl"));
    }

    @Test
    public void getDescriptionEN() throws Exception {
        assertThat(wall.getDescriptionEN(),is("description en"));
    }

    @Test
    public void getTitle() throws Exception {
        assertThat(wall.getTitle(),is("testwall"));
    }

    @Test
    public void setTitle() throws Exception {
        assertThat(wall.getTitle(),is("testwall"));
    }

    @Test
    public void getAllImages() throws Exception {
        List<String> images = new ArrayList<>();
        assertThat(wall.getAllImages(),is(images));
    }

    @Test
    public void setAllImages() throws Exception {
        List<String> images = new ArrayList<>();
        images.add("test");
        wall.setAllImages(images);
        assertThat(wall.getAllImages(),is(images));
    }

    @Test
    public void getAllVideos() throws Exception {
        List<String> videos = new ArrayList<>();
        assertThat(wall.getAllVideos(),is(videos));
    }

    @Test
    public void setAllVideos() throws Exception {
        List<String> videos = new ArrayList<>();
        videos.add("test");
        wall.setAllImages(videos);
        assertThat(wall.getAllImages(),is(videos));
    }

    @Test
    public void getLocation() throws Exception {
        assertThat(wall.getLocation(),is(new LatLng(0,0)));
    }

    @Test
    public void isVisited() throws Exception {
        assertThat(wall.isVisited(),is(false));
    }

    @Test
    public void setVisited() throws Exception {
        wall.setVisited(true);
        assertThat(wall.isVisited(),is(true));
    }

}