package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_phone extends AppCompatActivity {

    private static final String TAG = verify_phone.class.getName();

    private EditText editText;
    private Button button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        setTitleToolbar();
        initUi();
        mAuth = FirebaseAuth.getInstance();

        // Lấy số điện thoại từ Intent
        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");
        final String pass = intent.getStringExtra("pass");
        final String name = intent.getStringExtra("name");
        final String diachi = intent.getStringExtra("diachi");
        final String phone = intent.getStringExtra("phone");

        final String strphoneNumber = phone.startsWith("0") ? phone.replaceFirst("0", "+84") : phone;

        editText.setText(phone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickVerifyPhone(strphoneNumber, user, pass, name, diachi);
            }
        });
    }

    private String convertToInternationalFormat(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "+84" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Xác thực số điện thoại");
        }
    }

    private void initUi() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    private void onClickVerifyPhone(final String phone, final String user, final String pass, final String name, final String diachi) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(verify_phone.this, "Xác thực thất bại", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        super.onCodeSent(verificationId, token);
                        gotoEnterOTP(phone, verificationId, user, pass, name, diachi);
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            gotoMainActiviy(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(verify_phone.this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void gotoMainActiviy(String phoneNumber) {
        Intent intent = new Intent(this, DangNhap.class);
        intent.putExtra("phone_Number", phoneNumber);
        startActivity(intent);
    }

    private void gotoEnterOTP(String phone, String verificationId, String user, String pass, String name, String diachi) {
        Intent intent = new Intent(this, EnterOTP.class);
        intent.putExtra("phone_Number", phone);
        intent.putExtra("verificationId", verificationId);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);
        intent.putExtra("name", name);
        intent.putExtra("diachi", diachi);
        startActivity(intent);
    }
}
