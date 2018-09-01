package leonardolana.poppicture.helpers.impl;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;

import java.util.List;

import leonardolana.poppicture.helpers.api.ImageLabelHelper;

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
public class ImageLabelHelperImpl implements ImageLabelHelper {

    @Override
    public void isImageOfMatureContent(Bitmap bitmap, final IsMatureContentListener listener) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance()
                .getVisionLabelDetector();

        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionLabel> firebaseVisionLabels) {
                float fleshConfidence = 0;
                float swimwearConfidence = 0;

                for (FirebaseVisionLabel label : firebaseVisionLabels) {
                    if (TextUtils.equals(label.getEntityId(), "/m/02p16m6")) {
                        fleshConfidence = label.getConfidence();
                        continue;
                    }

                    if (TextUtils.equals(label.getEntityId(), "/m/01gkx_")) {
                        swimwearConfidence = label.getConfidence();
                    }
                }

                if(fleshConfidence > .55) {
                    // Might be a nude
                    if(swimwearConfidence > .5f) {
                        // Probably swimwear only
                        listener.onSuccess(MatureContent.NONE);
                    } else {
                        listener.onSuccess(MatureContent.NUDITY);
                    }

                    return;
                }

                listener.onSuccess(MatureContent.NONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onError();
            }
        });

    }


}
