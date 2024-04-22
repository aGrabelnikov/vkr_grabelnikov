package com.example.newanimals.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newanimals.R;
import com.example.newanimals.db.AdsData;
import com.example.newanimals.db.ChatData;
import com.example.newanimals.db.MessageData;
import com.example.newanimals.network.Const;
import com.example.newanimals.utils.SPHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class InfoAdsBottomDialog extends BottomSheetDialogFragment {
    private AdsData data;
    private ImageView img;
    private TextView nameAds, price, typePrice, description;
    private Button btn;

    public InfoAdsBottomDialog(AdsData data) {
        this.data = data;
    }

    public static InfoAdsBottomDialog newInstance(AdsData data) {
        return new InfoAdsBottomDialog(data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.info_ads_bottom_dialog, container, false);
        img =v.findViewById(R.id.image);
        nameAds = v.findViewById(R.id.name_ads);
        price = v.findViewById(R.id.price);
        typePrice = v.findViewById(R.id.type_ads);
        description = v.findViewById(R.id.description);
        btn = v.findViewById(R.id.btn);

        Picasso.get().load(data.getImg()).into(img);
        nameAds.setText(data.getName());
        price.setText(data.getPrice()+" Руб.");
        typePrice.setText(data.getType_price());
        description.setText(data.getAddress());



        btn.setOnClickListener(l->{
            createMessages();
        });
        return v;
    }

    public void createMessages(){

        FirebaseDatabase database = FirebaseDatabase.getInstance(Const.URL);
        DatabaseReference databaseReference = database.getReference();
        ChatData userData = new ChatData(
                data.getUser(),
                data.getName(),
                null
        );
        String login = SPHelper.getLogin();
        String login_receiv = data.getUser();
        String modifiedLogin = login.replace(".", "_"); // Заменяем "." на "_"
        String receiver = login_receiv.replace(".", "_");

        MessageData message = new MessageData(SPHelper.getLogin(),
                data.getName(), "Добырй день, меня заинтересовало ваше объявление.", "13/25");

        DatabaseReference usersRef = databaseReference.child("Chats").child(modifiedLogin);
        DatabaseReference receiverLogin =  usersRef.child(receiver);
        DatabaseReference messageRef = receiverLogin.child("Messages");

        String messageId = messageRef.push().getKey();
        DatabaseReference sendMsg = messageRef.child(messageId);

        receiverLogin.setValue(userData);
        sendMsg.setValue(message);


    }
}
