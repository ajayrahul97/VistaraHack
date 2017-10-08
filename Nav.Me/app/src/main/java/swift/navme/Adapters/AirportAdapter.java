package swift.navme.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import swift.navme.AmadeusModels.AirportsModel;
import swift.navme.R;


/**
 * Created by ajayrahul on 8/10/17.
 */

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> {

    private List<AirportsModel> airportsModelList;
    AirportsModel tempValues;
    Context context;

    @Override
    public int getItemCount() {
        if (airportsModelList != null)
            return airportsModelList.size();

        return 0;
    }

    public AirportAdapter(Context context, List<AirportsModel> airportsModelsList) {
        this.airportsModelList = airportsModelsList;
        this.context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView code;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.airport_name);
            code = (TextView) itemView.findViewById(R.id.code);
            this.context = context;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_airport, parent, false);
        vh = new ViewHolder(v, context);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position < airportsModelList.size()) {
            tempValues = airportsModelList.get(position);

            holder.name.setText(tempValues.getName());
            holder.code.setText(tempValues.getCode());

        } else {
            //It means the progress bar is shown on screen
        }
    }
}
