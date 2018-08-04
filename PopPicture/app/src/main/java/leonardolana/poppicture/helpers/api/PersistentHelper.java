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

    public void setString(String key, String value);

    public String getString(String key, String defaultValue);

    public void setInt(String key, int value);

    public int getInt(String key, int defaultValue);

    public void setLong(String key, long value);

    public long getLong(String key, long defaultValue);

    public void setFloat(String key, float value);

    public float getFloat(String key, int defaultValue);

    public void setBoolean(String key, boolean value);

    public boolean getBoolean(String key, boolean defaultValue);

}
