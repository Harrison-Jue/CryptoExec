package wit.cryptoexec.exchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import wit.cryptoexec.R;

public class PortfolioListAdapter extends ArrayAdapter<PortfolioInfo>{
    private LayoutInflater mInflater;
    public PortfolioListAdapter(Context context, int rid, List<PortfolioInfo> list){
        super(context, rid, list);
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PortfolioInfo item = (PortfolioInfo)getItem(position);
        View view = mInflater.inflate(R.layout.portfolio_item, null);

        TextView Exchange;
        Exchange = (TextView)view.findViewById(R.id.CoinName);
        Exchange.setText(item.currency);

        TextView orderType;
        orderType = (TextView)view.findViewById(R.id.Balance);
        String balanceHolder = Double.toString(item.balance);
        orderType.setText(balanceHolder);

        TextView Limit;
        Limit = (TextView)view.findViewById(R.id.Available);
        String availableHolder = Double.toString(item.available);
        Limit.setText(availableHolder);

        TextView Quantity;
        Quantity = (TextView)view.findViewById(R.id.Pending);
        String pendingHolder = Double.toString(item.pending);
        Quantity.setText(pendingHolder);

        TextView Remaining;
        Remaining = (TextView)view.findViewById(R.id.CryptoAddress);
        Remaining.setText(item.cryptoAddress);

        return view;
    }

}


