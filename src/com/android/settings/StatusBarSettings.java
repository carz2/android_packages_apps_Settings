/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;


public class StatusBarSettings extends SettingsPreferenceFragment implements 
        Preference.OnPreferenceChangeListener {

    private static final String STATUS_BAR_AM_PM = "status_bar_am_pm";

    private static final String STATUS_BAR_BATTERY = "status_bar_battery";

    private static final String STATUS_BAR_CLOCK = "status_bar_show_clock";

    private static final String STATUS_BAR_SIGNAL = "status_bar_signal";

    private static final String STATUS_BAR_NOTIF_COUNT = "status_bar_notif_count";

    private ListPreference mStatusBarAmPm;

    private ListPreference mStatusBarBattery;

    private ListPreference mStatusBarCmSignal;

    private CheckBoxPreference mStatusBarClock;

    private CheckBoxPreference mStatusBarNotifCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.status_bar);

        PreferenceScreen prefSet = getPreferenceScreen();

        mStatusBarClock = (CheckBoxPreference) prefSet.findPreference(STATUS_BAR_CLOCK);
        mStatusBarAmPm = (ListPreference) prefSet.findPreference(STATUS_BAR_AM_PM);
        mStatusBarBattery = (ListPreference) prefSet.findPreference(STATUS_BAR_BATTERY);
        mStatusBarCmSignal = (ListPreference) prefSet.findPreference(STATUS_BAR_SIGNAL);


        mStatusBarClock.setChecked((Settings.System.getInt(getActivity().getApplicationContext()
                .getContentResolver(),
        		Settings.System.STATUS_BAR_CLOCK, 1) == 1));

        int statusBarAmPm = Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
        	Settings.System.STATUS_BAR_AM_PM, 2);
        mStatusBarAmPm.setValue(String.valueOf(statusBarAmPm));
        mStatusBarAmPm.setOnPreferenceChangeListener(this);

        int statusBarBattery = Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
        	Settings.System.STATUS_BAR_BATTERY, 0);
        mStatusBarBattery.setValue(String.valueOf(statusBarBattery));
        mStatusBarBattery.setOnPreferenceChangeListener(this);

        int signalStyle = Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
        	Settings.System.STATUS_BAR_SIGNAL_TEXT, 0);
        mStatusBarCmSignal.setValue(String.valueOf(signalStyle));
        mStatusBarCmSignal.setOnPreferenceChangeListener(this);

        mStatusBarNotifCount = (CheckBoxPreference) prefSet.findPreference(STATUS_BAR_NOTIF_COUNT);
        mStatusBarNotifCount.setChecked((Settings.System.getInt(getActivity().getApplicationContext().getContentResolver(),
                Settings.System.STATUS_BAR_NOTIF_COUNT, 0) == 1));
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mStatusBarAmPm) {
            int statusBarAmPm = Integer.valueOf((String) newValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(), Settings.System.STATUS_BAR_AM_PM, statusBarAmPm);
            return true;
        } else if (preference == mStatusBarBattery) {
            int statusBarBattery = Integer.valueOf((String) newValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(), Settings.System.STATUS_BAR_BATTERY, statusBarBattery);
            return true;
        } else if (preference == mStatusBarCmSignal) {
            int signalStyle = Integer.valueOf((String) newValue);
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(), Settings.System.STATUS_BAR_SIGNAL_TEXT, signalStyle);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;

        if (preference == mStatusBarClock) {
            value = mStatusBarClock.isChecked();
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_CLOCK, value ? 1 : 0);
            return true;
        } else if (preference == mStatusBarNotifCount) {
            value = mStatusBarNotifCount.isChecked();
            Settings.System.putInt(getActivity().getApplicationContext().getContentResolver(),
                    Settings.System.STATUS_BAR_NOTIF_COUNT, value ? 1 : 0);
            return true;
        }
        return false;
    }
}
