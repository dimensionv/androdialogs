/**
 *
 */
package de.dimensionv.android.androdialogs.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import de.dimensionv.android.androdialogs.common.DialogConstants;

/**
 * <code>DialogFragmet</code>-class for displaying a date picker.
 *
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class DatePickerDialogFragment extends CalenderDialogFragment implements OnDateSetListener {

  /**
   * Default constructor, initializing this class by calling through to it's parent's constructor.
   */
  public DatePickerDialogFragment() {
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
    return new DatePickerDialog(activity, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * <p>Sets the date given by the integers <code>year</code>, <code>monthOfYear</code> and
   * <code>dayOfMonth</code> using a <code>Calendar</code> object internally.</p>
   *
   * @param view The <code>DatePicker</code> object used to choose the date.
   * @param year The selected year (e.g. 2014).
   * @param monthOfYear The selected month (0-11).
   * @param dayOfMonth The selected day (1-31).
   */
  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, monthOfYear);
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    callHandler(calendar);
  }

  /**
   * <p>Static method to conveniently initialize a <code>DatePickerDialogFragment</code>
   * object.</p>
   *
   * <p>The date displayed by this <code>DatePickerDialogFragment</code> is provided by the
   * <code>Calendar</code> object handed over to this method.</p>
   *
   * @param calendar Date to be displayed by this <code>DatePickerDialogFragment</code>
   * @return The newly create <code>DatePickerDialogFragment</code>.
   */
  public static DatePickerDialogFragment createDialog(Calendar calendar) {
    DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putLong(DialogConstants.CALENDAR, calendar.getTimeInMillis());
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

}
