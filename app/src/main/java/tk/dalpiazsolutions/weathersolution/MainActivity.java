package tk.dalpiazsolutions.weathersolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import tk.dalpiazsolutions.weathersolution.DataBase.Entity.Place;

public class MainActivity extends AppCompatActivity {

    Spinner placeSpinner;
    SpinnerListener spinnerListener;
    MainController mainController;
    PreferenceManager preferenceManager;
    ArrayAdapter<CharSequence> arrayAdapter;
    ImageView iconView;
    TextView txtWeatherMain;
    TextView txtWeatherDescription;
    TextView txtTemp;
    TextView txtPressure;
    TextView txtHumidity;
    TextView txtCityCountry;
    EditText txtNewPlace;
    TextView txtWind;
    Button mbuttonAddNewPlace;
    DecimalFormat decimalFormat;
    String[] places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = new MainController(this);

        decimalFormat = new DecimalFormat("0.00");

        if(mainController.getCount() == 0)
        {
            mainController.addPlace(getString(R.string.newyork));
        }

        mbuttonAddNewPlace = findViewById(R.id.buttonAddPlace);
        txtNewPlace = findViewById(R.id.textNewPlace);
        iconView = findViewById(R.id.iconView);
        txtWeatherMain = findViewById(R.id.textWeatherMain);
        txtWeatherDescription = findViewById(R.id.textWeatherDescription);
        txtTemp = findViewById(R.id.textTemp);
        txtPressure = findViewById(R.id.textPressure);
        txtHumidity = findViewById(R.id.textHumidity);
        txtCityCountry = findViewById(R.id.textCityCountry);
        txtWind = findViewById(R.id.textWind);

        preferenceManager = new PreferenceManager(this);

        spinnerListener = new SpinnerListener(this);

        placeSpinner = findViewById(R.id.spinnerPlace);
        placeSpinner.setOnItemSelectedListener(spinnerListener);
        setPlacesArray();
        setArrayAdapter();
        setSpinnerSelection();
        getWeather();
    }

    public void getWeather()
    {
        mainController.getWeather();
        mainController.getIcon();
    }

    public void updateWeather(MainModel mainModel)
    {
        txtWeatherMain.setText(mainModel.getWeatherMain());
        txtWeatherDescription.setText(mainModel.getWeatherDescription());
        txtTemp.setText(decimalFormat.format(mainModel.getTemperatureInCelsius()) + " °C");
        txtPressure.setText(Integer.toString(mainModel.getPressure()) + " mbar");
        txtHumidity.setText(Integer.toString(mainModel.getHumidity()) + " %");
        txtCityCountry.setText(String.format(Locale.getDefault(), getString(R.string.countryPlace), mainModel.getCity(), mainModel.getCountry()));
        txtWind.setText(Double.toString(mainModel.getWindSpeed()) + " m/s,  " + mainModel.getWindDirection() + "° " + "(" + mainModel.getTextWindDirection() + ")");
    }

    public void updateIcon(Bitmap bitmap)
    {
        iconView.setImageBitmap(bitmap);
    }

    public void setPlacesArray()
    {
        List<Place> places = mainController.getPlaces();
        this.places = new String[places.size()];

        for(int i = 0; i < places.size(); i++)
        {
            this.places[i] = places.get(i).getPlace();
        }
    }

    public void addPlace(View view)
    {
        String place = txtNewPlace.getText().toString();
        preferenceManager.savePlace(place);
        mainController.addPlace(place);
        setPlacesArray();
        setArrayAdapter();
        setSpinnerSelection();
    }

    public void deletePlaces(View view)
    {
        mainController.deleteAll();
        mainController.addPlace(getString(R.string.newyork));
        preferenceManager.savePlace(getString(R.string.newyork));
        setPlacesArray();
        setArrayAdapter();
        setSpinnerSelection();
    }

    public void setArrayAdapter()
    {
        arrayAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.places);
        placeSpinner.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(arrayAdapter);
    }

    public void setSpinnerSelection()
    {
        for(int i = 0; i < placeSpinner.getCount(); i++)
        {
            if(placeSpinner.getItemAtPosition(i).toString().equals(preferenceManager.getPlace()))
            {
                placeSpinner.setSelection(i);
            }
        }
    }
}
