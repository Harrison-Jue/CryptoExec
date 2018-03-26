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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.callbacks.ApiExchangesHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;
import wit.cryptoexec.main.CMC_Home.CoinMarketCapAdapter;
import wit.cryptoexec.main.MainActivity;

public class ExchangesActivity extends AppCompatActivity {

    private ApiDetailsDatabase apiDetailsDatabase;

    private LinearLayout exchangesLayout;
    private FloatingActionButton addExchanges;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        apiDetailsDatabase = new ApiDetailsDatabase();

        exchangesLayout = findViewById(R.id.exchangesLayout);

        addExchanges = findViewById(R.id.addExchange);

        addExchanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddExchangeActivity.class);
                startActivity(intent);
            }
        });


        try {
            apiDetailsDatabase.getExchanges(new ApiExchangesHandler() {
                @Override
                public void onSucess(ArrayList<String> response) {
                    if(!response.isEmpty()) {
                        for (String exchange : response) {
                            exchangesLayout.addView(createCardView(exchange));
                        }
                    } else {
                        exchangesLayout.addView(noExchangesMessage());
                    }
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

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

    }

    private TextView noExchangesMessage() {
        TextView exchangeText = new TextView(getApplicationContext());
        exchangeText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exchangeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exchangeText.setText("No Exchanges under this Account");

        return exchangeText;
    }

    private CardView createCardView(final String exchangeString) {
        CardView cardView = new CardView(getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(30);
        cardView.setContentPadding(10, 10, 10, 10);
        cardView.setMaxCardElevation(15);
        cardView.setCardElevation(9);
        cardView.setUseCompatPadding(true);

        RelativeLayout layout = new RelativeLayout(getApplicationContext());
        layout.setLayoutParams(layoutParams);

        TextView exchangeText = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams relativeLayoutStart = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutStart.addRule(RelativeLayout.ALIGN_PARENT_START);
        relativeLayoutStart.addRule(RelativeLayout.CENTER_VERTICAL);
        exchangeText.setLayoutParams(relativeLayoutStart);
        exchangeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exchangeText.setText(exchangeString);
        exchangeText.setTextSize(20);
        layout.addView(exchangeText);

        Button deleteButton = new Button(getApplicationContext());
        RelativeLayout.LayoutParams relativeLayoutEnd = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutEnd.addRule(RelativeLayout.ALIGN_PARENT_END);
        deleteButton.setLayoutParams(relativeLayoutEnd);
        deleteButton.setText("Delete");
        deleteButton.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiDetailsDatabase.deleteApiDetails(exchangeString);
                finish();
                Intent intent = new Intent(getApplicationContext(), ExchangesActivity.class);
                startActivity(intent);
            }
        });

        layout.addView(deleteButton);
        cardView.addView(layout);

        return cardView;
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