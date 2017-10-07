package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import swift.navme.Models.Category;
import swift.navme.draganddrop.ItemViewHolder;
import swift.navme.draganddrop.OnRecyclerItemChecked;
import swift.navme.draganddrop.RecyclerListAdapter;
import swift.navme.draganddrop.SimpleItemTouchHelperCallback;

public class UserPreferenceActivity extends AppCompatActivity implements ItemViewHolder.OnStartDragListener, OnRecyclerItemChecked {

    ItemTouchHelper mItemTouchHelper;
    private static String TAG = UserPreferenceActivity.class.getSimpleName();

    List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerListAdapter adapter = new RecyclerListAdapter(this, this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemChecked(String string, int position) {
        categories.add(new Category(string, position));
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category category, Category t1) {
                if(category.getPreference() < t1.getPreference())
                    return -1;
                else
                    return 1;
            }
        });

    }

    public void OnClickNext(View view) {
        Intent i = new Intent(this, SubPreferenceActivity.class);
        for(int j=0;j<categories.size();j++) {
            i.putExtra(categories.get(j).getTitle(), j);
        }
        startActivity(i);
    }
}
