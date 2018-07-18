package com.minami.android.logindemo;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView phone_tv;
    private TextView email_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone_tv = findViewById(R.id.phone_tv);
        email_tv = findViewById(R.id.email_tv);
//
//        if (AccessToken.getCurrentAccessToken() != null) {
//
//        }

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {

                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (account.getPhoneNumber() != null) {
                    String formattedPhoneNumber = formatPhoneNumber(phoneNumber.toString());
                    phone_tv.setText(formattedPhoneNumber);
                } else {
                    String email = account.getEmail();
                    email_tv.setText(email);
                }
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });

    }

    public void logoutButtonClicked(View view) {
        AccountKit.logOut();
        launchLoginActivity();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private String formatPhoneNumber(String phoneNumber) {
        try {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = phoneNumberUtil.parse(phoneNumber, Locale.getDefault().getCountry());
            phoneNumber = phoneNumberUtil.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException npe) {
            npe.printStackTrace();
        }
        return phoneNumber;
    }
}
