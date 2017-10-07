package swift.navme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import swift.navme.Adapters.ShopAdapter;
import swift.navme.Models.Shop;

public class SubPreferenceActivity extends AppCompatActivity {

    private List<Shop> shopList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShopAdapter mAdapter;

    boolean restaurant, clothing, utilities, coffee, salon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_preference);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ShopAdapter(shopList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Bundle extras = getIntent().getExtras();
        restaurant = extras.getBoolean("restaurant");
        clothing = extras.getBoolean("clothing");
        utilities = extras.getBoolean("utitlities");
        coffee = extras.getBoolean("coffee");
        salon = extras.getBoolean("salon");

        prepareMovieData();
    }

    private void prepareMovieData() {
        if(restaurant) {
            Shop shop = new Shop("Dominos", "Pizza and snack shop", "22 min", R.drawable.food_shop_1);
            shopList.add(shop);

            shop = new Shop("Pizza hut", "Pizza and snack shop", "32 min", R.drawable.food_shop_pizza);
            shopList.add(shop);

            shop = new Shop("Vaango", "South Indian food shop", "38 min", R.drawable.food_shop_south);
            shopList.add(shop);
        }

        mAdapter.notifyDataSetChanged();
    }
}
