package com.example.newanimals.fragment.messanger;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.adapter.MessageAdapter;
import com.example.newanimals.db.MessageData;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.presenter.MessagePresenter;
import com.example.newanimals.view.MessageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessangerFragment extends BaseFragment implements MessageView {
    private String login;
    private MessageAdapter adapter;
    private MessagePresenter presenter;
    @BindView(R.id.text) EditText text;
    @BindView(R.id.send)
    ImageView send;
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    protected void initViews() {
        super.initViews();
        presenter = new MessagePresenter(this);
        presenter.getMessages(login);

        send.setOnClickListener(l->{
            if(!text.getText().equals("") && text.getText() !=null){
                presenter.sendMessages(login, text.getText().toString());
                presenter.getMessages(login);
            }
        });
    }

    public MessangerFragment(String login) {
        this.login = login;
    }
    public static MessangerFragment newInstance(String login) {
        return new MessangerFragment(login);
    }
    @Override
    protected int layoutId() {
        return R.layout.messanger_fragment;
    }

    @Override
    public void getMessages(List<MessageData> messages) {
        adapter = new MessageAdapter(getContext(), messages);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    @Override
    public void errorMsg(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successSend() {
        adapter.notifyDataSetChanged();
        text.setText("");
    }
}
