package nl.thijswijnen.geojob.Controller.Adapters;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Maarten on 11/12/2017.
 */

public class ChooseRouteActivityItemDecorator extends RecyclerView.ItemDecoration{
    private final int decorationHeight;
    private Context context;

    public ChooseRouteActivityItemDecorator(Context context) {
        this.context = context;
        decorationHeight = 40;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent != null && view != null) {

            int itemPosition = parent.getChildAdapterPosition(view);
            int totalCount = parent.getAdapter().getItemCount();

            if (itemPosition >= 0 && itemPosition < totalCount - 1) {
                outRect.bottom = decorationHeight;
            }

        }

    }
}
