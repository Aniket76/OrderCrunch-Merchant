package in.ordercrunch.ordercrunchmerchant;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Account extends Fragment {

    private ImageView mDetailsBtn, mAddressBtn, mInfoBtn;
    private Button mLogout;

    private TextView mRestaurantName,mTagLine;
    private TextView mEmain,mWebsite,mPhoneNo,mAlternativePhoneNo,mCuisines,mServises1,mServises2,mServises3,mServises4;
    private TextView mAddress;
    private TextView mCostForTwo,mMinOrder,mDelCost,mSGST,mCGST,mServiseCgarges,mXAmount;
    private TextView mMonOpen,mMonClose,mTueOpen,mTueClose,mWedOpen,mWedClose,mThuOpen,mThuClose,mFriOpen,mFriClose,mSatOpen,mSatClose,mSunOpen,mSunClose;
    private TextView mMonTo,mTueTo,mWedTo,mThuTo,mFriTo,mSatTo,mSunTo;

    private FirebaseAuth mAuth;

    public Fragment_Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRestaurantName = (TextView)getActivity().findViewById(R.id.acc_name);
        mTagLine = (TextView)getActivity().findViewById(R.id.acc_tagLine);

        mEmain = (TextView)getActivity().findViewById(R.id.acc_email_txt);
        mWebsite = (TextView)getActivity().findViewById(R.id.acc_website_txt);
        mPhoneNo = (TextView)getActivity().findViewById(R.id.acc_phoneNo_txt);
        mAlternativePhoneNo = (TextView)getActivity().findViewById(R.id.acc_alternativePhoneNo_txt);
        mCuisines = (TextView)getActivity().findViewById(R.id.acc_cuisines_txt);
        mServises1 = (TextView)getActivity().findViewById(R.id.acc_servises1_txt);
        mServises2 = (TextView)getActivity().findViewById(R.id.acc_servises2_txt);
        mServises3 = (TextView)getActivity().findViewById(R.id.acc_servises3_txt);
        mServises4 = (TextView)getActivity().findViewById(R.id.acc_servises4_txt);

        mAddress = (TextView)getActivity().findViewById(R.id.acc_add_txt);

        mCostForTwo = (TextView)getActivity().findViewById(R.id.acc_costForTwo_txt);
        mMinOrder = (TextView)getActivity().findViewById(R.id.acc_minimunOrder_txt);
        mDelCost = (TextView)getActivity().findViewById(R.id.acc_deliveryCost_txt);
        mXAmount = (TextView)getActivity().findViewById(R.id.acc_xAmount_txt);
        mSGST = (TextView)getActivity().findViewById(R.id.acc_sgst_txt);
        mCGST = (TextView)getActivity().findViewById(R.id.acc_cgst_txt);
        mServiseCgarges = (TextView)getActivity().findViewById(R.id.acc_serviseCharges_txt);

        mMonOpen = (TextView)getActivity().findViewById(R.id.acc_mon_open);
        mMonClose = (TextView)getActivity().findViewById(R.id.acc_mon_close);
        mMonTo = (TextView)getActivity().findViewById(R.id.acc_mon_to);

        mTueOpen = (TextView)getActivity().findViewById(R.id.acc_tue_open);
        mTueClose = (TextView)getActivity().findViewById(R.id.acc_tue_close);
        mTueTo = (TextView)getActivity().findViewById(R.id.acc_tue_to);

        mWedOpen = (TextView)getActivity().findViewById(R.id.acc_wed_open);
        mWedClose = (TextView)getActivity().findViewById(R.id.acc_wed_close);
        mWedTo = (TextView)getActivity().findViewById(R.id.acc_wed_to);

        mThuOpen = (TextView)getActivity().findViewById(R.id.acc_thu_open);
        mThuClose = (TextView)getActivity().findViewById(R.id.acc_thu_close);
        mThuTo = (TextView)getActivity().findViewById(R.id.acc_thu_to);

        mFriOpen = (TextView)getActivity().findViewById(R.id.acc_fri_open);
        mFriClose = (TextView)getActivity().findViewById(R.id.acc_fri_close);
        mFriTo = (TextView)getActivity().findViewById(R.id.acc_fri_to);

        mSatOpen = (TextView)getActivity().findViewById(R.id.acc_sat_open);
        mSatClose = (TextView)getActivity().findViewById(R.id.acc_sat_close);
        mSatTo = (TextView)getActivity().findViewById(R.id.acc_sat_to);

        mSunOpen = (TextView)getActivity().findViewById(R.id.acc_sun_open);
        mSunClose = (TextView)getActivity().findViewById(R.id.acc_sun_close);
        mSunTo = (TextView)getActivity().findViewById(R.id.acc_sun_to);

        mDetailsBtn = (ImageView) getActivity().findViewById(R.id.acc_details_img);
        mAddressBtn = (ImageView)getActivity().findViewById(R.id.acc_address_img);
        mInfoBtn = (ImageView)getActivity().findViewById(R.id.acc_info_img);
        mLogout = (Button) getActivity().findViewById(R.id.acc_logout_btn);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = mAuth.getCurrentUser();
        String uid = current_user.getUid();

        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){

                    //----------Details Section---------

                    String dbName = documentSnapshot.getString("aaName");
                    mRestaurantName.setText(dbName);

                    String dbTagLine = documentSnapshot.getString("tagLine");
                    mTagLine.setText(dbTagLine);

                    String dbEmail = documentSnapshot.getString("email");
                    mEmain.setText(dbEmail);

                    String dbWebsite = documentSnapshot.getString("website");
                    mWebsite.setText(dbWebsite);

                    String dbPhoneNumber = documentSnapshot.getString("phoneNumber");
                    mPhoneNo.setText(dbPhoneNumber);

                    String dbAlterPhoneNumner = documentSnapshot.getString("alternativePhoneNumber");
                    if (dbAlterPhoneNumner.equals("")){
                        mAlternativePhoneNo.setText("No Alternative Phone Number");
                    }else {
                        mAlternativePhoneNo.setText(dbAlterPhoneNumner);
                    }

                    String dbcuisines = documentSnapshot.getString("cuisines");
                    mCuisines.setText(dbcuisines);

                    //-------Boolean Values--------
                    Boolean dbDineIn = documentSnapshot.getBoolean("dineIn");
                    if (dbDineIn){
                        mServises1.setVisibility(View.VISIBLE);
                        mServises1.setText("# Dine In");
                    }else {
                        mServises1.setVisibility(View.GONE);
                    }

                    Boolean dbTakeAway = documentSnapshot.getBoolean("takeAway");
                    if (dbTakeAway){
                        mServises2.setVisibility(View.VISIBLE);
                        mServises2.setText("# Take Away");
                    }else {
                        mServises2.setVisibility(View.GONE);
                    }

                    Boolean dbHomeDelivery = documentSnapshot.getBoolean("homeDelivery");
                    if (dbHomeDelivery){
                        mServises3.setVisibility(View.VISIBLE);
                        mServises3.setText("# Home Delivery");
                    }else {
                        mServises3.setVisibility(View.GONE);
                    }

                    Boolean dbOnTheGo = documentSnapshot.getBoolean("onTheGo");
                    if (dbOnTheGo){
                        mServises4.setVisibility(View.VISIBLE);
                        mServises4.setText("# On The Go");
                    }else {
                        mServises4.setVisibility(View.GONE);
                    }

                    //--------------Address Section-------------
                    String dbShopNo = documentSnapshot.getString("shopNo");
                    String dbLocation = documentSnapshot.getString("location");
                    String dbLandMark = documentSnapshot.getString("landMark");
                    String dbPinCode = documentSnapshot.getString("pinCode");
                    String dbCity = documentSnapshot.getString("city");
                    String dbState = documentSnapshot.getString("state");
                    if (dbShopNo.equals("")){
                        mAddress.setText("Please Add Your Address");
                    }else {

                        if (dbLandMark.equals("")){
                            mAddress.setText(dbShopNo+", "+dbLocation+", "+dbCity+", "+dbState+" - "+dbPinCode);
                        }else {
                            mAddress.setText(dbShopNo+", "+dbLocation+" ("+dbLandMark+"), "+dbCity+", "+dbState+" - "+dbPinCode);

                        }

                    }

                    //----------General Information Section--------------
                    String dbCostForTwo = documentSnapshot.getString("costForTwo");
                    mCostForTwo.setText(dbCostForTwo);

                    String dbMinOrder = documentSnapshot.getString("minOrder");
                    if (dbMinOrder.equals("")){
                        mMinOrder.setText("0");
                    }else {
                        mMinOrder.setText(dbMinOrder);
                    }

                    String dbDeliveryCost = documentSnapshot.getString("deliveryCost");
                    if (dbDeliveryCost.equals("")){
                        mDelCost.setText("0");
                    }else {
                        mDelCost.setText(dbMinOrder);
                    }

                    String dbXAmount = documentSnapshot.getString("xAmount");
                    if (dbXAmount.equals("")){
                        mXAmount.setText("Delivery cost will be applicable on EVERY order.");
                    }else {
                        mXAmount.setText("Delivery will be FREE above "+dbXAmount+" amount.");
                    }

                    String dbSgst = documentSnapshot.getString("sgst");
                    if (dbSgst.equals("")){
                        mSGST.setText("0.00%");
                    }else {
                        mSGST.setText(dbSgst+"%");
                    }

                    String dbCgst = documentSnapshot.getString("cgst");
                    if (dbCgst.equals("")){
                        mCGST.setText("0.00%");
                    }else {
                        mCGST.setText(dbCgst+"%");
                    }

                    String dbServiceCharges = documentSnapshot.getString("serviceCharges");
                    if (dbServiceCharges.equals("")){
                        mServiseCgarges.setText("0.00%");
                    }else {
                        mServiseCgarges.setText(dbServiceCharges+"%");
                    }

                    //-----------Timing-----------

                    String dbMonOpen = documentSnapshot.getString("monOpen");
                    String dbMonClose = documentSnapshot.getString("monClose");
                    String dbTueOpen = documentSnapshot.getString("tueOpen");
                    String dbTueClose = documentSnapshot.getString("tueClose");
                    String dbWedOpen = documentSnapshot.getString("wedOpen");
                    String dbWedClose = documentSnapshot.getString("wedClose");
                    String dbThuOpen = documentSnapshot.getString("thuOpen");
                    String dbThuClose = documentSnapshot.getString("thuClose");
                    String dbFriOpen = documentSnapshot.getString("friOpen");
                    String dbFriClose = documentSnapshot.getString("friClose");
                    String dbSatOpen = documentSnapshot.getString("satOpen");
                    String dbSatClose = documentSnapshot.getString("satClose");
                    String dbSunOpen = documentSnapshot.getString("sunOpen");
                    String dbSunClose = documentSnapshot.getString("sunClose");

                    Boolean dbDeliveryCB = documentSnapshot.getBoolean("deliveryCB");
                    Boolean dbWeekCB = documentSnapshot.getBoolean("weekCB");

                    Boolean dbmonCB = documentSnapshot.getBoolean("monCB");
                    if (!dbmonCB){
                        mMonOpen.setVisibility(View.INVISIBLE);
                        mMonClose.setVisibility(View.INVISIBLE);
                        mMonTo.setText("Closed");
                    }else {
                        mMonOpen.setVisibility(View.VISIBLE);
                        mMonClose.setVisibility(View.VISIBLE);
                        mMonTo.setText("to");
                        mMonOpen.setText(dbMonOpen);
                        mMonClose.setText(dbMonClose);
                    }

                    Boolean dbtueCB = documentSnapshot.getBoolean("tueCB");
                    if (!dbtueCB){
                        mTueOpen.setVisibility(View.INVISIBLE);
                        mTueClose.setVisibility(View.INVISIBLE);
                        mTueTo.setText("Closed");
                    }else {
                        mTueOpen.setVisibility(View.VISIBLE);
                        mTueClose.setVisibility(View.VISIBLE);
                        mTueTo.setText("to");
                        mTueOpen.setText(dbTueOpen);
                        mTueClose.setText(dbTueClose);
                    }

                    Boolean dbwedCB = documentSnapshot.getBoolean("wedCB");
                    if (!dbwedCB){
                        mWedOpen.setVisibility(View.INVISIBLE);
                        mWedClose.setVisibility(View.INVISIBLE);
                        mWedTo.setText("Closed");
                    }else {
                        mWedOpen.setVisibility(View.VISIBLE);
                        mWedClose.setVisibility(View.VISIBLE);
                        mWedTo.setText("to");
                        mWedOpen.setText(dbWedOpen);
                        mWedClose.setText(dbWedClose);
                    }

                    Boolean dbthuCB = documentSnapshot.getBoolean("thuCB");
                    if (!dbthuCB){
                        mThuOpen.setVisibility(View.INVISIBLE);
                        mThuClose.setVisibility(View.INVISIBLE);
                        mThuTo.setText("Closed");
                    }else {
                        mThuOpen.setVisibility(View.VISIBLE);
                        mThuClose.setVisibility(View.VISIBLE);
                        mThuTo.setText("to");
                        mThuOpen.setText(dbThuOpen);
                        mThuClose.setText(dbThuClose);
                    }

                    Boolean dbfriCB = documentSnapshot.getBoolean("friCB");
                    if (!dbfriCB){
                        mFriOpen.setVisibility(View.INVISIBLE);
                        mFriClose.setVisibility(View.INVISIBLE);
                        mFriTo.setText("Closed");
                    }else {
                        mFriOpen.setVisibility(View.VISIBLE);
                        mFriClose.setVisibility(View.VISIBLE);
                        mFriTo.setText("to");
                        mFriOpen.setText(dbFriOpen);
                        mFriClose.setText(dbFriClose);
                    }

                    Boolean dbsatCB = documentSnapshot.getBoolean("satCB");
                    if (!dbsatCB){
                        mSatOpen.setVisibility(View.INVISIBLE);
                        mSatClose.setVisibility(View.INVISIBLE);
                        mSatTo.setText("Closed");
                    }else {
                        mSatOpen.setVisibility(View.VISIBLE);
                        mSatClose.setVisibility(View.VISIBLE);
                        mSatTo.setText("to");
                        mSatOpen.setText(dbSatOpen);
                        mSatClose.setText(dbSatClose);
                    }

                    Boolean dbsunCB = documentSnapshot.getBoolean("sunCB");
                    if (!dbsunCB){
                        mSunOpen.setVisibility(View.INVISIBLE);
                        mSunClose.setVisibility(View.INVISIBLE);
                        mSunTo.setText("Closed");
                    }else {
                        mSunOpen.setVisibility(View.VISIBLE);
                        mSunClose.setVisibility(View.VISIBLE);
                        mSunTo.setText("to");
                        mSunOpen.setText(dbSunOpen);
                        mSunClose.setText(dbSunClose);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {



            }
        });

        mDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_RestaurantDetails fragment = new Fragment_RestaurantDetails();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"DetailsFragment");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        mAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_Address fragment = new Fragment_Address();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"AddressFragment");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment_GeneralInfo fragment = new Fragment_GeneralInfo();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.main_activity_layout,fragment,"InfoFragment");
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.commit();

            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent intent = new Intent(getActivity(),StartActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

    }
}
