package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Brandon on 3/24/2016.
 */
public class StockWidgetService extends RemoteViewsService {

    @Override public RemoteViewsFactory onGetViewFactory (Intent intent) {

        WidgetDataProvider dataProvider = new WidgetDataProvider(getApplicationContext(), intent);
        return dataProvider;
    }

}
