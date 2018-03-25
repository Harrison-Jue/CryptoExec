package wit.cryptoexec.backend.api.bittrex.account_api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import wit.cryptoexec.backend.api.bittrex.utils.EncryptionUtility;
import wit.cryptoexec.backend.api.bittrex.utils.Params;
import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.ApiKeyHandler;
import wit.cryptoexec.backend.database.ApiDetailsDatabase;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexAccountApiClient {
    private static final String BASE_URL = "https://bittrex.com/api/v1.1/account/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private String apiKey;
    private String apiSecret;

    public BittrexAccountApiClient(String key, String secret) throws Throwable {
        apiKey = key;
        apiSecret = secret;
    }

    public void get(String url, Params params, AsyncHttpResponseHandler responseHandler) {
        String fullUrl = createFullUrl(url, params);
        Log.v("DEBUG", fullUrl);
        client.addHeader("apisign", EncryptionUtility.calculateHash(apiSecret, fullUrl));
        client.get(getAbsoluteUrl(url), params.requestParams, responseHandler);
    }

    public void post(String url, Params params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("apisign", EncryptionUtility.calculateHash(apiSecret, getAbsoluteUrl(url)));
        client.post(getAbsoluteUrl(url), params.requestParams, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private String createFullUrl(String url, Params params) {
        String fullUrl = getAbsoluteUrl(url) + "?";
        Map<String, String> requestParameters = params.pararms;
        int size = requestParameters.size();
        int counter = 1;
        for(String key : requestParameters.keySet()) {
            fullUrl += key + "=" + requestParameters.get(key);
            if(counter < size) {
                fullUrl += "&";
            }
            counter++;
        }
        return fullUrl;
    }
}
