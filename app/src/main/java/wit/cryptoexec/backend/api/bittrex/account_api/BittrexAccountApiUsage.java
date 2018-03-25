package wit.cryptoexec.backend.api.bittrex.account_api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import wit.cryptoexec.backend.api.bittrex.utils.EncryptionUtility;
import wit.cryptoexec.backend.api.bittrex.utils.Params;
import wit.cryptoexec.backend.api.callbacks.ApiKeyHandler;
import wit.cryptoexec.backend.api.callbacks.JSONArrayResponseHandler;
import wit.cryptoexec.backend.api.callbacks.JSONObjectResponseHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexAccountApiUsage {
    private String ERROR = "ERROR";

    private BittrexAccountApiClient client;

    private String apiKey;

    public BittrexAccountApiUsage(String key, String secret) throws Throwable {
        client = new BittrexAccountApiClient(key, secret);
        apiKey = key;
    }

    public void getBalances(final JSONArrayResponseHandler callback) {
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());

        client.get("getbalances", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);

        client.get("getbalance", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);

        client.get("getdepositaddress", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);
        params.add("quantity", quantity);
        params.add("address", address);

        client.get("withdraw", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);
        params.add("quantity", quantity);
        params.add("address", address);
        params.add("paymentid", paymentId);

        client.get("withdraw", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("uuid", uuid);

        client.get("getorder", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());

        client.get("getorderhistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("market", market);

        client.get("getorderhistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());

        client.get("getwithdrawalhistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);

        client.get("getwithdrawalhistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());

        client.get("getdeposithistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());
        params.add("currency", currency);

        client.get("getdeposithistory", params, new AsyncHttpResponseHandler() {
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
        Params params = new Params();
        params.add("apikey", apiKey);
        params.add("nonce", EncryptionUtility.generateNonce());

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
