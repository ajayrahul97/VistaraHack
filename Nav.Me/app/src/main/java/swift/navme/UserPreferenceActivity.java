package swift.navme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class UserPreferenceActivity extends AppCompatActivity {

    CheckBox restaurant, clothing, utilities, coffee, salon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference);

        restaurant = (CheckBox)findViewById(R.id.checkbox_restaurant);
        clothing = (CheckBox) findViewById(R.id.checkbox_clothing);
        utilities = (CheckBox)findViewById(R.id.checkbox_utilities);
        coffee = (CheckBox)findViewById(R.id.checkbox_coffee);
        salon = (CheckBox)findViewById(R.id.checkbox_salon);
    }

    public void OnClickNext(View view) {
        Intent i = new Intent(this, SubPreferenceActivity.class);
        i.putExtra("restaurant", restaurant.isChecked());
        i.putExtra("clothing", clothing.isChecked());
        i.putExtra("utilities", utilities.isChecked());
        i.putExtra("coffee", coffee.isChecked());
        i.putExtra("salon", salon.isChecked());
        startActivity(i);
    }
}
