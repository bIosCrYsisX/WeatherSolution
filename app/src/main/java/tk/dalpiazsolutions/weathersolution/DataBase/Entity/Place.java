package tk.dalpiazsolutions.weathersolution.DataBase.Entity;

/**
 * Created by Christoph on 05.04.2018.
 */

public class Place {
    private int id;
    private String place;

    public Place(int id, String place)
    {
        this.id = id;
        this.place = place;
    }

    public Place()
    {
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
