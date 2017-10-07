package swift.navme.BeaconService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class DisconnectBeaconListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Beacon Disconnected.", Toast.LENGTH_SHORT).show();
    }
}
