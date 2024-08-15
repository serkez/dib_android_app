package com.example.dibapp.classes;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

        setColorOfAllTextViews(holder, position % 2 == 0); // becasue there is one response per every message, isUser is determined by index being odd/even

        holder.messageTextView.setText(message);
    }


    private void setColorOfAllTextViews(MessageViewHolder holder, boolean isUser) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.messageTextView.getLayoutParams();

        if (isUser) {
            // Align the user's message to the right and set colors
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            holder.messageTextView.setBackgroundResource(R.drawable.user_message_background);
            holder.messageTextView.setTextColor(mIS_NIGHT_MODE ? Color.BLACK : Color.WHITE); //text color is set based on isNightMode
        } else {
            // Align the computer's message to the left and set colors
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.UNSET;
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
