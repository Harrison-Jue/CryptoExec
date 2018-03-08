package wit.cryptoexec.backend.api.callbacks;

import org.json.JSONArray;

/**
 * Created by jueh on 3/7/2018.
 */

public interface JSONArrayResponseHandler {
    void onSuccess(JSONArray response);
}
