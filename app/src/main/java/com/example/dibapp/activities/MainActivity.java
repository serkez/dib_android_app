package com.example.dibapp.activities;

import static com.example.dibapp.model.Dib.getJSONFromMessages;
import static com.example.dibapp.model.Dib.getMessagesFromJSON;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;

import com.example.dibapp.R;
import com.example.dibapp.classes.MessageAdapter;
import com.example.dibapp.classes.Utils;
import com.example.dibapp.databinding.ActivityMainBinding;
import com.example.dibapp.model.Dib;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Dib chat;

    private MessageAdapter messageAdapter;
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;

    private boolean mIsNightMode;

    private final String mKEY = "MESS";

    private final String mKeyPrefsName = "PREFS";
    private boolean mPrefUseAutoSave;
    private String mKeyAutoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat = savedInstanceState != null
                ? Dib.getMessagesFromJSON(savedInstanceState.getString(mKEY))
                : new Dib();

        setContentView(R.layout.activity_main);
        setIsNightMode();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFieldReferencesToResFileValues();
        restoreAppSettingsFromPrefs();
       // setupScreen(); move stuff into method


        recyclerView = findViewById(R.id.rv_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new MessageAdapter(mIsNightMode, chat.getMessageList());
        recyclerView.setAdapter(messageAdapter);

        doInitialStart(savedInstanceState);

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

    @Override
    protected void onPause() {
        super.onPause();
        saveMessagesToSharedPrefs();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveMessagesToSharedPrefs();
    }

    private void saveMessagesToSharedPrefs() {
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(mKEY, getJSONFromMessages(chat));
        editor.apply();
    }

    private void restoreSavedMessagesFromPrefs() {
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        String savedMessagesJSON = preferences.getString(mKEY, "");
        if (!savedMessagesJSON.isEmpty()) {
            chat = getMessagesFromJSON(savedMessagesJSON);
            messageAdapter.notifyDataSetChanged();
        }
    }
    private void doInitialStart(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            chat = Dib.getMessagesFromJSON(savedInstanceState.getString(mKEY));
        } else if (mPrefUseAutoSave) {
            restoreSavedMessagesFromPrefs();
        } else {
            chat = new Dib();
        }

        // Update RecyclerView
        messageAdapter = new MessageAdapter(mIsNightMode, chat.getMessageList());
        recyclerView.setAdapter(messageAdapter);
    }


    private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
        mPrefUseAutoSave = item.isChecked();
        saveAutoSavePreference();
    }

    private void saveAutoSavePreference() {
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(mKeyAutoSave, mPrefUseAutoSave);
        editor.apply();
    }


    private void setupScreen() {
    }

    private void setFieldReferencesToResFileValues() {
        // These values are the same strings used in the prefs xml as keys for each pref there
        mKeyAutoSave = getString(R.string.key_use_auto_save);
    }

        private void restoreAppSettingsFromPrefs() {
        // Since this is for reading only, no editor is needed unlike in saveRestoreState
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);

        // restore AutoSave preference value
        mPrefUseAutoSave = preferences.getBoolean(mKeyAutoSave, true);
    }

    private void saveSettingsToSharedPrefs(SharedPreferences.Editor editor) {
        // save "autoSave" preference
        editor.putBoolean(mKeyAutoSave, mPrefUseAutoSave);
    }

    private void saveMessagesToSharedPrefsIfAutoSaveIsOn(SharedPreferences.Editor editor, ArrayList<String> messages) {
        if (mPrefUseAutoSave) {
            editor.putString(mKEY, getJSONFromMessages(chat));
        }
        else {
            editor.remove(mKEY);
        }
    }




    private void addMessage(String message) {
        chat.addMessage(message);
        messageAdapter.notifyItemInserted(chat.getMessageList().size() - 1);
        if (recyclerView != null && chat.getMessageList().size() > 1) //not working
            recyclerView.scrollToPosition(chat.getMessageList().size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_toggle_auto_save).setChecked(mPrefUseAutoSave);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        } else if (itemId == R.id.action_about) {
            showAbout();
            return true;
        } else if (itemId == R.id.action_toggle_auto_save) {
            toggleMenuItem(item);
            mPrefUseAutoSave = item.isChecked();
            return true;
        } else if (itemId == R.id.action_discard) {
            discardMessages();
            return true;
        } else// If the user clicked on some unknown menu item, then the super... will handle that
            return super.onOptionsItemSelected(item);
    }

    private void discardMessages() {
        chat.resetMessages();
        messageAdapter.notifyDataSetChanged();

        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

 /*   private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
    }*/

    private void showAbout() {
        Utils.showInfoDialog(this, R.string.app_name, R.string.about_message);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY, getJSONFromMessages(chat));
        //outState.putString(mKEY_GAME, getJSONFromGame(mGame));
        //outState.putBoolean(mKEY_AUTO_SAVE, mUseAutoSave);
    }


    private void setIsNightMode() {
        mIsNightMode = (getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
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