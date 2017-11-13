package in.ordercrunch.ordercrunchmerchant;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_GeneralInfo extends Fragment {

    private Button mGeneralInfoBtn;
    private CheckBox mWeekCheckBox, mDeliveryCheckBox;

    private TextInputLayout mCostForTwo, mMinOrder, mDeliveryCost, mXAmount, mSgst, mCgst, mServiceCharges;

    private LinearLayout mMonLayout, mTueLayout, mWedLayout, mThuLayout, mFriLayout, mSatLayout, mSunLayout;
    private RelativeLayout mDeliveryLatout;

    private TextView mMonOpen, mMonClose, mTueOpen, mTueClose, mWedOpen, mWedClose, mThuOpen, mThuClose, mFriOpen, mFriClose, mSatOpen, mSatClose, mSunOpen, mSunClose;
    private TextView mTimingMsg;
    private CheckBox mMonCB, mTueCB, mWedCB, mThuCB, mFriCB, mSatCB, mSunCB;

    private ProgressDialog mMainProgress;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;

    private String activityNameCheck;

    private int hour, minute;

    public Fragment_GeneralInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activityNameCheck = getActivity().getClass().getSimpleName().toString();

        mMainProgress = new ProgressDialog(getContext());

        mGeneralInfoBtn = (Button) getActivity().findViewById(R.id.btn_addInfo);

        mWeekCheckBox = (CheckBox) getActivity().findViewById(R.id.week_checkBox);
        mDeliveryCheckBox = (CheckBox) getActivity().findViewById(R.id.delivery_checkBox);

        mCostForTwo = (TextInputLayout) getActivity().findViewById(R.id.info_costfortwo);
        mMinOrder = (TextInputLayout) getActivity().findViewById(R.id.info_minOrder);
        mDeliveryCost = (TextInputLayout) getActivity().findViewById(R.id.info_deliveryCost);
        mXAmount = (TextInputLayout) getActivity().findViewById(R.id.info_minDelivery);
        mSgst = (TextInputLayout) getActivity().findViewById(R.id.info_sgst);
        mCgst = (TextInputLayout) getActivity().findViewById(R.id.info_cgst);
        mServiceCharges = (TextInputLayout) getActivity().findViewById(R.id.info_serviseCharges);

        mMonLayout = (LinearLayout) getActivity().findViewById(R.id.mon_layout);
        mTueLayout = (LinearLayout) getActivity().findViewById(R.id.tue_layout);
        mWedLayout = (LinearLayout) getActivity().findViewById(R.id.wed_layout);
        mThuLayout = (LinearLayout) getActivity().findViewById(R.id.thu_layout);
        mFriLayout = (LinearLayout) getActivity().findViewById(R.id.fri_layout);
        mSatLayout = (LinearLayout) getActivity().findViewById(R.id.sat_layout);
        mSunLayout = (LinearLayout) getActivity().findViewById(R.id.sun_layout);

        mDeliveryLatout = (RelativeLayout) getActivity().findViewById(R.id.info_layout);

        mTimingMsg = (TextView) getActivity().findViewById(R.id.timing_msg_txt);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

        if (activityNameCheck.equals("MainActivity")) {

            mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()) {

                        String dbCostForTwo = documentSnapshot.getString("costForTwo");
                        String dbMinOrder = documentSnapshot.getString("minOrder");
                        String dbDeliveryCost = documentSnapshot.getString("deliveryCost");
                        String dbXAmount = documentSnapshot.getString("xAmount");
                        String dbSgst = documentSnapshot.getString("sgst");
                        String dbCgst = documentSnapshot.getString("cgst");
                        String dbServiceCharges = documentSnapshot.getString("serviceCharges");

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
                        Boolean dbtueCB = documentSnapshot.getBoolean("tueCB");
                        Boolean dbwedCB = documentSnapshot.getBoolean("wedCB");
                        Boolean dbthuCB = documentSnapshot.getBoolean("thuCB");
                        Boolean dbfriCB = documentSnapshot.getBoolean("friCB");
                        Boolean dbsatCB = documentSnapshot.getBoolean("satCB");
                        Boolean dbsunCB = documentSnapshot.getBoolean("sunCB");

                        mCostForTwo.getEditText().setText(dbCostForTwo);
                        mMinOrder.getEditText().setText(dbMinOrder);
                        mDeliveryCost.getEditText().setText(dbDeliveryCost);
                        mXAmount.getEditText().setText(dbXAmount);
                        mSgst.getEditText().setText(dbSgst);
                        mCgst.getEditText().setText(dbCgst);
                        mServiceCharges.getEditText().setText(dbServiceCharges);

                        mMonOpen.setText(dbMonOpen);
                        mMonClose.setText(dbMonClose);
                        mTueOpen.setText(dbTueOpen);
                        mTueClose.setText(dbTueClose);
                        mWedOpen.setText(dbWedOpen);
                        mWedClose.setText(dbWedClose);
                        mThuOpen.setText(dbThuOpen);
                        mThuClose.setText(dbThuClose);
                        mFriOpen.setText(dbFriOpen);
                        mFriClose.setText(dbFriClose);
                        mSatOpen.setText(dbSatOpen);
                        mSatClose.setText(dbSatClose);
                        mSunOpen.setText(dbSunOpen);
                        mSunClose.setText(dbSunClose);

                        if (dbDeliveryCB)
                            mDeliveryCheckBox.setChecked(true);
                        else
                            mDeliveryCheckBox.setChecked(false);

                        if (dbWeekCB)
                            mWeekCheckBox.setChecked(true);
                        else
                            mWeekCheckBox.setChecked(false);

                        if (dbmonCB)
                            mMonCB.setChecked(true);
                        else
                            mMonCB.setChecked(false);

                        if (dbtueCB)
                            mTueCB.setChecked(true);
                        else
                            mTueCB.setChecked(false);

                        if (dbwedCB)
                            mWedCB.setChecked(true);
                        else
                            mWedCB.setChecked(false);

                        if (dbthuCB)
                            mThuCB.setChecked(true);
                        else
                            mThuCB.setChecked(false);

                        if (dbfriCB)
                            mFriCB.setChecked(true);
                        else
                            mFriCB.setChecked(false);

                        if (dbsatCB)
                            mSatCB.setChecked(true);
                        else
                            mSatCB.setChecked(false);

                        if (dbsunCB)
                            mSunCB.setChecked(true);
                        else
                            mSunCB.setChecked(false);

                        mMainProgress.dismiss();

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

        } else

        {

            Toast.makeText(getActivity(), "No Data found please fill the form", Toast.LENGTH_LONG).show();

        }

        //-------Monday Timing----------
        mMonOpen = (TextView) getActivity().findViewById(R.id.mon_open);
        mMonClose = (TextView) getActivity().findViewById(R.id.mon_close);
        mMonCB = (CheckBox) getActivity().findViewById(R.id.mon_cb);

        mMonCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMonCB.isChecked()) {
                    mMonOpen.setText("Opening");
                    mMonClose.setText("Closing");
                    mMonOpen.setEnabled(true);
                    mMonClose.setEnabled(true);
                    mMonOpen.setTextColor(Color.parseColor("#212121"));
                    mMonClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mMonOpen.setText("0:0");
                    mMonClose.setText("0:0");
                    mMonOpen.setEnabled(false);
                    mMonClose.setEnabled(false);
                    mMonOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mMonClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mMonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_monOpen(1).show();
            }
        });
        mMonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_monClose(1).show();
            }
        });

        //-------Tuesday Timing----------
        mTueOpen = (TextView) getActivity().findViewById(R.id.tue_open);
        mTueClose = (TextView) getActivity().findViewById(R.id.tue_close);
        mTueCB = (CheckBox) getActivity().findViewById(R.id.tue_cb);

        mTueCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTueCB.isChecked()) {
                    mTueOpen.setText("Opening");
                    mTueClose.setText("Closing");
                    mTueOpen.setEnabled(true);
                    mTueClose.setEnabled(true);
                    mTueOpen.setTextColor(Color.parseColor("#212121"));
                    mTueClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mTueOpen.setText("0:0");
                    mTueClose.setText("0:0");
                    mTueOpen.setEnabled(false);
                    mTueClose.setEnabled(false);
                    mTueOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mTueClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mTueOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_tueOpen(1).show();
            }
        });
        mTueClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_tueClose(1).show();
            }
        });

        //-------Wednesday Timing----------
        mWedOpen = (TextView) getActivity().findViewById(R.id.wed_open);
        mWedClose = (TextView) getActivity().findViewById(R.id.wed_close);
        mWedCB = (CheckBox) getActivity().findViewById(R.id.wed_cb);

        mWedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWedCB.isChecked()) {
                    mWedOpen.setText("Opening");
                    mWedClose.setText("Closing");
                    mWedOpen.setEnabled(true);
                    mWedClose.setEnabled(true);
                    mWedOpen.setTextColor(Color.parseColor("#212121"));
                    mWedClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mWedOpen.setText("0:0");
                    mWedClose.setText("0:0");
                    mWedOpen.setEnabled(false);
                    mWedClose.setEnabled(false);
                    mWedOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mWedClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mWedOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_wedOpen(1).show();
            }
        });
        mWedClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_wedClose(1).show();
            }
        });

        //-------Thuresday Timing----------
        mThuOpen = (TextView) getActivity().findViewById(R.id.thu_open);
        mThuClose = (TextView) getActivity().findViewById(R.id.thu_close);
        mThuCB = (CheckBox) getActivity().findViewById(R.id.thu_cb);

        mThuCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mThuCB.isChecked()) {
                    mThuOpen.setText("Opening");
                    mThuClose.setText("Closing");
                    mThuOpen.setEnabled(true);
                    mThuClose.setEnabled(true);
                    mThuOpen.setTextColor(Color.parseColor("#212121"));
                    mThuClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mThuOpen.setText("0:0");
                    mThuClose.setText("0:0");
                    mThuOpen.setEnabled(false);
                    mThuClose.setEnabled(false);
                    mThuOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mThuClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mThuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_thuOpen(1).show();
            }
        });
        mThuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_thuClose(1).show();
            }
        });

        //-------Friday Timing----------
        mFriOpen = (TextView) getActivity().findViewById(R.id.fri_open);
        mFriClose = (TextView) getActivity().findViewById(R.id.fri_close);
        mFriCB = (CheckBox) getActivity().findViewById(R.id.fri_cb);

        mFriCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFriCB.isChecked()) {
                    mFriOpen.setText("Opening");
                    mFriClose.setText("Closing");
                    mFriOpen.setEnabled(true);
                    mFriClose.setEnabled(true);
                    mFriOpen.setTextColor(Color.parseColor("#212121"));
                    mFriClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mFriOpen.setText("0:0");
                    mFriClose.setText("0:0");
                    mFriOpen.setEnabled(false);
                    mFriClose.setEnabled(false);
                    mFriOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mFriClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mFriOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_friOpen(1).show();
            }
        });
        mFriClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_friClose(1).show();
            }
        });

        //-------Saturday Timing----------
        mSatOpen = (TextView) getActivity().findViewById(R.id.sat_open);
        mSatClose = (TextView) getActivity().findViewById(R.id.sat_close);
        mSatCB = (CheckBox) getActivity().findViewById(R.id.sat_cb);

        mSatCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSatCB.isChecked()) {
                    mSatOpen.setText("Opening");
                    mSatClose.setText("Closing");
                    mSatOpen.setEnabled(true);
                    mSatClose.setEnabled(true);
                    mSatOpen.setTextColor(Color.parseColor("#212121"));
                    mSatClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mSatOpen.setText("0:0");
                    mSatClose.setText("0:0");
                    mSatOpen.setEnabled(false);
                    mSatClose.setEnabled(false);
                    mSatOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mSatClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mSatOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_satOpen(1).show();
            }
        });
        mSatClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_satClose(1).show();
            }
        });

        //-------Sunday Timing----------
        mSunOpen = (TextView) getActivity().findViewById(R.id.sun_open);
        mSunClose = (TextView) getActivity().findViewById(R.id.sun_close);
        mSunCB = (CheckBox) getActivity().findViewById(R.id.sun_cb);

        mSunCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSunCB.isChecked()) {
                    mSunOpen.setText("Opening");
                    mSunClose.setText("Closing");
                    mSunOpen.setEnabled(true);
                    mSunClose.setEnabled(true);
                    mSunOpen.setTextColor(Color.parseColor("#212121"));
                    mSunClose.setTextColor(Color.parseColor("#212121"));
                } else {
                    mSunOpen.setText("0:0");
                    mSunClose.setText("0:0");
                    mSunOpen.setEnabled(false);
                    mSunClose.setEnabled(false);
                    mSunOpen.setTextColor(Color.parseColor("#e0e0e0"));
                    mSunClose.setTextColor(Color.parseColor("#e0e0e0"));
                }
            }
        });

        mSunOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_sunOpen(1).show();
            }
        });
        mSunClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateDialog_sunClose(1).show();
            }
        });


        mWeekCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(mWeekCheckBox.isChecked())) {

//                    mMonLayout.setVisibility(View.VISIBLE);
                    mTueLayout.setVisibility(View.VISIBLE);
                    mWedLayout.setVisibility(View.VISIBLE);
                    mThuLayout.setVisibility(View.VISIBLE);
                    mFriLayout.setVisibility(View.VISIBLE);
                    mSatLayout.setVisibility(View.VISIBLE);
                    mSunLayout.setVisibility(View.VISIBLE);
                    mTimingMsg.setText("If the restaurant is closed on a perticular day then just UNCHECKED it.");

                } else {

//                    mMonLayout.setVisibility(View.GONE);
                    mTueLayout.setVisibility(View.GONE);
                    mWedLayout.setVisibility(View.GONE);
                    mThuLayout.setVisibility(View.GONE);
                    mFriLayout.setVisibility(View.GONE);
                    mSatLayout.setVisibility(View.GONE);
                    mSunLayout.setVisibility(View.GONE);
                    mTimingMsg.setText("To SET the timing please click on Opening and Closing text writen above.");

                }

            }
        });

        mDeliveryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDeliveryCheckBox.isChecked()) {

                    mDeliveryLatout.setVisibility(View.VISIBLE);

                } else {

                    mDeliveryLatout.setVisibility(View.GONE);

                }

            }
        });


        mGeneralInfoBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String costForTwo = mCostForTwo.getEditText().getText().toString();
                String minOrder = mMinOrder.getEditText().getText().toString();
                String deliveryCost = mDeliveryCost.getEditText().getText().toString();
                String xAmount = mXAmount.getEditText().getText().toString();
                String sgst = mSgst.getEditText().getText().toString();
                String cgst = mCgst.getEditText().getText().toString();
                String serviceCharges = mServiceCharges.getEditText().getText().toString();

                String monOpen = mMonOpen.getText().toString();
                String monClose = mMonClose.getText().toString();
                String tueOpen = mTueOpen.getText().toString();
                String tueClose = mTueClose.getText().toString();
                String wedOpen = mWedOpen.getText().toString();
                String wedClose = mWedClose.getText().toString();
                String thuOpen = mThuOpen.getText().toString();
                String thuClose = mThuClose.getText().toString();
                String friOpen = mFriOpen.getText().toString();
                String friClose = mFriClose.getText().toString();
                String satOpen = mSatOpen.getText().toString();
                String satClose = mSatClose.getText().toString();
                String sunOpen = mSunOpen.getText().toString();
                String sunClose = mSunClose.getText().toString();

                Boolean deliveryCB;
                Boolean weekCB;
                Boolean monCB;
                Boolean tueCB;
                Boolean wedCB;
                Boolean thuCB;
                Boolean friCB;
                Boolean satCB;
                Boolean sunCB;

                if (mDeliveryCheckBox.isChecked())
                    deliveryCB = true;
                else
                    deliveryCB = false;

                if (mWeekCheckBox.isChecked())
                    weekCB = true;
                else
                    weekCB = false;

                if (mMonCB.isChecked())
                    monCB = true;
                else
                    monCB = false;

                if (mTueCB.isChecked())
                    tueCB = true;
                else
                    tueCB = false;

                if (mWedCB.isChecked())
                    wedCB = true;
                else
                    wedCB = false;

                if (mThuCB.isChecked())
                    thuCB = true;
                else
                    thuCB = false;

                if (mFriCB.isChecked())
                    friCB = true;
                else
                    friCB = false;

                if (mSatCB.isChecked())
                    satCB = true;
                else
                    satCB = false;

                if (mSunCB.isChecked())
                    sunCB = true;
                else
                    sunCB = false;


                if (!TextUtils.isEmpty(costForTwo) && !monOpen.equals("Opening") && !monClose.equals("Closing")) {

                    mMainProgress.setTitle("Saving Data");
                    mMainProgress.setMessage("Please wait while we save the Data");
                    mMainProgress.setCanceledOnTouchOutside(true);
                    mMainProgress.show();

                    Map<String, Object> restaurant = new HashMap<>();
                    restaurant.put("costForTwo", costForTwo);
                    restaurant.put("minOrder", minOrder);
                    restaurant.put("deliveryCost", deliveryCost);
                    restaurant.put("xAmount", xAmount);
                    restaurant.put("sgst", sgst);
                    restaurant.put("cgst", cgst);
                    restaurant.put("serviceCharges", serviceCharges);
                    //-------Timing---------------
                    restaurant.put("monOpen", monOpen);
                    restaurant.put("monClose", monClose);
                    restaurant.put("tueOpen", tueOpen);
                    restaurant.put("tueClose", tueClose);
                    restaurant.put("wedOpen", wedOpen);
                    restaurant.put("wedClose", wedClose);
                    restaurant.put("thuOpen", thuOpen);
                    restaurant.put("thuClose", thuClose);
                    restaurant.put("friOpen", friOpen);
                    restaurant.put("friClose", friClose);
                    restaurant.put("satOpen", satOpen);
                    restaurant.put("satClose", satClose);
                    restaurant.put("sunOpen", sunOpen);
                    restaurant.put("sunClose", sunClose);
                    //--------Days--------------
                    restaurant.put("deliveryCB", deliveryCB);
                    restaurant.put("weekCB", weekCB);
                    restaurant.put("monCB", monCB);
                    restaurant.put("tueCB", tueCB);
                    restaurant.put("wedCB", wedCB);
                    restaurant.put("thuCB", thuCB);
                    restaurant.put("friCB", friCB);
                    restaurant.put("satCB", satCB);
                    restaurant.put("sunCB", sunCB);


                    mDocRef.update(restaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                mMainProgress.dismiss();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();

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

    //------------Monday Time Picker-------------

    private Dialog onCreateDialog_monOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), monOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener monOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mMonOpen.setText(hour + ":" + minute);
            mTueOpen.setText(hour + ":" + minute);
            mWedOpen.setText(hour + ":" + minute);
            mThuOpen.setText(hour + ":" + minute);
            mFriOpen.setText(hour + ":" + minute);
            mSatOpen.setText(hour + ":" + minute);
            mSunOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_monClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), monClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener monClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mMonClose.setText(hour + ":" + minute);
            mTueClose.setText(hour + ":" + minute);
            mWedClose.setText(hour + ":" + minute);
            mThuClose.setText(hour + ":" + minute);
            mFriClose.setText(hour + ":" + minute);
            mSatClose.setText(hour + ":" + minute);
            mSunClose.setText(hour + ":" + minute);
        }
    };


    //------------Tuesday Time Picker-------------

    private Dialog onCreateDialog_tueOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), tueOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener tueOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mTueOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_tueClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), tueClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener tueClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mTueClose.setText(hour + ":" + minute);
        }
    };

    //------------Wednesday Time Picker-------------

    private Dialog onCreateDialog_wedOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), wedOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener wedOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mWedOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_wedClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), wedClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener wedClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mWedClose.setText(hour + ":" + minute);
        }
    };

    //------------Thuresday Time Picker-------------

    private Dialog onCreateDialog_thuOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), thuOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener thuOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mThuOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_thuClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), thuClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener thuClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mThuClose.setText(hour + ":" + minute);
        }
    };

    //------------Friday Time Picker-------------

    private Dialog onCreateDialog_friOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), friOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener friOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mFriOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_friClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), friClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener friClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mFriClose.setText(hour + ":" + minute);
        }
    };

    //------------Saturday Time Picker-------------

    private Dialog onCreateDialog_satOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), satOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener satOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mSatOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_satClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), satClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener satClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mSatClose.setText(hour + ":" + minute);
        }
    };

    //------------sunday Time Picker-------------

    private Dialog onCreateDialog_sunOpen(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), sunOpen_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener sunOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mSunOpen.setText(hour + ":" + minute);
        }
    };

    private Dialog onCreateDialog_sunClose(int id) {
        if (id == 1)
            return new TimePickerDialog(getActivity(), sunClose_timePickerListener, hour, minute, true);
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener sunClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mSunClose.setText(hour + ":" + minute);
        }
    };


}
