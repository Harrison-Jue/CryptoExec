package wit.cryptoexec.main;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by kayya on 3/5/2018.
 */

public class CryptoInfo {
    public String id = "";
    public String name = "";
    public String symbol = "";
    public int rank = 0;
    public float price_usd;
    public float price_btc;
    public float usd_volume_24_hr;
    public BigDecimal market_cap_usd;
    public BigDecimal available_supply;
    public BigDecimal total_supply;
    public BigDecimal max_supply;
    public Double percent_change_1h;
    public Double percent_change_24h;
    public Double percent_change_7d;
    public BigInteger last_updated;
}
