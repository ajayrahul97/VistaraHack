package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    Button gotomaps,city_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotomaps = (Button)findViewById(R.id.gotomaps);
        city_info = (Button)findViewById(R.id.city_info);
        gotomaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MapsActivity3.class);
                startActivity(i);
            }
        });

        city_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CityActivity.class);
                startActivity(i);
            }
        });
    }

    public void OnClickPnrStatus(View view) {
        Intent i = new Intent(this, EnterPnrActivity.class);
        startActivity(i);
    }
}
