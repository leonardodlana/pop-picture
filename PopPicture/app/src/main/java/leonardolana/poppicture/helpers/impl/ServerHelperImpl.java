package leonardolana.poppicture.helpers.impl;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.server.ServerRequest;

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
public class ServerHelperImpl implements ServerHelper {

    private static ServerHelper INSTANCE;

    public static ServerHelper getInstance(Context context) {
        if(context instanceof Activity)
            throw new UnsupportedOperationException("To avoid leaks, use only application context");

        if(INSTANCE == null)
            INSTANCE = new ServerHelperImpl(context);

        return INSTANCE;
    }

    private RequestQueue mRequestQueue;

    private ServerHelperImpl(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        CookieManager cookieManage = new CookieManager();
        CookieHandler.setDefault(cookieManage);
    }

    @Override
    public void execute(ServerRequest serverRequest) {
        mRequestQueue.add(serverRequest.getRequest());
    }
}
