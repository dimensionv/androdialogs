// //////////////////////////////////////////////////////////////////////////
// $Id: NotificationDialogFragment.java,v 1.1 2013/10/12 07:29:17 mjoellnir Exp
// $
//
// Author: Volkmar Seifert
// Description:
// A simple notification-dialog with a single, neutral "OK"-close-button.
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
// $Log: NotificationDialogFragment.java,v $
// Revision 1.1  2013/12/05 21:28:25  mjoellnir
// Restructuring
//
// Revision 1.1 2013/10/12 07:29:17 mjoellnir
// Initial commit of the project into the repository
//
//
// //////////////////////////////////////////////////////////////////////////
package de.dimensionv.android.androdialogs.general;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ScrollView;
import android.widget.TextView;

import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.NotificationActionHandler;

/**
 * <p>
 * A simple notification-dialog with a single, neutral "OK"-close-button.
 * </p>
 * 
 * @author mjoellnir
 * @version 1.0
 */
@SuppressWarnings("UnusedDeclaration")
public class NotificationDialogFragment extends BaseDialogFragment<NotificationActionHandler> {

  public NotificationDialogFragment() {
    super();
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
        controller.getActionHandler().onClose(getArguments().getInt(DialogConstants.NOTIFICATION_ID));
        break;
      }
    }
  }

  /**
   * This method returns the notification-id provided to this
   * NotificationDialogFragment.
   * 
   * @return the notificationID
   */
  public int getNotificationID() {
    return getArguments().getInt(DialogConstants.NOTIFICATION_ID);
  }

  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    Activity activity = getActivity();
    ScrollView sv = new ScrollView(activity);
    TextView tv = new TextView(activity);
    tv.setText(arguments.getInt(DialogConstants.MESSAGE));
    Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
    sv.addView(tv);
    builder.setView(sv);
    builder.setTitle(arguments.getInt(DialogConstants.TITLE));
    builder.setNeutralButton(R.string.OK, this);
  }

  public static NotificationDialogFragment createDialog(int titleID, int messageID, int notificationID) {
    NotificationDialogFragment dialogFragment = new NotificationDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.TITLE, titleID);
    arguments.putInt(DialogConstants.MESSAGE, messageID);
    arguments.putInt(DialogConstants.NOTIFICATION_ID, notificationID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }
}
