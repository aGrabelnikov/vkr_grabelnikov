package com.example.newanimals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.db.MessageData;
import com.example.newanimals.utils.SPHelper;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.LabelHolader> {
    private Context context;
    private List<MessageData> data;

    public MessageAdapter(Context context, List<MessageData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MessageAdapter.LabelHolader onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, viewGroup, false);
        return new LabelHolader(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.LabelHolader labelHolader, int i) {
        if(data.get(i).sender.equals(SPHelper.getLogin())) labelHolader.name.setText("Вы");
        else labelHolader.name.setText(data.get(i).sender);

        labelHolader.text.setText(data.get(i).message);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class LabelHolader extends RecyclerView.ViewHolder {
        TextView name, text;
        public LabelHolader(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            text = itemView.findViewById(R.id.text);
        }
    }
}
