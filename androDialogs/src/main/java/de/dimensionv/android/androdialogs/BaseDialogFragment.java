// ////////////////////////////////////////////////////////////////////////////
// $Id: BaseDialogFragment.java,v 1.2 2013/12/05 21:26:58 mjoellnir Exp $
//
// Author: Volkmar Seifert
// Description:
// An abstract base-class for easing automated dialog creation.
//
// ////////////////////////////////////////////////////////////////////////////
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
// ////////////////////////////////////////////////////////////////////////////
//
// $Log: BaseDialogFragment.java,v $
// Revision 1.2  2013/12/05 21:26:58  mjoellnir
// Bugfixes and improvements
//
// Revision 1.1 2013/10/12 07:29:17 mjoellnir
// Initial commit of the project into the repository
//
//
// ////////////////////////////////////////////////////////////////////////////
package de.dimensionv.android.androdialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import de.dimensionv.android.androdialogs.handlers.ConfirmationActionHandler;
import de.dimensionv.android.androdialogs.interceptors.ViewInterceptor;

/**
 * <p>
 * An abstract base-class for easing automated dialog creation.
 * </p>
 * <p>
 * This class will make use of the ConfirmationActionHandler. Should the
 * Activity calling this dialog not implement this interface, a
 * ClassCastException will be thrown with an appropriate error-message.
 * </p>
 * 
 * @author mjoellnir
 * @version 1.0
 */
public abstract class BaseDialogFragment extends DialogFragment implements OnClickListener {

  private ConfirmationActionHandler handler = null;
  private ViewInterceptor viewInterceptor = null;

  protected boolean registerHandler = true;

  public BaseDialogFragment(boolean registerHandler) {
    this.registerHandler = registerHandler;
  }


  /**
   * Shows an AlertDialog, set the buttons and populate the Dialog with
   * content.
   * 
   * @param savedInstanceState
   *          The last saved instance state of the Fragment, or null if this is
   *          a freshly created Fragment.
   * @return Return a new Dialog instance to be displayed by the Fragment.
   */
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    populateDialog(builder, getArguments());
    builder.setOnCancelListener(this);
    // Create the AlertDialog object and return it
    return builder.create();
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
    if((handler == null) && registerHandler) {
      try {
        handler = (ConfirmationActionHandler) activity;
      } catch(ClassCastException ex) {
        throw new ClassCastException(activity.toString() +
            " does not implement the interface ConfirmationActionHandler.");
      }
    }
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
    if(handler != null) {
      handler.onDiscard(this);
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
    if(handler == null) {
      return;
    }
    switch(which) {
      case DialogInterface.BUTTON_POSITIVE: {
        handler.onConfirm(this);
        break;
      }
      case DialogInterface.BUTTON_NEGATIVE: {
        handler.onDiscard(this);
        break;
      }
    }
  }

  /**
   * Abstract method to populate the given builder-object with appropriate
   * content.
   *
   * @param builder
   *          The Builder-object that should be populated and represents the
   *          actual dialog
   * @param arguments
   */
  protected abstract void populateDialog(Builder builder, Bundle arguments);

  /**
   * @param handler
   *          the handler to set
   */
  public void setConfirmationActionHandler(ConfirmationActionHandler handler) {
    this.handler = handler;
  }

  /**
   * @return the registerHandler
   */
  public boolean isRegisterHandler() {
    return registerHandler;
  }

  /**
   * @param registerHandler
   *          the registerHandler to set
   */
  public void setRegisterHandler(boolean registerHandler) {
    this.registerHandler = registerHandler;
  }

  public void setViewInterceptor(ViewInterceptor interceptor) {
    viewInterceptor = interceptor;
  }

  protected ViewInterceptor getViewInterceptor() {
    return viewInterceptor;
  }

  protected View callInterceptor(View view) {
    if(viewInterceptor != null) {
      viewInterceptor.intercept(view);
    }
    return view;
  }
}
