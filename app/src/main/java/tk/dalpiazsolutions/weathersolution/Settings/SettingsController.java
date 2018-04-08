package tk.dalpiazsolutions.weathersolution.Settings;


import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

import tk.dalpiazsolutions.weathersolution.DataBase.DBController;
import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;
import tk.dalpiazsolutions.weathersolution.MainActivity;
import tk.dalpiazsolutions.weathersolution.MainModel;
import tk.dalpiazsolutions.weathersolution.PreferenceManager;
import tk.dalpiazsolutions.weathersolution.R;

/**
 * Created by Christoph on 07.04.2018.
 */

public class SettingsController {
    private SettingsActivity settingsActivity;
    private DBController dbController;
    private MainModel mainModel;
    private SettingsModel settingsModel;
    private PreferenceManager preferenceManager;

    public SettingsController(SettingsActivity settingsActivity)
    {
        this.settingsActivity = settingsActivity;
        settingsModel = new SettingsModel(settingsActivity);
        dbController = new DBController(settingsActivity);
        mainModel = new MainModel(new MainActivity());
        preferenceManager = new PreferenceManager(settingsActivity);
    }

    public void listPlaces()
    {
        settingsModel.setPlaceList(getPlaces());
        setPlacesTexts();
        settingsActivity.setArrayAdapter(new ArrayAdapter(settingsActivity.getApplicationContext(), android.R.layout.simple_list_item_1, settingsModel.getPlaceText()));
    }

    public List<Place> getPlaces()
    {
        mainModel = dbController.getAll(mainModel);
        return mainModel.getPlaces();
    }

    public void setPlacesTexts()
    {
        for(int i = 0; i < settingsModel.getPlaceList().size(); i++)
        {
            settingsModel.addPlaceTextElement(settingsModel.getPlaceList().get(i).getPlace());
        }
    }

    public void addPlaceToDelete(String place)
    {
        settingsModel.addPlacesToDelete(place);
        Log.i("delete", settingsModel.getPlacesToDelete().toString());
    }

    public int countPlacesToDelete()
    {
        return settingsModel.getPlacesToDelete().size();
    }

    public void deleteSelected()
    {
        for(int i = 0; i < settingsModel.getPlacesToDelete().size(); i++)
        {
            dbController.deleteByName(settingsModel.getPlacesToDelete().get(i));
        }
    }

    public void checkForDelete(int c, String s) {

        if(settingsModel.getPlacesToDelete().get(c).equals(s))
        {
            settingsModel.deletePlacesToDeleteAt(c);
        }
    }

    public void addPlace(String placeText)
    {
        preferenceManager.savePlace(placeText);
        Place place = new Place();
        place.setPlace(placeText);
        dbController.insert(place);
    }

    public void deleteAll()
    {
        dbController.dropTable();
    }

    public void savePlace(String place)
    {
        preferenceManager.savePlace(place);
    }

    public void checkDeletedPlace()
    {
        for(int i = 0; i < settingsModel.getPlacesToDelete().size(); i++)
        {
            if(preferenceManager.getPlace().equals(settingsModel.getPlacesToDelete().get(i)))
            {
                savePlace(settingsActivity.getString(R.string.newyork));
            }
        }
    }
}
