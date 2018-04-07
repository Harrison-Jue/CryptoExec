package wit.cryptoexec.exchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        TextView CoinName;
        CoinName = (TextView)view.findViewById(R.id.CoinName);
        CoinName.setText(item.currency);

        TextView Balance;
        Balance = (TextView)view.findViewById(R.id.Balance);
        String balanceHolder = Double.toString(item.balance);
        Balance.setText(balanceHolder);

        TextView Available;
        Available = (TextView)view.findViewById(R.id.Available);
        String availableHolder = Double.toString(item.available);
        Available.setText(availableHolder);

        TextView Pending;
        Pending = (TextView)view.findViewById(R.id.Pending);
        String pendingHolder = Double.toString(item.pending);
        Pending.setText(pendingHolder);

//        TextView CryptoAddress;
//        CryptoAddress = (TextView)view.findViewById(R.id.CryptoAddress);
//        CryptoAddress.setText(item.cryptoAddress);

        return view;
    }

}


