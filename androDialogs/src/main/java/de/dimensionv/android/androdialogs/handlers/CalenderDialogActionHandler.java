/**
 *
 */
package de.dimensionv.android.androdialogs.handlers;

import java.util.Calendar;

/**
 * <p>The <code>ActionHandler</code>-interface for <code>Calendar</code> specific operations.</p>
 * <p>It's specifically meant to get the date/time information of Date/Time dialogs after the user
 * has confirmed his selection.</p>
 *
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
public interface CalenderDialogActionHandler extends ActionHandler {
  /**
   * Called when the date is actually set from the Date/Time picker dialog.
   *
   * @param calendar The calendar-object holding the date/time information selected by the user.
   */
  public void onCalenderSet(Calendar calendar);
}
