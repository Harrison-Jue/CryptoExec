package wit.cryptoexec.backend.api.bittrex.utils;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jueh on 3/25/2018.
 */

public class UrlParams {
    public RequestParams requestParams;
    public Map<String, String> pararms;

    public static String NULL_VALUE = "NULL_VALUE";

    public UrlParams() {
        requestParams = new RequestParams();
        pararms = new HashMap<>();
    }

    public void add(String key, String value) {
        if(!value.equals(NULL_VALUE)) {
            requestParams.add(key, value);
            pararms.put(key, value);
        }
    }

    public static String createFullUrl(String absoluteUrl, UrlParams urlParams){
        StringBuilder fullUrl = new StringBuilder(absoluteUrl + "?");
        Map<String, String> requestParameters = urlParams.pararms;
        int size = requestParameters.size();
        int counter = 1;
        for(String key : requestParameters.keySet()) {
            fullUrl.append(key).append("=").append(requestParameters.get(key));
            if(counter < size) {
                fullUrl.append("&");
            }
            counter++;
        }
        return fullUrl.toString();
    }
}
