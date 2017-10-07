package swift.navme.BeaconService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ConnectBeaconListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "onReceive: Beacon Detected!");
        Toast.makeText(context,"Beacon Connected.", Toast.LENGTH_SHORT).show();

    }
}
