package wit.cryptoexec.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

/**
 * Created by kayya on 3/8/2018.
 */

public class CoinMarketCapAdapter extends Fragment {
    ListView listView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        Log.v("Test", "HERE");
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

        public  coinMarketCapData(Activity context,ListView listView)
        {
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
                    Log.v("Test", "HERE2");

                    try {
                        URL API_URL = new URL("https://api.coinmarketcap.com/v1/ticker/?start=0&limit=10");
                        HttpsURLConnection myConnection =
                                (HttpsURLConnection) API_URL.openConnection();
                        myConnection.setRequestProperty("User-Agent", "cryptoexec-V1");
                        myConnection.setRequestProperty("Accept",
                                "application/json");
                        if (myConnection.getResponseCode() == 200) {
                            InputStream responseBody = myConnection.getInputStream();
                            InputStreamReader responseBodyReader =
                                    new InputStreamReader(responseBody, "UTF-8");
                            JsonReader jsonReader = new JsonReader(responseBodyReader);
                            jsonReader.beginArray(); // Start processing the JSON object
                            while (jsonReader.hasNext()) { // Loop through all keys
                                cryptoArr.add(parseJSONArray(jsonReader));
                            }
                            Log.v("CryptoArr", cryptoArr.toString());
                            jsonReader.close();
                            myConnection.disconnect();
                        } else {
                            // Error handling code goes here
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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


        private CryptoInfo parseJSONArray(JsonReader reader) throws IOException {
            CryptoInfo crypto = new CryptoInfo();

            reader.beginObject();
            while (reader.hasNext()) {
                String keyName = reader.nextName();
                if (keyName.equals("id")) {
                    crypto.id = reader.nextString();
                } else if (keyName.equals("name")) {
                    crypto.name = reader.nextString();
                } else if (keyName.equals("symbol")) {
                    crypto.symbol = reader.nextString();
                } else if (keyName.equals("rank")) {
                    crypto.rank = Integer.parseInt(reader.nextString());
                } else if (keyName.equals("price_usd")) {
                    crypto.price_usd = Float.parseFloat(reader.nextString());
                } else if (keyName.equals("price_btc")) {
                    crypto.price_btc = Float.parseFloat(reader.nextString());
                } else if (keyName.equals("usd_volume_24_hr")) {
                    crypto.usd_volume_24_hr = Float.parseFloat(reader.nextString());
                } else if (keyName.equals("market_cap_usd")) {
                    crypto.market_cap_usd = new BigDecimal(reader.nextString());
                } else if (keyName.equals("availableSupply")) {
                    crypto.availableSupply = new BigDecimal(reader.nextString());
                } else if (keyName.equals("totalSupply")) {
                    crypto.totalSupply = new BigDecimal(reader.nextString());
                } else if (keyName.equals("maxSupply")) {
                    crypto.maxSupply = new BigDecimal(reader.nextString());
                } else if (keyName.equals("percent_change_1h")) {
                    crypto.percent_change_1h = Double.parseDouble(reader.nextString());
                } else if (keyName.equals("percent_change_24h")) {
                    crypto.percent_change_24h = Double.parseDouble(reader.nextString());
                } else if (keyName.equals("percent_change_7d")) {
                    crypto.percent_change_7d = Double.parseDouble(reader.nextString());
                } else if (keyName.equals("last_updated")) {
                    crypto.last_updated = new BigInteger(reader.nextString());
                } else {
                    reader.skipValue();
                }

            }
            reader.endObject();
            return crypto;
        }
    }
}