package tk.dalpiazsolutions.weathersolution;

import android.graphics.Bitmap;

/**
 * Created by Christoph on 03.04.2018.
 */

public class MainModel {

    private MainActivity mainActivity;
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
}
