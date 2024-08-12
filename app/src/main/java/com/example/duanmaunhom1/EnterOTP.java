package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;
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

public class EnterOTP extends AppCompatActivity {

    public static final String TAG = EnterOTP.class.getName();

    private TextView tvResendOTP;
    private EditText etOTP;
    private Button btnVerify;

    private FirebaseAuth mAuth;
    private String mPhoneNumber;
    private String mVerificationId;
    private String mUser;
    private String mPass;
    private String mName;
    private String mDiachi;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        getDataIntent();
        setTitleToolbar();
        iniUi();

        mAuth = FirebaseAuth.getInstance();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpCode = etOTP.getText().toString().trim();
                onClickVerify(otpCode);
            }
        });

        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickResendOTP();
            }
        });
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra("phone_Number");
        mVerificationId = intent.getStringExtra("verificationId");

        // Nhận thêm dữ liệu đăng ký
        mUser = intent.getStringExtra("user");
        mPass = intent.getStringExtra("pass");
        mName = intent.getStringExtra("name");
        mDiachi = intent.getStringExtra("diachi");
    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Nhập mã xác thực");
        }
    }

    private void iniUi() {
        etOTP = findViewById(R.id.etOTP);
        btnVerify = findViewById(R.id.btnVerify);
        tvResendOTP = findViewById(R.id.tvResendOTP);
    }

    private void onClickVerify(String otpCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void onClickResendOTP() {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(mPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setForceResendingToken(mResendToken)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(EnterOTP.this, "Xác thực thất bại", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        super.onCodeSent(verificationId, token);
                        mVerificationId = verificationId;
                        mResendToken = token;
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
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            if (user != null) {
                                String phoneNumber = user.getPhoneNumber();
                                if (phoneNumber != null) {
                                    addNewUser(phoneNumber, mUser, mPass, mName, mDiachi);
                                }
                            }
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(EnterOTP.this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void addNewUser(String phoneNumber, String user, String pass, String name, String diachi) {
        Log.d(TAG, "Attempting to add new user with phone number: " + phoneNumber);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);

        // Chuyển đổi số điện thoại từ +84xxxxxxxxx về 0xxxxxxxxx
        String updatedPhoneNumber = phoneNumber.replaceFirst("\\+84", "0");

        // Thêm người dùng mới vào cơ sở dữ liệu
        boolean isAdded = nguoiDungDAO.addNewUser(user, pass, name, diachi, updatedPhoneNumber);

        if (isAdded) {
            Log.d(TAG, "User added successfully");
            gotoMainActivity(updatedPhoneNumber);
        } else {
            Log.e(TAG, "Failed to add new user");
        }
    }




    private void gotoMainActivity(String phoneNumber) {
        Intent intent = new Intent(this, DangNhap.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
    }
}