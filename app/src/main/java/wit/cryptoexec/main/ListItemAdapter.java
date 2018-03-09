package wit.cryptoexec.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import wit.cryptoexec.R;

/**
 * Created by kayya on 3/6/2018.
 */

public class ListItemAdapter extends ArrayAdapter<CryptoInfo> {

    private LayoutInflater mInflater;
    public ListItemAdapter(Context context, int rid, List<CryptoInfo> list){
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CryptoInfo item = (CryptoInfo)getItem(position);
        View view = mInflater.inflate(R.layout.crypto_item, null);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);

        TextView symbol;
        symbol = (TextView)view.findViewById(R.id.symbol);
        symbol.setText(item.symbol);

        TextView fullName;
        fullName = (TextView)view.findViewById(R.id.fullname);
        fullName.setText(item.name);

        TextView change_24hr;
        change_24hr = (TextView)view.findViewById(R.id.change_24hr);
        change_24hr.setText(item.percent_change_24h.toString() +"%");

        TextView priceUSD;
        priceUSD = (TextView)view.findViewById(R.id.price_USD);
        priceUSD.setText("$" + String.valueOf(df.format(item.price_usd)));

        TextView priceBTC;
        priceBTC = (TextView)view.findViewById(R.id.price_BTC);
        priceBTC.setText(String.valueOf(df.format(item.price_btc)) + " BTC");

        return view;
    }
}
