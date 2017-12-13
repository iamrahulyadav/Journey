package com.archi.archiinfo.journey.calendar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.archi.archiinfo.journey.BaseActivity;
import com.archi.archiinfo.journey.MainActivity;
import com.archi.archiinfo.journey.R;
import com.archi.archiinfo.journey.Util.Constant;
import com.archi.archiinfo.journey.Util.Util;
import com.archi.archiinfo.journey.helper.LocaleHelper;
import com.archi.archiinfo.journey.model.Event;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class MainCalendar extends BaseActivity {

    public static HashSet<Date> eventsDate;
    public static List<Event> eventList;
    private CoordinatorLayout coordinatorLayout;
    private CalendarView cv;
    String eventData;
    public static Event events;
    PopupWindow popUpWindow;
    Dialog dialog;
    Date date1;
    Dialog customdialog;
    Button okDialogBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_calendar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_MainCalenderActivity);


//        String dtStart = "2016-11-11T09:27:37Z";
//        Date date = StringToDateConvert(dtStart);
//
//
//        String dtStartt = "2016-11-22T09:27:37Z";
//        Date datee = StringToDateConvert(dtStartt);
//
//        events = new HashSet<>();
//
//        events.add(date);
//        events.add(datee);

        cv = (CalendarView) findViewById(R.id.calendar_view);
//        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
              //  Toast.makeText(MainCalendar.this, df.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDayClickPress(Date date, View view) {
                DateFormat df = SimpleDateFormat.getDateInstance();
             //   Toast.makeText(MainCalendar.this, eventData, Toast.LENGTH_SHORT).show();

                for (int i = 0; i < eventList.size(); i++) {
                    Log.d("*******", "************");
                    Log.d("DATE", "!!! " + date);
                    date1 = StringToDateConvert(eventList.get(i).getStart_date());
                    Log.d("DATE", "!!! " + date1);
                    if (date == date1) {
                        Log.d("%%%%%%", "%%%%%%%%");
                        Log.d("DATE 0", "!!! " + date);
                        Log.d("DATE 1", "!!! " + date1);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                  //  promptEventDetail(MainCalendar.this, df.format(date1), "KANAN");
                }
            }
        });

        if (Util.isOnline(getApplicationContext())) {
            new getEventList().execute();
        }

        getPopupWindow();
}

    private void getPopupWindow() {
        dialog = new Dialog(MainCalendar.this);
     //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("انقر فوق التاريخ للحصول على معلومات");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        okDialogBtn=(Button)dialog.findViewById(R.id.btn_yes);
        TextView txtVwTitle=(TextView) dialog.findViewById(R.id.txtVwTitle);
        txtVwTitle.setText("انقر فوق التاريخ للحصول على معلومات");

        TextView txtVwAlert=(TextView) dialog.findViewById(R.id.txtVwAlert);
        txtVwAlert.setText("تنبيه");
        okDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_language, menu);//Menu Resource, Menu
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_english:
                Toast.makeText(getApplicationContext(), "English Selected", Toast.LENGTH_LONG).show();
                Util.setLocale(getApplicationContext(), "en");
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE, "en");
                return true;
            case R.id.select_arabic:
                Toast.makeText(getApplicationContext(), "Arabic Selected", Toast.LENGTH_LONG).show();
                Util.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_APP_LANGUAGE, "ar");
                Util.setLocale(getApplicationContext(), "ar");
                return true;
            default:
                return false;
        }
    }


    public Date StringToDateConvert(String dtStart) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;

    }

    public class getEventList extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainCalendar.this);
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
            Log.e("RESULT", ">>" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
            pd.dismiss();
            Log.d("Response", "" + s);

            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    JSONArray arry = object.getJSONArray("data");
                    for (int i = 0; i < arry.length(); i++) {
                        JSONObject obj = arry.getJSONObject(i);
                        events = new Event();
                        events.setId(obj.getString("id"));
                        events.setStart_date(obj.getString("event_start"));
                        events.setEnd_time(obj.getString("event_end"));
                        events.setTitle(obj.getString("title"));
                        eventData = obj.getString("title");
                        eventList.add(events);
                    }
                }
            } catch (Exception e) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }

                addEventsToTheCalender();

        }

        private void addEventsToTheCalender() {
//            String dtStart = "2016-11-11T09:27:37Z";
//            Date date = StringToDateConvert(dtStart);

//            String dtStartt = "2016-11-22T09:27:37Z";
//            Date datee = StringToDateConvert(dtStartt);

            for (int i = 0; i < eventList.size(); i++) {
                //   Date start = StringToDateConvert(eventList.get(i).getStart_date() + "T" + eventList.get(i).getStart_time());
                Date start = StringToDateConvert(eventList.get(i).getStart_date());
                Log.d(">>>>>>> ", ">>>>>>>>>>>>>>>>>>>>>>>>>> ");
                Log.d("@@@@ ", "" + eventList.get(i).getStart_date());
                Log.d("START ", ">>>> " + start);
                Date end = StringToDateConvert(eventList.get(i).getEnd_date());
                Log.d("END ", ">>>> " + end);

                eventsDate = new HashSet<>();
                eventsDate.add(start);
//                eventsDate.add(end);
                cv.updateCalendar(eventsDate);
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    private void updateViews(String languageCode) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void promptEventDetail(MainCalendar mainCalendar, String title, String msg) {

      /*  AlertDialog.Builder upgradeAlert = new AlertDialog.Builder(mainCalendar);
        upgradeAlert.setTitle(title);
        upgradeAlert.setView(R.layout.custom_dialog);
        upgradeAlert.setMessage(msg);
        upgradeAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        upgradeAlert.show();

        customdialog = new Dialog(mainCalendar);
        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customdialog.setContentView(R.layout.custom_dialog);
        customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customdialog.show();
        Window window = customdialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        TextView txtVw=(TextView) customdialog.findViewById(R.id.editText1);
        txtVw.setText(title);
        txtVw.setContentDescription(msg);
        okDialogBtn=(Button)customdialog.findViewById(R.id.btn_yes);



        okDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customdialog.dismiss();

            }
        });*/

    }
}
