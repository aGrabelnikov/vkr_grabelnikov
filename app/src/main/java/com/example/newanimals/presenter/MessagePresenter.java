package com.example.newanimals.presenter;

import androidx.annotation.NonNull;

import com.example.newanimals.db.MessageData;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.SPHelper;
import com.example.newanimals.view.MessageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagePresenter {
    private MessageView view;

    public MessagePresenter(MessageView view) {
        this.view = view;
    }


    public void getMessages(String loginReceiver){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://avito-55e51-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = database.getReference();
        String modiferLogin = SPHelper.getLogin().replace(".","_");
        String modiferRecerv = loginReceiver.replace(".","_");

        DatabaseReference userLoginRef = databaseReference.child("Chats").child(modiferLogin).child(modiferRecerv).child("Messages");
        List<MessageData> messages = new ArrayList<>();

        userLoginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot childSnapshot: snapshot.getChildren()){
                    MessageData db = childSnapshot.getValue(MessageData.class);
                    if(db!=null)
                        messages.add(db);
                }
                view.getMessages(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                view.errorMsg(error.getMessage());
            }
        });
    }


    private String getTime(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
        return dateFormat.format(currentDate);
    }
    public void sendMessages(String loginReceiver, String msg){
        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference databaseReference = database.getReference();

        String modifiedLogin = SPHelper.getLogin().replace(".", "_");
        String receiver = loginReceiver.replace(".", "_");

//        MessageData message = new MessageData(    loginReceiver,SPHelper.getLogin(),
//             msg, getTime());
        MessageData message = new MessageData(SPHelper.getLogin(),
                loginReceiver, msg, getTime());

        DatabaseReference usersRef = databaseReference.child("Chats").child(modifiedLogin);
        DatabaseReference receiverLogin =  usersRef.child(receiver);
        DatabaseReference messageRef = receiverLogin.child("Messages");

        String messageId = messageRef.push().getKey();
        DatabaseReference sendMsg = messageRef.child(messageId);

        sendMsg.setValue(message);

        view.successSend();

    }
}
