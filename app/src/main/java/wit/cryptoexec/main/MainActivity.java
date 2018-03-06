package wit.cryptoexec.main;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

    private static final String TAG = "TAG";
    public List<CryptoInfo> cryptoArr = new ArrayList<CryptoInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveDataFromAPI(); //gets data from coinmarketcap API https://coinmarketcap.com/api/
    }

    private void retrieveDataFromAPI() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL API_URL = new URL("https://api.coinmarketcap.com/v1/ticker/?start=0&limit=10");
                    HttpsURLConnection myConnection =
                            (HttpsURLConnection) API_URL.openConnection();
                    myConnection.setRequestProperty("User-Agent", "cryptoexec-V1");
                    myConnection.setRequestProperty("Accept",
                            "application/json");
                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        jsonReader.beginArray(); // Start processing the JSON object
                        while (jsonReader.hasNext()) { // Loop through all keys
                            cryptoArr.add(parseJSONArray(jsonReader));
                        }
                        jsonReader.close();
                        myConnection.disconnect();
                    } else {
                        // Error handling code goes here
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private CryptoInfo parseJSONArray(JsonReader reader) throws IOException {
        String id = null;
        String name = null;
        String symbol = null;
        int rank = 0;
        float price_usd = 0;
        float price_btc = 0;
        float usd_volume_24_hr = 0;
        BigDecimal market_cap_usd = null;
        BigDecimal availableSupply = null;
        BigDecimal totalSupply = null;
        BigDecimal maxSupply = null;
        Double percent_change_1h = 0.0;
        Double percent_change_24h = 0.0;
        Double percent_change_7d = 0.0;
        BigInteger last_updated = null; //UNIX TIMESTAMP

        reader.beginObject();
        while (reader.hasNext()) {
            String keyName = reader.nextName();
            if (keyName.equals("id")) {
                id = reader.nextString();
            } else if (keyName.equals("name")) {
                name = reader.nextString();
            } else if (keyName.equals("symbol")) {
                symbol = reader.nextString();
            } else if (keyName.equals("rank")) {
                rank = Integer.parseInt(reader.nextString());
            } else if (keyName.equals("price_usd")) {
                price_usd = Float.parseFloat(reader.nextString());
            } else if (keyName.equals("price_btc")) {
                price_btc = Float.parseFloat(reader.nextString());
            } else if (keyName.equals("usd_volume_24_hr")) {
                usd_volume_24_hr = Float.parseFloat(reader.nextString());
            } else if (keyName.equals("market_cap_usd")) {
                market_cap_usd = new BigDecimal(reader.nextString());
            } else if (keyName.equals("availableSupply")) {
                availableSupply = new BigDecimal(reader.nextString());
            } else if (keyName.equals("totalSupply")) {
                totalSupply = new BigDecimal(reader.nextString());
            } else if (keyName.equals("maxSupply")) {
                maxSupply = new BigDecimal(reader.nextString());
            } else if (keyName.equals("percent_change_1h")) {
                percent_change_1h = Double.parseDouble(reader.nextString());
            } else if (keyName.equals("percent_change_24h")) {
                percent_change_24h = Double.parseDouble(reader.nextString());
            } else if (keyName.equals("percent_change_7d")) {
                percent_change_7d = Double.parseDouble(reader.nextString());
            } else if (keyName.equals("last_updated")) {
                last_updated = new BigInteger(reader.nextString());
            } else {
                reader.skipValue();
            }

        }
        reader.endObject();
        return new CryptoInfo(id, name, symbol, rank, price_usd, price_btc, usd_volume_24_hr, market_cap_usd, availableSupply,
                totalSupply, maxSupply, percent_change_1h, percent_change_24h, percent_change_7d, last_updated);
    }
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
