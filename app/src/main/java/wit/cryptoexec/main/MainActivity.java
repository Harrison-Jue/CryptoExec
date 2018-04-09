package wit.cryptoexec.main;


import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.R;
import wit.cryptoexec.exchange.AddExchangeActivity;
import wit.cryptoexec.exchange.ExchangesActivity;
import wit.cryptoexec.main.CMC_Home.CoinMarketCapAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    /* Code omitted, crashes on going back to main activity because of onSaveInstanceState error */
//    private Handler handler;
//    private Runnable refreshTask = new Runnable() {
//        @Override
//        public void run() {
//            Fragment fragment = new CoinMarketCapAdapter();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTableFrame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
//            handler.postDelayed(refreshTask, 10000);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        /* Code omitted, crashes on going back to main activity because of onSaveInstanceState error
//        handler = new Handler();
//        handler.post(refreshTask);

        Fragment fragment = new CoinMarketCapAdapter();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTableFrame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

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
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.addExchange:
                                intent = new Intent(MainActivity.this, AddExchangeActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.Portfolio:
                                intent = new Intent(MainActivity.this, ExchangesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.OpenOrders:
                                intent = new Intent(MainActivity.this, OpenOrders.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }
        );
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
