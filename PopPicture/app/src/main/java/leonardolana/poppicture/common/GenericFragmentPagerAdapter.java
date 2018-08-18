package leonardolana.poppicture.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import leonardolana.poppicture.data.HomeSection;

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

public class GenericFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String KEY_SAVE_FRAGMENT = "home_fragment_save_fragment_";
    private static final String KEY_SAVED_FRAGMENTS_COUNT = "home_fragment_saved_fragments_count";

    private Fragment mFragmentReferences[];
    private final FragmentManager mFragmentManager;

    public GenericFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public GenericFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mFragmentManager = fm;
        mFragmentReferences = fragments;
    }

    @Override
    public Fragment getItem(final int position) {
        return mFragmentReferences != null ? mFragmentReferences[position] : null;
    }

    public int getItemPosition(HomeSection section) {
        Fragment fragment;
        for(int i=0; i<mFragmentReferences.length; i++) {
            fragment = mFragmentReferences[i];
            if(TextUtils.equals(fragment.getClass().getName(), section.getName()))
                return i;
        }

        return 0;
    }

    @Override
    public int getCount() {
        return mFragmentReferences != null ? mFragmentReferences.length : 0;
    }

    public void saveFragments(Bundle outState) {
        outState.putInt(KEY_SAVED_FRAGMENTS_COUNT, mFragmentReferences.length);
        for (int i = 0; i < mFragmentReferences.length; i++) {
            if (mFragmentReferences[i] != null)
                mFragmentManager.putFragment(outState, KEY_SAVE_FRAGMENT + i, mFragmentReferences[i]);
        }
    }

    public void restoreFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            int count = savedInstanceState.getInt(KEY_SAVED_FRAGMENTS_COUNT, 0);
            mFragmentReferences = new Fragment[count];
            Fragment frag;
            int i = 0;
            while ((frag = mFragmentManager.getFragment(savedInstanceState, KEY_SAVE_FRAGMENT + i)) != null) {
                mFragmentReferences[i] = frag;
                i++;
            }
        }
    }
}
