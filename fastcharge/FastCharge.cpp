/*
 * Copyright (C) 2023 The LineageOS Project
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

#define LOG_TAG "fastcharge@1.0-service.venus"

#include <android-base/logging.h>
#include <android-base/properties.h>
#include <cstdlib>
#include "FastCharge.h"

namespace vendor {
namespace lineage {
namespace fastcharge {
namespace V1_0 {
namespace implementation {

static constexpr const char *kFastChargingProp = "persist.vendor.sec.fastchg_enabled";
static constexpr bool FASTCHARGE_DEFAULT_SETTING = true;

FastCharge::FastCharge() {
    android::base::SetProperty(kFastChargingProp, "true");

    system("start batterysecret");
}

android::hardware::Return<bool> FastCharge::isEnabled() {
    return android::hardware::Return<bool>(android::base::GetBoolProperty(kFastChargingProp, false));
}

android::hardware::Return<bool> FastCharge::setEnabled(bool enable) {
    android::base::SetProperty(kFastChargingProp, enable ? "true" : "false");

    if (enable) {
        system("start batterysecret");
    } else {
        system("stop batterysecret");
    }

    return android::hardware::Return<bool>(enable);
}

} // namespace implementation
} // namespace V1_0
} // namespace fastcharge
} // namespace lineage
} // namespace vendor
