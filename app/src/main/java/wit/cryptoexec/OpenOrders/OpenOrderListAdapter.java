package wit.cryptoexec.OpenOrders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import wit.cryptoexec.R;
import wit.cryptoexec.exchange.PortfolioInfo;
import wit.cryptoexec.main.CMC_Home.CryptoInfo;

/**
 * Created by kayyaliz on 3/27/2018.
 */

public class OpenOrderListAdapter  extends ArrayAdapter<OpenOrderInfo> {

    private LayoutInflater mInflater;
    public OpenOrderListAdapter(Context context, int rid, List<OpenOrderInfo> list){
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        OpenOrderInfo item = (OpenOrderInfo)getItem(position);
        View view = mInflater.inflate(R.layout.openorder_item, null);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);

        TextView Exchange;
        Exchange = (TextView)view.findViewById(R.id.Exchange);
        Exchange.setText(item.Exchange);

        TextView orderType;
        orderType = (TextView)view.findViewById(R.id.OrderType);
        orderType.setText(item.OrderType);

        TextView Limit;
        Limit = (TextView)view.findViewById(R.id.Limit);
        Limit.setText(item.Limit.toString());

        TextView Quantity;
        Quantity = (TextView)view.findViewById(R.id.Quantity);
        Quantity.setText("Quantity");

        TextView Remaining;
        Remaining = (TextView)view.findViewById(R.id.QuantityRemaining);
        Remaining.setText(item.QuantityRemaining + "/" + item.Quantity);

        return view;
    }
}
