package tk.dalpiazsolutions.weathersolution.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import tk.dalpiazsolutions.weathersolution.R;
import tk.dalpiazsolutions.weathersolution.WeatherDownloader;

public class AddPlaceActivity extends AppCompatActivity {

    Button mbuttonAdd;
    EditText txtNewPlace;
    TextView txtSuggest;
    String[] suggestions;
    String suggestion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        mbuttonAdd = findViewById(R.id.buttonAdd);
        txtNewPlace = findViewById(R.id.textNewPlace);
        txtSuggest = findViewById(R.id.textSuggestion);


        WeatherDownloader weatherDownloader = new WeatherDownloader();
        try {
            String suggestionString = weatherDownloader.execute("http://www.dalpiaz-solutions.tk/wp-content/uploads/2018/04/suggestions.txt").get();
            suggestions = suggestionString.split(",");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        txtNewPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length() != 0) {
                    checkSuggest(charSequence.toString().substring(0, 1).toUpperCase() + charSequence.toString().substring(1));
                    txtSuggest.setText(suggestion);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNewPlace.setText(txtSuggest.getText().toString());
                txtNewPlace.setSelection(txtSuggest.getText().toString().length());
            }
        });

    }

    public void addPlace(View view)
    {
        if(txtNewPlace.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), getString(R.string.pleaseenter), Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("place", txtNewPlace.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    public void back(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkSuggest(String text)
    {
        for(int i = 0; i < suggestions.length; i++)
        {
            if (suggestions[i].indexOf(text) > -1)
            {
                suggestion = suggestions[i];
            }
        }
    }
}
