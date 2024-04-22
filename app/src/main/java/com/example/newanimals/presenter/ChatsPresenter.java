package com.example.newanimals.presenter;

import androidx.annotation.NonNull;

import com.example.newanimals.db.ChatData;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.ChatsView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsPresenter {
    private ChatsView view;

    public ChatsPresenter(ChatsView view) {
        this.view = view;
    }


    public void getChats(){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference databaseReference = database.getReference();
        String modiferLogin = SPHelper.getLogin().replace(".","_");

        DatabaseReference userLoginRef = databaseReference.child("Chats").child(modiferLogin);
        List<ChatData> chats = new ArrayList<>();

        userLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    ChatData db = childSnapshot.getValue(ChatData.class);
                    if(db!=null)
                        chats.add(db);
                }
                view.getChats(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.errorMsg(error.getMessage());
            }
        });
    }

}
