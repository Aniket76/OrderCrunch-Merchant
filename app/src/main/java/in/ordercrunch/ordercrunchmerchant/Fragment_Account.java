package in.ordercrunch.ordercrunchmerchant;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Account extends Fragment {

    private ImageView mDetailsBtn, mAddressBtn, mInfoBtn, mChangeImageBtn, mVegImg;
    private Button mLogout;

    private TextView mRestaurantName,mTagLine;
    private TextView mVegTxt,mEmain,mWebsite,mPhoneNo,mAlternativePhoneNo,mCuisines,mServises1,mServises2,mServises3,mServises4;
    private TextView mAddress;
    private TextView mCostForTwo,mMinOrder,mDelCost,mSGST,mCGST,mServiseCgarges,mXAmount;
    private TextView mMonOpen,mMonClose,mTueOpen,mTueClose,mWedOpen,mWedClose,mThuOpen,mThuClose,mFriOpen,mFriClose,mSatOpen,mSatClose,mSunOpen,mSunClose;
    private TextView mMonTo,mTueTo,mWedTo,mThuTo,mFriTo,mSatTo,mSunTo;

    private CircleImageView mDisplayPic;

    private ProgressDialog mMainProgress;

    private FirebaseAuth mAuth;
    private StorageReference mImageStorage;

    private String uid;
    private String dbImage;

    private static final int GALLEY_PICK = 1;

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

        mDisplayPic = (CircleImageView)getActivity().findViewById(R.id.acc_dp);

        mRestaurantName = (TextView)getActivity().findViewById(R.id.acc_name);
        mTagLine = (TextView)getActivity().findViewById(R.id.acc_tagLine);

        mVegTxt = (TextView)getActivity().findViewById(R.id.acc_veg_txt);
        mVegImg = (ImageView) getActivity().findViewById(R.id.acc_veg_img);

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
        mChangeImageBtn = (ImageView)getActivity().findViewById(R.id.acc_changeImage_img);
        mLogout = (Button) getActivity().findViewById(R.id.acc_logout_btn);

        mAuth = FirebaseAuth.getInstance();
        mImageStorage = FirebaseStorage.getInstance().getReference();

        FirebaseUser current_user = mAuth.getCurrentUser();
        uid = current_user.getUid();

        DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){

                    //----------Details Section---------

                    dbImage = documentSnapshot.getString("image");
                    if(!(dbImage.equals(""))){

                                Picasso.with(getContext()).load(dbImage).networkPolicy(NetworkPolicy.OFFLINE)
                                        .placeholder(R.drawable.walkthrough_background).into(mDisplayPic, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Picasso.with(getContext()).load(dbImage).placeholder(R.drawable.walkthrough_background).into(mDisplayPic);
                                    }
                                });
                    }

                    String dbName = documentSnapshot.getString("aaName");
                    mRestaurantName.setText(dbName);

                    String dbTagLine = documentSnapshot.getString("tagLine");
                    if (dbTagLine.equals("")){
                        mTagLine.setText("**No TagLine**");
                    }else {
                        mTagLine.setText(dbTagLine);
                    }

                    Boolean dbOnlyVeg = documentSnapshot.getBoolean("onlyVeg");
                    if (dbOnlyVeg){
                        mVegTxt.setText("Only Veg");
                        Drawable veg  = getResources().getDrawable(R.drawable.ic_veg);
                        mVegImg.setImageDrawable(veg);
                    }else {
                        mVegTxt.setText("Veg and Non-Veg Both");
                        Drawable nonveg  = getResources().getDrawable(R.drawable.ic_nonveg);
                        mVegImg.setImageDrawable(nonveg);                    }

                    String dbEmail = documentSnapshot.getString("email");
                    mEmain.setText(dbEmail);

                    String dbWebsite = documentSnapshot.getString("website");
                    if (dbWebsite.equals("")){
                        mWebsite.setText("**No Website**");
                    }else {
                        mWebsite.setText(dbWebsite);
                    }

                    String dbPhoneNumber = documentSnapshot.getString("phoneNumber");
                    mPhoneNo.setText(dbPhoneNumber);

                    String dbAlterPhoneNumner = documentSnapshot.getString("alternativePhoneNumber");
                    if (dbAlterPhoneNumner.equals("")){
                        mAlternativePhoneNo.setText("**No Alternative PhoneNo.**");
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
                    String dbAddress = documentSnapshot.getString("address");
                    if (dbAddress.equals("")){
                        mAddress.setText("Please Add Your Address");
                    }else {
                        mAddress.setText(dbAddress);
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

                // 1. Instantiate an AlertDialog.Builder with its constructor
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Are you sure you want to logout?")
                        .setTitle("Logout");

                // 3. Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked YES button

                        mAuth.signOut();
                        Intent intent = new Intent(getActivity(),StartActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();

                    }
                });

                // 4. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        mChangeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent galleryIntent = new Intent();
//                galleryIntent.setType("image/*");
//                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"), GALLEY_PICK);

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(getContext(),Fragment_Account.this);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mMainProgress = new ProgressDialog(getContext());
                mMainProgress.setTitle("Uploading Image");
                mMainProgress.setMessage("Please wait while we upload the image");
                mMainProgress.setCanceledOnTouchOutside(false);
                mMainProgress.show();

                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());


                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(getContext())
                                .setMaxHeight(250)
                                .setMaxWidth(250)
                                .setQuality(80)
                                .compressToBitmap(thumb_filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();

                final StorageReference filepath = mImageStorage.child("restaurant_images").child(uid+".jpg");

                UploadTask uploadTask = filepath.putBytes(thumb_byte);

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){

                            String downloadUrl = task.getResult().getDownloadUrl().toString();

                            Map<String, Object> restaurant = new HashMap<>();
                            restaurant.put("image", downloadUrl);

                            DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

                            mDocRef.update(restaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        mMainProgress.dismiss();

                                        Fragment_Account fragment = new Fragment_Account();
                                        FragmentManager manager = getFragmentManager();
                                        FragmentTransaction transaction = manager.beginTransaction();
                                        transaction.detach(fragment);
                                        transaction.attach(fragment);
                                        transaction.commit();

                                        Toast.makeText(getActivity(),"Successfully uplodad the image",Toast.LENGTH_LONG).show();

                                    }

                                    else {

                                        mMainProgress.dismiss();
                                        Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                                    }

                                }
                            });

                        }else {

                            Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                        }

                    }
                });

                //Toast.makeText(getActivity(),resultUri.toString(),Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
