package tk.dalpiazsolutions.weathersolution.DataBase.Facades;

import android.content.Context;

import java.util.List;

import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;
import tk.dalpiazsolutions.weathersolution.DataBase.Repository.PlaceRepository;

/**
 * Created by Christoph on 05.04.2018.
 */

public class PlaceFacade {
    private final Context context;

    public PlaceFacade(Context context)
    {
        this.context = context;
    }

    public List<Place> getAll()
    {
        return PlaceRepository.getInstance(context).getAll();
    }

    public Place find(int id)
    {
        return PlaceRepository.getInstance(context).find(id);
    }

    public void insert(Place place)
    {
        PlaceRepository.getInstance(context).insert(place);
    }

    public int count()
    {
        return getAll().size();
    }

    public void update(Place place)
    {
        PlaceRepository.getInstance(context).update(place);
    }

    public void dropTable()
    {
        PlaceRepository.getInstance(context).dropTable();
    }
}
