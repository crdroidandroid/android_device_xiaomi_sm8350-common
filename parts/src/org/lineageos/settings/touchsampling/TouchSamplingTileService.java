/*
 * Copyright (C) 2024 The LineageOS Project
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

import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.utils.FileUtils;

public class TouchSamplingTileService extends TileService {

    private static final String HTSR_ENABLE_KEY = "htsr_enable";
    private static final String HTSR_FILE = "/sys/devices/virtual/touch/touch_dev/bump_sample_rate";

    private void updateUI(boolean enabled) {
        final Tile tile = getQsTile();
        if (FileUtils.fileExists(HTSR_FILE)) {
            tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        } else {
            tile.setState(Tile.STATE_UNAVAILABLE);
        }
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateUI(sharedPrefs.getBoolean(HTSR_ENABLE_KEY, false));
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean enabled = !(sharedPrefs.getBoolean(HTSR_ENABLE_KEY, false));
        FileUtils.writeLine(HTSR_FILE, enabled ? "1" : "0");
        sharedPrefs.edit().putBoolean(HTSR_ENABLE_KEY, enabled).commit();
        updateUI(enabled);
    }
}
