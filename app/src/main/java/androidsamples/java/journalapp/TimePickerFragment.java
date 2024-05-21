package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
  public final static String NEW_TIME = "new_time";
  private int _viewId;

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    TimePickerFragment fragment = new TimePickerFragment();
    Bundle args = new Bundle();
    args.putSerializable("Time", time);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    if(args != null && args.getInt("viewId")!=0) {
      _viewId = args.getInt("viewId");
    }

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);
    return new TimePickerDialog(requireContext(), this, hour, min, false);
  }

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    String time = "";
    if (hourOfDay < 10) {
      time += "0" + hourOfDay + ":";
    } else {
      time += hourOfDay + ":";
    }
    if (minute < 10) {
      time += "0" + minute;
    } else {
      time += minute;
    }
    NavController navController = NavHostFragment.findNavController(this);
    navController.getPreviousBackStackEntry().getSavedStateHandle().set(NEW_TIME,time+";"+_viewId);
  }
}
