package nl.thijswijnen.geojob.Controller.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

import nl.thijswijnen.geojob.Model.BlindWall;
import nl.thijswijnen.geojob.Model.HistorischeKilometer;
import nl.thijswijnen.geojob.Model.Monument;
import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.R;

/**
 * Created by Maarten on 19/12/2017.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private PointOfInterest pointOfInterest;

    public ImagePagerAdapter(Context context, PointOfInterest pointOfInterest) {
        this.mContext = context;
        this.pointOfInterest = pointOfInterest;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pointOfInterest.getAllImages().size();
    }

    @Override
    public int getItemPosition(Object object) {
        return pointOfInterest.getAllImages().indexOf(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.image_pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.image_pager_item_image);
        String map= "";
        if(pointOfInterest instanceof BlindWall){
            map = "blindWalls/";
        }else if(pointOfInterest instanceof Monument){
            map = "historicKilometer/";
        }
        try {
            imageView.setImageDrawable(Drawable.createFromStream(mContext.getAssets().open(map + pointOfInterest.getAllImages().get(position).toLowerCase()), null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
