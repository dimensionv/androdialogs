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
// $Log: DisplayDialogFragment.java,v $
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.dimensionv.android.androdialogs.BaseDialogFragment;
import de.dimensionv.android.androdialogs.R;
import de.dimensionv.android.androdialogs.common.DialogConstants;
import de.dimensionv.android.androdialogs.handlers.CancelActionHandler;
import de.dimensionv.android.androdialogs.interceptors.ViewInterceptor;
import de.dimensionv.android.androtools.ui.ViewTools;
import de.dimensionv.java.libraries.common.exceptions.InvalidIntegerValueException;
import de.dimensionv.java.libraries.common.utilities.strings.StringUtils;

/**
 * <p>Show a message with a progressbar in a dialog. It can be used for indicating that
 * a progress with unknown run-time is currently in progress, or to show the advancing progress of
 * an operation like copying a file or something similar.</p>
 * <p>For the latter case, if used within an {@link AsyncTask}, please note that it will take the
 * ProgressDialog a moment to settle and have all its components initialized. In case of </p>
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 2.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class ProgressDialogFragment extends BaseDialogFragment<CancelActionHandler> implements ViewInterceptor {

  /**
   * Constant for an infinite (indeterminate) progress dialog.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static final int PROGRESS_MODE_INFINITE = 0;
  /**
   * Constant for a finite (not indeterminate) progress dialog.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static final int PROGRESS_MODE_FINITE = 1;

  /**
   * Default maximum value of 100 (e.g. for percentage steps)
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static final int DEFAULT_MAX = 100;

  private static final int LAYOUT[] = new int[] {
      R.layout.progress_dialog_infinite,
      R.layout.progress_dialog_finite
  };

  private static final String MAX_VALUE = "MAXVALUE";
  private static final String PROGRESS_VALUE = "PROGRESS_VALUE";

  private ProgressBar progressBar = null;
  private TextView text = null;

  /**
   * Default constructor that creates and initializes a new instance of the
   * {@code DisplayDialogFragment}-class.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public ProgressDialogFragment() {
    super(CancelActionHandler.class, false);
  }

  /**
   * {@inheritDoc}
   * @param builder
   *          The Builder-object that should be populated and represents the
   *          actual dialog
   * @param arguments The arguments of the <code>DialogFragment</code>, as retrievable via <code>getArguments()</code>.
   */
  @Override
  public void populateDialog(Builder builder, Bundle arguments) {
    if(getViewInterceptor() == null) {
      setViewInterceptor(this);
    }
    LayoutInflater li = getActivity().getLayoutInflater();
    builder.setView(callInterceptor(li.inflate(arguments.getInt(DialogConstants.DIALOG_RESOURCE_ID), null, false)));
  }

  /**
   * <p>Creates the bare {@code ProgressDialogFragment}, with no layout or anything else
   * specified, except for the cancelability.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  private static ProgressDialogFragment createDialog(boolean cancelable) {
    ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
    // hand-over the requested cancelability...
    dialogFragment.setCancelable(cancelable);
    // set default arguments
    return dialogFragment;
  }

  /**
   * <p>Convenience method to easily create a new dialog fragment by providing the appropriate
   * resource-id.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it. The returned dialog fragment will not be cancelable by the user.</p>
   * <p>Please note that you will need to provide a {@link ViewInterceptor} via the
   * {@link #setViewInterceptor(ViewInterceptor)} method, when you use a costum layout.</p>
   *
   * @param dialogResourceID
   *     The layout resource ID which should be displayed by this dialog fragment.
   *
   * @return The new {@code ProgressDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createDialog(int dialogResourceID) {
    return createDialog(dialogResourceID, false);
  }

  /**
   * <p>Convenience method to easily create a new dialog-fragment by providing the appropriate
   * resource-id.</p>
   * <p>The method takes care of proper instantiation and initialization of the the
   * fragment, and then returns it.</p>
   * <p>Please note that you will need to provide a {@link ViewInterceptor} via the
   * {@link #setViewInterceptor(ViewInterceptor)} method, when you use a costum layout.</p>
   *
   * @param dialogResourceID
   *     The layout resource ID which should be displayed by this dialog fragment.
   * @param cancelable
   *     Whether this dialog is cancellable or not.
   *
   * @return The new {@code DisplayDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createDialog(int dialogResourceID, boolean cancelable) {
    ProgressDialogFragment dialogFragment = createDialog(cancelable);
    dialogFragment.getArguments().putInt(DialogConstants.DIALOG_RESOURCE_ID, dialogResourceID);
    return dialogFragment;
  }

  /**
   * <p>Convenience method to easily create a new dialog-fragment by providing the appropriate
   * resource-id and a {@link ViewInterceptor}.</p>
   * <p>The method takes care of proper instantiation and initialization of the the fragment, and then
   * returns it. The returned dialog fragment will not be cancelable by the user.</p>
   *
   * @param dialogResourceID
   *     The layout resource ID which should be displayed by this dialog fragment.
   * @param viewInterceptor
   *     The {@code ViewInterceptor}-object that should be used to cease control over the
   *     dialog's view-elements.
   *
   * @return The new {@code DisplayDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createDialog(int dialogResourceID, ViewInterceptor viewInterceptor) {
    ProgressDialogFragment dialogFragment = createDialog(dialogResourceID);
    dialogFragment.setViewInterceptor(viewInterceptor);
    return dialogFragment;
  }

  /**
   * <p>Convenience method to easily create a new dialog-fragment by providing the appropriate
   * resource-id and a {@link ViewInterceptor}.</p>
   * <p>The method takes care of proper instantiation and initialization of the the fragment, and then
   * returns it.</p>
   *
   * @param dialogResourceID
   *     The layout resource ID which should be displayed by this dialog fragment.
   * @param viewInterceptor
   *     The {@code ViewInterceptor}-object that should be used to cease control over the
   *     dialog's view-elements.
   * @param cancelable
   *     Whether this dialog is cancellable or not.
   *
   * @return The new {@code DisplayDialogFragment} object.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createDialog(int dialogResourceID, ViewInterceptor viewInterceptor, boolean cancelable) {
    ProgressDialogFragment dialogFragment = createDialog(dialogResourceID, cancelable);
    dialogFragment.setViewInterceptor(viewInterceptor);
    return dialogFragment;
  }

  /**
   * <p>Creates an infinitely running (a.k.a. indeterminate) {@code ProgressDialogFragment}.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it. The fragment will be an indeterminate progress dialog that displays
   * a rotating circle on the left and a default message on the right side next to the
   * circle.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createInfiniteDialog(boolean cancelable) {
    return createDialog(cancelable, PROGRESS_MODE_INFINITE);
  }

  /**
   * <p>Creates a {@code ProgressDialogFragment} with a typical horizontal progress bar.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it. A default message will be displayed above the progress bar, and the
   * progress bar will use the {@link #DEFAULT_MAX} default value.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createFiniteProgressDialog(boolean cancelable) {
    return createDialog(cancelable, PROGRESS_MODE_FINITE);
  }

  /**
   * <p>Creates a {@code ProgressDialogFragment} with a typical horizontal progress bar.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it. A default message will be displayed above the progress bar.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @param maxValue The maximum value for the progress bar.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createFiniteProgressDialog(boolean cancelable, int maxValue) {
    ProgressDialogFragment dialogFragment = createDialog(cancelable, PROGRESS_MODE_FINITE);
    dialogFragment.getArguments().putInt(MAX_VALUE, maxValue);
    return dialogFragment;
  }

  /**
   * <p>Creates a {@code ProgressDialogFragment} with a typical horizontal progress bar.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it. The progress bar will use the {@link #DEFAULT_MAX} default value.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @param message The message to be displayed in place of the default message.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createFiniteProgressDialog(boolean cancelable, String message) {
    ProgressDialogFragment dialogFragment = createDialog(cancelable, PROGRESS_MODE_FINITE);
    dialogFragment.getArguments().putString(DialogConstants.SIMPLE_MESSAGE, message);
    return dialogFragment;
  }

  /**
   * <p>Creates a {@code ProgressDialogFragment} with a typical horizontal progress bar.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @param maxValue The maximum value for the progress bar.
   * @param messageID The resource ID of the message to be displayed in place of the default message.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createFiniteProgressDialog(boolean cancelable, int maxValue, int messageID) {
    ProgressDialogFragment dialogFragment = createDialog(cancelable, PROGRESS_MODE_FINITE);
    Bundle args = dialogFragment.getArguments();
    args.putInt(DialogConstants.MESSAGE_RESOURCE_ID, messageID);
    args.putInt(MAX_VALUE, maxValue);
    return dialogFragment;
  }

  /**
   * <p>Creates a {@code ProgressDialogFragment} with a typical horizontal progress bar.</p>
   * <p>The method takes care of proper instantiation and initialization of the dialog fragment,
   * and then returns it.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @param maxValue The maximum value for the progress bar.
   * @param message The message to be displayed in place of the default message.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public static ProgressDialogFragment createFiniteProgressDialog(boolean cancelable, int maxValue, String message) {
    ProgressDialogFragment dialogFragment = createDialog(cancelable, PROGRESS_MODE_FINITE);
    Bundle args = dialogFragment.getArguments();
    args.putString(DialogConstants.SIMPLE_MESSAGE, message);
    args.putInt(MAX_VALUE, maxValue);
    return dialogFragment;
  }

  /**
   * <p>Creates a basic {@code ProgressDialogFragment}, displaying either an indeterminate or a
   * non-indeterminate progress bar with a default layout and, unless overriden, a default message.</p>
   * @param cancelable Flag that defines whether the dialog is cancelable or not.
   * @param mode The mode, must be either {@link #PROGRESS_MODE_INFINITE} or {@link #PROGRESS_MODE_FINITE}.
   * @return The newly created {@code ProgressDialogFragment}.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  private static ProgressDialogFragment createDialog(boolean cancelable, int mode) {
    if((mode != PROGRESS_MODE_INFINITE) && (mode != PROGRESS_MODE_FINITE)) {
      throw new InvalidIntegerValueException(mode);
    }

    ProgressDialogFragment dialogFragment = createDialog(cancelable);
    // make the fragment a do-it-yourself viewInterceptor...
    dialogFragment.setViewInterceptor(dialogFragment);
    // set default arguments
    Bundle args = new Bundle();

    args.putInt(DialogConstants.MODE, mode);
    args.putInt(DialogConstants.DIALOG_RESOURCE_ID, LAYOUT[mode]);

    dialogFragment.setArguments(args);

    return dialogFragment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(DialogInterface dialog, int which) {
    CancelActionHandler actionHandler = controller.getActionHandler();
    if(actionHandler != null) {
      switch(which) {
        case DialogInterface.BUTTON_NEGATIVE: {
          actionHandler.onCancel(this);
          break;
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void intercept(View view) {
    progressBar = ViewTools.findView(view, R.id.progressDialogProgressBar);
    text = ViewTools.findView(view, R.id.progressDialogText);

    Bundle args = getArguments();

    // put progress bar into correct mode if possible...
    int mode = args.getInt(DialogConstants.MODE, Integer.MIN_VALUE);
    if(mode != Integer.MIN_VALUE) {
      boolean indeterminate = mode == PROGRESS_MODE_INFINITE;
      progressBar.setIndeterminate(indeterminate);
      if(!indeterminate) {
        progressBar.setMax(args.getInt(MAX_VALUE, DEFAULT_MAX));
        progressBar.setProgress(args.getInt(PROGRESS_VALUE, 0));
      }
    }

    // set message to be displayed, if one was provided...
    String message = args.getString(DialogConstants.SIMPLE_MESSAGE);
    if(StringUtils.isEmpty(message)) {
      // check for a message resource ID...
      int msgResID = args.getInt(DialogConstants.MESSAGE_RESOURCE_ID, Integer.MIN_VALUE);
      if(msgResID != Integer.MIN_VALUE) {
        text.setText(msgResID);
      }
    } else {
      text.setText(message);
    }
  }

  /**
   * Check whether the mode is set or not. In case it's not set, an exception is thrown.
   * @throws IllegalStateException in case the mode is not set.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  private void checkForMode() {
    if(getArguments().getInt(DialogConstants.MODE, Integer.MIN_VALUE) == Integer.MIN_VALUE) {
      throw new IllegalStateException("Invalid mode for calling this method.");
    }
  }

  /**
   * Sets the maximum value on the progress bar. This method can only be used with a predefined
   * layouts. It will throw an IllegalStateException in any other case.
   * @param value the new maximum value for the progress bar.
   * @throws IllegalStateException in case the mode is not set.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public void setMax(int value) {
    checkForMode();
    getArguments().putInt(MAX_VALUE, value);
    progressBar.setMax(value);
  }

  /**
   * Sets the new progress bar value. This method can only be used with a predefined
   * layouts. It will throw an IllegalStateException in any other case.
   * @param value the new value for the progress bar.
   * @throws IllegalStateException in case the mode is not set.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public void setProgress(int value) {
    checkForMode();
    getArguments().putInt(PROGRESS_VALUE, value);
    progressBar.setProgress(value);
  }

  /**
   * Sets the new message according to the given string resource ID.
   * This method can only be used with a predefined layouts. It will throw an
   * IllegalStateException in any other case.
   * @param messageResourceID the resource ID of the message.
   * @throws IllegalStateException in case the mode is not set.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public void setMessage(int messageResourceID) {
    checkForMode();
    Bundle args = getArguments();
    args.remove(DialogConstants.SIMPLE_MESSAGE);
    args.putInt(DialogConstants.MESSAGE_RESOURCE_ID, messageResourceID);
    text.setText(messageResourceID);
  }

  /**
   * Sets the new message.
   * This method can only be used with a predefined layouts. It will throw an
   * IllegalStateException in any other case.
   * @param message the new the message.
   * @throws IllegalStateException in case the mode is not set.
   *
   * @since Class 1.0
   * @since API 2.0.0
   */
  public void setMessage(String message) {
    checkForMode();
    Bundle args = getArguments();
    args.remove(DialogConstants.MESSAGE_RESOURCE_ID);
    args.putString(DialogConstants.SIMPLE_MESSAGE, message);
    text.setText(message);
  }
}
