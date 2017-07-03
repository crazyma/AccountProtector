package com.beibeilab.accountprotector.feature.addaccount;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.feature.mainpage.MainFragment;

public class AddAccountActivity extends AppCompatActivity {

    public static final String PARAM_ACCOUNT_VIEW_MODEL = "account_view_model";
    public static final String PARAM_MODE = "mode";
    public static final int PARAM_NEW = 11;
    public static final int PARAM_EDIT = 12;
    public static final int REQUEST_CODE_EDIT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupFragment(getIntent().getIntExtra(PARAM_MODE, PARAM_NEW));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupFragment(int mode) {
        AddAccountFragment fragment;
        if (mode == PARAM_NEW) {
            fragment = new AddAccountFragment();
        } else {
            fragment = new EditAccountFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }
}
