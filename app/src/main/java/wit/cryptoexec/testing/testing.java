package wit.cryptoexec.testing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class testing extends AppCompatActivity {
    private TextView testTextView;
    private Button testButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        testTextView = findViewById(R.id.testTextView);

        ApiDetailsDatabase database = new ApiDetailsDatabase();
        try {
            database.getApiDetails("Bittrex", new ApiDetailsHandler() {
                @Override
                public void onSuccess(String key, String secret) throws Throwable {
                    BittrexAccountApiUsage client = new BittrexAccountApiUsage(key, secret);
                    client.getBalances(new JSONArrayResponseHandler() {
                        @Override
                        public void onSuccess(JSONArray response) {
                            testTextView.setText(response.toString());
                        }
                    });
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
