package com.example.dibapp.activities;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dibapp.R;
import com.example.dibapp.classes.HelpAdapter;
import com.example.dibapp.model.HelpItem;

import java.util.ArrayList;
/*

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
*/

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.rv_help_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HelpItem> helpItemList = getHelpItemList();
        HelpAdapter helpAdapter = new HelpAdapter(helpItemList);
        recyclerView.setAdapter(helpAdapter);
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
