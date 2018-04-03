package wit.cryptoexec.exchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.ApiExchangesHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;
import wit.cryptoexec.main.MainActivity;

public class ExchangesActivity extends AppCompatActivity {

    private ApiDetailsDatabase apiDetailsDatabase;

    private DrawerLayout mDrawerLayout;

    ExpandableListAdapter explistAdapter;
    ExpandableListView expListView;
    List<String> expListHeader;
    HashMap<String, List<String>> expListChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        apiDetailsDatabase = new ApiDetailsDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setCheckable(true);
                        mDrawerLayout.closeDrawers();
                        int id = item.getItemId();
                        Intent intent;
                        switch (id) {
                            case R.id.coinmarketcaphome:
                                intent = new Intent(ExchangesActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.addExchange:
                                intent = new Intent(ExchangesActivity.this, AddExchangeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.Portfolio:
                                intent = new Intent(ExchangesActivity.this, ExchangesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.OpenOrders:
                                intent = new Intent(ExchangesActivity.this, OpenOrders.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }
        );

        expListView = findViewById(R.id.ExpandableList);
        prepareListData();

        explistAdapter = new ExpandableListAdapter(this, expListHeader, expListChild);
        expListView.setAdapter(explistAdapter);

    }


    private void prepareListData() {
        expListHeader = new ArrayList<String>();
        expListChild = new HashMap<String, List<String>>();

        final ApiDetailsDatabase database = new ApiDetailsDatabase();

        try {
            database.getExchanges(new ApiExchangesHandler() {
                @Override
                public void onSucess(ArrayList<String> response) {
                    if(!response.isEmpty()) {
                        for (String exchange : response) {
                            expListHeader.add(exchange);
                            Log.v("DEBUGEXCHANGE0", expListHeader.get(0));
                        }

                        Log.v("DEBUGLIST", expListHeader.get(0));
                        for(final String exchange : expListHeader) {
                            try {
                                database.getApiDetails(exchange, new ApiDetailsHandler() {
                                    @Override
                                    public void onSuccess(String key, String secret) throws Throwable {
                                        Log.v("DEBUGKEY", key);
                                        Log.v("DEBUGSECRET", secret);
                                        BittrexAccountApiUsage client = new BittrexAccountApiUsage(key, secret);
                                        client.getBalances(new JSONArrayResponseHandler() {
                                            @Override
                                            public void onSuccess(JSONArray response) throws JSONException {
                                                Log.v("DEBUG", response.toString());
                                                expListHeader.add(exchange);
                                                for(int i = 0; i < response.length(); i++) {
                                                    JSONObject balanceJson = response.getJSONObject(i);

                                                    String currency = balanceJson.getString("Currency");
                                                    double balance = balanceJson.getDouble("Balance");
                                                    double available = balanceJson.getDouble("Available");
                                                    double pending = balanceJson.getDouble("Pending");
                                                    String cryptoAddress = balanceJson.getString("CryptoAddress");
                                                    boolean requested  = balanceJson.optBoolean("Requested", false);
                                                    String uuid = balanceJson.optString("Uuid", "null");

                                                    Log.v("CURRENCY", currency);

                                                    List<String> children = new ArrayList<String>();
                                                    children.add(currency);
                                                    expListChild.put(expListHeader.get(0), children);
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
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

//        Log.v("DEBUGLIST", expListHeader.get(0));
//        for(String exchange : expListHeader) {
//            try {
//                database.getApiDetails(exchange, new ApiDetailsHandler() {
//                    @Override
//                    public void onSuccess(String key, String secret) throws Throwable {
//                        Log.v("DEBUGKEY", key);
//                        Log.v("DEBUGSECRET", secret);
//                        BittrexAccountApiUsage client = new BittrexAccountApiUsage(key, secret);
//                        client.getBalances(new JSONArrayResponseHandler() {
//                            @Override
//                            public void onSuccess(JSONArray response) throws JSONException {
//                                Log.v("DEBUG", response.toString());
//                                for(int i = 0; i < response.length(); i++) {
//                                    JSONObject balanceJson = response.getJSONObject(i);
//
//                                    String currency = balanceJson.getString("Currency");
//                                    double balance = balanceJson.getDouble("Balance");
//                                    double available = balanceJson.getDouble("Available");
//                                    double pending = balanceJson.getDouble("Pending");
//                                    String cryptoAddress = balanceJson.getString("CryptoAddress");
//                                    boolean requested  = balanceJson.optBoolean("Requested", false);
//                                    String uuid = balanceJson.optString("Uuid", "null");
//
//                                    List<String> Bittrex = new ArrayList<String>();
//                                    Bittrex.add(currency);
//                                    //Bittrex.add(balance);
//                                    //Bittrex.add("Coin Value");
//                                    expListChild.put(expListHeader.get(0), Bittrex);
//
//                                }
//                            }
//                        });
//                    }
//                });
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        }
    }


    private TextView noExchangesMessage() {
        TextView exchangeText = new TextView(getApplicationContext());
        exchangeText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exchangeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exchangeText.setText("No Exchanges under this Account");

        return exchangeText;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}