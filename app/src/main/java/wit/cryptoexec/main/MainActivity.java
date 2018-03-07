package wit.cryptoexec.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.public_api.BittrexPublicApiCallback;
import wit.cryptoexec.backend.api.public_api.BittrexPublicApiUsage;

public class MainActivity extends AppCompatActivity {
    private TextView restCall;
    private EditText market;
    private EditText type;
    private Button getMarkets;
    private Button getCurrencies;
    private Button getTicker;
    private Button getMarketSummaries;
    private Button getMarketSummary;
    private Button getOrderBook;
    private Button getMarketHistory;

    private BittrexPublicApiUsage publicClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restCall = findViewById(R.id.restCall);
        market = findViewById(R.id.market);
        type = findViewById(R.id.type);
        getMarkets = findViewById(R.id.getMarketsButton);
        getCurrencies = findViewById(R.id.getCurrenciesButton);
        getTicker = findViewById(R.id.getTickerButton);
        getMarketSummaries = findViewById(R.id.getMarketSummariesButton);
        getMarketSummary = findViewById(R.id.getMarketSummaryButton);
        getOrderBook = findViewById(R.id.getOrderBookButton);
        getMarketHistory = findViewById(R.id.getMarketHistoryButton);

        publicClient = new BittrexPublicApiUsage();

        getMarkets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicClient.getMarkets(new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicClient.getCurrencies(new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getTicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marketString = market.getText().toString();
                publicClient.getTicker(marketString, new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getMarketSummaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicClient.getMarketSummaries(new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getMarketSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marketString = market.getText().toString();
                publicClient.getMarketSummary(marketString, new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getOrderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marketString = market.getText().toString();
                String typeString = type.getText().toString();
                publicClient.getOrderBook(marketString, typeString, new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });

        getMarketHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marketString = market.getText().toString();
                publicClient.getMarketHistory(marketString, new BittrexPublicApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        restCall.setText(response);
                    }
                });
            }
        });
    }
}
