package tk.dalpiazsolutions.weathersolution;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Christoph on 03.04.2018.
 */

public class PreferenceManager {

    private static final String PREFS_FILE = "placeFile";
    private static final String PLACE = "place";
    private SharedPreferences preferences;
    private Context context;

    public PreferenceManager(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public String getPlace()
    {
        return preferences.getString(PLACE, context.getString(R.string.newyork));
    }

    public void savePlace(String place)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLACE, place);
        editor.apply();
    }
}
