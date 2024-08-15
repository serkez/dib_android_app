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

// Main Activity. Here is where all our COntroller logic is

public class MainActivity extends AppCompatActivity {
    private Dib chat;

    private MessageAdapter messageAdapter;
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
        setupToolbar();
        setFieldReferencesToResFileValues();
        restoreAppSettingsFromPrefs();
        setupView();

        doInitialStart(savedInstanceState); //we redundantly create new dib. should only check for sharedPref?

        setupSendButton();
        scrollToLastMessage();
    }

    //we need to keep track of night mode so we can change text color's based on night mode
    private void setIsNightMode() {
        mIsNightMode = (getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setFieldReferencesToResFileValues() {
        // These values are the same strings used in the prefs xml as keys for each pref there
        mKeyAutoSave = getString(R.string.key_use_auto_save);
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

    private void setupView() {
        recyclerView = findViewById(R.id.rv_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new MessageAdapter(mIsNightMode, chat.getMessageList());
        recyclerView.setAdapter(messageAdapter);
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

    private void setupSendButton() {
        Button sendButton = findViewById(R.id.send_button);
        EditText inputText = findViewById(R.id.input_text);

        sendButton.setOnClickListener(view -> {
            handleSendButton(inputText);
        });
    }

    //adds message from textBox, retreives response from model, and adds that as well
    private void handleSendButton(EditText inputText) {
        String userInput = inputText.getText().toString().trim().toLowerCase();
        if (!userInput.isEmpty()) {
            addMessage(userInput);
            inputText.setText("");
            String response = chat.getResponse(userInput); // response logic is handled by model
            new Handler().postDelayed(() -> addMessage(response), 1000);
        }
    }

    //adds message to message list in model and scrolls to current message
    private void addMessage(String message) {
        chat.addMessageToList(message);
        messageAdapter.notifyItemInserted(chat.getMessageList().size() - 1);
        if (recyclerView != null && chat.getMessageList().size() > 1) //not working
            recyclerView.scrollToPosition(chat.getMessageList().size() - 1);
    }

    private void scrollToLastMessage() {
        if (!chat.getMessageList().isEmpty()) {
            recyclerView.post(() -> recyclerView.scrollToPosition(chat.getMessageList().size() - 1));
        }
    }

    private void saveAutoSavePreference() {
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(mKeyAutoSave, mPrefUseAutoSave);
        editor.apply();
    }

    private void restoreAppSettingsFromPrefs() {
        SharedPreferences preferences = getSharedPreferences(mKeyPrefsName, MODE_PRIVATE);
        // restore AutoSave preference value
        mPrefUseAutoSave = preferences.getBoolean(mKeyAutoSave, true);
    }

    private void showAbout() {
        Utils.showInfoDialog(this, R.string.app_name, R.string.about_message);
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
        } else// If the user clicked on some unknown menu item, then the super will handle that
            return super.onOptionsItemSelected(item);
    }

    private void toggleMenuItem(MenuItem item) {
        item.setChecked(!item.isChecked());
        mPrefUseAutoSave = item.isChecked();
        saveAutoSavePreference();
    }

    //deletes all messages
    private void discardMessages() {
        chat.resetMessages();
        messageAdapter.notifyDataSetChanged();

        if (recyclerView != null) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(mKEY, getJSONFromMessages(chat));
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

}