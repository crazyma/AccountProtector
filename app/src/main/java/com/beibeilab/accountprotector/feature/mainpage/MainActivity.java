package com.beibeilab.accountprotector.feature.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity;

import static com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity.PARAM_EDIT;
import static com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity.PARAM_MODE;
import static com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity.PARAM_NEW;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToAddAccount();
            }
        });

        setupFragment();
    }

    private void setupFragment(){
        MainFragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }

    public void jumpToAddAccount(){
        Intent intent = new Intent(this, AddAccountActivity.class);
        intent.putExtra(PARAM_MODE, PARAM_NEW);
        startActivity(intent);
    }

    public void showFAB(){
        findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    public void hideFAB(){
        findViewById(R.id.fab).setVisibility(View.GONE);
    }
}
