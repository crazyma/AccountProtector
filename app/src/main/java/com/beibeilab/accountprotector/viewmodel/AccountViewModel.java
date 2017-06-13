package com.beibeilab.accountprotector.viewmodel;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.beibeilab.accountprotector.PasswordGenerateFragment;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntityBuilder;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by david on 2017/6/9.
 */

public class AccountViewModel {

    private String account, password, userName, email, remark, oauth;

    public View.OnClickListener commitButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

            final AccountEntityBuilder builder = new AccountEntityBuilder();
            builder.setAccount(account);
            builder.setPassword(password);
            builder.setUserName(userName);
            builder.setEmail(email);
            builder.setRemark(remark);
            builder.setOauth(oauth);

            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    AccountDatabase.getInstance(view.getContext())
                            .getAccountDao()
                            .insert(builder.build());
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    Timber.d("insert completed");
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e(e.toString());
                }
            });



        }
    };

    public View.OnClickListener generatePasswordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentActivity activity = (FragmentActivity)view.getContext();
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = PasswordGenerateFragment.newInstance();
            newFragment.show(ft, "dialog");
        }
    };

    public TextWatcher addAccountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            account = editable.toString();
        }
    };

    public TextWatcher addPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            password = editable.toString();
        }
    };

    public TextWatcher addUserNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            userName = editable.toString();
        }
    };

    public TextWatcher addEmailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            email = editable.toString();
        }
    };

    public TextWatcher addRemarkTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            remark = editable.toString();
        }
    };

}
