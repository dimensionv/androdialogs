/**
 *
 */
package de.dimensionv.android.androdialogs.general;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.HintActionHandler;

/**
 * @author mjoellnir
 * 
 */
public class HintDialogFragment extends BaseDialogFragment {

  private CheckBox cbShowAgain = null;
  private HintActionHandler handler = null;

  public HintDialogFragment() {
    super(false);
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
      handler = (HintActionHandler) activity;
    } catch(ClassCastException ex) {
      throw new ClassCastException(activity.toString() +
          " does not implement the interface HintActionHandler.");
    }
  }

  /**
   * This method will be invoked when a button in the dialog is clicked.
   * 
   * @param dialog
   *          The dialog that received the click.
   * @param which
   *          The button that was clicked (e.g. BUTTON1) or the position of the
   *          item clicked.
   */
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch(which) {
      case DialogInterface.BUTTON_NEUTRAL: {
        handler.onClose(getArguments().getInt(DialogConstants.HINT_ID), cbShowAgain.isChecked());
        break;
      }
    }
  }

  @Override
  protected void populateDialog(Builder builder, Bundle arguments) {
    Activity activity = getActivity();
    View view = activity.getLayoutInflater().inflate(R.layout.hint_dialog_fragment, null);
    TextView tv = (TextView) view.findViewById(R.id.tvHintMessage);
    cbShowAgain = (CheckBox) view.findViewById(R.id.cbShowAgain);
    cbShowAgain.setChecked(true);
    tv.setText(getArguments().getInt(DialogConstants.MESSAGE));
    Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
    builder.setView(view);
    builder.setTitle(getArguments().getInt(DialogConstants.TITLE));
    builder.setNeutralButton(R.string.OK, this);
  }

  public static HintDialogFragment createDialog(int titleID, int messageID, int hintID) {
    HintDialogFragment dialogFragment = new HintDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.TITLE, titleID);
    arguments.putInt(DialogConstants.MESSAGE, messageID);
    arguments.putInt(DialogConstants.HINT_ID, hintID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

}
