package leonardolana.poppicture.data;

/**
 * Created by Leonardo Lana
 * Github: https://github.com/leonardodlana
 * <p>
 * Copyright 2018 Leonardo Lana
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public enum TrackingEvent {

    ABOUT_CLICK_REVIEW("about_click_review"),
    ABOUT_CLICK_PRIVACY_POLICY("about_click_privacy_policy"),
    ABOUT_CLICK_TERMS_AND_CONDITIONS("about_click_terms_and_conditions"),
    ABOUT_CLICK_GITHUB("about_click_github"),
    EDITOR_MATURE_CONTENT("editor_mature_content"),
    PROFILE_ONBOARDING_CLICK_SIGN_IN("profile_onboarding_click_sign_in"),
    PROFILE_ONBOARDING_CLICK_DISMISS("profile_onboarding_click_dismiss"),
    LOGIN_START("login_start"),
    LOGIN_SUCCESSFUL("login_successful"),
    VIEWER_CLICK_LIKE("viewer_click_like"),
    VIEWER_CLICK_REPORT("viewer_click_report"),
    VIEWER_OPEN("viewer_open");

    private final String mName;

    TrackingEvent(String name) {
        mName = name;
    }

    public String getTrackingName() {
        return mName;
    }

}
