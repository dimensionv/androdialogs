/**
 *
 */
package de.dimensionv.android.androdialogs.calendar;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import de.dimensionv.android.androdialogs.common.DialogConstants;

/**
 * @author mjoellnir
 * 
 */
public class DatePickerDialogFragment extends CalenderDialogFragment implements OnDateSetListener {

  /**
   *
   */
  public DatePickerDialogFragment() {
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
    return new DatePickerDialog(activity, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
  }

  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, monthOfYear);
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    callHandler(calendar);
  }

  public static DatePickerDialogFragment createDialog(Calendar calendar) {
    DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putLong(DialogConstants.CALENDAR, calendar.getTimeInMillis());
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

}
