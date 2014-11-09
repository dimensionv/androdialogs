/**
 *
 */
package de.dimensionv.android.androdialogs.calendar;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import de.dimensionv.android.androdialogs.common.DialogConstants;

/**
 * @author mjoellnir
 * 
 */
public class TimePickerDialogFragment extends CalenderDialogFragment implements OnTimeSetListener {

  /**
   *
   */
  public TimePickerDialogFragment() {
    super();
  }

  /**
   * Shows an AlertDialog, set the buttons and populate the Dialog with
   * content.
   * 
   * @param savedInstanceState
   *          The last saved instance state of the Fragment, or null if this is
   *          a freshly created Fragment.
   * @return Return a new Dialog instance to be displayed by the Fragment.
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Activity activity = getActivity();
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(getArguments().getLong(DialogConstants.CALENDAR));
    return new TimePickerDialog(activity, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
        DateFormat.is24HourFormat(activity));
  }

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
    calendar.set(Calendar.MINUTE, minute);
    callHandler(calendar);
  }

  public static TimePickerDialogFragment createDialog(Calendar calendar) {
    TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putLong(DialogConstants.CALENDAR, calendar.getTimeInMillis());
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }
}
