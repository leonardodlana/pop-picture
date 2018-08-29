package leonardolana.poppicture.helpers.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import leonardolana.poppicture.helpers.api.RunnableExecutor;

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
public class RunnableExecutorImpl implements RunnableExecutor {

    private static RunnableExecutor INSTANCE;

    public static RunnableExecutor getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RunnableExecutorImpl();

        return INSTANCE;
    }

    private final HandlerThread mBackgroundThread;
    private final Handler mBackgroundHandler;
    private final Handler mMainThreadHandler;

    public RunnableExecutorImpl() {
        mBackgroundThread = new HandlerThread("background_executor");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void executeInBackground(Runnable runnable) {
        mBackgroundHandler.post(runnable);
    }

    @Override
    public void execute(Runnable runnable) {
        mMainThreadHandler.post(runnable);
    }
}
