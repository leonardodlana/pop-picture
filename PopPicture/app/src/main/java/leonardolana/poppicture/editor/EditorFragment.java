package leonardolana.poppicture.editor;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import leonardolana.poppicture.R;
import leonardolana.poppicture.common.BaseDialogFragment;
import leonardolana.poppicture.common.GenericFragmentPagerAdapter;
import leonardolana.poppicture.home.HomeFragmentPagerAdapter;
import leonardolana.poppicture.home.HomeFragmentPresenter;

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

    public static EditorFragment newInstance(Uri fileURI) {
        EditorFragment editorFragment = new EditorFragment();
        editorFragment.setFileURI(fileURI);
        return editorFragment;
    }

    private Uri mFileUri;
    private EditorFragmentPresenter mPresenter;
    private GenericFragmentPagerAdapter mPageAdapter;

    @BindView(R.id.pager_fragments)
    ViewPager mPagerFragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new EditorFragmentPresenter(this);
        init(mPresenter);

        setFullScreen(true);
        setHasTitle(false);

        if (savedInstanceState == null) {
            Fragment fragments[] = {EditorPictureFragment.newInstance(mFileUri),
                    new EditorExtraInfoFragment()};
            mPageAdapter = new GenericFragmentPagerAdapter(getChildFragmentManager(), fragments);
        } else {
            mPageAdapter = new GenericFragmentPagerAdapter(getChildFragmentManager());
        }
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

    private void setFileURI(Uri fileURI) {
        mFileUri = fileURI;
    }

}
