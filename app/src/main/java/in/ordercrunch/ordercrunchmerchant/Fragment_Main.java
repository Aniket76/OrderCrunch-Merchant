package in.ordercrunch.ordercrunchmerchant;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Main extends Fragment {

    private Button one,two,three,logout,activity;
    private FirebaseAuth mAuth;

    public Fragment_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        one = (Button)getActivity().findViewById(R.id.one);
        two = (Button)getActivity().findViewById(R.id.two);
        three = (Button)getActivity().findViewById(R.id.three);
        logout = (Button)getActivity().findViewById(R.id.logout);
        activity = (Button)getActivity().findViewById(R.id.activity);

        mAuth = FirebaseAuth.getInstance();

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_restaurantDetails fragment = new Fragment_restaurantDetails();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"Fragment Details");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_address fragment = new Fragment_address();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"Fragment Address");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_generalInfo fragment = new Fragment_generalInfo();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"Fragment Info");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent startIntent = new Intent(getActivity(),StartActivity.class);
                startActivity(startIntent);
                getActivity().finish();

            }
        });

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),getActivity().getClass().getSimpleName().toString(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
