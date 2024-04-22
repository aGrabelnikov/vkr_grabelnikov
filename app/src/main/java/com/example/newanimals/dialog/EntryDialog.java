package com.example.newanimals.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.newanimals.R;
import com.example.newanimals.presenter.LoginPresenter;
import com.example.newanimals.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class EntryDialog extends DialogFragment implements LoginView {

    private LoginPresenter presenter;
    private FirebaseAuth mAuth;
    EditText login, password;
    TextView losePass;
    Button nextBtn;
    ImageView closeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.entry_dialog_layout, null);
        presenter = new LoginPresenter(this);
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);
        losePass = view.findViewById(R.id.password_lose);
        nextBtn = view.findViewById(R.id.btn_next);
        closeBtn  = view.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(vis->{
            getDialog().dismiss();
        });
        nextBtn.setOnClickListener(view1 ->{
            handleChekProdectly();});
        getDialog().setTitle("Title");
        return view;
    }
    private void handleChekProdectly() {
        if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            auth(login.getText().toString(), password.getText().toString());
        } else Toast.makeText(getContext(), "Введите логин/пароль!", Toast.LENGTH_SHORT).show();
    }

    private void auth(String login, String password){
        mAuth = FirebaseAuth.getInstance();
        if(login!=null && password!=null){
            mAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(l->{
                        if(l.isSuccessful()){
                            presenter.getUserInfo(login);
                        } else Toast.makeText(getContext(), "Ошибка аунтефикации!", Toast.LENGTH_SHORT).show();
                    });
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

    @Override
    public void errorMsg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successMsg() {
        getDialog().dismiss();
    }
}
