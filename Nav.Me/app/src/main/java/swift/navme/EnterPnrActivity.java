package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EnterPnrActivity extends AppCompatActivity {

    String str;
    TextView tv_pnr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pnr);
        tv_pnr = (TextView)findViewById(R.id.pnr_editText);
    }

    public void OnClickGetStatus(View view) {
        Intent i = new Intent(this, FlightDetails.class);
        startActivity(i);
    }

    public void barcodeScan(View view) {
        new IntentIntegrator(EnterPnrActivity.this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //The operation was cancelled
            } else {
                str = result.getContents();
                tv_pnr.setText(str);
            }
        }
    }

}
