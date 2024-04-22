package com.example.newanimals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.db.ChatData;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.LabelHolder> {
    private Context context;
    private List<ChatData> chats;
    private OnClickChats clickChats;

    public ChatsAdapter(Context context, List<ChatData> chats, OnClickChats clickChats) {
        this.context = context;
        this.chats = chats;
        this.clickChats = clickChats;
    }

    public void setFilterData(List<ChatData> filterdName) {
        this.chats = filterdName;
        notifyDataSetChanged();
    }

    public interface OnClickChats{
        void onClickChats(ChatData chat);
    }

    @NonNull
    @Override
    public ChatsAdapter.LabelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chats_item, parent, false);
        return new LabelHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.LabelHolder holder, int position) {
        holder.itemView.setOnClickListener(l->{
            clickChats.onClickChats(chats.get(position));
        });
        holder.name.setText(chats.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class LabelHolder extends RecyclerView.ViewHolder {
        TextView name;
        public LabelHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}

