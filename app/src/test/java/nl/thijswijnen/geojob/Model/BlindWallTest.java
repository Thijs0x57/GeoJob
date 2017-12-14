package nl.thijswijnen.geojob.Model;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by Maarten on 14/12/2017.
 */
public class BlindWallTest {
    private BlindWall wall;
    @Before
    public void setUp() throws Exception {
        wall = new BlindWall("testwall","description nl","description en" ,new LatLng(0,0),
                "artist","photographer","materialEn","materialNL");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getArtist() throws Exception {
        assertThat(wall.getArtist(), is("artist"));
    }

    @Test
    public void setArtist() throws Exception {
        wall.setArtist("new artist");
        assertThat(wall.getArtist(),is("new artist"));
    }

    @Test
    public void getPhotographer() throws Exception {
        assertThat(wall.getPhotographer(),is("photographer"));
    }

    @Test
    public void setPhotographer() throws Exception {
        wall.setPhotographer("new photographer");
        assertThat(wall.getPhotographer(),is("new photographer"));
    }

    @Test
    public void getMaterialEn() throws Exception {
        assertThat(wall.getMaterialEn(),is("materialEn"));
    }

    @Test
    public void setMaterialEn() throws Exception {
        wall.setMaterialEn("thats");
        assertThat(wall.getMaterialEn(),is("thats"));
    }

    @Test
    public void getMaterialNl() throws Exception {
        assertThat(wall.getMaterialNl(),is("materialNL"));
    }

    @Test
    public void setMaterialNl() throws Exception {
        wall.setMaterialEn("test");
        assertThat(wall.getMaterialNl(),is("test"));
    }

}