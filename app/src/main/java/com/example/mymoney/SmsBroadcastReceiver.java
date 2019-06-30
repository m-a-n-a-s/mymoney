package com.example.mymoney;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    DatabaseHelper myDB;

    private static final String TAG = "SmsBroadcastReceiver";
    public static Integer amount = 10;

    @Override
    public void onReceive(Context context, Intent intent) {

        myDB = new DatabaseHelper(context);

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        {
            String smsSender = "";
            String smsBody = "";

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
                smsSender = smsMessage.getOriginatingAddress();
            }

            Log.d(TAG, "Sms with condition detected");
            Toast.makeText(context, "BroadcastReceiver caught conditional SMS: " + smsBody, Toast.LENGTH_LONG).show();
            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);

            smsBody = smsBody.toUpperCase();

//            if(!(smsSender.charAt(0) =='+') && !smsSender.matches("[0-9]+"))
            if(true)
            {
                Log.d(TAG, "\n\n\nInside 1 if\n\n\n");

                Pattern regex = Pattern.compile("(?:RS\\.?|INR)\\s*(\\d+(?:[.,]\\d+)*)|(\\d+(?:[.,]\\d+)*)\\s*(?:RS\\.?|INR)");
                Matcher matcher = regex.matcher(smsBody);
                if(matcher.find()) {
                    Log.d(TAG, "\n\n\nInside 2nd if\n\n\n");

                    //amount = Integer.parseInt(matcher.group(1));
                    amount = Float.valueOf(matcher.group(1)).intValue();

                    if (smsBody.contains("CREDITED")) {
                        Log.d(TAG, "credited");
                    } else if (smsBody.contains("DEBITED")) {
                        Log.d(TAG, "debited");
                    }

                    Log.d(TAG, "SMS detected: From ");
                    Log.d(TAG, "Amount is : " + amount);

                    boolean isInserted = myDB.insertData(amount);
                    if(isInserted == true){

                        Toast.makeText(context,"Data Inserted",Toast.LENGTH_LONG).show();
                    }
                    else

                        Toast.makeText(context,"Data Not Inserted",Toast.LENGTH_LONG).show();

                }

            }
        }
    }
}