package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;



public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    Context c;

    private static final String TAG = "CallLog";
    private static final int URL_LOADER = 1;

    private TextView callLogsTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");

        setContentView(R.layout.activity_main);
        initialize();
    }


    @SuppressLint("CutPasteId")
    private void initialize() {
        Log.d(TAG, "initialize()");


            Log.d(TAG, "initialize() >> initialise loader");
                getLoaderManager().initLoader(URL_LOADER, null, MainActivity.this);



        callLogsTextView = (TextView) findViewById(R.id.call_logs);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        Log.d(TAG, "onCreateLoader() >> loaderID : " + loaderID);

        switch (loaderID) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor managedCursor) {
        Log.d(TAG, "onLoadFinished()");

        StringBuilder sb = new StringBuilder();

        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

        sb.append("<h4>Call Log Details <h4>");
        sb.append("\n");
        sb.append("\n");

        sb.append("<table>");

        while (managedCursor.moveToNext()) {

            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            // Date callDayTime = new Date(Long.valueOf(callDate));

            String callDayTime;
            callDayTime = DateFormat.format("HH:MM", Long.parseLong(callDate)).toString();

            // String callDayTime = dateFormat.format(callDate);  
            String callDuration = managedCursor.getString(duration);
            String dir = null;

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;
            }

            DBHelper db=new DBHelper(c, "ZnSoftech.db", null, 2);
            db.insertdata(phNumber, callDate, callDayTime, callDuration, dir);

            sb.append("<tr>")
                    .append("<td>Phone Number: </td>")
                    .append("<td><strong>")
                    .append(phNumber)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Call Type:</td>")
                    .append("<td><strong>")
                    .append(dir)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Date & Time:</td>")
                    .append("<td><strong>")
                    .append(callDayTime)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<tr>")
                    .append("<td>Call Duration (Seconds):</td>")
                    .append("<td><strong>")
                    .append(callDuration)
                    .append("</strong></td>");
            sb.append("</tr>");
            sb.append("<br/>");
            sb.append("<br/>");
        }
        sb.append("</table>");

        managedCursor.close();

        callLogsTextView.setText(Html.fromHtml(sb.toString()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset()");
        // do nothing
    }
}
