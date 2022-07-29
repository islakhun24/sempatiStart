package sempati.star.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sempati.star.app.R;
import sempati.star.app.databinding.FragmentTransaksiBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransaksiFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransaksiFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransaksiFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransaksiFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static TransaksiFrag newInstance(String param1, String param2) {
        TransaksiFrag fragment = new TransaksiFrag();
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

    FragmentTransaksiBinding v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = FragmentTransaksiBinding.inflate(inflater, container, false);

        v.shimmerViewContainer.setVisibility(View.VISIBLE);

        return v.getRoot();
    }
}