package wit.cryptoexec.exchange;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import wit.cryptoexec.R;

/**
 * Created by elvin on 3/23/18.
 */

public class ExpandableListDisplay extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> expListHeader;
    HashMap<String, List<String>> expListChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        expListView = findViewById(R.id.ExpandableList);
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, expListHeader, expListChild);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        expListHeader = new ArrayList<String>();
        expListChild = new HashMap<String, List<String>>();

        expListHeader.add("Bittrex");

        List<String> Bittrex = new ArrayList<String>();
        Bittrex.add("Coin Name");
        Bittrex.add("Coin Amount");
        Bittrex.add("Coin Value");
        expListChild.put(expListHeader.get(0), Bittrex);
    }
}
