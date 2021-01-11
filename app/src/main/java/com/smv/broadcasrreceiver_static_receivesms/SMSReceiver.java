package com.smv.broadcasrreceiver_static_receivesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        String incomingNumber = "";
        String incomingMsg = "";
        //"android.provider.Telephony.SMS_RECEIVED" JE POTREBNO NATIPKATI, sistem ga ne predlaga
        if (action.equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            //Toast.makeText(context, "Nov SMS", Toast.LENGTH_SHORT).show();
            Object[] pdus = (Object[]) extras.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMessage.length; i++)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    String format = extras.getString("format");
                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }
                else
                {
                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                incomingNumber = smsMessage[i].getOriginatingAddress();
                incomingMsg = smsMessage[i].getMessageBody();
            }
            Toast.makeText(context, "Sporočilo od " + incomingNumber + ":\n\n" + incomingMsg, Toast.LENGTH_LONG).show();
        }
    }

}