package in.ordercrunch.ordercrunchmerchant;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_generalInfo extends Fragment {

    private Button mGeneralInfoBtn;
    private CheckBox mWeekCheckBox,mDeliveryCheckBox;

    private int hour;
    private int minute;

    private LinearLayout mMonLayout,mTueLayout,mWedLayout,mThuLayout,mFriLayout,mSatLayout,mSunLayout;
    private RelativeLayout mDeliveryLatout;

    private TextView mMonOpen,mMonClose;

    public Fragment_generalInfo() {
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

        mGeneralInfoBtn = (Button)getActivity().findViewById(R.id.btn_addInfo);

        mWeekCheckBox = (CheckBox)getActivity().findViewById(R.id.week_checkBox);
        mDeliveryCheckBox=(CheckBox)getActivity().findViewById(R.id.delivery_checkBox);

        mMonLayout = (LinearLayout)getActivity().findViewById(R.id.mon_layout);
        mTueLayout = (LinearLayout)getActivity().findViewById(R.id.tue_layout);
        mWedLayout = (LinearLayout)getActivity().findViewById(R.id.wed_layout);
        mThuLayout = (LinearLayout)getActivity().findViewById(R.id.thu_layout);
        mFriLayout = (LinearLayout)getActivity().findViewById(R.id.fri_layout);
        mSatLayout = (LinearLayout)getActivity().findViewById(R.id.sat_layout);
        mSunLayout = (LinearLayout)getActivity().findViewById(R.id.sun_layout);

        mDeliveryLatout = (RelativeLayout)getActivity().findViewById(R.id.delivery_layout);

        mMonOpen = (TextView)getActivity().findViewById(R.id.mon_open);
        mMonClose = (TextView)getActivity().findViewById(R.id.mon_close);

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

        mWeekCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(mWeekCheckBox.isChecked())){

//                    mMonLayout.setVisibility(View.VISIBLE);
                    mTueLayout.setVisibility(View.VISIBLE);
                    mWedLayout.setVisibility(View.VISIBLE);
                    mThuLayout.setVisibility(View.VISIBLE);
                    mFriLayout.setVisibility(View.VISIBLE);
                    mSatLayout.setVisibility(View.VISIBLE);
                    mSunLayout.setVisibility(View.VISIBLE);

                }else {

//                    mMonLayout.setVisibility(View.GONE);
                    mTueLayout.setVisibility(View.GONE);
                    mWedLayout.setVisibility(View.GONE);
                    mThuLayout.setVisibility(View.GONE);
                    mFriLayout.setVisibility(View.GONE);
                    mSatLayout.setVisibility(View.GONE);
                    mSunLayout.setVisibility(View.GONE);

                }

            }
        });

        mDeliveryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDeliveryCheckBox.isChecked()){

                    mDeliveryLatout.setVisibility(View.VISIBLE);

                }else {

                    mDeliveryLatout.setVisibility(View.GONE);

                }

            }
        });

        mGeneralInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

    }

    private Dialog onCreateDialog_monOpen(int id){
        if (id == 1)
            return new TimePickerDialog(getActivity(), monOpen_timePickerListener, hour, minute, true);
        return null;
    }
    protected TimePickerDialog.OnTimeSetListener monOpen_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mMonOpen.setText(hour+":"+minute);
        }
    };

    private Dialog onCreateDialog_monClose(int id){
        if (id == 1)
            return new TimePickerDialog(getActivity(), monClose_timePickerListener, hour, minute, true);
        return null;
    }
    protected TimePickerDialog.OnTimeSetListener monClose_timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourofDay, int minuteofDay) {
            hour = hourofDay;
            minute = minuteofDay;
            mMonClose.setText(hour+":"+minute);
        }
    };



}
