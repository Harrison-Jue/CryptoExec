package wit.cryptoexec.backend.api.callbacks;

/**
 * Created by jueh on 3/8/2018.
 */

public interface ApiDetailsHandler {
    void onSuccess(String key, String secret);
}
