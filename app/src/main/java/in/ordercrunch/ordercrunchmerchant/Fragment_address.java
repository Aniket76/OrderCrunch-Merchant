package in.ordercrunch.ordercrunchmerchant;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_address extends Fragment {

    private TextInputLayout mShopNo, mLocation, mLandMark, mPinCode, mCity, mState;

    private Button mAddressBtn;

    private ProgressDialog mMainProgress;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;

    private String dbCostForTwo, activityNameCheck;

    public Fragment_address() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityNameCheck = getActivity().getClass().getSimpleName().toString();

        mMainProgress = new ProgressDialog(getContext());

        mShopNo = (TextInputLayout) getActivity().findViewById(R.id.address_shopno);
        mLocation = (TextInputLayout) getActivity().findViewById(R.id.address_location);
        mLandMark = (TextInputLayout) getActivity().findViewById(R.id.address_landmark);
        mPinCode = (TextInputLayout) getActivity().findViewById(R.id.address_pinCode);
        mCity = (TextInputLayout) getActivity().findViewById(R.id.address_city);
        mState = (TextInputLayout) getActivity().findViewById(R.id.address_state);

        mAddressBtn = (Button) getActivity().findViewById(R.id.btn_addAddress);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

        if (activityNameCheck.equals("MainActivity")) {

            mMainProgress.setTitle("Retrieving Data");
            mMainProgress.setMessage("Please wait while we retrieve the Data");
            mMainProgress.setCanceledOnTouchOutside(true);
            mMainProgress.show();

            mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()) {

                        mMainProgress.dismiss();

                        String dbShopNo = documentSnapshot.getString("shopNo");
                        String dbLocation = documentSnapshot.getString("location");
                        String dbLandMark = documentSnapshot.getString("landMark");
                        String dbPinCode = documentSnapshot.getString("pinCode");
                        String dbCity = documentSnapshot.getString("city");
                        String dbState = documentSnapshot.getString("state");

                        mShopNo.getEditText().setText(dbShopNo);
                        mLocation.getEditText().setText(dbLocation);
                        mLandMark.getEditText().setText(dbLandMark);
                        mPinCode.getEditText().setText(dbPinCode);
                        mCity.getEditText().setText(dbCity);
                        mState.getEditText().setText(dbState);
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            mMainProgress.dismiss();
                            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    });

        } else {

            Toast.makeText(getActivity(), "No Data found please fill the form", Toast.LENGTH_LONG).show();

        }


        mAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String shopNo = mShopNo.getEditText().getText().toString();
                String location = mLocation.getEditText().getText().toString();
                String landmark = mLandMark.getEditText().getText().toString();
                String picCode = mPinCode.getEditText().getText().toString();
                String city = mCity.getEditText().getText().toString();
                String state = mState.getEditText().getText().toString();


                if (!TextUtils.isEmpty(shopNo) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(picCode) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(state)) {

                    mMainProgress.setTitle("Saving Data");
                    mMainProgress.setMessage("Please wait while we save the Data");
                    mMainProgress.setCanceledOnTouchOutside(true);
                    mMainProgress.show();


                    Map<String, Object> restaurant = new HashMap<>();
                    restaurant.put("shopNo", shopNo);
                    restaurant.put("location", location);
                    restaurant.put("landMark", landmark);
                    restaurant.put("pinCode", picCode);
                    restaurant.put("city", city);
                    restaurant.put("state", state);

                    mDocRef.update(restaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                mMainProgress.dismiss();

                                if (activityNameCheck.equals("MainActivity")) {

                                    Fragment_Main fragment = new Fragment_Main();
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.main_activity_layout, fragment, "Main Fragment");
                                    transaction.addToBackStack(null);
                                    transaction.setReorderingAllowed(true);
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                    transaction.commit();

                                } else {

                                    Fragment_generalInfo fragment = new Fragment_generalInfo();
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.details_activity_layout, fragment, "GeneralInfoFragment");
                                    transaction.addToBackStack(null);
                                    transaction.setReorderingAllowed(true);
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                    transaction.commit();

                                }

                            } else {

                                mMainProgress.dismiss();
                                Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                } else {

                    Toast.makeText(getActivity(), "Please fill all the mandatory fileds and try again", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

}
