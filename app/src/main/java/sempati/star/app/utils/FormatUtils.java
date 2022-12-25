package sempati.star.app.utils;

import android.icu.text.SimpleDateFormat;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class FormatUtils {

    String dayString = "";
    String dateString = "";
    SimpleDateFormat simpleDateFormat = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dateIndonesia(String dateString){
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.parse(dateString);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("EEEE");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dayString = simpleDateFormat.format(date) + ", ";
        }
        dateString = formateDateString(date.getYear(), date.getMonthValue() + 1, date.getDayOfMonth(), dayString);

        return dateString;
    }

    String formateDateString(int year, int month, int day, String dayName){
        String months = "", days = String.valueOf(day), years =String.valueOf(year);
        if(month == 1){
            months = "Januari";
        }else if(month == 2){
            months = "Februari";
        }else if(month == 3){
            months = "Maret";
        }else if(month == 4){
            months = "April";
        }else if(month == 5){
            months = "Mei";
        }else if(month == 6){
            months = "Juni";
        }else if(month == 7){
            months = "Juli";
        }else if(month == 8){
            months = "Agustus";
        }else if(month == 9){
            months = "September";
        }else if(month == 10){
            months = "Oktober";
        }else if(month == 11){
            months = "November";
        }else if(month == 12){
            months = "Desember";
        }
        if(day<10){
            days = '0'+days;
        }
        return dayName+ day +" "+ months + " " + years;
    }
}
