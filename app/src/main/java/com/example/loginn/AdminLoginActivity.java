// AdminLoginActivity.java
package com.example.loginn;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import com.google.firebase.FirebaseException;

public class AdminLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText phoneNumberInput, otpInput;
    private Button sendOtpButton, verifyOtpButton, resendOtpButton;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        otpInput = findViewById(R.id.otpInput);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);
        resendOtpButton = findViewById(R.id.resendOtpButton);

        otpInput.setVisibility(View.GONE);
        verifyOtpButton.setVisibility(View.GONE);
        resendOtpButton.setVisibility(View.GONE);
    }

    private void setupClickListeners() {
        sendOtpButton.setOnClickListener(v -> sendOTP());
        verifyOtpButton.setOnClickListener(v -> verifyOTP());
        resendOtpButton.setOnClickListener(v -> resendOTP());
    }

    private void sendOTP() {
        String phoneNumber = phoneNumberInput.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            phoneNumberInput.setError("Phone number is required");
            return;
        }

        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }

        final String finalPhoneNumber = phoneNumber;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                finalPhoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(AdminLoginActivity.this,
                                "Verification failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String vId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        verificationId = vId;
                        showOtpInputs();
                        Toast.makeText(AdminLoginActivity.this,
                                "OTP sent successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showOtpInputs() {
        otpInput.setVisibility(View.VISIBLE);
        verifyOtpButton.setVisibility(View.VISIBLE);
        resendOtpButton.setVisibility(View.VISIBLE);
        sendOtpButton.setVisibility(View.GONE);
    }

    private void verifyOTP() {
        String otp = otpInput.getText().toString().trim();
        if (otp.isEmpty()) {
            otpInput.setError("OTP is required");
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendOTP() {
        sendOTP();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startGoogleSignIn();
                    } else {
                        Toast.makeText(AdminLoginActivity.this,
                                "Authentication failed: " + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startGoogleSignIn() {
        Intent intent = new Intent(AdminLoginActivity.this, AdminGoogleSignInActivity.class);
        startActivity(intent);
        finish();
    }
}