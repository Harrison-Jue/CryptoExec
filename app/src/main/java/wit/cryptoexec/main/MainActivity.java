package wit.cryptoexec.main;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new CoinMarketCapAdapter();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTableFrame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }
}
