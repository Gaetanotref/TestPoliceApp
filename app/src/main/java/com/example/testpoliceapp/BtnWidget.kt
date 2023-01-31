package com.example.testpoliceapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.widget.RemoteViews
/**
 * Implementation of App Widget functionality.
 */
class BtnWidget : AppWidgetProvider() {
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

        @SuppressLint("QueryPermissionsNeeded")
        override fun onReceive(context: Context, intent: Intent) {
            super.onReceive(context, intent)
            if (intent.action == "com.example.testpoliceapp.CALL") {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                callIntent.data = Uri.parse("tel:3452343065")
                if (callIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(callIntent)
                }
            }
        }
    }


@SuppressLint("UseCompatLoadingForDrawables", "RemoteViewLayout", "UnspecifiedImmutableFlag")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.btn_widget)
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    callIntent.data = Uri.parse("tel:3452343065")
    val pendingIntent = PendingIntent.getActivity(context, 0, callIntent,0)
    views.setOnClickPendingIntent(R.id.redbtn, pendingIntent)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}