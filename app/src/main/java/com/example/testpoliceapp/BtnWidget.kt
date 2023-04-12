package com.example.testpoliceapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.SmsManager
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

/**
 * Implementation of App Widget functionality.
 */
class BtnWidget : AppWidgetProvider() {
    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @SuppressLint("QueryPermissionsNeeded", "UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "com.example.testpoliceapp.SEND_SMS") {
            // Invia un messaggio SMS
            sendSms(context)
        }

        if (intent.action == "com.example.testpoliceapp.SEND_SMS") {
            //CALL
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            callIntent.data = Uri.parse("tel:3452343065")
            if (callIntent.resolveActivity(context.packageManager) != null && isPoliceCallEnabled) {
                context.startActivity(callIntent)
            }
        }

    }
}


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UseCompatLoadingForDrawables", "RemoteViewLayout", "UnspecifiedImmutableFlag")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.btn_widget)
    //CALL
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    callIntent.data = Uri.parse("tel:3452343065")
    if (isPoliceCallEnabled) {
        val pendingIntent = PendingIntent.getActivity(context, 0, callIntent, FLAG_MUTABLE)
        views.setOnClickPendingIntent(R.id.redbtn, pendingIntent)
    }
    //SMS
    val smsIntent = Intent(context, BtnWidget::class.java)
    smsIntent.action = "com.example.testpoliceapp.SEND_SMS"
    smsIntent.putExtra("sms1", numberSms1)
    smsIntent.putExtra("sms2", numberSms2)
    smsIntent.putExtra("sms3", numberSms3)
    smsIntent.putExtra("sms_body", textSms)
    val pendingIntentSms = PendingIntent.getBroadcast(context, 0, smsIntent, FLAG_MUTABLE)
    views.setOnClickPendingIntent(R.id.redbtn, pendingIntentSms)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun sendSms(context: Context) {
    val smsManager: SmsManager = if (Build.VERSION.SDK_INT > 29) {
        context.getSystemService(SmsManager::class.java)
    } else {
        SmsManager.getDefault()
    }
    val message = textSms
    val numbers = arrayOf(numberSms1, numberSms2, numberSms3)
    for (number in numbers) {
        smsManager.sendTextMessage(number, null, message, null, null)
    }
}

