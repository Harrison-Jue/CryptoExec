package wit.cryptoexec.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.R;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;
import wit.cryptoexec.main.MainActivity;

public class AddExchangeActivity extends AppCompatActivity {
    private ApiDetailsDatabase apiDetailsDatabase;

    private Spinner exchangeOptions;
    private EditText apiKeySave;
    private EditText apiSecretSave;
    private Button submitApiDetails;

    private static final String TAG = "TAG";

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exchange);

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
                                intent = new Intent(AddExchangeActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.addExchange:
                                intent = new Intent(AddExchangeActivity.this, AddExchangeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.Portfolio:
                                intent = new Intent(AddExchangeActivity.this, ExchangesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.OpenOrders:
                                intent = new Intent(AddExchangeActivity.this, OpenOrders.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }
        );

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
