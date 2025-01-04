/*
 * Copyright (C) 2018 The LineageOS Project
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

package org.lineageos.settings.touchsampling;

import android.content.Context;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreferenceCompat;

import org.lineageos.settings.R;
import org.lineageos.settings.utils.FileUtils;

public class TouchSamplingSettingsFragment extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreferenceCompat mTouchSamplingPreference;
    private static final String HTSR_ENABLE_KEY = "htsr_enable";
    private static final String HTSR_FILE = "/sys/devices/virtual/touch/touch_dev/bump_sample_rate";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.htsr_settings);
        mTouchSamplingPreference = (SwitchPreferenceCompat) findPreference(HTSR_ENABLE_KEY);
        if (FileUtils.fileExists(HTSR_FILE)) {
            mTouchSamplingPreference.setEnabled(true);
            mTouchSamplingPreference.setOnPreferenceChangeListener(this);
        } else {
            mTouchSamplingPreference.setSummary(R.string.htsr_enable_summary_not_supported);
            mTouchSamplingPreference.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (HTSR_ENABLE_KEY.equals(preference.getKey())) {
            FileUtils.writeLine(HTSR_FILE, (Boolean) newValue ? "1" : "0");
        }
        return true;
    }
}
