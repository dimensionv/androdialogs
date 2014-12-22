/**
 *
 */
package de.dimensionv.android.androdialogs.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import de.dimensionv.android.androdialogs.common.DialogConstants;

/**
 * <code>DialogFragmet</code>-class for displaying a date picker.
 *
 * @author Volkmar Seifert
 */
@SuppressWarnings("UnusedDeclaration")
public class TimePickerDialogFragment extends CalenderDialogFragment implements OnTimeSetListener {

  /**
   * Default constructor, initializing this class by calling through to it's parent's constructor.
   */
  public TimePickerDialogFragment() {
    super();
  }

  /**
   * Shows an AlertDialog, sets the buttons and populates the Dialog with
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

  /**
   * <p>Sets the time given by the integers <code>hourOfDay</code> and
   * <code>mintue</code> using a <code>Calendar</code> object internally.</p>
   *
   * @param view The <code>TimePicker</code> object used to choose the date.
   * @param hourOfDay The selected year (0-23) (independent of whether 24 hour-mode is selected or not).
   * @param minute The selected month (0-59).
   */
  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
    calendar.set(Calendar.MINUTE, minute);
    callHandler(calendar);
  }

  /**
   * <p>Static method to conveniently initialize a <code>TimePickerDialogFragment</code>
   * object.</p>
   *
   * <p>The time displayed by this <code>TimePickerDialogFragment</code> is provided by the
   * <code>Calendar</code> object handed over to this method.</p>
   *
   * @param calendar Time to be displayed by this <code>TimePickerDialogFragment</code>
   * @return The newly create <code>TimePickerDialogFragment</code>.
   */
  public static TimePickerDialogFragment createDialog(Calendar calendar) {
    TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putLong(DialogConstants.CALENDAR, calendar.getTimeInMillis());
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }
}
