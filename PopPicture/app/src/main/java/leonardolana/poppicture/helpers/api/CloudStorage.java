package leonardolana.poppicture.helpers.api;

import android.util.Pair;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

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
public interface CloudStorage {

    interface OnUploadListener {
        void onCompletion();
        void onError();
    }

    interface OnDownloadListener {
        void onCompletion();
        void onError();
    }

    void upload(OnUploadListener onUploadListener, Pair<String, InputStream>... filesToUpload);

    // TODO allow multiple downloads
    void download(OnDownloadListener onDownloadListener, String path, File file);

}
