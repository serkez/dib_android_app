package com.example.dibapp.activities;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dibapp.R;
import com.example.dibapp.classes.HelpAdapter;
import com.example.dibapp.classes.Utils;
import com.example.dibapp.model.HelpItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import androidx.annotation.Nullable;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    // Help Activity is where all the possible things to text the chat is displayed
    // It also contains a back button and a FAB for viewing about section (2nd way to view it)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setupToolbar();
        setupView();
        setupFAB();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.rv_help_item);
        final int RV_COLUMN_COUNT = getResources().getInteger(R.integer.rv_help_columns);
        recyclerView.setLayoutManager(new GridLayoutManager(this, RV_COLUMN_COUNT));

        List<HelpItem> helpItemList = getHelpItemList();
        HelpAdapter helpAdapter = new HelpAdapter(helpItemList);
        recyclerView.setAdapter(helpAdapter);
    }

    private void setupFAB() {
        FloatingActionButton fabAbout = findViewById(R.id.fab_about);
        fabAbout.setOnClickListener(view -> handleFABClick());
    }

    private void handleFABClick() {
        Utils.showInfoDialog(this, R.string.app_name, R.string.about_message);
    }

    private List<HelpItem> getHelpItemList() {
        String[] helpItemsArray = getResources().getStringArray(R.array.helpItemList);
        List<HelpItem> helpItemList = new ArrayList<>();

        for (String item : helpItemsArray) {
            String[] parts = item.split("\\|");
            if (parts.length == 2) {
                helpItemList.add(new HelpItem(parts[0], parts[1]));
            }
        }

        return helpItemList;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
