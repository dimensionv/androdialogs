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
package de.dimensionv.android.androdialogs.dialogs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.NotificationActionHandler;
import de.dimensionv.android.androdialogs.interceptors.ViewInterceptor;

/**
 * <p> A simple notification-dialog with a single, neutral "OK"-close-button.</p>
 *
 * @author Volkmar Seifert
 * @version 2.0
 * @since API 1.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class NotificationDialogFragment extends BaseDialogFragment<NotificationActionHandler> {

  public NotificationDialogFragment() {
    super(NotificationActionHandler.class);
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
        NotificationActionHandler actionHandler = controller.getActionHandler();
        if(actionHandler != null) {
          actionHandler.onClose(getArguments().getInt(DialogConstants.NOTIFICATION_ID));
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
    NotificationActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      actionHandler.onClose(getArguments().getInt(DialogConstants.NOTIFICATION_ID));
    }
  }

  /**
   * This method returns the notification-id provided to this NotificationDialogFragment.
   *
   * @return the notificationID
   *
   * @since Class 1.0
   * @since API 1.0.0
   */
  public int getNotificationID() {
    return getArguments().getInt(DialogConstants.NOTIFICATION_ID);
  }

  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    Activity activity = getActivity();

    if(arguments.getInt(DialogConstants.DIALOG_RESOURCE_ID, Integer.MIN_VALUE) > Integer.MIN_VALUE) {

      boolean hasViewIntercepter = getViewInterceptor() != null;
      if(!hasViewIntercepter && (activity instanceof ViewInterceptor)) {
        setViewInterceptor((ViewInterceptor) activity);
      } else if(!hasViewIntercepter) {
        throw new IllegalStateException("Custom layout requires the implementation of a ViewInterceptor.");
      }
      LayoutInflater li = activity.getLayoutInflater();
      builder.setView(callInterceptor(li.inflate(arguments.getInt(DialogConstants.DIALOG_RESOURCE_ID), null, false)));

    } else {

      ScrollView sv = new ScrollView(activity);
      TextView tv = new TextView(activity);
      tv.setText(arguments.getInt(DialogConstants.MESSAGE_RESOURCE_ID));
      Linkify.addLinks(tv, Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);
      sv.addView(tv);
      builder.setView(sv);
    }

    int titleID = arguments.getInt(DialogConstants.TITLE_RESOURCE_ID, Integer.MIN_VALUE);
    if(titleID > Integer.MIN_VALUE) {
      builder.setTitle(arguments.getInt(DialogConstants.TITLE_RESOURCE_ID));
    }
    builder.setNeutralButton(R.string.OK, this);
  }

  /**
   * <p>Static method to conveniently initialize a {@code NotificationDialogFragment} object.</p>
   * <p/>
   * <p>This method initializes the dialog fragment with the given {@code titleID}, {@code
   * messageID} and {@code notificationID}.</p>
   *
   * @param titleID
   *     The {@link String} resource ID of the title.
   * @param messageID
   *     The {@link String} resource ID of the notification-message.
   * @param notificationID
   *     The ID of the notification. This can be any arbitrary integer number with the purpose to help you
   *     identify the notification.
   *
   * @return The new {@code NotificationDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 1.0.0
   */
  public static NotificationDialogFragment createDialog(int titleID, int messageID, int notificationID) {
    NotificationDialogFragment dialogFragment = new NotificationDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.TITLE_RESOURCE_ID, titleID);
    arguments.putInt(DialogConstants.MESSAGE_RESOURCE_ID, messageID);
    arguments.putInt(DialogConstants.NOTIFICATION_ID, notificationID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }

  /**
   * <p>Static method to conveniently initialize a {@code NotificationDialogFragment} object.</p>
   * <p/>
   * <p>This method initializes the dialog fragment with the given {@code layoutID}, {@code titleID}
   * and {@code notificationID}.</p>
   *
   * @param layoutID
   *     The resource ID of the layout that should be used instead of the default.
   * @param titleID
   *     The {@link String} resource ID of the title.
   * @param notificationID
   *     The ID of the notification. This can be any arbitrary integer number with the purpose to help you
   *     identify the notification.
   *
   * @return The new {@code NotificationDialogFragment} object.
   *
   * @since Class 2.0
   * @since API 2.0.0
   */
  public static NotificationDialogFragment createCustomDialog(int layoutID, int titleID, int notificationID) {
    NotificationDialogFragment dialogFragment = new NotificationDialogFragment();
    Bundle arguments = new Bundle();
    arguments.putInt(DialogConstants.DIALOG_RESOURCE_ID, layoutID);
    arguments.putInt(DialogConstants.TITLE_RESOURCE_ID, titleID);
    arguments.putInt(DialogConstants.NOTIFICATION_ID, notificationID);
    dialogFragment.setArguments(arguments);
    return dialogFragment;
  }
}
