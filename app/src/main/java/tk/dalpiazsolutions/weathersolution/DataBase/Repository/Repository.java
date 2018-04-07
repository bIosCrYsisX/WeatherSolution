package tk.dalpiazsolutions.weathersolution.DataBase.Repository;

import java.util.List;

/**
 * Created by Christoph on 05.04.2018.
 */

public interface Repository<T> {
    List<T> getAll();
    void insert(T entity);
    T find(int id);
    void update(T entity);
    void dropTable();
    void deleteByName(String name);
}
