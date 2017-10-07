package swift.navme.draganddrop;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import swift.navme.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    public interface OnStartDragListener {

        /**
         * Called when a view is requesting a start of a handleView.
         *
         * @param viewHolder The holder of the view to handleView.
         */
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    public final CheckBox checkBox;
    public final TextView textView;
    public final ImageView handleView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
        handleView = (ImageView) itemView.findViewById(R.id.handle);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_preference);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }

}