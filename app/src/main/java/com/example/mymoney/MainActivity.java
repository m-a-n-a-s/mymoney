package com.example.mymoney;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String PREF_USER_MOBILE_PHONE = "pref_user_mobile_phone";
    private static final int SMS_PERMISSION_CODE = 0;
    private EditText mNumberEditText;
    private String mUserMobilePhone;
    private SharedPreferences mSharedPreferences;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private TextView mTextMessage;
    final Fragment fragment1 = new frag_home();
    final Fragment fragment2 = new frag_dash();
    final Fragment fragment3 = new frag_notifiactions();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    Fragment currentFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home");
                    //mTextMessage.setText("fuck you");
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;
                case R.id.navigation_dashboard:
                    setTitle("Dashboard");
                    //mTextMessage.setText("fuck you 2");
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;
                case R.id.navigation_notifications:
                    setTitle("Notifications");
                    //mTextMessage.setText("fuck you 3");
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Transactions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("limit", 50000);
        editor.apply();
        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_conditional_sms:
//                if (!hasValidPreConditions()) return;
//
////
////                SmsHelper.sendDebugSms(String.valueOf(mNumberEditText.getText()), SmsHelper.SMS_CONDITION + " This SMS is conditional, Hello toast");
////                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
////                break;
//                Cursor cursor = (Cursor) databaseHelper.getTransactions();
//
//                StringBuffer sb = new StringBuffer();
//                Integer[] months = new Integer[13];
//                for(int i=0;i<13;i++){
//                    months[i] = 0;
//                }
//                //month at 5 and 6
//                while(cursor.moveToNext()) {
//                    sb.append(cursor.getString(1) + "---> " + cursor.getString(2) + "\n");
//                    int month = Integer.parseInt(""+sb.charAt(5) + sb.charAt(6));
//                    months[month] += Integer.parseInt(cursor.getString(2));
//                    months[12] = Integer.parseInt(""+sb.charAt(0) + sb.charAt(1) + sb.charAt(2) + sb.charAt(3)) ;
//                }
//
//                //Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
//                ArrayList<String> arrayList = new ArrayList<String>();
//                for(int s:months) {
//                    arrayList.add(String.valueOf(s));
//                }
//
//                Toast.makeText(getApplicationContext(), Arrays.toString(months), Toast.LENGTH_SHORT).show();
//
//                break;
//
//
//            case R.id.btn_normal_sms:
//                if (!hasValidPreConditions()) return;
//
//                //SmsHelper.sendDebugSms(String.valueOf(mNumberEditText.getText()), "The broadcast should not show a toast for this");
//                Toast.makeText(getApplicationContext(), "Nikal **** pehli fursat se", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

    /**
     * Validates if the app has readSmsPermissions and the mobile phone is valid
     *
     * @return boolean validation value
     */
    private boolean hasValidPreConditions() {
        if (!hasReadSmsPermission()) {
            requestReadAndSendSmsPermission();
            return false;
        }

        return true;
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }

}
