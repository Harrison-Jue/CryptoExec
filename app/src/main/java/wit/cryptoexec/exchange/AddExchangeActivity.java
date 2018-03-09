package wit.cryptoexec.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class AddExchangeActivity extends AppCompatActivity {
    private ApiDetailsDatabase apiDetailsDatabase;

    private Spinner exchangeOptions;
    private EditText apiKeySave;
    private EditText apiSecretSave;
    private Button submitApiDetails;

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exchange);

        apiDetailsDatabase = new ApiDetailsDatabase();

        exchangeOptions = findViewById(R.id.exchangeOptions);

        ArrayAdapter<CharSequence> exchangesAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.exchanges, android.R.layout.simple_spinner_item);
        exchangesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exchangeOptions.setAdapter(exchangesAdapter);
        exchangeOptions.setSelection(0);

        exchangeOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position > 0) {
                    exchangeOptions.setSelection(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Leave empty I guess
            }
        });

        apiKeySave = findViewById(R.id.apiKey);
        apiSecretSave = findViewById(R.id.apiSecret);
        submitApiDetails = findViewById(R.id.submitApiDetails);

        submitApiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exchangeService = exchangeOptions.getSelectedItem().toString();
                String apiKey = apiKeySave.getText().toString();
                String apiSecret = apiSecretSave.getText().toString();

                Log.v("Exchange Service", exchangeService);
                Log.v("API Key", apiKey);
                Log.v("API Secret", apiSecret);

                if(exchangeService.equals("Exchange Service")) {
                    Toast.makeText(getApplicationContext(), "Please select an Exchange Service", Toast.LENGTH_SHORT);
                } else {
                    try {
                        if (apiDetailsDatabase.saveApiDetails(exchangeService, apiKey, apiSecret)) {
                            Log.v("Success", "Added Api Detail to Database");
                            Intent intent = new Intent(getApplicationContext(), ExchangesActivity.class);
                            startActivity(intent);
                        } else {
                            Log.v("Failure", "Could not add Api Detail to Database");
                            Intent intent = new Intent(getApplicationContext(), ExchangesActivity.class);
                            startActivity(intent);
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        });
    }
}
