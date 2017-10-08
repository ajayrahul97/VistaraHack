package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import swift.navme.Adapters.ShopAdapter;
import swift.navme.Models.Shop;

public class SubPreferenceActivity extends AppCompatActivity {

    private static String TAG = SubPreferenceActivity.class.getSimpleName();
    private static int ERR = -1023;

    private List<Shop> shopList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShopAdapter mAdapter;

    int food = ERR, beverages = ERR, utilities = ERR, other = ERR;
    List<Shop> foodsList, beveragesList, utilitiesList, othersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_preference);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ShopAdapter(shopList, new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Shop shop) {
                Intent i = new Intent(getApplicationContext(), MapsActivity3.class);
                i.putExtra("shop", shop);
                startActivity(i);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            food = extras.getInt("Food", ERR);
            beverages = extras.getInt("Beverages", ERR);
            utilities = extras.getInt("Utilities", ERR);
            other = extras.getInt("Other", ERR);
        }

        foodsList = new ArrayList<>();
        beveragesList = new ArrayList<>();
        utilitiesList = new ArrayList<>();
        othersList = new ArrayList<>();

        prepareShopData();
    }

    private void prepareShopData() {
        for(int i=0;i<4;i++) {
            if(food == i) {
                Shop s1 = new Shop("McDonalds",
                        "Restaurant | Burgers and Fries",
                        25,
                        R.drawable.mc_d,
                        5,
                        100,
                        4.5,
                        37.61584137416485,
                        -122.38449577242135);
                Shop s2 = new Shop("Dominos",
                        "Restaurant | Pizzas",
                        30,
                        R.drawable.dominos,
                        10,
                        200,
                        4.3,
                        37.61584137416485,
                        -122.38449577242135);
                Shop s3 = new Shop("KFC",
                        "Restaurant | Burgers and Fries",
                        20,
                        R.drawable.kfc,
                        5,
                        100,
                        4.4,
                        37.61584137416485,
                        -122.38449577242135);
                foodsList.add(s1);
                foodsList.add(s2);
                foodsList.add(s3);
                Log.i(TAG, "prepareShopData: food");
            } else if(beverages == i) {
                Shop s1 = new Shop("CCD",
                        "Coffee and Pastry",
                        16,
                        R.drawable.ccd,
                        6,
                        120,
                        4.5,
                        37.61740747841049,
                        -122.38484378904106);
                Shop s2 = new Shop("Starbucks",
                        "Coffee",
                        15,
                        R.drawable.starbucks,
                        10,
                        200,
                        4.7,
                        37.61740747841049,
                        -122.38484378904106);

                beveragesList.add(s1);
                beveragesList.add(s2);
                Log.i(TAG, "prepareShopData: beverages");
            } else if(utilities == i) {
                Shop s1 = new Shop("Washroom",
                        "",
                        2,
                        R.drawable.washroom,
                        4,
                        80,
                        4.7,
                        37.61779548048554,
                        -122.38618958741426);
                Shop s2 = new Shop("WheelChair",
                        "get free assistance",
                        5,
                        R.drawable.wheelchair,
                        4,
                        80,
                        5.0,
                        37.61779548048554,
                        -122.38618958741426);
                utilitiesList.add(s1);
                utilitiesList.add(s2);
                Log.i(TAG, "prepareShopData: utilites");
            } else if(other == i) {
                Shop s1 = new Shop("BookStore",
                        "Bibliphile corner",
                        20,
                        R.drawable.bookstore,
                        7,
                        140,
                        4.7,
                        37.617700671130514,
                        -122.38750655204058);
                othersList.add(s1);
                Log.i(TAG, "prepareShopData: others");
            }
        }
        shopList.addAll(foodsList);
        shopList.addAll(beveragesList);
        shopList.addAll(utilitiesList);
        shopList.addAll(othersList);

        mAdapter.notifyDataSetChanged();
    }
}
