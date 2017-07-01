package com.beibeilab.accountprotector.feature.addaccount;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beibeilab.accountprotector.BR;
import com.beibeilab.accountprotector.R;
import com.beibeilab.accountprotector.feature.account.AccountUnit;
import com.beibeilab.accountprotector.room.AccountDatabase;
import com.beibeilab.accountprotector.room.AccountEntity;
import com.beibeilab.accountprotector.room.AccountEntityBuilder;
import com.beibeilab.accountprotector.util.Util;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by david on 2017/6/9.
 */

public class AccountViewModel extends BaseObservable implements Parcelable {

    public interface PasswordButtonClickListener {
        void onPasswordButtonClick(View view);
    }

    /*  member  */
    private String account, password, userName, email, remark, serviceName, oauth;
    private int color;

    public static final Creator<AccountViewModel> CREATOR = new Creator<AccountViewModel>() {
        @Override
        public AccountViewModel createFromParcel(Parcel in) {
            AccountViewModel accountViewModel = new AccountViewModel();
            accountViewModel.setAccount(in.readString());
            accountViewModel.setPassword(in.readString());
            accountViewModel.setUserName(in.readString());
            accountViewModel.setEmail(in.readString());
            accountViewModel.setRemark(in.readString());
            accountViewModel.setServiceName(in.readString());
            accountViewModel.setOauth(in.readString());
            accountViewModel.setColor(in.readInt());

            return accountViewModel;
        }

        @Override
        public AccountViewModel[] newArray(int size) {
            return new AccountViewModel[size];
        }
    };

    private AddAccountFragmentCallback callback;
    private boolean editable;

    public AccountViewModel() {
    }

    public AccountViewModel(AccountEntity accountEntity) {
        setByAccountEntity(accountEntity);
    }

    protected AccountViewModel(Parcel in) {
        account = in.readString();
        password = in.readString();
        userName = in.readString();
        email = in.readString();
        remark = in.readString();
        serviceName = in.readString();
        oauth = in.readString();
        color = in.readInt();
    }

    public void commitButtonClicked(final View view) {
        commitNewAccount(view.getContext());
    }

    public TextWatcher addServiceNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            serviceName = editable.toString();
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

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        notifyPropertyChanged(BR.color);
    }

    @Bindable
    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
        notifyPropertyChanged(BR.oauth);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void oauthButtonClicked(View view) {
        String newOauth;

        switch (view.getId()) {
            case R.id.image_google:
                newOauth = AccountUnit.OAUTH_GOOGLE;
                break;
            case R.id.image_facebook:
                newOauth = AccountUnit.OAUTH_FACEBOOK;
                break;
            case R.id.image_twitter:
                newOauth = AccountUnit.OAUTH_TWITTER;
                break;
            case R.id.image_github:
                newOauth = AccountUnit.OAUTH_GITHUB;
                break;
            default:
                newOauth = AccountUnit.OAUTH_GOOGLE;
        }

        if (oauth != null && oauth.equals(newOauth)) {
            setOauth(null);
        } else {
            setOauth(newOauth);
        }

    }

    @BindingAdapter("oauth_icon")
    public static void setOauthIcon(ImageView imageView, String oauth) {
        if (Util.validString(oauth)) {
            imageView.setVisibility(View.VISIBLE);
            switch (oauth) {
                case AccountUnit.OAUTH_GOOGLE:
                    imageView.setImageResource(R.drawable.icon_google);
                    break;
                case AccountUnit.OAUTH_FACEBOOK:
                    imageView.setImageResource(R.drawable.icon_facebook);
                    break;
                case AccountUnit.OAUTH_TWITTER:
                    imageView.setImageResource(R.drawable.icon_twitter);
                    break;
                case AccountUnit.OAUTH_GITHUB:
                    imageView.setImageResource(R.drawable.icon_github);
                    break;
                default:
            }
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter("oauth_icon_switch")
    public static void setOauthIconSwitch(ImageView imageView, String oauth) {
        if (Util.validString(oauth)) {
            switch (imageView.getId()) {
                case R.id.image_google:
                    imageView.setImageResource(R.drawable.icon_google_pale);
                    break;
                case R.id.image_facebook:
                    imageView.setImageResource(R.drawable.icon_facebook_pale);
                    break;
                case R.id.image_twitter:
                    imageView.setImageResource(R.drawable.icon_twitter_pale);
                    break;
                case R.id.image_github:
                    imageView.setImageResource(R.drawable.icon_github_pale);
                    break;
                default:
                    imageView.setImageResource(R.drawable.icon_google_pale);
            }

            switch (oauth) {
                case AccountUnit.OAUTH_GOOGLE:
                    if (imageView.getId() == R.id.image_google)
                        imageView.setImageResource(R.drawable.icon_google);
                    break;
                case AccountUnit.OAUTH_FACEBOOK:
                    if (imageView.getId() == R.id.image_facebook)
                        imageView.setImageResource(R.drawable.icon_facebook);
                    break;
                case AccountUnit.OAUTH_TWITTER:
                    if (imageView.getId() == R.id.image_twitter)
                        imageView.setImageResource(R.drawable.icon_twitter);
                    break;
                case AccountUnit.OAUTH_GITHUB:
                    if (imageView.getId() == R.id.image_github)
                        imageView.setImageResource(R.drawable.icon_github);
                    break;
                default:
            }
        } else {
            switch (imageView.getId()) {
                case R.id.image_google:
                    imageView.setImageResource(R.drawable.icon_google_pale);
                    break;
                case R.id.image_facebook:
                    imageView.setImageResource(R.drawable.icon_facebook_pale);
                    break;
                case R.id.image_twitter:
                    imageView.setImageResource(R.drawable.icon_twitter_pale);
                    break;
                case R.id.image_github:
                    imageView.setImageResource(R.drawable.icon_github_pale);
                    break;
                default:
                    imageView.setImageResource(R.drawable.icon_google_pale);
            }
        }
    }

    @BindingAdapter({"visible_editable", "visible_content"})
    public static void setVisibleByEditable(View view, boolean editable, String content) {
        if (!editable) {
            if (!Util.validString(content)) {
                view.setVisibility(View.GONE);
                return;
            }
        }

        view.setVisibility(View.VISIBLE);
    }

    @BindingAdapter({"visible_content"})
    public static void setVisibleByContent(View view, String content) {
        if (!Util.validString(content)) {
            view.setVisibility(View.GONE);
            return;
        }

        view.setVisibility(View.VISIBLE);
    }

    public void setCallback(AddAccountFragmentCallback callback) {
        this.callback = callback;
    }

    public void commitNewAccount(final Context context) {
        final AccountEntityBuilder builder = new AccountEntityBuilder();
        builder.setServiceName(serviceName);
        builder.setAccount(account);
        builder.setPassword(password);
        builder.setUserName(userName);
        builder.setEmail(email);
        builder.setRemark(remark);
        builder.setOauth(oauth);
        builder.setColor(color);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                AccountDatabase.getInstance(context)
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
                        if (callback != null)
                            callback.onInsertSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, R.string.warning_no_parameter, Toast.LENGTH_SHORT).show();
                        Timber.e(e.toString());
                    }
                });
    }

    private void setByAccountEntity(AccountEntity accountEntity) {
        serviceName = accountEntity.getServiceName();
        account = accountEntity.getAccount();
        password = accountEntity.getPassword();
        userName = accountEntity.getUserName();
        email = accountEntity.getEmail();
        remark = accountEntity.getRemark();
        color = accountEntity.getColor();
        oauth = accountEntity.getOauth();
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("service name: ");
        builder.append(serviceName);

        if (oauth != null) {
            builder.append(", oauth: ");
            builder.append(oauth);
        }
        if (account != null) {
            builder.append(", account: ");
            builder.append(account);
        }
        if (email != null) {
            builder.append(", email: ");
            builder.append(email);
        }
        if (password != null) {
            builder.append(", password: ");
            builder.append(password);
        }
        if (userName != null) {
            builder.append(", userName: ");
            builder.append(userName);
        }
        if (remark != null) {
            builder.append(", remark: ");
            builder.append(remark);
        }
        builder.append(", color: ");
        builder.append(color);

        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(account);
        parcel.writeString(password);
        parcel.writeString(userName);
        parcel.writeString(email);
        parcel.writeString(remark);
        parcel.writeString(serviceName);
        parcel.writeString(oauth);
        parcel.writeInt(color);
    }
}
