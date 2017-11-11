package com.sctech.sctechshop;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sctech.sctechshop.ShopCategory.ShopEntry;

/**
 * Created by miketsai on 11/10/2017.
 */

public class ShowInventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ItemDbHelper mDbHelper;
    private static final int PET_LOADER_ID = 2;
    private CursorLoader mLoader = null;
    private ItemCursorAdapter mAdapter = null;
    public static final String LOG_TAG = ShowInventoryActivity.class.getName();

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ShopCategory.ShopEntry._ID,
                ShopCategory.ShopEntry.COLUMN_ITEM_NAME,
                ShopCategory.ShopEntry.COLUMN_ITEM_PRICE,
                ShopCategory.ShopEntry.COLUMN_ITEM_QTY,
                ShopCategory.ShopEntry.COLUMN_ITEM_PIC};

        Log.v(LOG_TAG, "Pet Loader created: " + i);

        return new CursorLoader(this,
                ShopCategory.ShopEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_show_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_delete_all: {
                showDeleteConfirmationDialog();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllPet();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteAllPet() {
        getContentResolver().delete(ShopCategory.ShopEntry.CONTENT_URI, null, null);
        finish();
        Intent intent = new Intent(this, ShowInventoryActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_all);

        Intent intent = getIntent();

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new ItemDbHelper(this);

        ListView itemListView = (ListView) findViewById(R.id.listInventories);
        View emptyView = findViewById(R.id.empty_view);

        itemListView.setEmptyView(emptyView);
        mAdapter = new ItemCursorAdapter(this, null);
        itemListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(PET_LOADER_ID, null, this);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Cursor cursor = db.
                Intent intent = new Intent(ShowInventoryActivity.this, MainActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(ShopEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);
                startActivity(intent);

            }
        });
    }
}
