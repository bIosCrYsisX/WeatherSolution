package tk.dalpiazsolutions.weathersolution;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import tk.dalpiazsolutions.weathersolution.DataBase.DBController;
import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;

/**
 * Created by Christoph on 03.04.2018.
 */

public class MainController {

    private MainActivity mainActivity;
    private MainModel mainModel;
    private WeatherDownloader weatherDownloader;
    private IconDownloader iconDownloader;
    private PreferenceManager preferenceManager;
    private DBController dbController;

    public MainController(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        mainModel = new MainModel(mainActivity);
        preferenceManager = new PreferenceManager(mainActivity);
        dbController = new DBController(mainActivity);
    }

    public void getWeather()
    {
        mainModel.setNoResult(false);
        weatherDownloader = new WeatherDownloader();
        mainModel.setPlace(preferenceManager.getPlace());
        //The string resource file, which contains the app-id, is not public!
        mainModel.setUrl(String.format(Locale.getDefault(), mainActivity.getString(R.string.jsonURL), mainActivity.getString(R.string.appid), mainModel.getPlace().toLowerCase()));

        try {
            mainModel.setSiteResult(weatherDownloader.execute(mainModel.getUrl()).get());

            if(mainModel.getSiteResult() == null)
            {
                Toast.makeText(mainActivity.getApplicationContext(), mainActivity.getString(R.string.noresult), Toast.LENGTH_LONG).show();
                mainModel.setNoResult(true);
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(mainModel.getSiteResult());
            String partString = jsonObject.getString("weather");
            JSONArray jsonArray = new JSONArray(partString);

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

            jsonObject = new JSONObject(mainModel.getSiteResult());
            mainModel.setCity(jsonObject.getString("name"));

            jsonObject = new JSONObject(mainModel.getSiteResult());
            partString = jsonObject.getString("sys");
            jsonObject = new JSONObject(partString);

            for(int i = 0; i < jsonObject.length(); i++)
            {
                mainModel.setCountry(jsonObject.getString("country"));
            }

            jsonObject = new JSONObject(mainModel.getSiteResult());
            partString = jsonObject.getString("wind");
            jsonObject = new JSONObject(partString);

            for(int i = 0; i < jsonObject.length(); i++)
            {
                mainModel.setWindSpeed(jsonObject.getDouble("speed"));
                mainModel.setWindDirection(jsonObject.getInt("deg"));
            }

            mainModel.setTextWindDirection(checkWindDirection());
        } catch (JSONException e) {
            e.printStackTrace();
            if(e.getMessage().contains("deg"))
            {
                mainModel.setWindDirection(-1);
            }
        }
    }

    public void getIcon()
    {
        if(mainModel.isNoResult())
        {
            return;
        }
        iconDownloader = new IconDownloader();
        try {
            mainModel.setIcon(iconDownloader.execute(String.format(Locale.getDefault(), mainActivity.getString(R.string.iconURL), mainModel.getIconID())).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public List<Place> getPlaces()
    {
        mainModel = dbController.getAll(mainModel);
        return mainModel.getPlaces();
    }

    public void addPlace(String placeText)
    {
        Place place = new Place();
        place.setPlace(placeText);
        dbController.insert(place);
    }

    public int getCount()
    {
        return dbController.getCount();
    }

    public String checkWindDirection()
    {
        int windDirection = mainModel.getWindDirection();

        if(windDirection == 0 || windDirection == 360) {
            return "(N)";
        } else if(windDirection > 0 && windDirection < 90) {
            return "(NO)";
        } else if(windDirection == 90) {
            return "(O)";
        } else if(windDirection > 90 && windDirection < 180) {
            return "(SO)";
        } else if(windDirection == 180){
            return "(S)";
        } else if(windDirection > 180 && windDirection < 270) {
            return "(SW)";
        } else if(windDirection == 270) {
            return "(W)";
        } else if(windDirection > 270 && windDirection < 360) {
            return "(NW)";
        } else return "";
    }
}
