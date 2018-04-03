package tk.dalpiazsolutions.weathersolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

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
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iconView = findViewById(R.id.iconView);
        txtWeatherMain = findViewById(R.id.textWeatherMain);
        txtWeatherDescription = findViewById(R.id.textWeatherDescription);
        txtTemp = findViewById(R.id.textTemp);
        txtPressure = findViewById(R.id.textPressure);
        txtHumidity = findViewById(R.id.textHumidity);

        preferenceManager = new PreferenceManager(this);

        spinnerListener = new SpinnerListener(this);

        placeSpinner = findViewById(R.id.spinnerPlace);
        placeSpinner.setOnItemSelectedListener(spinnerListener);
        arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.places, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(arrayAdapter);

        for(int i = 0; i < placeSpinner.getCount(); i++)
        {
            if(placeSpinner.getItemAtPosition(i).toString().equals(preferenceManager.getPlace()))
            {
                placeSpinner.setSelection(i);
            }
        }

        mainController = new MainController(this);

        getWeather();
    }

    public void getWeather()
    {
        mainController.getWeather();
        mainController.getIcon();
    }

    public void updateWeather(MainModel mainModel)
    {
        if(mainModel.getTemperatureInCelsius() < 10)
        {
            decimalFormat = new DecimalFormat("0.00");
        }
        else
        {
            decimalFormat = new DecimalFormat("0.00");
        }
        txtWeatherMain.setText(mainModel.getWeatherMain());
        txtWeatherDescription.setText(mainModel.getWeatherDescription());
        txtTemp.setText(decimalFormat.format(mainModel.getTemperatureInCelsius()) + " °C");
        txtPressure.setText(Integer.toString(mainModel.getPressure()) + " mbar");
        txtHumidity.setText(Integer.toString(mainModel.getHumidity()) + " %");
    }

    public void updateIcon(Bitmap bitmap)
    {
        iconView.setImageBitmap(bitmap);
    }
}
