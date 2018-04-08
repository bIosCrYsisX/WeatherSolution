package tk.dalpiazsolutions.weathersolution.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import tk.dalpiazsolutions.weathersolution.MainActivity;
import tk.dalpiazsolutions.weathersolution.R;

public class SettingsActivity extends AppCompatActivity {

    ListView listPlaces;
    SettingsController settingsController;
    Button mbuttonDelete;
    Button mbuttonSave;

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        listPlaces = findViewById(R.id.listPlaces);
        settingsController = new SettingsController(this);
        mbuttonDelete = findViewById(R.id.buttonDelete);
        mbuttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();

        if(intent.getStringExtra("place") != null) {
            settingsController.addPlace(intent.getStringExtra("place"));
            settingsController.savePlace(intent.getStringExtra("place"));
        }

        listPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(view.isEnabled()) {
                    view.setEnabled(false);
                    settingsController.addPlaceToDelete(adapterView.getItemAtPosition(i).toString());
                } else {
                    view.setEnabled(true);
                    for(int c = 0; c < settingsController.countPlacesToDelete(); c++)
                    {
                        settingsController.checkForDelete(c, adapterView.getItemAtPosition(i).toString());
                    }
                }

                if(settingsController.countPlacesToDelete() == 0) {
                    mbuttonDelete.setEnabled(false);
                } else {
                    mbuttonDelete.setEnabled(true);
                }
            }
        });

        settingsController.listPlaces();
    }

    public void setArrayAdapter(ArrayAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
        listPlaces.setAdapter(this.arrayAdapter);
    }

    public void addPlace(View view)
    {
        Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
        startActivity(intent);
        finish();
    }


    public void deleteSelected(View view)
    {
        settingsController.deleteSelected();
        this.arrayAdapter.clear();
        settingsController.listPlaces();
    }

    public void save(View view)
    {
        settingsController.checkDeletedPlace();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void deletePlaces(View view)
    {
        settingsController.deleteAll();
        this.arrayAdapter.clear();
        settingsController.listPlaces();
    }
}
