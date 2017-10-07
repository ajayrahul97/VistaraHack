package swift.navme.draganddrop;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import swift.navme.R;

import static mehdi.sakout.fancybuttons.FancyButton.TAG;

public class RecyclerListAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter {

    private static final String[] STRINGS = new String[]{
            "Food", "Beverages", "Utilities", "Other"
    };

    private final ItemViewHolder.OnStartDragListener mDragStartListener;
    private final OnRecyclerItemChecked mRecyclerItemChecked;

    private final List<String> mItems = new ArrayList<>();

    public RecyclerListAdapter(ItemViewHolder.OnStartDragListener dragStartListener,
                               OnRecyclerItemChecked recyclerItemChecked) {
        mItems.addAll(Arrays.asList(STRINGS));
        mDragStartListener = dragStartListener;
        mRecyclerItemChecked = recyclerItemChecked;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.textView.setText(mItems.get(position));
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) ==
                        MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String s = holder.textView.getText().toString();
                int pos = 0;
                for(int i=0;i<mItems.size();i++) {
                    if(mItems.get(i).compareTo(s) == 0) {
                        pos = i;
                    }
                }
                mRecyclerItemChecked.onItemChecked(s, pos);
                Log.i(TAG, "onCheckedChanged: " + pos + " " + mItems.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mItems, i, i - 1);
            }
        }
        Log.i(TAG, "onItemMove: " + fromPosition + " " + toPosition);
        notifyItemMoved(fromPosition, toPosition);

        Log.i(TAG, "After sorting");
        for(int i=0;i<mItems.size();i++) {
            Log.i(TAG, "onItemMove: " + mItems.get(i));
        }
        return true;
    }

}