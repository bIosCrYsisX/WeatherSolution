package tk.dalpiazsolutions.weathersolution.DataBase;

import android.content.Context;

import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;
import tk.dalpiazsolutions.weathersolution.DataBase.Facades.PlaceFacade;
import tk.dalpiazsolutions.weathersolution.MainModel;

/**
 * Created by Christoph on 05.04.2018.
 */

public class DBController {

    private PlaceFacade placeFacade;
    private Context context;

    public DBController(Context context)
    {
        this.context = context;
        placeFacade = new PlaceFacade(this.context);
    }

    public MainModel getAll(MainModel mainModel)
    {
        mainModel.setPlaces(placeFacade.getAll());
        return mainModel;
    }

    public void insert(Place place)
    {
        placeFacade.insert(place);
    }

    public int getCount()
    {
        return placeFacade.count();
    }

    public void dropTable()
    {
        placeFacade.dropTable();
    }
}
