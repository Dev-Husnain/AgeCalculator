package hm.dev.agecalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btBirth,btToday,btCalculate;
    TextView tvResult,Month;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btBirth=findViewById(R.id.bt_birth);
        btToday=findViewById(R.id.bt_currentDate);
        tvResult=findViewById(R.id.tv_result);
        btCalculate=findViewById(R.id.calculate);
        Month=findViewById(R.id.month);
        //////////////////////////////////////
        //get current months, year,day
        Date date12=new Date();
        //SimpleDateFormat sdf=new SimpleDateFormat("dd");
        //SimpleDateFormat sdf=new SimpleDateFormat("EEEE");//day name in english
       SimpleDateFormat sdf=new SimpleDateFormat("MMMM");//month name in english
        String m=sdf.format(date12);
       Month.setText(m);
        //////////////////////////////////////////////

        ///////////////////////////////////////////
        LocalDate date32 = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        String abc= String.valueOf(date32.get(weekFields.weekOfWeekBasedYear())-1);//be
        //String abc= String.valueOf(date32.get(weekFields.weekOfMonth())); //week of month
        DateTime dt = new DateTime();
        String dayOfYear = String.valueOf(dt.getDayOfYear());
        //Month.setText(dayOfYear);
        //Month.setText(dayOfYear);
        /////////////////////////////////////////////////////////////////



        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String date=simpleDateFormat.format(Calendar.getInstance().getTime());
        btToday.setText(date);

        btBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,dateSetListener1,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month=month+1;
                String date=day + "/" +month +"/" + year;
                btBirth.setText(date);
            }
        };

        btToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,dateSetListener2,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new
                        ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month=month+1;
                String date=day + "/" +month +"/" + year;
                btToday.setText(date);
            }
        };

        btCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String sDate=btBirth.getText().toString();
                 String eDate=btToday.getText().toString();
                 SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1=simpleDateFormat1.parse(sDate);
                    Date date2=simpleDateFormat1.parse(eDate);
                    long startDate=date1.getTime();
                    long endDate=date2.getTime();
                    if (startDate<=endDate){
                        Period period=new Period(startDate,endDate, PeriodType.yearMonthDay());
                        int years=period.getYears();
                        int months=period.getMonths();
                        int days=period.getDays();

                        tvResult.setText(years+" Years | " + months+ " Months | "+ days+" Days");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Given date is larger than current date", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}