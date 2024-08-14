package com.example.dibapp.classes;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dibapp.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<String> messageList;
    private final boolean mIS_NIGHT_MODE;


    public MessageAdapter(boolean isNightMode, List<String> messageList) {
        mIS_NIGHT_MODE = isNightMode;
        this.messageList = messageList;

    }
/*    public MessageAdapter(Dib chat){
        this.chat = chat;
        if(this.chat.getMessageList() != null)
            this.messageList = this.chat.getMessageList();
        else messageList = new ArrayList<>();
    }*/

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

@Override
public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
    String message = messageList.get(position);

    setColorOfAllTextViews(holder, position % 2 == 0);
    // Check if the index is even or odd to determine the sender
   /* if (position % 2 == 0) {
        // Even index: User's message (right-aligned)
        holder.messageTextView.setGravity(Gravity.END); // X work
        holder.messageTextView.setBackgroundResource(R.drawable.user_message_background);
        holder.messageTextView.setTextColor(Color.WHITE);
    } else {
        // Odd index: Computer's message (left-aligned)
        holder.messageTextView.setGravity(Gravity.START); //X work
        holder.messageTextView.setBackgroundResource(R.drawable.computer_message_background);
        holder.messageTextView.setTextColor(Color.BLACK);
    }*/

    holder.messageTextView.setText(message);
}


    private void setColorOfAllTextViews(MessageViewHolder holder, boolean isUser) {
        if(isUser){
            holder.messageTextView.setBackgroundResource(R.drawable.user_message_background);
            holder.messageTextView.setTextColor(mIS_NIGHT_MODE ? Color.BLACK : Color.WHITE);
        }
        else{
            holder.messageTextView.setBackgroundResource(R.drawable.computer_message_background);
            holder.messageTextView.setTextColor(mIS_NIGHT_MODE ? Color.WHITE : Color.BLACK);
        }
    }


    @Override
    public int getItemCount() {
        if (messageList == null)
            return 0;
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text_view);
        }
    }

}
/*
package com.example.dibapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<String> messageList;

    public MessageAdapter(List<String> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the individual message item from content_main.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_main, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = messageList.get(position);
        holder.messageTextView.setText(message);
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assuming the TextView in content_main.xml has an id message_text_view
            messageTextView = itemView.findViewById(R.id.message_text_view);
        }
    }
}
*/
