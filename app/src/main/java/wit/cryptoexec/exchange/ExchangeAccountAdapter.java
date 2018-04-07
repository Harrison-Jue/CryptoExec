package wit.cryptoexec.exchange;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

public class ExchangeAccountAdapter  extends android.support.v4.app.Fragment{
    private ListView listView;
    private BittrexAccountApiUsage client;
    public List<PortfolioInfo> cryptoArr = new ArrayList<PortfolioInfo>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lv = inflater.inflate(R.layout.portfoliolist, null);
        listView = (ListView) lv.findViewById(R.id.portfolio_listview);

        bindListView();

        return lv;
    }

    private void bindListView() {
        new ExchangeAcccountData(getActivity(), listView).execute();
    }

    class ExchangeAcccountData extends AsyncTask<Void, Void, Integer> {

        public List<PortfolioInfo> cryptoArr = new ArrayList<PortfolioInfo>();
        ListView listView;
        Activity context;

        public ExchangeAcccountData(Activity context, ListView listView) {
            this.listView = listView;
            this.context = context;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            //Use client Java to handle the api call
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ApiDetailsDatabase database = new ApiDetailsDatabase();
                    try {
                        database.getApiDetails("Bittrex", new ApiDetailsHandler() {
                            @Override
                            public void onSuccess(String key, String secret) throws Throwable {
                                client = new BittrexAccountApiUsage(key, secret);
                                client.getBalances(new JSONArrayResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONArray response) throws JSONException {
                                        Log.v("RESPONSELENGTH", String.valueOf(response.length()));
                                        for(int i = 0; i < response.length(); i++) {
                                            JSONObject balanceJson = response.getJSONObject(i);
                                            PortfolioInfo accountBalance = new PortfolioInfo();

                                            accountBalance.uuid = balanceJson.optString("Uuid", "null");

                                            accountBalance.currency = balanceJson.getString("Currency");
                                            accountBalance.balance = new BigDecimal(balanceJson.getDouble("Balance"));
                                            accountBalance.available = new BigDecimal(balanceJson.getDouble("Available"));
                                            accountBalance.pending = balanceJson.getDouble("Pending");

                                            //accountBalance.cryptoAddress = balanceJson.getString("CryptoAddress");
                                            cryptoArr.add(accountBalance);
                                        }
                                        PortfolioListAdapter adapter = new PortfolioListAdapter(context, 0, cryptoArr);
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            };
            handler.post(runnable);
            return 1;
        }
    }
}
