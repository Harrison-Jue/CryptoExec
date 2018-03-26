package wit.cryptoexec.backend.api.bittrex.market_api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.backend.api.bittrex.utils.UrlParams;
import wit.cryptoexec.backend.api.callbacks.BooleanResponseHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexMarketApiUsage {
    private String ERROR = "ERROR";

    private BittrexMarketApiClient client;

    public BittrexMarketApiUsage(String key, String secret) throws Throwable {
        client = new BittrexMarketApiClient(key, secret);
    }

    public void buyLimit(String market, String quantity, String rate, final JSONArrayResponseHandler callback) {
        UrlParams params = new UrlParams();
        params.add("market", market);
        params.add("quantity", quantity);
        params.add("rate", rate);

        client.get("buylimit", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONArray("result"));
                    } else {
                        throw new Error(String.format("success: %s", Boolean.toString(responseJson.getBoolean("success"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void sellLimit(String market, String quantity, String rate, final JSONArrayResponseHandler callback) {
        UrlParams params = new UrlParams();
        params.add("market", market);
        params.add("quantity", quantity);
        params.add("rate", rate);

        client.get("selllimit", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONArray("result"));
                    } else {
                        throw new Error(String.format("success: %s", Boolean.toString(responseJson.getBoolean("success"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void cancel(String uuid, final BooleanResponseHandler callback) {
        UrlParams params = new UrlParams();
        params.add("uuid", uuid);

        client.get("cancel", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    callback.onSuccess(responseJson.getBoolean("success"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }

    public void getOpenOrders(String market, final JSONArrayResponseHandler callback) {
        UrlParams params = new UrlParams();
        params.add("market", market);

        client.get("getopenorders", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONArray("result"));
                    } else {
                        throw new Error(String.format("success: %s", Boolean.toString(responseJson.getBoolean("success"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(ERROR, error.getMessage());
            }
        });
    }
}
