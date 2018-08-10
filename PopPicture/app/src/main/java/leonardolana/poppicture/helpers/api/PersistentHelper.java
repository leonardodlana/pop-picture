package leonardolana.poppicture.helpers.api;

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

public interface PersistentHelper {

    void setString(String key, String value);

    String getString(String key, String defaultValue);

    void setInt(String key, int value);

    int getInt(String key, int defaultValue);

    void setLong(String key, long value);

    long getLong(String key, long defaultValue);

    void setFloat(String key, float value);

    float getFloat(String key, int defaultValue);

    void setBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);

    void setDouble(String key, double value);

    double getDouble(String key, double defaultValue);

}
