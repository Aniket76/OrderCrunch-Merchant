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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
public class Fragment_RestaurantDetails extends Fragment {

    private TextInputLayout mRestaurantName, mTagLine, mEmail, mWebsite, mPhoneNumber, mAlterPhoneNumber, mCuisines;

    private CheckBox mDineIn, mTakeAway, mHomeDelivery, mOnTheGo;

    private ImageView mChangeNumber,mChangeEmail;

    private Button mDetailsBtn;

    private Switch mVeg;

    private ProgressDialog mMainProgress;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;

    private String activityNameCheck;

    public Fragment_RestaurantDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityNameCheck = getActivity().getClass().getSimpleName().toString();

        mRestaurantName = (TextInputLayout) getActivity().findViewById(R.id.details_name);
        mTagLine = (TextInputLayout) getActivity().findViewById(R.id.details_tagLine);
        mEmail = (TextInputLayout) getActivity().findViewById(R.id.details_email);
        mWebsite = (TextInputLayout) getActivity().findViewById(R.id.details_website);
        mPhoneNumber = (TextInputLayout) getActivity().findViewById(R.id.details_phoneNumber);
        mAlterPhoneNumber = (TextInputLayout) getActivity().findViewById(R.id.details_alterPhoneNumber);
        mCuisines = (TextInputLayout) getActivity().findViewById(R.id.details_cuisines);

        mDineIn = (CheckBox) getActivity().findViewById(R.id.details_dinein);
        mTakeAway = (CheckBox) getActivity().findViewById(R.id.details_takeaway);
        mHomeDelivery = (CheckBox) getActivity().findViewById(R.id.details_homedelivery);
        mOnTheGo = (CheckBox) getActivity().findViewById(R.id.details_onthego);

        mChangeNumber = (ImageView) getActivity().findViewById(R.id.details_changeNumber);
        mChangeEmail = (ImageView) getActivity().findViewById(R.id.details_changeEmail);

        mVeg = (Switch) getActivity().findViewById(R.id.details_veg_switch);

        mDetailsBtn = (Button) getActivity().findViewById(R.id.btn_addDetails);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

        mMainProgress = new ProgressDialog(getContext());

        mMainProgress.setTitle("Retrieving Data");
        mMainProgress.setMessage("Please wait while we retrieve the Data");
        mMainProgress.setCanceledOnTouchOutside(true);
        mMainProgress.show();

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    mMainProgress.dismiss();

                    mPhoneNumber.setEnabled(false);
                    mEmail.setEnabled(false);

                    String dbName = documentSnapshot.getString("aaName");
                    String dbTagLine = documentSnapshot.getString("tagLine");
                    String dbEmail = documentSnapshot.getString("email");
                    String dbWebsite = documentSnapshot.getString("website");
                    String dbPhoneNumner = documentSnapshot.getString("phoneNumber");
                    String dbAlterPhoneNumner = documentSnapshot.getString("alternativePhoneNumber");
                    String dbcuisines = documentSnapshot.getString("cuisines");
                    Boolean dbDineIn = documentSnapshot.getBoolean("dineIn");
                    Boolean dbTakeAway = documentSnapshot.getBoolean("takeAway");
                    Boolean dbHomeDelivery = documentSnapshot.getBoolean("homeDelivery");
                    Boolean dbOnTheGo = documentSnapshot.getBoolean("onTheGo");
                    Boolean dbOnlyVeg = documentSnapshot.getBoolean("onlyVeg");

                    mRestaurantName.getEditText().setText(dbName);
                    mTagLine.getEditText().setText(dbTagLine);
                    mEmail.getEditText().setText(dbEmail);
                    mWebsite.getEditText().setText(dbWebsite);
                    mPhoneNumber.getEditText().setText(dbPhoneNumner);
                    mAlterPhoneNumber.getEditText().setText(dbAlterPhoneNumner);
                    mCuisines.getEditText().setText(dbcuisines);

                    if (dbDineIn)
                        mDineIn.setChecked(true);
                    else
                        mDineIn.setChecked(false);

                    if (dbTakeAway)
                        mTakeAway.setChecked(true);
                    else
                        mTakeAway.setChecked(false);

                    if (dbHomeDelivery)
                        mHomeDelivery.setChecked(true);
                    else
                        mHomeDelivery.setChecked(false);

                    if (dbOnTheGo)
                        mOnTheGo.setChecked(true);
                    else
                        mOnTheGo.setChecked(false);

                    if (dbOnlyVeg)
                        mVeg.setChecked(true);
                    else
                        mVeg.setChecked(false);

                }
            }
        });

        mChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityNameCheck.equals("MainActivity")){

                    Fragment_PhoneVerification fragment = new Fragment_PhoneVerification();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.main_activity_layout,fragment,"Fragment PhoneVerification");
                    transaction.addToBackStack(null);
                    transaction.setReorderingAllowed(true);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    transaction.commit();

                }else {

                    Fragment_PhoneVerification fragment = new Fragment_PhoneVerification();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.details_activity_layout, fragment, "Fragment PhoneVerification");
                    transaction.addToBackStack(null);
                    transaction.setReorderingAllowed(true);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    transaction.commit();

                }
            }
        });

        mChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityNameCheck.equals("MainActivity")){

//                    Fragment_PhoneVerification fragment = new Fragment_PhoneVerification();
//                    FragmentManager manager = getFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.main_activity_layout,fragment,"Fragment PhoneVerification");
//                    transaction.addToBackStack(null);
//                    transaction.setReorderingAllowed(true);
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//                    transaction.commit();

                    Toast.makeText(getActivity(),"Called from MainActivity",Toast.LENGTH_LONG).show();

                }else {

//                    Fragment_PhoneVerification fragment = new Fragment_PhoneVerification();
//                    FragmentManager manager = getFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    transaction.replace(R.id.details_activity_layout, fragment, "Fragment PhoneVerification");
//                    transaction.addToBackStack(null);
//                    transaction.setReorderingAllowed(true);
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//                    transaction.commit();

                    Toast.makeText(getActivity(),"Called from DetailsActivity",Toast.LENGTH_LONG).show();

                }
            }
        });

        mDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String name = mRestaurantName.getEditText().getText().toString();
                String tagLine = mTagLine.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String website = mWebsite.getEditText().getText().toString();
                String phoneNumber = mPhoneNumber.getEditText().getText().toString();
                String alterPhoneNumber = mAlterPhoneNumber.getEditText().getText().toString();
                String cuisines = mCuisines.getEditText().getText().toString();

                Boolean dineIn;
                Boolean homeDelivery;
                Boolean takeAway;
                Boolean onTheGo;
                Boolean onlyVeg;

                if (mDineIn.isChecked())
                    dineIn = true;
                else
                    dineIn = false;

                if (mTakeAway.isChecked())
                    takeAway = true;
                else
                    takeAway = false;

                if (mHomeDelivery.isChecked())
                    homeDelivery = true;
                else
                    homeDelivery = false;

                if (mOnTheGo.isChecked())
                    onTheGo = true;
                else
                    onTheGo = false;

                if (mVeg.isChecked())
                    onlyVeg = true;
                else
                    onlyVeg = false;

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(cuisines) && (dineIn || takeAway || homeDelivery || onTheGo))
                {

                    mMainProgress.setTitle("Saving Data");
                    mMainProgress.setMessage("Please wait while we save the Data");
                    mMainProgress.setCanceledOnTouchOutside(true);
                    mMainProgress.show();


                    Map<String, Object> restaurant = new HashMap<>();
                    restaurant.put("aaName", name);
                    restaurant.put("tagLine", tagLine);
                    restaurant.put("email", email);
                    restaurant.put("website", website);
                    restaurant.put("phoneNumber", phoneNumber);
                    restaurant.put("alternativePhoneNumber", alterPhoneNumber);
                    restaurant.put("cuisines", cuisines);
                    restaurant.put("dineIn", dineIn);
                    restaurant.put("takeAway", takeAway);
                    restaurant.put("homeDelivery", homeDelivery);
                    restaurant.put("onTheGo",onTheGo);
                    restaurant.put("onlyVeg",onlyVeg);

                    mDocRef.update(restaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                mMainProgress.dismiss();

                                if(activityNameCheck.equals("MainActivity")){

                                    Fragment_Main fragment = new Fragment_Main();
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.main_activity_layout, fragment, "Main Fragment");
                                    transaction.addToBackStack(null);
                                    transaction.setReorderingAllowed(true);
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                    transaction.commit();

                                }else {

                                    Fragment_Address fragment = new Fragment_Address();
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.details_activity_layout, fragment, "AddressFragment");
                                    transaction.addToBackStack(null);
                                    transaction.setReorderingAllowed(true);
                                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                    transaction.commit();

                                }

                            }else {

                                mMainProgress.dismiss();
                                Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }else {

                    Toast.makeText(getActivity(),"Please fill all the mandatory fileds and try again",Toast.LENGTH_LONG).show();

                }


            }
        });

    }
}
