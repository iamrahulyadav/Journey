package com.archi.archiinfo.journey;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.model.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by archi_info on 1/4/2017.
 * http://web-medico.com/web2/Android_IOS_App_journey/api/get_event_data.php
 */

public class EventsActivity extends BaseActivity implements View.OnClickListener {
    private CoordinatorLayout coordinatorLayout;
    TextView tvBack, tvNext;
    private String TAG = "msg";
    private RecyclerView recyclerViewDanoteList;
    private List<Event> eventList;
    private Button btnCalendar;
    private TextView tvDateShow;
    private long timeInMilliseconds;
    private List<Event> filterEventList;
    private String userId;
    private LinearLayout linearCalendarOne;
    private LinearLayout linearCalendarTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        init();
        if (Util.isOnline(getApplicationContext())) {
            new getEventList().execute();
        }

        // Added event 2 GMT: Sun, 07 Jun 2015 19:10:51 GMT
//        Event ev2 = new Event((Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.blue_selected))) ,"DATA");
//        compactCalendarView.addEvent(ev2);

    }

    private void init() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_EventsActivity);


        eventList = new ArrayList<>();

        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT

    }


    public long convertDateToMilliSecond(String date) {

        String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    public class getEventList extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(EventsActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            eventList = new ArrayList<>();
            eventList.clear();
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";

            response = Util.getResponseofGet(Constant.BASE_URL + "get_event_data.php");
//                urlString = Constant.BASE_URL + "login.php?email=" + usernamestr + "&password=" + passwordstr;
            Log.e("RESULT", ">>" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").equalsIgnoreCase("true")) {

                    JSONArray arry = object.getJSONArray("data");
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        Event events = new Event();
                        events.setId(obj.getString("id"));
                        events.setStart_date(obj.getString("start_date"));
                        events.setEnd_time(obj.getString("end_date"));
                        events.setStart_time(obj.getString("start_time"));
                        events.setEnd_time(obj.getString("end_time"));
                        events.setTitle(obj.getString("title"));
                        eventList.add(events);
                    }
                }
            } catch (Exception e) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                e.printStackTrace();
            }
        }
    }
}
