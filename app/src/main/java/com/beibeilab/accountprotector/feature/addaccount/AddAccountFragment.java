package com.beibeilab.accountprotector.feature.addaccount;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.beibeilab.accountprotector.feature.password.PasswordGenerateFragment;
import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.databinding.AddAccountBinding;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

import static com.beibeilab.accountprotector.feature.addaccount.AddAccountActivity.PARAM_ACCOUNT_VIEW_MODEL;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends Fragment implements
        PasswordGenerateFragment.PasswordGenerateListener, AddAccountFragmentCallback,
        AccountViewModel.PasswordButtonClickListener,
        ColorPickerSwatch.OnColorSelectedListener {

    private AddAccountBinding mBinding;
    private AccountViewModel accountViewModel;

    private AccountDatabase accountDatabase;

    public AddAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        accountViewModel = getActivity().getIntent().getParcelableExtra(PARAM_ACCOUNT_VIEW_MODEL);

        if(accountViewModel == null) {
            accountViewModel = new AccountViewModel();
            accountViewModel.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        accountViewModel.setEditable(true);
        accountViewModel.setCallback(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_account, container, false);
        mBinding.setAccountViewModel(accountViewModel);
        mBinding.setPasswordClickListener(this);
        mBinding.setCallback(this);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountDatabase = AccountDatabase.getInstance(getContext());

        accountDatabase.getAccountDao().getAllFlowable()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<AccountEntity>, Publisher<AccountEntity>>() {
                    @Override
                    public Publisher<AccountEntity> apply(List<AccountEntity> accountEntities) throws Exception {
                        return Flowable.fromIterable(accountEntities);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<AccountEntity>() {
                    @Override
                    public void onNext(AccountEntity accountEntity) {
                        Timber.d("result : " + accountEntity);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_account, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save_account) {
            accountViewModel.commitNewAccount(getContext());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPasswordGenerate(String password) {
        accountViewModel.setPassword(password);
    }

    @Override
    public void onInsertSuccessfully() {
        getActivity().finish();
    }

    @Override
    public void onPickerButtonClicked(View view) {

        int[] colors = getContext().getResources().getIntArray(R.array.androidcolors);
        int selectedColor = accountViewModel.getColor();

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.initialize(
                R.string.dialog_color_picker_title,
                colors,
                selectedColor,
                4, colors.length);
        colorPickerDialog.show(getFragmentManager(), "color_picker");
        colorPickerDialog.setOnColorSelectedListener(this);
    }

    @Override
    public void onPasswordButtonClick(View view) {
        FragmentActivity activity = getActivity();
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = PasswordGenerateFragment.newInstance();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onColorSelected(int color) {
        accountViewModel.setColor(color);
    }
}
