package nl.thijswijnen.geojob.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.R;

/**
 * Created by Jeffrey on 17-12-2017.
 */

public class PoiListTester  extends ArrayAdapter<PointOfInterest>{
    public PoiListTester(Context context,  ArrayList<PointOfInterest> pois){
        super(context,0,pois);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        PointOfInterest poi = getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activty_detail_route_pinpoint_item, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.activity_detail_route_pinpoint_item_name);
        tvName.setText(poi.getTitle());

        return convertView;
    }
}
