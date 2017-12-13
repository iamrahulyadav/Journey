package com.archi.archiinfo.journey.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.archi.archiinfo.journey.R;
import com.archi.archiinfo.journey.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout {
    private String name1, name2;
    // for logging
    private static final String LOGTAG = "Calendar View";
    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";
    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;
    // seasons' rainbow
    int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };
    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    // date format
    private String dateFormat;
    // current displayed month
    private Calendar currentDate = Calendar.getInstance();
    //event handling
    private EventHandler eventHandler = null;
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private Dialog customdialog;
    private Button okDialogBtn;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }


    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components

        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
                return true;
            }
        });


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (eventHandler != null) {
                    eventHandler.onDayClickPress((Date) parent.getItemAtPosition(position), view);
                    final TextView textView = (TextView) view.findViewById(R.id.dateSquare2);


//                    promptEventDetail();

                    String date = textView.getText().toString();
                    textView.setText(date);
               /*     new SimpleTooltip.Builder(getContext())
                            .anchorView(view)
                            .arrowColor(ContextCompat.getColor(getContext(), R.color.button_bg))
                            .backgroundColor(ContextCompat.getColor(getContext(), R.color.button_bg))
                            .textColor(ContextCompat.getColor(getContext(), R.color.white))
                            .padding(R.dimen._10sdp)
                            .text(date)
                            .gravity(Gravity.BOTTOM)
                            .dismissOnOutsideTouch(true)
                            .dismissOnInsideTouch(true)
                            .modal(true)
                            .build()
                            .show();
                    AlertDialog.Builder upgradeAlert = new AlertDialog.Builder(getContext());
                    upgradeAlert.setTitle(date);

                    upgradeAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    upgradeAlert.show();*/
                    customdialog = new Dialog(getContext());
                    //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customdialog.setContentView(R.layout.custom_dialog);
                    customdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    customdialog.show();
                    Window window = customdialog.getWindow();
                    window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.CENTER);

                    TextView txtVw=(TextView) customdialog.findViewById(R.id.txtVwTitle);
                    txtVw.setText(date);

                    TextView txtVwAlert=(TextView) customdialog.findViewById(R.id.txtVwAlert);
                    txtVwAlert.setVisibility(GONE);

                    okDialogBtn=(Button)customdialog.findViewById(R.id.btn_yes);
                    okDialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customdialog.dismiss();
                        }
                    });
                }
            }
        });


    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events) {

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d("msg", "monthBeginningCell   " + monthBeginningCell);

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);


        Log.d("msg", "month count " + DAYS_COUNT + "  month  " + calendar.getTime().getMonth());


        // fill cells
        while (cells.size() < DAYS_COUNT) {

            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.d("msg", "day " + calendar.getTime().getMonth() + "  day   count " + day);

        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Log.e("@@@ ", " " + currentDate);
        Log.e("@@@ ", " " + sdf.format(currentDate.getTime()));
        String months = sdf.format(currentDate.getTime());
        String[] splited = months.split("\\s+");
        name1 = splited[0];
        name2 = splited[1];
        for (int i = 0; i < splited.length; i++) {
            Log.e("@@", " " + i + "  " + splited[i]);

        }

        switch (name1) {
            case "Jan":
                Log.e("JAN", "@ " + R.string.JAN);
                Log.d("NAME ", "@ " + name2);
                txtDate.setText(getContext().getString(R.string.JAN) + "  " + name2);
                break;
            case "Fab":
                txtDate.setText(getContext().getString(R.string.FAB) + "  " + name2);
                break;
            case "Mar":
                txtDate.setText(getContext().getString(R.string.MAR) + " " + name2);
                break;
            case "Apr":
                txtDate.setText(getContext().getString(R.string.APR) + " " + name2);
                break;
            case "May":
                txtDate.setText(getContext().getString(R.string.MAY) + " " + name2);
                break;
            case "Jun":
                txtDate.setText(getContext().getString(R.string.JUN) + " " + name2);
                break;
            case "Jul":
                txtDate.setText(getContext().getString(R.string.JUL) + " " + name2);
                break;
            case "Aug":
                txtDate.setText(getContext().getString(R.string.AUG) + " " + name2);
                break;
            case "Sep":
                txtDate.setText(getContext().getString(R.string.SEP) + " " + name2);
                break;
            case "Oct":
                txtDate.setText(getContext().getString(R.string.OCT) + " " + name2);
                break;
            case "Nov":
                txtDate.setText(getContext().getString(R.string.NOV) + " " + name2);
                break;
            case "Dec":
                txtDate.setText(getContext().getString(R.string.DEC) + " " + name2);
                break;


        }
//        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];
        header.setBackgroundColor(ContextCompat.getColor(getContext(), color));
//        header.setBackgroundColor(getResources().getColor(color));
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */


    public interface EventHandler {

        void onDayLongPress(Date date);

        void onDayClickPress(Date date, View view);
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            //    Log.d("msg", "getview " + day + ":" + month + ":" + year);

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image

            TextView tvDaySquare = (TextView) view.findViewById(R.id.dateSquare);
            TextView tvDaySquare2 = (TextView) view.findViewById(R.id.dateSquare2);
            view.setBackgroundResource(0);

//            if (MainCalendar.eventsDate != null) {
//
//                for (Date eventDate : MainCalendar.eventsDate) {
//
//                    if (eventDate.getDate() == day &&
//                            eventDate.getMonth() == month &&
//                            eventDate.getYear() == year) {
//                        // mark this day for event
//                        view.setBackgroundResource(R.drawable.reminder);
//                        tvDaySquare.setText("this is test ");
//                        break;
//                    }
//                }
//            }
            // clear styling
            tvDaySquare.setTypeface(null, Typeface.NORMAL);
//            tvDaySquare.setTextColor(getResources().getColor(R.color.current_month_color));
            tvDaySquare.setTextColor(ContextCompat.getColor(getContext(), R.color.brown));

            if (month != today.getMonth() || year != today.getYear()) {
                // if this day is outside current month, grey it out
//                tvDaySquare.setTextColor(getResources().getColor(R.color.white));
                tvDaySquare.setTextColor(ContextCompat.getColor(getContext(), R.color.brown));
            } else if (day == today.getDate()) {
                // if it is today, set it to blue/bold
                tvDaySquare.setTypeface(null, Typeface.BOLD);
                tvDaySquare.setTextColor(getResources().getColor(R.color.brown));
            }
            // set text
            tvDaySquare.setText(String.valueOf(date.getDate()));

            if (MainCalendar.eventList != null) {
                for (Event eventDate : MainCalendar.eventList) {
                    String startDate = eventDate.getStart_date();
                    Date date1 = StringToDateConvert(startDate);
                    Log.e("DATE ", "==>>  " + date1);
                    if (date1.getDate() == day &&
                            date1.getMonth() == month &&
                            date1.getYear() == year) {
                        // mark this day for event
//                        view.setBackgroundResource(R.drawable.reminder);
//                        tvDaySquare.setText(String.valueOf(date.getDate()) + eventDate.getTitle());
                        String text = "<font color=#FF0000>" + String.valueOf(date.getDate() + "<br>") + "</font> <font color=#ebd7b4>" + String.valueOf(eventDate.getTitle()) + " </font>";
                        tvDaySquare.setText(Html.fromHtml(text));
                        tvDaySquare2.setText(eventDate.getTitle());
                        break;
                    }
                }
            }
            //   Log.d("msg", "date " + date.getDate());
            return view;
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


    public void promptEventDetail(String title, String msg) {

        AlertDialog.Builder upgradeAlert = new AlertDialog.Builder(getContext());
        upgradeAlert.setTitle(title);
        upgradeAlert.setMessage(msg);
        upgradeAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        upgradeAlert.show();
    }

}
