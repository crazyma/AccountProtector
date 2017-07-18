package com.beibeilab.accountprotector.feature.password;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckedTextView;

import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.util.PasswordGenerator;

/**
 * Created by david on 2017/6/13.
 */

public class PasswordGenerateViewModel {

    public interface PGViewModelListener {
        void viewModelCallback(String password);
    }

    public final ObservableBoolean isNextStep = new ObservableBoolean();
    private String length;
    private boolean[] ruleArray;
    private PGViewModelListener listener;
    private PasswordGenerator generator;
    final public ObservableField<String> password = new ObservableField<>();

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

    public void commitButtonClicked(View view) {
        if(!isNextStep.get()){
            isNextStep.set(true);
            changeLayout(view);
            generatePassword();
        }else{
            if (listener != null) {
                listener.viewModelCallback(password.get());
            }
        }
    }

    public void cancelButtonClicked(View view) {
        listener.viewModelCallback(null);
    }

    public void reGenerateClicked(View view){
        generatePassword();
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

    private void changeLayout(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view.getParent();
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(view.getId(), ConstraintSet.TOP, R.id.image_refresh, ConstraintSet.BOTTOM, 16);
        constraintSet.applyTo(constraintLayout);
    }

    private void generatePassword(){
        if(generator == null)
            generator = new PasswordGenerator(Integer.valueOf(length), ruleArray);
        password.set(generator.generate());
    }
}
