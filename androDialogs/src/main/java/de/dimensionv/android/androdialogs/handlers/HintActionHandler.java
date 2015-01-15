/**
 *
 */
package de.dimensionv.android.androdialogs.handlers;

/**
 * Interface for event-handling of the hint-dialog
 *
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
public interface HintActionHandler extends ActionHandler {

  /**
   * This method is called when the <code>HintDialog</code> is closed. It will receive the hint-ID
   * and a flag whether to show the hint again, next time, or not.
   *
   * @param hintID The ID of the hint.
   * @param showAgain Flag, whether to show the hint again or not.
   */
  void onClose(int hintID, boolean showAgain);

}
