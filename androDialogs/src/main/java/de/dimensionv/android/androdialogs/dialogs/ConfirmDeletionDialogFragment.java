// //////////////////////////////////////////////////////////////////////////
// $Id: ConfirmDeletionDialogFragment.java,v 1.1 2013/10/12 07:29:17 mjoellnir
// Exp $
//
// Author: Volkmar Seifert
// Description:
// A confirmation-dialog specialised for deletion/removal of things. It has two
// constructors:
//
// //////////////////////////////////////////////////////////////////////////
// License:
// // // // // // // // // // // // // // // // // // // //
// Copyright 2011 Volkmar Seifert <vs@dimensionv.de>.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY VOLKMAR SEIFERT AND CONTRIBUTORS ``AS IS''
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE FOUNDATION OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
// DAMAGE.
//
// The views and conclusions contained in the software and documentation are
// those of the authors and should not be interpreted as representing official
// policies, either expressed or implied, of Volkmar Seifert <vs@dimensionv.de>.
// //////////////////////////////////////////////////////////////////////////
//
// $Log: ConfirmDeletionDialogFragment.java,v $
// Revision 1.1  2013/12/05 21:28:25  mjoellnir
// Restructuring
//
// Revision 1.1 2013/10/12 07:29:17 mjoellnir
// Initial commit of the project into the repository
//
//
// //////////////////////////////////////////////////////////////////////////
package de.dimensionv.android.androdialogs.dialogs;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.ConfirmationActionHandler;

/**
 * <p> A confirmation-dialog specialised for deletion/removal of things. It has two constructors:
 * </p> <ul> <li>A constructor to inject data (String) for specifying what should be deleted, a
 * default message will be used in that case.</li> <li>A constructor to inject the message and data
 * that should be displayed. The message has to be a valid resource-ID</li> </ul> <p> This class
 * will make use of the ConfirmationActionHandler. Should the Activity calling this dialog not
 * implement this interface, a ClassCastException will be thrown with an appropriate error-message.
 * </p>
 *
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class ConfirmDeletionDialogFragment extends BaseDialogFragment<ConfirmationActionHandler> {


  public ConfirmDeletionDialogFragment() {
    super(ConfirmationActionHandler.class);
  }

  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    int messageResourceID = arguments.getBoolean(DialogConstants.SIMPLE_MESSAGE)
                              ? R.string.dialogConfirmDeletionSimple
                              : R.string.dialogConfirmDeletion;

    String data = arguments.getString(DialogConstants.DATA);

    if(data != null) {
      String pattern = getResources().getString(arguments.getInt(DialogConstants.MESSAGE_RESOURCE_ID, messageResourceID));
      builder.setMessage(String.format(pattern, data));
    } else {
      builder.setMessage(arguments.getInt(DialogConstants.MESSAGE_RESOURCE_ID, messageResourceID));
    }
    builder.setPositiveButton(R.string.confirm, this);
    builder.setNegativeButton(R.string.discard, this);
  }

  /**
   * Constructor for injecting a data-string helping the user to verify what should be deleted. A
   * default message will be used to display the data.
   *
   * @param data
   *     the string that helps the user identify/verify the data, which deletion should be
   *     confirmed.
   */
  public static ConfirmDeletionDialogFragment createDialog(String data) {
    return createDialog(data, false);
  }

  /**
   * Constructor for injecting a data-string helping the user to verify what should be deleted. A
   * default message will be used to display the data.
   *
   * @param data
   *     the string that helps the user identify/verify the data, which deletion should be
   *     confirmed.
   */
  public static ConfirmDeletionDialogFragment createDialog(String data, boolean simpleMessage) {
    ConfirmDeletionDialogFragment dialogFragment = new ConfirmDeletionDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putString(DialogConstants.DATA, data);
    arguments.putBoolean(DialogConstants.SIMPLE_MESSAGE, simpleMessage);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

  /**
   * <p>Static method to conveniently initialize a <code>ConfirmDeletionDialogFragment</code>
   * object.</p>
   *
   * <p>This method initializes the dialog fragment with a custom message and some data that is meant
   * to specify what to actually delete.<br />
   * The message-string provided by the <code>messageResourceID</code> has to contain one
   * "<code>%s</code>" in order for the <code>data</code> to appear within it.</p>
   *
   * @param messageResourceID
   *          The message-template to be used
   * @param data
   *          the string that helps the user identify/verify the data, which
   *          deletion should be confirmed.
   *
   * @return The new ConfirmDeletionDialogFragment object.
   */
  public static ConfirmDeletionDialogFragment createDialog(int messageResourceID, String data) {
    ConfirmDeletionDialogFragment dialogFragment = createDialog(data);
    dialogFragment.getArguments().putInt(DialogConstants.MESSAGE_RESOURCE_ID, messageResourceID);
    return dialogFragment;
  }

  /**
   * <p>Static method to conveniently initialize a <code>ConfirmDeletionDialogFragment</code>
   * object.</p>
   *
   * <p>This method initializes the dialog fragment with a default message.</p>
   *
   * @return The new ConfirmDeletionDialogFragment object.
   */
  public static ConfirmDeletionDialogFragment createDialog() {
    return createDialog(null, true);
  }

  /**
   * This method will be invoked when the dialog is canceled.
   *
   * @param dialog
   *          The dialog that was canceled will be passed into the method.
   */
  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    ConfirmationActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      actionHandler.onCancel(this);
    }
  }

  @Override
  public void onClick(DialogInterface dialog, int which) {
    ConfirmationActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      switch(which) {
        case DialogInterface.BUTTON_POSITIVE: {
          actionHandler.onConfirm(this);
          break;
        }
        case DialogInterface.BUTTON_NEGATIVE: {
          actionHandler.onCancel(this);
          break;
        }
      }
    }
  }
}
