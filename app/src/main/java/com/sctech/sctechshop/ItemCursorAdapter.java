package com.sctech.sctechshop;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sctech.sctechshop.ShopCategory.ShopEntry;

/**
 * Created by miketsai on 11/10/2017.
 */

  public class ItemCursorAdapter extends CursorAdapter {

        /**
         * Constructs a new {@link ItemCursorAdapter}.
         *
         * @param context The context
         * @param c       The cursor from which to get the data.
         */
        public ItemCursorAdapter(Context context, Cursor c) {
            super(context, c, 0 /* flags */);
        }

        /**
         * Makes a new blank list item view. No data is set (or bound) to the views yet.
         *
         * @param context app context
         * @param cursor  The cursor from which to get the data. The cursor is already
         *                moved to the correct position.
         * @param parent  The parent to which the new view is attached to
         * @return the newly created list item view.
         */
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View listItemView = null;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(context).inflate(
                        R.layout.list_items, parent, false);
            }
            return listItemView;
        }

        /**
         * This method binds the pet data (in the current row pointed to by cursor) to the given
         * list item layout. For example, the name for the current pet can be set on the name TextView
         * in the list item layout.
         *
         * @param view    Existing view, returned earlier by newView() method
         * @param context app context
         * @param cursor  The cursor from which to get the data. The cursor is already moved to the
         *                correct row.
         */
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = view;


            int idColumnIndex = cursor.getColumnIndex(ShopCategory.ShopEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ShopCategory.ShopEntry.COLUMN_ITEM_NAME);
            int breedColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_ITEM_PRICE);
            int genderColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_ITEM_QTY);
            int weightColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_ITEM_PIC);

            TextView nameView = (TextView) listItemView.findViewById(R.id.name);
            nameView.setText(cursor.getString(nameColumnIndex));

            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentBreed = cursor.getString(breedColumnIndex);
            int currentGender = cursor.getInt(genderColumnIndex);
            int currentWeight = cursor.getInt(weightColumnIndex);

            TextView summaryView = (TextView) listItemView.findViewById(R.id.summary);
            summaryView.setText(currentID + " - " +
                    currentName + " - " +
                    currentBreed + " - " +
                    currentGender + " - " +
                    currentWeight);

        }
}
