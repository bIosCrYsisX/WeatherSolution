package tk.dalpiazsolutions.weathersolution;

import android.graphics.Bitmap;

import java.util.List;

import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;

/**
 * Created by Christoph on 03.04.2018.
 */

public class MainModel {

    private MainActivity mainActivity;
    private List<Place> places;
    private String siteResult;
    private String url;
    private String place;
    private String weatherMain;
    private String weatherDescription;
    private String iconID;
    private Bitmap icon;
    private double temperature;
    private int pressure;
    private int humidity;
    private String city;
    private String country;
    private double windSpeed;
    private int windDirection;
    private String textWindDirection;
    private boolean noResult;

    public MainModel(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public String getSiteResult() {
        return siteResult;
    }

    public void setSiteResult(String siteResult) {
        this.siteResult = siteResult;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
        propertyChanged();
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
        propertyChanged();
    }

    public String getIconID() {
        return iconID;
    }

    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
        iconChanged();
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureInCelsius() {
        return (temperature - 273.15);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        propertyChanged();
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
        propertyChanged();
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
        propertyChanged();
    }

    public void propertyChanged()
    {
        mainActivity.updateWeather(this);
    }

    public void iconChanged()
    {
        mainActivity.updateIcon(icon);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        propertyChanged();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        propertyChanged();
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        propertyChanged();
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
        propertyChanged();
    }

    public String getTextWindDirection() {
        return textWindDirection;
    }

    public void setTextWindDirection(String textWindDirection) {
        this.textWindDirection = textWindDirection;
        propertyChanged();
    }

    public boolean isNoResult() {
        return noResult;
    }

    public void setNoResult(boolean noResult) {
        this.noResult = noResult;
    }
}
