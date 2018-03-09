package wit.cryptoexec.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.coinmarket.CoinMarketApiClient;
import wit.cryptoexec.backend.api.coinmarket.CoinMarketApiUsage;

/**
 * Created by kayya on 3/8/2018.
 */

public class CoinMarketCapAdapter extends Fragment
{
    private ListView listView;

    private CoinMarketApiUsage client;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        Log.v("Test", "HERE");

        client = new CoinMarketApiUsage();

        View lv = inflater.inflate(R.layout.coinmarketcaplist, null);
        listView = (ListView) lv.findViewById(R.id.cryptomarketcap_listview);
        bindListView();
        return lv;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void bindListView() {
        new coinMarketCapData(getActivity(), listView).execute();
    }

    class coinMarketCapData extends AsyncTask<Void, Void, Integer> {

        public List<CryptoInfo> cryptoArr = new ArrayList<CryptoInfo>();
        ListView listView;
        Activity context;

        public coinMarketCapData(Activity context,ListView listView) {
            this.listView=listView;
            this.context=context;
            Log.v("Test", "HERE1");
            doInBackground();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    //Use client Java to handle the api call
                    client.ticker("0", "10", CoinMarketApiUsage.NO_VALUE, new JSONArrayResponseHandler() {
                        @Override
                        public void onSuccess(JSONArray response) {
                            try {
                                parseJSONArray(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
            return 1;
        }

        protected void onPostExecute(Integer result) {
            ListItemAdapter adapter;
            adapter = new ListItemAdapter(context, 0, cryptoArr);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.v("Test", "HERE3");

        }


        private void parseJSONArray(JSONArray jsonArray) throws Exception {
            for(int i = 0; i < jsonArray.length(); i++) {
                //Get JSONObject from JSONArray
                JSONObject result = jsonArray.getJSONObject(i);

                //Initialize and set values
                CryptoInfo crypto = new CryptoInfo();
                crypto.id = result.getString("id");
                crypto.name = result.getString("name");
                crypto.symbol = result.getString("symbol");
                crypto.rank = Integer.parseInt(result.getString("rank"));
                crypto.price_usd = Float.parseFloat(result.getString("price_usd"));
                crypto.price_btc = Float.parseFloat(result.getString("price_btc"));
                crypto.usd_volume_24_hr = Float.parseFloat(result.getString("usd_volume_24_hr"));
                crypto.market_cap_usd = new BigDecimal(result.getString("market_cap_usd"));
                crypto.available_supply = new BigDecimal(result.getString("available_supply"));
                crypto.total_supply = new BigDecimal(result.getString("total_supply"));
                crypto.max_supply = new BigDecimal(result.getString("max_supply"));
                crypto.percent_change_1h = Double.parseDouble(result.getString("percent_change_1h"));
                crypto.percent_change_24h = Double.parseDouble(result.getString("percent_change_24h"));
                crypto.percent_change_7d = Double.parseDouble(result.getString("percent_change_7d"));
                crypto.last_updated = new BigInteger(result.getString("last_updated"));

                //Add to Crypto Array
                cryptoArr.add(crypto);
            }
        }
    }
}