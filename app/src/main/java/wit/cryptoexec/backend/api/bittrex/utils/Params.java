package wit.cryptoexec.backend.api.bittrex.utils;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jueh on 3/25/2018.
 */

public class Params {
    public RequestParams requestParams;
    public Map<String, String> pararms;

    public Params() {
        requestParams = new RequestParams();
        pararms = new HashMap<>();
    }

    public void add(String key, String value) {
        requestParams.add(key, value);
        pararms.put(key, value);
    }
}
