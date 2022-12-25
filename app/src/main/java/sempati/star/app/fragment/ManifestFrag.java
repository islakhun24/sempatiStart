package sempati.star.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import sempati.star.app.R;
import sempati.star.app.adapter.ViewPagerAdapterLihatMenu;
import sempati.star.app.databinding.FragmentBookingBinding;
import sempati.star.app.databinding.FragmentManifestBinding;

public class ManifestFrag extends Fragment implements TabLayout.BaseOnTabSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManifestFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManifestFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ManifestFrag newInstance(String param1, String param2) {
        ManifestFrag fragment = new ManifestFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentManifestBinding v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = FragmentManifestBinding.inflate(inflater, container, false);

        v.tabLayout.addTab(v.tabLayout.newTab().setText("TRANSAKSI"));
        v.tabLayout.addTab(v.tabLayout.newTab().setText("MANIFEST"));
        v.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapterLihatMenu adapter = new ViewPagerAdapterLihatMenu(getActivity().getSupportFragmentManager(), v.tabLayout.getTabCount());
        v.viewpager.setAdapter(adapter);
        v.tabLayout.setOnTabSelectedListener(this);

        return v.getRoot();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        v.viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}