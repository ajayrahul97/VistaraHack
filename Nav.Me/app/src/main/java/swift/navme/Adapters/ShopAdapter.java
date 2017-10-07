package swift.navme.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import swift.navme.Models.Shop;
import swift.navme.R;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    private List<Shop> shopsList;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(Shop shop);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RatingBar ratingBar;
        public TextView shopName, avgTime, description, timeToReach, distance;
        public ImageView background;

        public MyViewHolder(View view) {
            super(view);
            shopName = (TextView) view.findViewById(R.id.shop_name);
            description = (TextView) view.findViewById(R.id.shop_type);
            avgTime = (TextView) view.findViewById(R.id.avg_time);
            background = (ImageView)view.findViewById(R.id.background);

            ratingBar = (RatingBar)view.findViewById(R.id.ratings_bar);
            timeToReach = (TextView)view.findViewById(R.id.time_to_reach_textView);
            distance = (TextView)view.findViewById(R.id.ditance_textView);
        }
    }


    public ShopAdapter(List<Shop> shopsList, OnItemClickListener listener) {
        this.shopsList = shopsList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Shop shop = shopsList.get(position);
        holder.shopName.setText(shop.getShopName());
        holder.description.setText(shop.getDescription());
        holder.avgTime.setText(shop.getAvgTime() + " min");
        holder.background.setImageResource(shop.getImageId());

        holder.distance.setText(shop.getDistance() + "m");
        holder.ratingBar.setRating((float)shop.getRating());
        holder.timeToReach.setText(shop.getTimeToReach() + " min to reach");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(shopsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }
}
