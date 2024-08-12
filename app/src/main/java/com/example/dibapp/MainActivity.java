package com.example.dibapp;

import static com.example.dibapp.model.Dib.getJSONFromMessages;
import static com.example.dibapp.model.Dib.getMessagesFromJSON;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;

import com.example.dibapp.databinding.ActivityMainBinding;
import com.example.dibapp.model.Dib;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Dib chat;
    private MessageAdapter messageAdapter;
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;


    private final String mKEY = "MESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = savedInstanceState != null
                ? Dib.getMessagesFromJSON(savedInstanceState.getString(mKEY))
                : new Dib();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new MessageAdapter(chat.getMessageList());
        recyclerView.setAdapter(messageAdapter);

        Button sendButton = findViewById(R.id.send_button);
        EditText inputText = findViewById(R.id.input_text);

        sendButton.setOnClickListener(view -> {
            String response = "I don't understand that, here is what you can ask me...";
            String userInput = inputText.getText().toString().trim().toLowerCase();
            if (!userInput.isEmpty()) {
                addMessage(userInput);
                inputText.setText("");
                if (userInput.equals("hi") || userInput.equals("hello"))
                    response = "Hello there!";
                else if (userInput.contains("bring me home")) {
                    response = "The directions feature is currently under construction, try again another time.";
                }
                // Simulate a response after a delay
                String finalResponse = response;
                new Handler().postDelayed(() -> addMessage(finalResponse), 1000);
            }
        });

    }


    private void addMessage(String message) {
        chat.addMessage(message);
        messageAdapter.notifyItemInserted(chat.getMessageList().size() - 1);
        if (recyclerView != null && chat.getMessageList().size() > 1)
            recyclerView.scrollToPosition(chat.getMessageList().size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY, getJSONFromMessages(chat));
        //outState.putString(mKEY_GAME, getJSONFromGame(mGame));
        //outState.putBoolean(mKEY_AUTO_SAVE, mUseAutoSave);
    }
/*
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Retrieve the JSON string from the saved instance state
        String jsonMessages = savedInstanceState.getString(mKEY);

        // Convert JSON string back to Dib object
       // chat = getMessagesFromJSON(jsonMessages);

        // Notify the adapter of the data change
      //  messageAdapter.notifyDataSetChanged();

        // Scroll to the last message
        if (chat.getMessageList().size() > 1) {
            recyclerView.scrollToPosition(chat.getMessageList().size() - 1);
        }



    // mUseAutoSave = savedInstanceState.getBoolean(mKEY_AUTO_SAVE, true);
        //  updateUI();
    }

    UpdateUI();*/

 /*   private void updateUI() {
      try {
            mImageViewStones.setImageDrawable(ContextCompat.getDrawable(this, mImages[mGame.getStonesRemaining()]));
        } catch (ArrayIndexOutOfBoundsException e) {
            mSnackBar.setText(R.string.error_msg_could_not_update_image);
            mSnackBar.show();
        }
    }*/

}