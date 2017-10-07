package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import swift.navme.draganddrop.RecyclerActivity;

public class FlightDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);
    }

    public void OnClickContinue(View view) {
        Intent i = new Intent(this, RecyclerActivity.class);
        startActivity(i);
    }
}
