package wit.cryptoexec.main;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by kayya on 3/5/2018.
 */

public class CryptoInfo {
    public String id;
    public String name;
    public String symbol;
    public int rank;
    public float price_usd;
    public float price_btc;
    public float usd_volume_24_hr;
    public BigDecimal market_cap_usd;
    public BigDecimal availableSupply;
    public  BigDecimal totalSupply;
    public BigDecimal maxSupply;
    public Double percent_change_1h;
    public Double percent_change_24h;
    public Double percent_change_7d;
    public  BigInteger last_updated;
}
