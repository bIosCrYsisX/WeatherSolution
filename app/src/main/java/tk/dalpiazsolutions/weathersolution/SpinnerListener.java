package tk.dalpiazsolutions.weathersolution;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Christoph on 03.04.2018.
 */

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    PreferenceManager preferenceManager;
    private MainActivity mainActivity;

    public SpinnerListener(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        preferenceManager = new PreferenceManager(adapterView.getContext());
        preferenceManager.savePlace(adapterView.getItemAtPosition(i).toString());
        mainActivity.getWeather();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
