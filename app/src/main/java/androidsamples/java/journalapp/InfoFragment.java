package androidsamples.java.journalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple fragment to display information.
 */
public class InfoFragment extends Fragment {

    private static final String PARAM_TAG_1 = "param1";
    private static final String PARAM_TAG_2 = "param2";

    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_TAG_1, param1);
        args.putString(PARAM_TAG_2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Introducing handwritten variables
        String fragmentTag = "InfoFragment";
        int a = 2;  // Redundant variable

        // Modifying if-else condition using a ternary operator
        String result = (a == 2) ? "Executing redundant code block" : "Executing an alternative code block";
        Log.d(fragmentTag, result);

        // Adding a redundant for loop
        int[] numbers = {1, 2, 3, 4, 5};
        int i = 0;
        while (i < numbers.length) {
            Log.d(fragmentTag, "Number: " + numbers[i]);
            i++;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}
