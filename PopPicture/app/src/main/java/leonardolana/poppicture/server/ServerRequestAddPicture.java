package leonardolana.poppicture.server;

import android.support.annotation.NonNull;

import leonardolana.poppicture.helpers.api.RunnableExecutor;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;

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
public class ServerRequestAddPicture extends ServerRequest {

    public ServerRequestAddPicture(String filename, String title, String description, double latitude, double longitude) {
        super(ServerConstants.URL, "Picture.add");
        addParam(KEY_FILENAME, filename);
        addParam(KEY_TITLE, title);
        addParam(KEY_DESCRIPTION, description);
        addParam(KEY_LATITUDE, latitude);
        addParam(KEY_LONGITUDE, longitude);
    }

    @Override
    public void execute(@NonNull RunnableExecutor runnableExecutor, @NonNull ServerHelper serverHelper, @NonNull UserHelper userHelper, @NonNull RequestResponse callback) {
        super.execute(runnableExecutor, serverHelper, userHelper, callback);
    }
}
