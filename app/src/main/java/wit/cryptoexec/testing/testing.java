package wit.cryptoexec.testing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class testing extends AppCompatActivity {
    private LinearLayout testLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        testLayout = findViewById(R.id.testLayout);

        ApiDetailsDatabase database = new ApiDetailsDatabase();
        try {
            database.getApiDetails("Bittrex", new ApiDetailsHandler() {
                @Override
                public void onSuccess(String key, String secret) throws Throwable {
                    BittrexAccountApiUsage client = new BittrexAccountApiUsage(key, secret);
                    client.getBalances(new JSONArrayResponseHandler() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {
                            ArrayList<CardView> cardViews = new ArrayList<>();
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject balanceJson = response.getJSONObject(i);
                                Log.v("JSON ID", Integer.toString(i));

                                String currency = balanceJson.getString("Currency");
                                double balance = balanceJson.getDouble("Balance");
                                double available = balanceJson.getDouble("Available");
                                double pending = balanceJson.getDouble("Pending");
                                String cryptoAddress = balanceJson.getString("CryptoAddress");
                                boolean requested  = balanceJson.optBoolean("Requested", false);
                                String uuid = balanceJson.optString("Uuid", "null");

                                String text = String.format("Currency: %s\nBalance: %s\nAvailable: %s\nPending: %s\nCryptoAddress: %s\nRequested: %s\nUUID: %s", currency, balance, available, pending, cryptoAddress, requested, uuid);

                                CardView cardView = new CardView(getApplicationContext());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(20, 20, 20, 20);
                                cardView.setLayoutParams(layoutParams);
                                cardView.setRadius(30);
                                cardView.setContentPadding(15, 15, 15, 15);
                                cardView.setMaxCardElevation(15);
                                cardView.setCardElevation(9);
                                cardView.setUseCompatPadding(true);

                                TextView textView = new TextView(getApplicationContext());
                                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                textView.setText(text);

                                cardView.addView(textView);
                                cardViews.add(cardView);
                            }
                            for(CardView cardView : cardViews) {
                                testLayout.addView(cardView);
                            }
                        }
                    });
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
