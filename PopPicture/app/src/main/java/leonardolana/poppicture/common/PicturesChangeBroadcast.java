package leonardolana.poppicture.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

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
public class PicturesChangeBroadcast extends BroadcastReceiver {

    private static final String BROADCAST_ITEM_ADDED = "broadcast_pictures_item_added";
    private static final String BROADCAST_ITEMS_SCROLLED = "broadcast_pictures_items_scrolled";

    private static final String EXTRA_SCROLL_DX = "scroll_dx";
    private static final String EXTRA_SCROLL_DY = "scroll_dy";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BROADCAST_ITEM_ADDED:
                onPictureAdded();
                break;
            case BROADCAST_ITEMS_SCROLLED:
                int dx = intent.getIntExtra(EXTRA_SCROLL_DX, 0);
                int dy = intent.getIntExtra(EXTRA_SCROLL_DY, 0);
                onPicturesScrolled(dx, dy);
                break;
        }
    }

    protected void onPictureAdded() {
    }

    protected void onPicturesScrolled(int dx, int dy) {}

    public void register(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ITEM_ADDED);
        intentFilter.addAction(BROADCAST_ITEMS_SCROLLED);

        LocalBroadcastManager.getInstance(context).registerReceiver(this, intentFilter);
    }

    public void unRegister(@NonNull Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
    }

    public static void sendItemAdded(Context context) {
        Intent intent = new Intent(BROADCAST_ITEM_ADDED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendItemsScrolled(Context context, int dx, int dy) {
        Intent intent = new Intent(BROADCAST_ITEMS_SCROLLED);
        intent.putExtra(EXTRA_SCROLL_DX, dx);
        intent.putExtra(EXTRA_SCROLL_DY, dy);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
