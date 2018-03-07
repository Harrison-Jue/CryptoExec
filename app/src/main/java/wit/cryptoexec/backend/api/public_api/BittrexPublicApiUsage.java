package wit.cryptoexec.backend.api.public_api;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.main.MainActivity;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexPublicApiUsage {
    private String ERROR = "ERROR";

    BittrexPublicApiClient client = new BittrexPublicApiClient();

    public void getMarkets(final BittrexPublicApiCallback callback) {
        client.get("getmarkets", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getCurrencies(final BittrexPublicApiCallback callback) {
        client.get("getcurrencies", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    //Market is the type of Coin to look at
    public void getTicker(String market, final BittrexPublicApiCallback callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getticker", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getMarketSummaries(final BittrexPublicApiCallback callback) {
        client.get("getmarketsummaries", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getMarketSummary(String market, final BittrexPublicApiCallback callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getmarketsummary", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getOrderBook(String market, String type, final BittrexPublicApiCallback callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        params.add("type", type);
        client.get("getorderbook", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getMarketHistory(String market, final BittrexPublicApiCallback callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getmarkethistory", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }
}
