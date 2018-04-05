package tk.dalpiazsolutions.weathersolution;

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
        weatherDownloader = new WeatherDownloader();
        mainModel.setPlace(preferenceManager.getPlace());
        //The string resource file, which contains the app-id, is not public!
        mainModel.setUrl(String.format(Locale.getDefault(), mainActivity.getString(R.string.jsonURL), mainActivity.getString(R.string.appid), mainModel.getPlace().toLowerCase()));

        try {
            mainModel.setSiteResult(weatherDownloader.execute(mainModel.getUrl()).get().toString());
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

    public void deleteAll()
    {
        dbController.dropTable();
    }
}
