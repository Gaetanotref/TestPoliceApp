package com.example.testpoliceapp

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            //NUMBER
            val numberPreference1: EditTextPreference? = findPreference("number1")
            val numberPreference2: EditTextPreference? = findPreference("number2")
            val numberPreference3: EditTextPreference? = findPreference("number3")
            //SET ONLY NUM VALUE
            typeOf<EditTextPreference>(numberPreference1)
            typeOf<EditTextPreference>(numberPreference2)
            typeOf<EditTextPreference>(numberPreference3)

        }
        //FUN FOR NUM ONLY
        private fun <T> typeOf(number: EditTextPreference?) {
            number?.setOnBindEditTextListener { editText ->
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }


}