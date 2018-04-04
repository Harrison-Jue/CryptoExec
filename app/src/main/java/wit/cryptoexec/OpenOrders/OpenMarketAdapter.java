package wit.cryptoexec.OpenOrders;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import wit.cryptoexec.R;
import wit.cryptoexec.backend.api.bittrex.account_api.BittrexAccountApiUsage;
import wit.cryptoexec.backend.api.bittrex.market_api.BittrexMarketApiUsage;
import wit.cryptoexec.backend.api.bittrex.utils.UrlParams;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.coinmarket.CoinMarketApiUsage;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;
import wit.cryptoexec.main.CMC_Home.CryptoInfo;
import wit.cryptoexec.main.CMC_Home.ListItemAdapter;

/**
 * Created by topaniana on 3/23/2018.
 */

public class OpenMarketAdapter extends Fragment {
    private ListView listView;

    private BittrexMarketApiUsage client;

    public List<OpenOrderInfo> cryptoArr = new ArrayList<OpenOrderInfo>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View lv = inflater.inflate(R.layout.openorderslist, null);
        listView = (ListView) lv.findViewById(R.id.openorders_listview);

        bindListView();

        return lv;
    }

    private void bindListView() {
        new openOrdersData(getActivity(), listView).execute();
    }

    class openOrdersData extends AsyncTask<Void, Void, Integer> {

        public List<OpenOrderInfo> cryptoArr = new ArrayList<OpenOrderInfo>();
        ListView listView;
        Activity context;

        public openOrdersData(Activity context, ListView listView) {
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
                                client = new BittrexMarketApiUsage(key, secret);
                                client.getOpenOrders(UrlParams.NULL_VALUE, new JSONArrayResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONArray response) throws JSONException {
                                        Log.v("RESPONSELENGTH", String.valueOf(response.length()));
                                        for(int i = 0; i < response.length(); i++) {
                                            JSONObject openOrder = response.getJSONObject(i);
                                            OpenOrderInfo order = new OpenOrderInfo();
                                            order.OrderUuid = openOrder.getString("OrderUuid");
                                            Log.v("UUID1", order.OrderUuid);
                                            order.Exchange = openOrder.getString("Exchange");
                                            order.OrderType = openOrder.getString("OrderType");
                                            order.Quantity = openOrder.getString("Quantity");
                                            order.QuantityRemaining = openOrder.getString("Quantity");
                                            order.Limit =  BigDecimal.valueOf(openOrder.getDouble("Quantity"));
                                            cryptoArr.add(order);
                                        }
                                        OpenOrderListAdapter adapter = new OpenOrderListAdapter(context, 0, cryptoArr);
                                        listView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                    //   OpenOrderListAdapter adapter = new OpenOrderListAdapter(context, 0, cryptoArr)
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


