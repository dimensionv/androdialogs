/**
 *
 */
package de.dimensionv.android.androdialogs.dialogs;

import android.annotation.SuppressLint;
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
 * <p>A simple dialog for showing hints and tips with a single, neutral "OK"-close-button.</p>
 *
 * @author Volkmar Seifert
 * @version 2.0
 * @since API 1.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class HintDialogFragment extends BaseDialogFragment<HintActionHandler> {

  private CheckBox cbShowAgain = null;

  public HintDialogFragment() {
    super(HintActionHandler.class);
  }

  /**
   * This method will be invoked when a button in the dialog is clicked.
   *
   * @param dialog
   *     The dialog that received the click.
   * @param which
   *     The button that was clicked (e.g. BUTTON1) or the position of the item clicked.
   *
   * @since Class 1.0
   * @since API 1.0.0
   */
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch(which) {
      case DialogInterface.BUTTON_NEUTRAL: {
        HintActionHandler actionHandler = controller.getActionHandler();
        if(actionHandler != null) {
          actionHandler.onClose(getArguments().getInt(DialogConstants.HINT_ID), cbShowAgain.isChecked());
        }
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   *
   * @since Class 2.0
   * @since API 2.0.0
   */
  @Override
  public void onCancel(DialogInterface dialog) {
    HintActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      actionHandler.onClose(getArguments().getInt(DialogConstants.HINT_ID), false);
    }
  }

  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    Activity activity = getActivity();
    @SuppressLint("InflateParams")
    View view = activity.getLayoutInflater().inflate(R.layout.hint_dialog_fragment, null);
    TextView tv = (TextView) view.findViewById(R.id.tvHintMessage);
    cbShowAgain = (CheckBox) view.findViewById(R.id.cbShowAgain);
    cbShowAgain.setChecked(true);
    tv.setText(getArguments().getInt(DialogConstants.MESSAGE_RESOURCE_ID));
    Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
    builder.setView(view);
    builder.setTitle(getArguments().getInt(DialogConstants.TITLE_RESOURCE_ID));
    builder.setNeutralButton(R.string.OK, this);
  }

  /**
   * <p>Static method to conveniently initialize a {@code HintDialogFragment} object.</p>
   * <p/>
   * <p>This method initializes the dialog fragment with the given {@code title}, {@code messageID}
   * and {@code hintID}.</p>
   *
   * @param titleID
   *     The {@link String} resource ID of the title.
   * @param messageID
   *     The {@link String} resource ID of the hint-message.
   * @param hintID
   *     The ID of the hint. This can be any arbitrary integer number with the purpose to help you
   *     identify the hint.
   *
   * @return The new {@code HintDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 1.0.0
   */
  public static HintDialogFragment createDialog(int titleID, int messageID, int hintID) {
    HintDialogFragment dialogFragment = new HintDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.TITLE_RESOURCE_ID, titleID);
    arguments.putInt(DialogConstants.MESSAGE_RESOURCE_ID, messageID);
    arguments.putInt(DialogConstants.HINT_ID, hintID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

}
