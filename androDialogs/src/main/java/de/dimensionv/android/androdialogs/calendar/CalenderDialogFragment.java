package de.dimensionv.android.androdialogs.calendar;

import java.util.Calendar;

import de.dimensionv.android.androdialogs.handlers.CalenderDialogActionHandler;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

public class CalenderDialogFragment extends DialogFragment {

  private CalenderDialogActionHandler handler = null;

  public CalenderDialogFragment() {
    super();
  }

  /**
   * Called when a fragment is first attached to its activity. onCreate(Bundle)
   * will be called after this.
   * 
   * @param activity
   *          the Activity this dialog is attached to.
   */
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      handler = (CalenderDialogActionHandler) activity;
    } catch(ClassCastException ex) {
      throw new ClassCastException(activity.toString() +
          " does not implement the interface CalenderDialogActionHandler.");
    }
  }

  protected void callHandler(Calendar calendar) {
    handler.onCalenderSet(calendar);
  }
}