package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class EnterPnrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pnr);
    }

    public void OnClickGetStatus(View view) {
        Intent i = new Intent(this, FlightDetails.class);
        startActivity(i);
    }
}
