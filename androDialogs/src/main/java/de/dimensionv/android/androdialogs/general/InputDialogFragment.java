// //////////////////////////////////////////////////////////////////////////
// $Id$
//
// Author: Volkmar Seifert
// Description:
// An InputDialog is a dialog which can be used to let the user enter some data,
// when a complete activity for handling that would be overkill.
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
// $Log: InputDialogFragment.java,v $
// Revision 1.1  2013/12/05 21:28:25  mjoellnir
// Restructuring
//
// Revision 1.1 2013/10/12 07:29:17 mjoellnir
// Initial commit of the project into the repository
//
//
// //////////////////////////////////////////////////////////////////////////
package de.dimensionv.android.androdialogs.general;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.ConfirmationActionHandler;
import de.dimensionv.android.androdialogs.interceptors.ViewInterceptor;

/**
 * <p>
 * An InputDialog is a dialog which can be used to let the user enter some data,
 * when a complete activity for handling that would be overkill.
 * </p>
 *
 * @author mjoellnir
 * @version 1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class InputDialogFragment extends BaseDialogFragment<ConfirmationActionHandler> {

  public InputDialogFragment() {
    super();
  }

  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    LayoutInflater li = getActivity().getLayoutInflater();
    builder.setView(callInterceptor(li.inflate(arguments.getInt(DialogConstants.DIALOG_RESOURCE_ID), null)));
    builder.setPositiveButton(R.string.confirm, this);
    builder.setNegativeButton(R.string.discard, this);
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
      actionHandler.onDiscard(this);
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
    ConfirmationActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      switch(which) {
        case DialogInterface.BUTTON_POSITIVE: {
          actionHandler.onConfirm(this);
          break;
        }
        case DialogInterface.BUTTON_NEGATIVE: {
          actionHandler.onDiscard(this);
          break;
        }
      }
    }
  }

  public static InputDialogFragment createDialog(int dialogResourceID) {
    InputDialogFragment dialogFragment = new InputDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.DIALOG_RESOURCE_ID, dialogResourceID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

  public static InputDialogFragment createDialog(int dialogResourceID, ViewInterceptor viewInterceptor) {
    InputDialogFragment dialogFragment = createDialog(dialogResourceID);
    dialogFragment.setViewInterceptor(viewInterceptor);
    return dialogFragment;
  }
}