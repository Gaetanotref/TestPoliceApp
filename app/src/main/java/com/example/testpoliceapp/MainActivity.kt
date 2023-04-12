package com.example.testpoliceapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.testpoliceapp.databinding.ActivityMainBinding
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
lateinit var binding: ActivityMainBinding
lateinit var numberSms1:String
lateinit var numberSms2:String
lateinit var numberSms3:String
var textSms:String = ""
var isPoliceCallEnabled by Delegates.notNull<Boolean>()



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //BINDING ON VIEW
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRedButton.setOnClickListener {
            permissionSms(numberSms1, textSms)
            permissionSms(numberSms2, textSms)
            permissionSms(numberSms3, textSms)

            if (isPoliceCallEnabled){
                permissionCall()
            }

        }
        binding.btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsNumberActivity::class.java)
            startActivity(intent)
        }
    }

    //FUN FOR TAKE VARIABLES FROM SETTINGS ACTIVITY

    override fun onResume() {
        super.onResume()
        mySettings()
    }

    fun mySettings() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val number1 = prefs.getString("number1", "")
        val number2 = prefs.getString("number2", "")
        val number3 = prefs.getString("number3", "")
        val enablePoliceCall = prefs.getBoolean("enablePoliceCall", true)


        val text = prefs.getString("text", "")
        binding.apply {
            /*if (Build.VERSION.SDK_INT < 30) {
                numberSms1 = number1.toString()
                numberSms2 = number2.toString()
                numberSms3 = number3.toString()
                textSms = text.toString()
                isPoliceCallEnabled = enablePoliceCall
            } else {*/
                numberSms1 = "sms:$number1"
                numberSms2 = "sms:$number2"
                numberSms3 = "sms:$number3"
                textSms = text.toString()
                isPoliceCallEnabled = enablePoliceCall
            //}
        }
    }


    //FUN FOR ACTION
    private fun sendText(number:String,text:String) {
        val phoneNumber = "sms:$number"
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, text, null, null)
    }

    private fun makePhoneCall() {
        val phoneNumber = "tel:" + "112" // specifica il numero di telefono
        val dial = Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber))
        startActivity(dial)
    }

    companion object {
        private const val REQUEST_CALL = 1
    }

//FUN FOR PERMISSION
    private fun permissionSms(number: String,text: String) {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), REQUEST_CALL)
        } else {
            sendText(number,text)
        }
    }
    private fun permissionCall() {
        run {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL
                )
            } else {
                // permission already granted
                makePhoneCall()
            }
        }
    }
}
