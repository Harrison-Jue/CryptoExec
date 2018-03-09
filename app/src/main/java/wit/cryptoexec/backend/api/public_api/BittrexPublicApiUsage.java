package wit.cryptoexec.backend.api.public_api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.callbacks.JSONObjectResponseHandler;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexPublicApiUsage {
    private String ERROR = "ERROR";

    private BittrexPublicApiClient client = new BittrexPublicApiClient();

    public void getMarkets(final JSONArrayResponseHandler callback) {
        client.get("getmarkets", null, new AsyncHttpResponseHandler() {
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

    public void getCurrencies(final JSONArrayResponseHandler callback) {
        client.get("getcurrencies", null, new AsyncHttpResponseHandler() {
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

    //Market is the type of Coin to look at
    public void getTicker(String market, final JSONObjectResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getticker", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONObject("result"));
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

    public void getMarketSummaries(final JSONArrayResponseHandler callback) {
        client.get("getmarketsummaries", null, new AsyncHttpResponseHandler() {
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

    public void getMarketSummary(String market, final JSONArrayResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getmarketsummary", params, new AsyncHttpResponseHandler() {
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

    public void getOrderBook(String market, String type, final JSONObjectResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        params.add("type", type);
        client.get("getorderbook", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONObject("result"));
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

    public void getMarketHistory(String market, final JSONArrayResponseHandler callback) {
        RequestParams params = new RequestParams();
        params.add("market", market);
        client.get("getmarkethistory", params, new AsyncHttpResponseHandler() {
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
