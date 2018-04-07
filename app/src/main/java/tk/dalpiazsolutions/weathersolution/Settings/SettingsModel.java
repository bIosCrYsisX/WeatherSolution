package tk.dalpiazsolutions.weathersolution.Settings;


import java.util.LinkedList;
import java.util.List;

import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;

/**
 * Created by Christoph on 07.04.2018.
 */

public class SettingsModel {
    private SettingsActivity settingsActivity;
    private List<Place> placeList;
    private List<String> placeText;
    private List<String> placesToDelete;

    public SettingsModel(SettingsActivity settingsActivity)
    {
        this.settingsActivity = settingsActivity;
        placeList = new LinkedList<>();
        placeText = new LinkedList<>();
        placesToDelete = new LinkedList<>();
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public List<String> getPlaceText() {
        return placeText;
    }

    public void setPlaceText(List<String> placeText) {
        this.placeText = placeText;
    }

    public void addPlaceTextElement(String element)
    {
        this.placeText.add(element);
    }

    public List<String> getPlacesToDelete() {
        return placesToDelete;
    }

    public void setPlacesToDelete(List<String> placesToDelete) {
        this.placesToDelete = placesToDelete;
    }

    public void addPlacesToDelete(String place)
    {
        this.placesToDelete.add(place);
    }

    public void deletePlacesToDeleteAt(int position)
    {
        this.placesToDelete.remove(position);
    }
}
