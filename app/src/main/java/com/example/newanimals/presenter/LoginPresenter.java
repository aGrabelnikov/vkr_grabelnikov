package com.example.newanimals.presenter;

import androidx.annotation.NonNull;

import com.example.newanimals.db.UserData;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.LoginView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter {
    private LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void getUserInfo(String login){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference databaseReference = database.getReference();
        String modiferLogin = login.replace(".","_");

        DatabaseReference userLoginRef = databaseReference.child("Users").child(modiferLogin);

        userLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserData userDB = snapshot.getValue(UserData.class);
                    if(userDB!=null){
                        SPHelper.setLogin(userDB.getLogin());
                        SPHelper.setPhone(userDB.getPhone());
                        SPHelper.setName(userDB.getName());
                        SPHelper.setUrlPhotoUser(userDB.getImage());
                        System.out.println(SPHelper.getUrlPhotoDownload());
                        view.successMsg();
                    }
                } else view.errorMsg("Пользователь не зарегистрирован!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.errorMsg(error.getMessage());
            }
        });
    }
}
