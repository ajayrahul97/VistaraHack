package swift.navme;

import android.app.Application;

/**
 * Created by ajayrahul on 8/10/17.
 */

public class NavmeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkingFactory.init(this);
    }
}
