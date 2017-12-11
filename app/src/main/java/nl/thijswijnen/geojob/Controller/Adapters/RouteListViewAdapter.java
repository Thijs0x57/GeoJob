package nl.thijswijnen.geojob.Controller.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.thijswijnen.geojob.Model.Route;
import nl.thijswijnen.geojob.R;
import nl.thijswijnen.geojob.UI.DetailRouteActivity;

/**
 * Created by thijs_000 on 05-Dec-17.
 */

public class RouteListViewAdapter extends RecyclerView.Adapter<RouteListViewAdapter.RouteViewHolder> implements Filterable
{
    private List<Route> routes;
    private List<Route> filterdRoute;
    private Filter filter;

    public RouteListViewAdapter(List<Route> routes) {
        this.routes = routes;
        filterdRoute = new ArrayList<>(routes);
        filter = new CustomFilter(this);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_choose_route_route_item,parent,false);
        return new RouteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RouteViewHolder holder, final int position) {
        holder.text.setText(filterdRoute.get(position).getRouteTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.cardView.getContext(), DetailRouteActivity.class);
                i.putExtra("route", (Serializable) filterdRoute.get(position));
                holder.cardView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterdRoute.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView text;
        public RouteViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.activity_choose_route_item_cardview);
            text = cardView.findViewById(R.id.activity_choose_route_item_text);
        }
    }

    public class CustomFilter extends Filter{

        private RouteListViewAdapter adapter;

        public CustomFilter(RouteListViewAdapter adapter) {
            super();
            this.adapter =  adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filterdRoute.clear();
            final FilterResults results = new FilterResults();
            if(constraint.length() == 0){
                filterdRoute.addAll(routes);
            }else {
                final String filter = constraint.toString().toLowerCase();
                for (Route route : routes) {
                    if(route.getRouteTitle().toLowerCase().startsWith(filter)){
                        filterdRoute.add(route);
                    }
                }
            }

            results.values = filterdRoute;
            results.count = filterdRoute.size();
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapter.notifyDataSetChanged();
        }
    }
}
