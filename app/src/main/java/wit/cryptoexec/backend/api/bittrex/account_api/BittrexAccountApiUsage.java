package wit.cryptoexec.backend.api.bittrex.account_api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.backend.api.bittrex.utils.EncryptionUtility;
import wit.cryptoexec.backend.api.bittrex.utils.UrlParams;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.callbacks.JSONObjectResponseHandler;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexAccountApiUsage {
    private String ERROR = "ERROR";

    private BittrexAccountApiClient client;

    public BittrexAccountApiUsage(String key, String secret) throws Throwable {
        client = new BittrexAccountApiClient(key, secret);
    }

    public void getBalances(final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();

        client.get("getbalances", urlParams, new AsyncHttpResponseHandler() {
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

    public void getBalance(String currency, final JSONObjectResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);

        client.get("getbalance", urlParams, new AsyncHttpResponseHandler() {
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

    public void getDepositAddress(String currency, final JSONObjectResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);

        client.get("getdepositaddress", urlParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    callback.onSuccess(responseJson.getJSONObject("result"));
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

    public void withdraw(String currency, String quantity, String address, final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);
        urlParams.add("quantity", quantity);
        urlParams.add("address", address);

        client.get("withdraw", urlParams, new AsyncHttpResponseHandler() {
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

    public void withdraw(String currency, String quantity, String address, String paymentId, final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);
        urlParams.add("quantity", quantity);
        urlParams.add("address", address);
        urlParams.add("paymentid", paymentId);

        client.get("withdraw", urlParams, new AsyncHttpResponseHandler() {
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

    public void getOrder(String uuid, final JSONObjectResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("uuid", uuid);

        client.get("getorder", urlParams, new AsyncHttpResponseHandler() {
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

    public void getOrderHistory(final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();

        client.get("getorderhistory", urlParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        callback.onSuccess(responseJson.getJSONArray("result"));
                    } else {
                        Log.v("ERROR", response);
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

    public void getOrderHistory(String market, final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("market", market);

        client.get("getorderhistory", urlParams, new AsyncHttpResponseHandler() {
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

    public void getWithdrawalHistory(final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();

        client.get("getwithdrawalhistory", urlParams, new AsyncHttpResponseHandler() {
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

    public void getWithdrawalHistory(String currency, final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);

        client.get("getwithdrawalhistory", urlParams, new AsyncHttpResponseHandler() {
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

    public void getDepositHistory(final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();

        client.get("getdeposithistory", urlParams, new AsyncHttpResponseHandler() {
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

    public void getDepositHistory(String currency, final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();
        urlParams.add("currency", currency);

        client.get("getdeposithistory", urlParams, new AsyncHttpResponseHandler() {
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

    public void getOpenOrders(final JSONArrayResponseHandler callback) {
        UrlParams urlParams = new UrlParams();

        client.get("getopenorders", urlParams, new AsyncHttpResponseHandler() {
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
