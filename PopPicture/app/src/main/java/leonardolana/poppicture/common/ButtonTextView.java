package leonardolana.poppicture.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import leonardolana.poppicture.R;

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
public class ButtonTextView extends AppCompatTextView {

    public ButtonTextView(Context context) {
        super(context);
        init();
    }

    public ButtonTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setAllCaps(true);
        setGravity(Gravity.CENTER);
        setHeight((int) getResources().getDimension(R.dimen.button_min_height));
        int paddingHorizontal = (int) getResources().getDimension(R.dimen.button_padding);
        setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.button_text));
    }

}
