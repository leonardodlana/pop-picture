package leonardolana.poppicture.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.AlertDialog;
import leonardolana.poppicture.common.BaseFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.editor.contract.EditorPictureContract;
import leonardolana.poppicture.helpers.api.CloudStorage;
import leonardolana.poppicture.helpers.api.PersistentHelper;
import leonardolana.poppicture.helpers.api.ServerHelper;
import leonardolana.poppicture.helpers.api.UserHelper;
import leonardolana.poppicture.helpers.impl.CloudStorageImpl;
import leonardolana.poppicture.helpers.impl.PersistentHelperImpl;
import leonardolana.poppicture.helpers.impl.ServerHelperImpl;
import leonardolana.poppicture.helpers.impl.UserHelperImpl;

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
public class EditorPictureFragment extends BaseFragment implements EditorPictureFragmentView, EditorPictureContract {

    public static EditorPictureFragment newInstance(Uri fileURI) {
        EditorPictureFragment editorFragment = new EditorPictureFragment();
        editorFragment.setFileURI(fileURI);
        return editorFragment;
    }

    private Uri mFileURI;
    private EditorPictureFragmentPresenter mPresenter;
    private Bitmap mSampleBitmap;

    @BindView(R.id.image_view)
    ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditorPictureFragmentPresenter(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor_picture, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            @Override
            public void run() {
                readFile();
            }
        });
    }

    private void setFileURI(Uri uri) {
        mFileURI = uri;
    }

    private void readFile() {
        try {
            //todo background, foreground is very bad
            InputStream inputStream = getContext().getContentResolver().openInputStream(mFileURI);
            // To avoid loading a huge bitmap to memory, we first get only the size and see if
            // we have to reduce it before loading it. At this point is indifferent for the user.
            // large images will have no benefit, will increase the change of a crash and
            // will be very slow to render and apply effects before sharing.

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);

            // Reset inputstream not supported
            inputStream.close();
            inputStream = getContext().getContentResolver().openInputStream(mFileURI);

            int sampleSize = Utils.calculateSampleSize(mImageView.getMeasuredWidth(), mImageView.getMeasuredHeight(),
                    options.outWidth, options.outHeight);

            options.inJustDecodeBounds = false;
            options.inSampleSize = sampleSize;
            mSampleBitmap = BitmapFactory.decodeStream(inputStream, null, options);
            mImageView.setImageBitmap(mSampleBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            mPresenter.onErrorLoadingFile();
        }
    }

    /*
        View methods
     */

    @Override
    public void showLoadingErrorDialogAndDismiss() {
        AlertDialog dialog = AlertDialog.newInstance("Error loading the file", "Sorry, we couldn't load your file, please try again.");
        dialog.setCancelable(false);
        dialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
            @Override
            public void onDismiss() {
//                dismiss();
            }
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    /*
        Contract methods
     */

    @Override
    public Bitmap getSampleBitmap() {
        return mSampleBitmap;
    }
}
