package in.ordercrunch.ordercrunchmerchant;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_PhoneVerification extends Fragment {

    private TextInputLayout mPhoneText;
    private TextInputLayout mVerificationCode;
    private Button mMainBtn;

    private ProgressDialog mPhoneProgress;

    private FirebaseAuth mAuth;

    private int mBtnType=0;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    public Fragment_PhoneVerification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_verification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPhoneText = (TextInputLayout)getActivity().findViewById(R.id.phone_phone);
        mVerificationCode = (TextInputLayout)getActivity().findViewById(R.id.phone_otp);
        mMainBtn = (Button)getActivity().findViewById(R.id.phone_main_btn);

        mAuth = FirebaseAuth.getInstance();

        mPhoneProgress = new ProgressDialog(getContext());

        mMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = mPhoneText.getEditText().getText().toString();


                if(mBtnType == 0){

                    mPhoneProgress.setTitle("Sending Code");
                    mPhoneProgress.setMessage("Please wait while we send the Verification code");
                    mPhoneProgress.setCanceledOnTouchOutside(true);
                    mPhoneProgress.show();

                    mPhoneText.setEnabled(false);
                    mMainBtn.setEnabled(false);


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            getActivity(),               // Activity (for callback binding)
                            mCallbacks);

                }
                else {

                    mMainBtn.setEnabled(false);
                    mVerificationCode.setEnabled(false);

                    mPhoneProgress.setTitle("Verifying Code");
                    mPhoneProgress.setMessage("Please wait while we Verfy the code");
                    mPhoneProgress.setCanceledOnTouchOutside(true);
                    mPhoneProgress.show();

                    String verificationCode = mVerificationCode.getEditText().getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Toast.makeText(getActivity(), "The phone number format is not valid.", Toast.LENGTH_LONG).show();

                    mPhoneProgress.dismiss();
                    mPhoneText.setEnabled(true);
                    mMainBtn.setEnabled(true);
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(getActivity(), "The SMS quota for the project has been exceeded", Toast.LENGTH_LONG).show();

                    mPhoneProgress.dismiss();
                    mPhoneText.setEnabled(true);
                    mMainBtn.setEnabled(true);
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                mPhoneProgress.dismiss();
                mVerificationCode.setVisibility(View.VISIBLE);

                mMainBtn.setText("Verify Code");
                mBtnType = 1;
                mMainBtn.setEnabled(true);

                // ...
            }

        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("TAG", "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//                            // ...
//
//                            mPhoneProgress.dismiss();
//
//                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
//                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(mainIntent);
//                            getActivity().finish();
//
//                        } else {
//                            // Sign in failed, display a message and update the UI
//
//                            Toast.makeText(getActivity(), "There is some Error. Please try again.", Toast.LENGTH_LONG).show();
//
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//
//                                Toast.makeText(getActivity(), "The verification code entered was invalid.", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                    }
//                });

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();
                            // ...

                            mPhoneProgress.dismiss();

                            Intent mainIntent = new Intent(getActivity(), DetailsActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            getActivity().finish();

                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Toast.makeText(getActivity(), "The verification code entered was invalid.", Toast.LENGTH_LONG).show();

                                mPhoneProgress.dismiss();
                                mVerificationCode.setEnabled(true);
                                mMainBtn.setEnabled(true);
                            }
                            // ...
                        }
                    }
                });

    }

}
