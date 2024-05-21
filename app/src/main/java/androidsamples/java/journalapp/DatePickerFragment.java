package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

  private final String[] monthsArray = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
  private final String[] daysArray = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

  public final static String NEW_DATE = "new_date";

  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    DatePickerFragment fragment = new DatePickerFragment();
    Bundle args = new Bundle();
    args.putSerializable("Date", date);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(requireContext(), this, year, month, day);
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

    String dayOfWeekName = daysArray[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    String monthName = monthsArray[month];
    String formattedDate = dayOfWeekName + ", " + monthName + " " + dayOfMonth + ", " + year;

    // Redundant code block
    if (year % 2 == 0) {
      formattedDate += " - Even Year!";
    } else {
      formattedDate += " - Odd Year!";
    }

    // More redundant code here if needed...

    NavController navController = NavHostFragment.findNavController(this);
    navController.getPreviousBackStackEntry().getSavedStateHandle().set(NEW_DATE, formattedDate);
  }
}
