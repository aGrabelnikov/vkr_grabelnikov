package com.example.newanimals.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.newanimals.R;
import com.example.newanimals.activity.MainActivity;
import com.example.newanimals.activity.StartAppActivity;
import com.example.newanimals.db.UserData;
import com.example.newanimals.network.Const;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class WritePhoneNumDialog extends DialogFragment {
    private static final String TAG = "PhoneAuthActivity";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    EditText login, password, imya, phone;
    Button nextBtn;
    ImageView closeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.registration_dialog_layout, null);
        mAuth = FirebaseAuth.getInstance();
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);
        nextBtn = view.findViewById(R.id.btn_next);
        imya = view.findViewById(R.id.imya);
        phone = view.findViewById(R.id.phone);
        closeBtn = view.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(v->{
            getDialog().dismiss();
        });
        nextBtn.setOnClickListener(view1 ->{
        handleChekProdectly();});
        getDialog().setTitle("Title");
        return view;
    }

    private void handleChekProdectly() {
      if(login.getText().toString()!=null && !login.getText().toString().equals("") &&
            password.getText().toString()!=null && password.getText().toString().length()>=8 &&
              imya.getText().toString()!=null && !imya.getText().toString().equals("") &&
              phone.getText().toString()!=null && !phone.getText().toString().equals("")) {
          mAuth.createUserWithEmailAndPassword(login.getText().toString(),password.getText().toString()).addOnCompleteListener(task->{
              if(task.isSuccessful()){
                  createAccount(imya.getText().toString(), login.getText().toString(), phone.getText().toString());
                  FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                  WriteCodeForRegDialog dialog = new WriteCodeForRegDialog();
                dialog.show(getFragmentManager(), "lol");
                getDialog().dismiss();
              } else (Toast.makeText(getContext(), "Произошла ошибка! \n Повторите попытку позже", Toast.LENGTH_LONG)).show();
          }).addOnFailureListener(e->Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
      }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0.99));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void createAccount(String name, String email, String phone){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference databaseReference = database.getReference().child("Users");
        UserData userData = new UserData(
                name,
                phone,
                email,
                "https://www.bnamodelworld.com/image/cache2/550x550_contain/placeholder2.png?v=53"
        );

        String modifiedLogin = email.replace(".", "_");

        DatabaseReference userLoginRef = databaseReference.child(modifiedLogin);
        userLoginRef.setValue(userData);
    }
}
