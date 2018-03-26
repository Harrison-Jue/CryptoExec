package wit.cryptoexec.backend.api.callbacks;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jueh on 3/7/2018.
 */

public interface JSONObjectResponseHandler {
    void onSuccess(JSONObject response) throws JSONException;
}
