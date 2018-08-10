package leonardolana.poppicture.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

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
public class ButtonTextViewOutlined extends ButtonTextView {

    public ButtonTextViewOutlined(Context context) {
        super(context);
    }

    public ButtonTextViewOutlined(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonTextViewOutlined(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        setBackground(getResources().getDrawable(R.drawable.button_outlined_shape));
    }

}
