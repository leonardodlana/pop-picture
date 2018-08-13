package leonardolana.poppicture.helpers.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import leonardolana.poppicture.data.Picture;
import leonardolana.poppicture.data.User;
import leonardolana.poppicture.helpers.api.CacheHelper;
import leonardolana.poppicture.helpers.api.CloudStorage;
import leonardolana.poppicture.helpers.api.PersistentHelper;

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
public class CacheHelperImpl extends CacheHelper {

    private static CacheHelper INSTANCE;

    public static CacheHelper getInstance(Context context) {
        if (context instanceof Activity)
            throw new UnsupportedOperationException("To avoid leaks, use only application context");

        if (INSTANCE == null)
            INSTANCE = new CacheHelperImpl(context);

        return INSTANCE;
    }

    //TODO memory cache
    private final CloudStorage mCloudStorage;
    private final Context mContext;
    private final HandlerThread mBackgroundThread;
    private final Handler mBackgroundHandler;
    private final Handler mMainThreadHandler;

    private CacheHelperImpl(Context context) {
        mContext = context;
        mCloudStorage = new CloudStorageImpl();
        mBackgroundThread = new HandlerThread("gallery_background_thread");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void loadPicture(Picture picture, boolean thumbnail, @NonNull final OnLoadPicture onLoadPicture) {
        try {
            final File folder = new File(mContext.getCacheDir(), picture.getUserId());
            folder.mkdirs();

            final String fullPath = thumbnail ? Picture.getThumbPath(picture) : Picture.getPath(picture);
            final File file = new File(mContext.getCacheDir().getAbsolutePath() + "/" + fullPath);

            if (file.exists()) {
                //TODO better way to execute, queue probably
                mBackgroundHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onLoadPicture.onLoad(bitmap);
                            }
                        });
                    }
                });

                return;
            }

            mCloudStorage.download(new CloudStorage.OnDownloadListener() {
                @Override
                public void onCompletion() {
                    mBackgroundHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            mMainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onLoadPicture.onLoad(bitmap);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError() {

                }
            }, fullPath, file);
        } catch (Exception e) {
            onLoadPicture.onError(LOAD_ERROR_UNKNOWN);
            e.printStackTrace();
        }
    }

    @Override
    public void loadPicture(Picture picture, boolean thumbnail, ImageView target) {
        try {
            final WeakReference<ImageView> weakReference = new WeakReference<>(target);

            final File folder = new File(mContext.getCacheDir(), picture.getUserId());
            folder.mkdirs();

            final String fullPath = thumbnail ? Picture.getThumbPath(picture) : Picture.getPath(picture);
            final File file = new File(mContext.getCacheDir().getAbsolutePath() + "/" + fullPath);

            if (file.exists()) {
                //TODO better way to execute, queue probably
                mBackgroundHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ImageView imageView = weakReference.get();
                                if (imageView != null)
                                    imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                });

                return;
            }

            mCloudStorage.download(new CloudStorage.OnDownloadListener() {
                @Override
                public void onCompletion() {
                    //TODO better way to execute, queue probably
                    mBackgroundHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            mMainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ImageView imageView = weakReference.get();
                                    if (imageView != null)
                                        imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError() {

                }
            }, fullPath, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
