package nl.thijswijnen.geojob.Controller.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.thijswijnen.geojob.Model.PointOfInterest;
import nl.thijswijnen.geojob.R;

/**
 * Created by thijs_000 on 05-Dec-17.
 */


public class PoiListViewAdapter extends RecyclerView.Adapter<PoiListViewAdapter.POIViewHolder>
{
    private List<PointOfInterest> pointOfInterests;

    public PoiListViewAdapter(List<PointOfInterest> pointOfInterests) {
        this.pointOfInterests = pointOfInterests;
    }

    @Override
    public POIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new POIViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activty_detail_route_pinpoint_item,parent,false));
    }

    @Override
    public void onBindViewHolder(POIViewHolder holder, int position) {
        holder.title.setText(pointOfInterests.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return pointOfInterests.size();
    }

    public class POIViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public POIViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.activity_detail_route_pinpoint_item_name);
        }
    }
}