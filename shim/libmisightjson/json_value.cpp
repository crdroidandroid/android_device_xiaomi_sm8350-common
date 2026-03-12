//
// SPDX-FileCopyrightText: The LineageOS Project
// SPDX-License-Identifier: Apache-2.0
//

#include <json/value.h>

extern "C" Json::Value* _ZN4Json5ValueC1Ex(Json::Value* thisptr, Json::Value other) {
    *thisptr = other;
    return thisptr;
}
