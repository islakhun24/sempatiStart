package sempati.star.app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import sempati.star.app.fragment.LaporanManifestFragment;
import sempati.star.app.fragment.LaporanTransaksiFragment;

public class ViewPagerAdapterLihatMenu extends FragmentStatePagerAdapter {

    int tabCount;

    //Constructor to the class
    public ViewPagerAdapterLihatMenu(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LaporanTransaksiFragment tab1 = new LaporanTransaksiFragment();
                return tab1;
            case 1:
                LaporanManifestFragment tab2 = new LaporanManifestFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
