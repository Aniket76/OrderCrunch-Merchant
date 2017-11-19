package in.ordercrunch.ordercrunchmerchant;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Main extends Fragment {

    private BottomNavigationView mBottomNavigationItemView;

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

        mBottomNavigationItemView = (BottomNavigationView)getActivity().findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationItemView);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_cont, new Fragment_Order()).commit();
        mBottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){

                    case R.id.nav_order_btn:
                        transaction.replace(R.id.nav_cont, new Fragment_Order());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.commit();
                        return true;

                    case R.id.nav_items_btn:
                        transaction.replace(R.id.nav_cont, new Fragment_Items());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.commit();
                        return true;

                    case R.id.nav_history_btn:
                        transaction.replace(R.id.nav_cont, new Fragment_History());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.commit();
                        return true;

                    case R.id.nav_account_btn:
                        transaction.replace(R.id.nav_cont, new Fragment_Account());
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.commit();
                        return true;
                }

                return false;
            }
        });

    }
}
