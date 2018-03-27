package wit.cryptoexec.exchange;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.ApiExchangesHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

/**
 * Created by elvin on 3/23/18.
 */

public class ExpandableListDisplay extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> expListHeader;
    HashMap<String, List<String>> expListChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        expListView = findViewById(R.id.ExListView);
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, expListHeader, expListChild);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        expListHeader = new ArrayList<String>();
        expListChild = new HashMap<String, List<String>>();

        ApiDetailsDatabase database = new ApiDetailsDatabase();

        try {
            database.getExchanges(new ApiExchangesHandler() {
                @Override
                public void onSucess(ArrayList<String> response) {
                    if(!response.isEmpty()) {
                        for (String exchange : response) {
                            expListHeader.add(exchange);
                        }
                    }
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        for(String exchange : expListHeader) {
            try {
                database.getApiDetails(exchange, new ApiDetailsHandler() {
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

                                    List<String> Bittrex = new ArrayList<String>();
                                    Bittrex.add(currency);
                                    //Bittrex.add(balance);
                                    //Bittrex.add("Coin Value");
                                    expListChild.put(expListHeader.get(0), Bittrex);

                                }

//                                for(CardView cardView : cardViews) {
//                                    testLayout.addView(cardView);
//                                }
                            }
                        });
                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}