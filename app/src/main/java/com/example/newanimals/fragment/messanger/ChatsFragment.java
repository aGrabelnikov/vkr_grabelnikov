package com.example.newanimals.fragment.messanger;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.activity.StartAppActivity;
import com.example.newanimals.adapter.ChatsAdapter;
import com.example.newanimals.db.ChatData;
import com.example.newanimals.fragment.BaseFragment;
import com.example.newanimals.presenter.ChatsPresenter;
import com.example.newanimals.view.ChatsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatsFragment extends BaseFragment implements ChatsView {
    private ChatsAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.filter)
    EditText filter;
    @BindView(R.id.add)
    ImageView add;
    private List<ChatData> originalData;
    private ChatsPresenter presenter;

    public static ChatsFragment newInstance() {
        return new ChatsFragment();
    }
    @Override
    protected void initViews() {
        super.initViews();
        presenter = new ChatsPresenter(this);
        presenter.getChats();

//        add.setOnClickListener(l->{
//            UsersContactBottomDialog dialog = new UsersContactBottomDialog();
//            dialog.setCancelable(true);
//            dialog.show(getChildFragmentManager(), "users");
//        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {filter(editable.toString());}
        });
    }
    private void filter(String text) {
        List<ChatData> filterdName = new ArrayList<>();
        if(originalData!=null && !originalData.isEmpty()) {
            for (ChatData data : originalData)
                if (data.getName().toLowerCase().contains(text.toLowerCase()) || data.getName().toLowerCase().contains(text.toLowerCase())) filterdName.add(data);
            adapter.setFilterData(filterdName);
        }
    }
    @Override
    protected int layoutId() {
        return R.layout.chat_frgament;
    }

    @Override
    public void getChats(List<ChatData> chats) {
        originalData = new ArrayList<>(chats);
        adapter = new ChatsAdapter(getContext(), chats, click->{
            ((StartAppActivity)getActivity()).replaceFragment(MessangerFragment.newInstance(click.login), true);
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    @Override
    public void errorMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
