package wit.cryptoexec.backend.api.bittrex.public_api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jueh on 3/7/2018.
 */

public class BittrexPublicApiClient {
    private static final String BASE_URL = "https://bittrex.com/api/v1.1/public/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
