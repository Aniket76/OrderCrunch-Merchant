package in.ordercrunch.ordercrunchmerchant;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_AddRestaurant extends Fragment {

    private TextInputLayout mRestaurantName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mRetypePassword;
    private Button mCreateBtn;

    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;
    private DocumentReference mDocRef;

    public Fragment_AddRestaurant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_restaurant, container, false);

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mRegProgress = new ProgressDialog(getContext());


        mRestaurantName = (TextInputLayout) getActivity().findViewById(R.id.addRestaurant_name);
        mEmail = (TextInputLayout) getActivity().findViewById(R.id.addRestaurant_email);
        mPassword = (TextInputLayout) getActivity().findViewById(R.id.addRestaurant_password);
        mRetypePassword = (TextInputLayout) getActivity().findViewById(R.id.addRestaurant_retype_password);
        mCreateBtn = (Button) getActivity().findViewById(R.id.addRestaurant_btn_create);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String Restaurant_name = mRestaurantName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String repassword = mRetypePassword.getEditText().getText().toString();
                String blank_string = "";


                if (password.equals(repassword)) {

                    if (!TextUtils.isEmpty(Restaurant_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                        mRegProgress.setTitle("Registering User");
                        mRegProgress.setMessage("Please wait while we create your Account.");
                        mRegProgress.setCanceledOnTouchOutside(false);
                        mRegProgress.show();

                        register_user(Restaurant_name, email, password, blank_string);

                    } else {

                        Toast.makeText(getActivity(), "Please fill all the fields and try again", Toast.LENGTH_LONG).show();

                    }


                } else {

                    Toast.makeText(getActivity(), "Password do not match", Toast.LENGTH_LONG).show();

                }


            }

        });


    }

    private void register_user(final String restaurant_name, final String email, String password, final String blank) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    Map<String, Object> restaurant = new HashMap<>();
                    restaurant.put("aaName", restaurant_name);
                    restaurant.put("tagLine", blank);
                    restaurant.put("email", email);
                    restaurant.put("website", blank);
                    restaurant.put("phoneNumber", blank);
                    restaurant.put("alternativePhoneNumber", blank);
                    restaurant.put("cuisines", blank);
                    restaurant.put("dineIn", false);
                    restaurant.put("takeAway", false);
                    restaurant.put("homeDelivery", false);
                    restaurant.put("onTheGo", false);
                    //-----address------
                    restaurant.put("shopNo",blank);
                    restaurant.put("city","Kolkata");
                    restaurant.put("state","West Bengal");
                    //-----INFO--------
                    restaurant.put("costForTwo",blank);
                    restaurant.put("deliveryCB",false);
                    restaurant.put("weekCB",true);
                    restaurant.put("monCB",true);
                    restaurant.put("tueCB",true);
                    restaurant.put("wedCB",true);
                    restaurant.put("thuCB",true);
                    restaurant.put("friCB",true);
                    restaurant.put("satCB",true);
                    restaurant.put("sunCB",true);

                    mDocRef = FirebaseFirestore.getInstance().collection("restaurant").document(uid);

                    mDocRef.set(restaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                mRegProgress.dismiss();

                                Fragment_PhoneVerification fragment = new Fragment_PhoneVerification();
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.start_avtivity_layout,fragment,"Fragment PhoneVerification");
                                transaction.addToBackStack(null);
                                transaction.setReorderingAllowed(true);
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                transaction.commit();

//                                Intent mainIntent = new Intent(getActivity(), DetailsActivity.class);
//                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(mainIntent);
//                                getActivity().finish();

                            }

                            else {

                                mRegProgress.dismiss();

                                Toast.makeText(getActivity(),task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                            }

                        }
                    });


                } else {

                    mRegProgress.hide();

                    Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();

                }

            }
        });
    }

}
