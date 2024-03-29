package wit.cryptoexec.exchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.R;
import wit.cryptoexec.main.MainActivity;

public class ExchangesActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchanges);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Fragment fragment = new ExchangeAccountAdapter();
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




