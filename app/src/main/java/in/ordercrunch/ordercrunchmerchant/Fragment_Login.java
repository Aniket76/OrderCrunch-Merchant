package in.ordercrunch.ordercrunchmerchant;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Login extends Fragment {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mLoginBtn;
    private TextView mForgetPass;

    private ProgressDialog mRegProgress;

    private FirebaseAuth mAuth;


    public Fragment_Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mRegProgress = new ProgressDialog(getContext());

        mEmail = (TextInputLayout)getActivity().findViewById(R.id.login_email);
        mPassword = (TextInputLayout)getActivity().findViewById(R.id.login_password);
        mLoginBtn = (Button)getActivity().findViewById(R.id.login_btn);
        mForgetPass = (TextView) getActivity().findViewById(R.id.login_forgotpass);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("Logging In");
                    mRegProgress.setMessage("Please wait while we check your credentials.");
                    mRegProgress.setCanceledOnTouchOutside(true);
                    mRegProgress.show();

                    signin_user(email,password);

                }

                else{

                    Toast.makeText(getActivity(),"Cannot Sign in. Please fill all the fields and try again",Toast.LENGTH_LONG).show();

                }


            }
        });


        mForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                String email = mEmail.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email)){

                    mRegProgress.setTitle("Sending Mail");
                    mRegProgress.setMessage("Please wait while we send the recovery Mail");
                    mRegProgress.setCanceledOnTouchOutside(true);
                    mRegProgress.show();

                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "Email sent.");

                                        mRegProgress.dismiss();

                                        AlertDialog.Builder forgotpassword = new AlertDialog.Builder(getActivity());
                                        forgotpassword.setMessage("Recovery Email has been sent to the given Email ID")
                                                .setCancelable(false)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Fragment_Start fragment = new Fragment_Start();
                                                        FragmentManager manager = getFragmentManager();
                                                        FragmentTransaction transaction = manager.beginTransaction();
                                                        transaction.replace(R.id.start_avtivity_layout,fragment,"StartFragment");
                                                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                                                        transaction.commit();
                                                    }
                                                });

                                        AlertDialog alertDialog = forgotpassword.create();
                                        alertDialog.setTitle("Password Recovery");
                                        alertDialog.show();

//                                        Toast.makeText(getActivity(),"Recovery Email has been sent to the given Email ID",Toast.LENGTH_LONG).show();

                                    }else {

                                        mRegProgress.dismiss();
                                        Toast.makeText(getActivity(),"Unknown error accrued. Please try again",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                }
                else {

                    mRegProgress.dismiss();
                    Toast.makeText(getActivity(),"Please Enter Your Email ID and try again.",Toast.LENGTH_LONG).show();

                }

            }
        });


    }


    private void signin_user(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            mRegProgress.dismiss();

                            Intent mainIntent = new Intent(getActivity(),MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            getActivity().finish();

                        }
                        else {

                            mRegProgress.hide();

                            Toast.makeText(getActivity(),"Cannot Sign in. Please check the Email and Password",Toast.LENGTH_LONG).show();

                        }


                    }
                });

    }


}
