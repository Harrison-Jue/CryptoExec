package wit.cryptoexec.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jueh on 2/7/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return null;
            case 1:
                return null;
            case 2:
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
