package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;
import java.util.List;

/**
 Created by Brandon on 03/24/2016.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private class WidgetQuote {
        private String symbol;
        private String change;

        public String getSymbol () {
            return symbol;
        }

        public void setSymbol (String symbol) {
            this.symbol = symbol;
        }

        public String getChange () {
            return change;
        }

        public void setChange (String change) {
            this.change = change;
        }
    }

    List<WidgetQuote> mCollections = new ArrayList ();
    RemoteViews mView = null;
    Context mContext = null;

    public WidgetDataProvider (Context context, Intent intent) {
        mContext = context;
    }

    @Override public int getCount () {
        return mCollections.size ();
    }

    @Override public long getItemId (int position) {
        return position;
    }

    @Override public RemoteViews getLoadingView () {
        return null;
    }

    @Override public RemoteViews getViewAt (int position) {
        WidgetQuote quote = mCollections.get (position);
        mView.setTextViewText (R.id.stock_symbol, quote.getSymbol ());
        mView.setTextViewText (R.id.change, quote.getChange ());
        mView.setTextColor (android.R.id.text1, Color.BLACK);
        return mView;
    }

    @Override public int getViewTypeCount () {
        return 1;
    }

    @Override public boolean hasStableIds () {
        return true;
    }

    @Override public void onCreate () {
        mView = new RemoteViews (mContext.getPackageName (), R.layout.widget_collection_item);
        mCollections.clear ();
        Cursor cursor = mContext.getContentResolver ()
                .query (QuoteProvider.Quotes.CONTENT_URI, new String[]{QuoteColumns.SYMBOL, QuoteColumns.PERCENT_CHANGE}, QuoteColumns.ISCURRENT + " = ?", new String[]{"1"}, null);
        while (cursor.moveToNext ()) {
            WidgetQuote widgetQuote = new WidgetQuote ();
            widgetQuote.setChange (cursor.getString (1));
            widgetQuote.setSymbol (cursor.getString (0));
            mCollections.add (widgetQuote);
        }

    }

    @Override public void onDataSetChanged () {
        onCreate ();
    }

    @Override public void onDestroy () {

    }

}