package com.example.bduan1.Login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.bduan1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    String TAG = "zzzzzzzzz";
    private EditText edtEmail, edtPass, edtConfilm;
    private TextView btnDangNhap;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail_dk);
        edtPass = findViewById(R.id.edtPass_dk);
        edtConfilm = findViewById(R.id.edtPassNhapLai_dk);
        Button btnDangKy = findViewById(R.id.btnDangKyMoi);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        // Icons for eyes
        Drawable eyeOpen = getResources().getDrawable(R.drawable.matmo); // Icon mắt mở
        Drawable eyeClosed = getResources().getDrawable(R.drawable.mat); // Icon mắt đóng

// Function to toggle password visibility
        View.OnTouchListener togglePasswordVisibility = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                EditText editText = (EditText) v;
                int drawableEndIndex = editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width();
                if (event.getRawX() >= drawableEndIndex) {
                    if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeClosed, null);
                    } else {
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeOpen, null);
                    }
                    editText.setSelection(editText.getText().length()); // Đặt con trỏ ở cuối
                    return true;
                }
            }
            return false;
        };

// Attach listener to both EditText fields
        edtPass.setOnTouchListener(togglePasswordVisibility);
        edtConfilm.setOnTouchListener(togglePasswordVisibility);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String edtfirm = edtConfilm.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Vui lòng nhập email");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(DangKy.this, "Định dạng email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    edtPass.setError("Vui lòng nhập mật khẩu");
                    return;
                }
                if (pass.length() < 6) {
                    edtPass.setError("Mật khẩu phải có ít nhất 6 ký tự");
                    return;
                }
                if (TextUtils.isEmpty(edtfirm)) {
                    edtConfilm.setError("Vui lòng nhập lại mật khẩu");
                    return;
                }
                if (!pass.equals(edtfirm)) {
                    edtConfilm.setError("Mật khẩu không khớp");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(DangKy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Đăng ký thành công"); // Thêm log này
                                    String userID = mAuth.getCurrentUser().getUid();
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("email", email);
                                    userData.put("role", "user");
                                    userData.put("name", "");
                                    userData.put("phoneNumber", "");
                                    userData.put("gender","");
                                    userData.put("birth","");
                                    firestore.collection("users").document(userID).set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Dữ liệu đã được ghi lên Firestore thành công"); // Thêm log này
                                                        Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                        finish(); // Đóng activity sau khi đăng ký thành công
                                                    } else {
                                                        Log.e(TAG, "Ghi dữ liệu lên Firestore thất bại", task.getException()); // Thêm log lỗi
                                                        Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Log.e(TAG, "Đăng ký thất bại", task.getException()); // Thêm log lỗi
                                    Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            });
    }
}