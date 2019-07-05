package com.example.mymoney;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.mymoney.ApplicationClass.transaction_list;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    DatabaseHelper myDB;

    private static final String TAG = "SmsBroadcastReceiver";
    public static Integer amount = 10;
    public static final String PREFS_NAME = "MyApp_Settings";

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

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
                transaction_list.clear();
                frag_dash.transadapter.notifyDataSetChanged();

                //MainActivity.getInstance().notify(context);
                Log.d(TAG, "\n\n\nInside 1 if\n\n\n");

                Pattern regex = Pattern.compile("(?:RS\\.?|INR)\\s*(\\d+(?:[.,]\\d+)*)|(\\d+(?:[.,]\\d+)*)\\s*(?:RS\\.?|INR)");
                Matcher matcher = regex.matcher(smsBody);
                if(matcher.find()) {
                    Log.d(TAG, "\n\n\nInside 2nd if\n\n\n");

                    //amount = Integer.parseInt(matcher.group(1));
                    amount = Float.valueOf(matcher.group(1)).intValue();

//                    String monthString = new DateFormatSymbols().getMonths()[9];
////                    String date = cursor.getString(1).charAt(5)+cursor.getString(1).charAt(6)+" "+monthString;
//                    String date = "broud";

                    if (smsBody.contains("CREDITED")) {
                        Log.d(TAG, "credited");
                        //transaction_list.add(new Transaction("Paytm to Ambani","iejdie","jed","0"));
                    } else if (smsBody.contains("DEBITED")) {
                        amount = 0 - amount;
                        Log.d(TAG, "debited");
                        //transaction_list.add(new Transaction("Paytm to Ambani","iejdie","jed","0"));
                    }



                    Log.d(TAG, "SMS detected: From ");
                    Log.d(TAG, "Amount is : " + amount);

                    boolean isInserted = myDB.insertData(amount);
                    if(isInserted == true){
                        String value = settings.getString("key", "");


                        Toast.makeText(context,"Data Inserted",Toast.LENGTH_LONG).show();
                        frag_home.adapter.notifyItemChanged(0);
                    }
                    else

                        Toast.makeText(context,"Data Not Inserted",Toast.LENGTH_LONG).show();

                    Cursor cursor = (Cursor) myDB.ReverseDB();
                    StringBuffer sb = new StringBuffer();
                    while (cursor.moveToNext()) {
                        sb.append(cursor.getString(1) + "---> " + cursor.getString(2) + "\n");
                        int month = Integer.parseInt("" + sb.charAt(5) + sb.charAt(6));
                        String monthString = new DateFormatSymbols().getMonths()[month-1];
                        String date = cursor.getString(1).charAt(8)+""+cursor.getString(1).charAt(9)+" "+monthString;
                        String amount = cursor.getString(2);
                        String[] time = cursor.getString(1).split(" ");
                        int pm = 0;
                        if (Integer.parseInt(time[1].split(":")[0]) >= 12 )
                            pm = 1;
                        String df = "";


                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
                            final Date dateObj = sdf.parse(time[1]);
                            System.out.println(dateObj);
                            System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
                            df = new SimpleDateFormat("K:mm").format(dateObj);
                            Log.d("--------------", df);

                        } catch (final ParseException e) {
                            e.printStackTrace();
                        }

                        String time12hr = df + " " + ((pm == 1) ? "PM" : "AM");


                          if(amount.charAt(0) == '-') {
                              amount = amount.substring(1);
                              //transaction_list2 = new ArrayList<Transaction>();
                              //transaction_list.remove(0);
                              Log.d("dadad","cleared array");

                              transaction_list.add(new Transaction(date, time12hr, amount, "0"));
                          }
                            else
                                  transaction_list.add(new Transaction(date,time12hr,"0",amount));
                    }

                }
            }
        }
    }

}


