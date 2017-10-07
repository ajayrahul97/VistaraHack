package swift.navme.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import swift.navme.Models.Shop;
import swift.navme.R;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    private List<Shop> shopsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView shopName, avgTime, description;
        public ImageView background;

        public MyViewHolder(View view) {
            super(view);
            shopName = (TextView) view.findViewById(R.id.shop_name);
            description = (TextView) view.findViewById(R.id.shop_type);
            avgTime = (TextView) view.findViewById(R.id.avg_time);
            background = (ImageView)view.findViewById(R.id.background);
        }
    }


    public ShopAdapter(List<Shop> shopsList) {
        this.shopsList = shopsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = shopsList.get(position);
        holder.shopName.setText(shop.getShopName());
        holder.description.setText(shop.getDescription());
        holder.avgTime.setText(shop.getAvgTime());
        holder.background.setImageResource(shop.getImageId());
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }
}
