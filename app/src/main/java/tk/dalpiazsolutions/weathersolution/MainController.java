package tk.dalpiazsolutions.weathersolution;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Christoph on 03.04.2018.
 */

public class MainController {

    private MainActivity mainActivity;
    private MainModel mainModel;
    private WeatherDownloader weatherDownloader;
    private IconDownloader iconDownloader;
    private PreferenceManager preferenceManager;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String partString;

    public MainController(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        mainModel = new MainModel(mainActivity);
        preferenceManager = new PreferenceManager(mainActivity);
    }

    public void getWeather()
    {
        weatherDownloader = new WeatherDownloader();
        mainModel.setPlace(preferenceManager.getPlace());
        //The string resource file, which contains the app-id, is not public!
        mainModel.setUrl(String.format(Locale.getDefault(), mainActivity.getString(R.string.jsonURL), mainActivity.getString(R.string.appid), mainModel.getPlace().toLowerCase()));
        Log.i("url", mainModel.getUrl());

        try {
            mainModel.setSiteResult(weatherDownloader.execute(mainModel.getUrl()).get().toString());
            Log.i("site", mainModel.getSiteResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            jsonObject = new JSONObject(mainModel.getSiteResult());
            partString = jsonObject.getString("weather");
            jsonArray = new JSONArray(partString);

            for(int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                mainModel.setWeatherMain(jsonObject.getString("main"));
                mainModel.setWeatherDescription(jsonObject.getString("description"));
                mainModel.setIconID(jsonObject.getString("icon"));
            }

            jsonObject = new JSONObject(mainModel.getSiteResult());
            partString = jsonObject.getString("main");
            jsonObject = new JSONObject(partString);

            for(int i = 0; i < jsonObject.length(); i++)
            {
                mainModel.setTemperature(jsonObject.getDouble("temp"));
                mainModel.setPressure(jsonObject.getInt("pressure"));
                mainModel.setHumidity(jsonObject.getInt("humidity"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getIcon()
    {
        iconDownloader = new IconDownloader();
        try {
            mainModel.setIcon(iconDownloader.execute(String.format(Locale.getDefault(), mainActivity.getString(R.string.iconURL), mainModel.getIconID())).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
