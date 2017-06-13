package com.beibeilab.accountprotector.viewmodel;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckedTextView;

import com.beibeilab.accountprotector.R;

import timber.log.Timber;

/**
 * Created by david on 2017/6/13.
 */

public class PasswordGenerateViewModel {

    public interface PGViewModelListener {
        void viewModelCallback(int length, boolean[] ruleArray);
    }

    private String length;
    private boolean[] ruleArray;
    private PGViewModelListener listener;

    public PasswordGenerateViewModel() {
        ruleArray = new boolean[4];
    }

    public void setListener(PGViewModelListener listener) {
        this.listener = listener;
    }

    public void checkedTextViewClickListener(View view) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        checkedTextView.toggle();
        switch (checkedTextView.getId()) {
            case R.id.checkedtext_upper_case:
                ruleArray[0] = checkedTextView.isChecked();
                break;
            case R.id.checkedtext_lower_case:
                ruleArray[1] = checkedTextView.isChecked();
                break;
            case R.id.checkedtext_number:
                ruleArray[2] = checkedTextView.isChecked();
                break;
            case R.id.checkedtext_other:
                ruleArray[3] = checkedTextView.isChecked();
                break;
        }
    }

    public void commitButtonClickListener(View view){
        Timber.d("length : " + length + " | " + ruleArray[0] + ", " + ruleArray[1] + ", " + ruleArray[2] + ", " + ruleArray[3]);
        if(listener != null){
            listener.viewModelCallback(Integer.valueOf(length), ruleArray);
        }
    }

    public TextWatcher lengthTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            length = editable.toString();
        }
    };
}
