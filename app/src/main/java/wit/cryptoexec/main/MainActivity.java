package wit.cryptoexec.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import wit.cryptoexec.R;
import wit.cryptoexec.add_exchange.AddExchangeActivity;
import wit.cryptoexec.backend.api.callbacks.ApiExchangesHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.callbacks.JSONObjectResponseHandler;
import wit.cryptoexec.backend.api.public_api.BittrexPublicApiUsage;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class MainActivity extends AppCompatActivity {

    private ApiDetailsDatabase apiDetailsDatabase;

    private LinearLayout exchangesLayout;
    private FloatingActionButton addExchanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private TextView noExchangesMessage() {
        TextView exchangeText = new TextView(getApplicationContext());
        exchangeText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exchangeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exchangeText.setText("No Exchanges under this Account");

        return exchangeText;
    }

    private CardView createCardView(String exchangeString) {
        CardView cardView = new CardView(getApplicationContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 0, 0);
        cardView.setLayoutParams(layoutParams);

        cardView.setRadius(30);

        cardView.setContentPadding(15, 15, 15, 15);

        cardView.setMaxCardElevation(15);

        cardView.setCardElevation(9);

        TextView exchangeText = new TextView(getApplicationContext());
        exchangeText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        exchangeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exchangeText.setText(exchangeString);

        cardView.addView(exchangeText);

        return cardView;
    }
}
