package wit.cryptoexec.backend.api.coinmarket;

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

public class CoinMarketApiUsage {
    private String ERROR = "ERROR";

    private CoinMarketApiClient client = new CoinMarketApiClient();

    public static String NO_VALUE = "NO_VALUE";

    public void ticker(String start, String limit, String convert, final JSONArrayResponseHandler callback) {
        RequestParams params = new RequestParams();

        //Optional parameters via public NO_VALUE option
        if(!start.equals(NO_VALUE)) {
            params.add("start", start);
        }
        if(!limit.equals(NO_VALUE)) {
            params.add("limit", limit);
        }
        if(!convert.equals(NO_VALUE)) {
            params.add("convert", convert);
        }

        //Just in case...
        if(start.equals(NO_VALUE) && limit.equals(NO_VALUE) && convert.equals(NO_VALUE)) {
            params = null;
        }

        client.get("ticker/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                try {
                    JSONObject responseJson = new JSONObject(response);
                    if (responseJson.getBoolean("success")) {
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
