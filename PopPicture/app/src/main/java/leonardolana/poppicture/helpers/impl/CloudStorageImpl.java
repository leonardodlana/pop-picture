package leonardolana.poppicture.helpers.impl;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import leonardolana.poppicture.helpers.api.CloudStorage;

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
public class CloudStorageImpl implements CloudStorage {

    @Override
    public void upload(final OnUploadListener onUploadListener, Pair<String, InputStream>[] filesToUpload) {
        if(filesToUpload == null || filesToUpload.length == 0)
            throw new InvalidParameterException("filesToUpload must be > 0");

        final Set<String> hashSet = new HashSet<>();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef;

        for(final Pair<String, InputStream> fileToUpload : filesToUpload) {
            hashSet.add(fileToUpload.first);
            storageRef = storage.getReference().child(fileToUpload.first);
            storageRef.putStream(fileToUpload.second).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    hashSet.remove(fileToUpload.first);

                    // Queue is done
                    if(hashSet.size() == 0) {
                        onUploadListener.onCompletion();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

    }

    @Override
    public void download(String path, OutputStream outputStream) {

    }

}
