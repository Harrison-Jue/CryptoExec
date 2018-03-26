package wit.cryptoexec.backend.api.bittrex.market_api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import wit.cryptoexec.backend.api.bittrex.utils.EncryptionUtility;
import wit.cryptoexec.backend.api.bittrex.utils.UrlParams;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexMarketApiClient {
    private static final String BASE_URL = "https://bittrex.com/api/v1.1/market/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    private String apiKey;
    private String apiSecret;

    public BittrexMarketApiClient(String key, String secret) {
        apiKey = key;
        apiSecret = secret;
    }

    public void get(String url, UrlParams urlParams, AsyncHttpResponseHandler responseHandler) {
        urlParams.add("apikey", apiKey);
        urlParams.add("nonce", EncryptionUtility.generateNonce());
        String fullUrl = UrlParams.createFullUrl(getAbsoluteUrl(url), urlParams);
        client.addHeader("apisign", EncryptionUtility.calculateHash(apiSecret, fullUrl));
        client.get(getAbsoluteUrl(url), urlParams.requestParams, responseHandler);
    }

    public void post(String url, UrlParams urlParams, AsyncHttpResponseHandler responseHandler) {
        urlParams.add("apikey", apiKey);
        urlParams.add("nonce", EncryptionUtility.generateNonce());
        String fullUrl = UrlParams.createFullUrl(getAbsoluteUrl(url), urlParams);
        client.addHeader("apisign", EncryptionUtility.calculateHash(apiSecret, getAbsoluteUrl(url)));
        client.post(getAbsoluteUrl(url), urlParams.requestParams, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
