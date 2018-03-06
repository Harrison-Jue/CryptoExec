package wit.cryptoexec.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.ApiDetailsDatabase;

public class MainActivity extends AppCompatActivity {
    private ApiDetailsDatabase apiDetailsDatabase;

    private EditText exchangeServiceSave;
    private EditText apiKeySave;
    private EditText apiSecretSave;
    private Button submitApiDetails;

    private EditText getExchangeServiceGet;
    private Button submitGetApiDetails;
    private TextView apiDetailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiDetailsDatabase = new ApiDetailsDatabase();

        exchangeServiceSave = findViewById(R.id.exchangeService);
        apiKeySave = findViewById(R.id.apiKey);
        apiSecretSave = findViewById(R.id.apiSecret);
        submitApiDetails = findViewById(R.id.submitApiDetails);

        getExchangeServiceGet = findViewById(R.id.exchangeServiceInput);
        submitGetApiDetails = findViewById(R.id.getApiDetails);
        apiDetailsText = findViewById(R.id.apiDetailsText);

        submitApiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exchangeService = exchangeServiceSave.getText().toString();
                String apiKey = apiKeySave.getText().toString();
                String apiSecret = apiSecretSave.getText().toString();

                Log.v("Exchange Service", exchangeService);
                Log.v("API Key", apiKey);
                Log.v("API Secret", apiSecret);

                try {
                    if(apiDetailsDatabase.saveApiDetails(exchangeService, apiKey, apiSecret)) {
                        Log.v("Success", "Added Api Detail to Database");
                    } else {
                        Log.v("Failure", "Could not add Api Detail to Database");
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        submitGetApiDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String exchangeService = getExchangeServiceGet.getText().toString();
                try {
                    apiDetailsDatabase.getApiKey(exchangeService).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(final String apiKey) {
                            try {
                                apiDetailsDatabase.getApiSecret(exchangeService).addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(String apiSecret) {
                                        apiDetailsText.setText(String.format("Exchange Service: %s\nAPI Key: %s\nAPI Secret: %s", exchangeService, apiKey, apiSecret));
                                    }
                                });
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    });
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
