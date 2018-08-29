package leonardolana.poppicture.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.AlertDialog;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.BasePresenter;
import leonardolana.poppicture.common.GenericFragmentPagerAdapter;
import leonardolana.poppicture.common.LoadingDialog;
import leonardolana.poppicture.common.Utils;
import leonardolana.poppicture.editor.contract.EditorExtraInfoContract;
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
public class EditorFragment extends BaseDialogFragment implements EditorFragmentView {

    private static final int ITEM_EDITOR_PICTURE = 0;
    private static final int ITEM_EDITOR_INFO = 1;

    public static EditorFragment newInstance(Uri fileURI) {
        EditorFragment editorFragment = new EditorFragment();
        editorFragment.setFileURI(fileURI);
        return editorFragment;
    }

    private Uri mFileUri;
    private EditorFragmentPresenter mPresenter;
    private GenericFragmentPagerAdapter mPageAdapter;
    private EditorPictureContract mEditorPictureContract;
    private EditorExtraInfoContract mEditorExtraFieldContract;

    @BindView(R.id.pager_fragments)
    ViewPager mPagerFragments;

    @BindView(R.id.button_next)
    TextView mButtonNext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context applicationContext = getContext().getApplicationContext();
        PersistentHelper persistentHelper = PersistentHelperImpl.getInstance(applicationContext);
        UserHelper userHelper = UserHelperImpl.getInstance(persistentHelper);
        ServerHelper serverHelper = ServerHelperImpl.getInstance(applicationContext);
        CloudStorage cloudStorage = new CloudStorageImpl();
        mPresenter = new EditorFragmentPresenter(this, userHelper, serverHelper, cloudStorage);

        setFullScreen(true);
        setHasTitle(false);

        if (savedInstanceState == null) {
            EditorPictureFragment editorPictureFragment = EditorPictureFragment.newInstance(mFileUri);
            mEditorPictureContract = editorPictureFragment;

            EditorExtraInfoFragment editorExtraInfoFragment = new EditorExtraInfoFragment();
            mEditorExtraFieldContract = editorExtraInfoFragment;

            Fragment fragments[] = {editorPictureFragment,
                    editorExtraInfoFragment};
            mPageAdapter = new GenericFragmentPagerAdapter(getChildFragmentManager(), fragments);
        } else {
            //todo contract
            mPageAdapter = new GenericFragmentPagerAdapter(getChildFragmentManager());
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPagerFragments.setAdapter(mPageAdapter);
        mPagerFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == ITEM_EDITOR_PICTURE)
                    mButtonNext.setText(R.string.next);
                else
                    mButtonNext.setText(R.string.share);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPageAdapter.saveFragments(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        mPageAdapter.restoreFragments(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @OnClick(R.id.button_next)
    public void onClickNext() {
        if (mPagerFragments.getCurrentItem() == ITEM_EDITOR_PICTURE) {
            mPagerFragments.setCurrentItem(ITEM_EDITOR_INFO, true);
        } else {
            // validate page info
            if(mEditorExtraFieldContract.areFieldsValid()) {
                try {
                    InputStream inputStream = getContext().getContentResolver().openInputStream(mFileUri);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap thumbnail = Utils.createThumbnail(mEditorPictureContract.getSampleBitmap(), 1000);
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumbBytes = baos.toByteArray();

                    mPresenter.onClickShare(inputStream, new ByteArrayInputStream(thumbBytes),
                            mEditorExtraFieldContract.getTitle(), mEditorExtraFieldContract.getDescription());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        //        EditorExtraInfoFragment fragment = EditorExtraInfoFragment.newInstance(mFileURI);
//        fragment.show(getFragmentManager(), "dialog");

    }

    @OnClick(R.id.button_close)
    public void onClickClose() {
        mPresenter.onClickClose();
    }

    @Override
    public void showSuccess() {
//        dismiss();
    }

    @Override
    public void showError() {
        AlertDialog dialog = AlertDialog.newInstance(getString(R.string.error_sharing), getString(R.string.error_sharing_description));
        dialog.setCancelable(false);
        dialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
            @Override
            public void onDismiss() {
//                dismiss();
            }
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    private void setFileURI(Uri fileURI) {
        mFileUri = fileURI;
    }

}
