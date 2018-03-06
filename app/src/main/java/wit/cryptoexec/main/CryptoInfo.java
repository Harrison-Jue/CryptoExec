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

    public CryptoInfo (String id, String name, String symbol, int rank, float price_usd, float price_btc, float usd_volume_24_hr, BigDecimal market_cap_usd, BigDecimal availableSupply,
                       BigDecimal totalSupply, BigDecimal maxSupply, Double percent_change_1h, Double percent_change_24h, Double percent_change_7d, BigInteger last_updated) {
        id = this.id;
        name = this.name;
        symbol = this.symbol;
        rank = this.rank;
        price_usd = this.price_usd;
        price_btc = this.price_btc;
        usd_volume_24_hr = this.usd_volume_24_hr;
        market_cap_usd = this.market_cap_usd;
        availableSupply = this.availableSupply;
        totalSupply = this.totalSupply;
        maxSupply = this.maxSupply;
        percent_change_1h = this.percent_change_1h;
        percent_change_24h = this.percent_change_24h;
        percent_change_7d = this.percent_change_7d;
        last_updated = this.last_updated;
    }
}
