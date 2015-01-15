package de.dimensionv.android.androdialogs.calendar;

import android.app.Activity;
import android.app.DialogFragment;

import java.util.Calendar;

import de.dimensionv.android.androdialogs.handlers.CalenderDialogActionHandler;

/**
 * <p>Base-class for the date- and time-picker <code>DialogFragment</code>s.</p>
 *
 * <p>Please note that this class is intended to be used directly.</p>
 *
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
abstract class CalenderDialogFragment extends DialogFragment {

  /**
   * the mandatory handler-object
   */
  private CalenderDialogActionHandler handler = null;

  private String dialogTag = null;

  /**
   * Default constructor. Initializes the object by calling through to it's parent's constructor.
   */
  public CalenderDialogFragment() {
    super();
    dialogTag = getClass().getName() + ".TAG";
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
      throw new ClassCastException(activity.toString() + " does not implement the interface CalenderDialogActionHandler.");
    }
  }

  /**
   * Calls the mandatory handler for setting the date/time the user has provided through this
   * <code>DialogFragment</code>.
   *
   * @param calendar The selected date/time.
   */
  protected void callHandler(Calendar calendar) {
    handler.onCalenderSet(calendar);
  }

  /**
   * Returns the tag under which this dialog can be registered with the fragment manager.
   *
   * @return The default tag under which the dialog can be registered with the <code>FragmentManager</code>.
   */
  @SuppressWarnings("UnusedDeclaration")
  public String getDialogTag() {
    return dialogTag;
  }
}