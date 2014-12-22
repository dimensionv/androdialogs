// ////////////////////////////////////////////////////////////////////////////
// $Id$
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
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import de.dimensionv.android.androdialogs.common.DialogFragmentInterface;
import de.dimensionv.android.androdialogs.common.FragmentDialogController;
import de.dimensionv.android.androdialogs.handlers.ActionHandler;
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
@SuppressWarnings("UnusedDeclaration")
public abstract class BaseDialogFragment<T extends ActionHandler> extends DialogFragment implements DialogFragmentInterface {

  protected FragmentDialogController<T> controller = null;

  /**
   * <p>Default constructor, actually forwarding to <code>BaseDialogFragment(boolean registerHandler)</code>
   * with <code>registerHandler</code> being set to <code>true</code>.</p>
   */
  public BaseDialogFragment() {
    this(true);
  }

  /**
   * <p>This constructor initializes the <code>BaseDialogFragment</code>. All descendents must call
   * through to either this or the default constructor.</p>
   *
   * <p>The <code>registerHandler</code> parameter is used to allow or forbid registering the
   * parent <code>Activity</code> as an <code>ActionHandler</code>.</p>
   * <p>In case it is forbidden, the method <code>setActionHandler()</code> can (and should) be used
   * to set an <code>ActionHandler</code> manually that can actually be any class, not just the
   * parent <code>Activity</code>.</p>
   *
   * @param registerHandler Flag whether to register an <code>ActionHandler</code> or not.
   */
  public BaseDialogFragment(boolean registerHandler) {
    controller = new FragmentDialogController<>(registerHandler, this);
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
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return controller.onCreateDialog(savedInstanceState);
  }

  /**
   * Called when a fragment is first attached to its activity. onCreate(Bundle)
   * will be called after this.
   *
   * @param activity
   *          the Activity this dialog is attached to.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    controller.onAttach(activity);
  }

  /**
   * Abstract method to populate the given builder-object with appropriate
   * content.
   *
   * @param builder
   *          The Builder-object that should be populated and represents the
   *          actual dialog
   * @param arguments The arguments of the <code>DialogFragment</code>, as retrievable via <code>getArguments()</code>.
   */
  @Override
  public abstract void populateDialog(Builder builder, Bundle arguments);

  /**
   * @param handler
   *          the handler to set
   */
  public void setActionHandler(T handler) {
    controller.setActionHandler(handler);
  }

  /**
   * Returns the registerHandler flag, which is used to determine whether an <code>ActionHandler</code>
   * should be registered or not.
   *
   * @return the registerHandler
   */
  public boolean isRegisterHandler() {
    return controller.canRegisterHandler();
  }

  /**
   * Sets the flag whether to allow registering of an <code>ActionHandler</code>.
   *
   * @param registerHandler Flag whether to register a handler or not.
   *
   * @see ActionHandler
   */
  public void setRegisterHandler(boolean registerHandler) {
    controller.setRegisterHandler(registerHandler);
  }

  /**
   * <p>Sets the <code>ViewInterceptor</code> for this <code>BaseDialogFragment</code>.</p>
   *
   * @param interceptor The <code>ViewInterceptor</code> for this <code>BaseDialogFragment</code>.
   *
   * @see ViewInterceptor
   */
  public void setViewInterceptor(ViewInterceptor interceptor) {
    controller.setViewInterceptor(interceptor);
  }

  /**
   * <p>Returns the <code>ViewInterceptor</code> of this <code>BaseDialogFragment</code>.</p>
   *
   * @return The <code>ViewInterceptor</code> of this <code>BaseDialogFragment</code>.
   */
  protected ViewInterceptor getViewInterceptor() {
    return controller.getViewInterceptor();
  }

  /**
   * <p>Calls the <code>ViewInterceptor</code> of this <code>BaseDialogFragment</code>.</p>
   *
   * @param view The <code>View</code> containing this dialog's UI elements
   * @return The view that's been handed over to this method.
   */
  protected View callInterceptor(View view) {
    return controller.callInterceptor(view);
  }

  /**
   * Returns the tag under which this dialog can be registered with the fragment manager.
   *
   * @return The default tag under which the dialog can be registered with the <code>FragmentManager</code>.
   */
  public String getDialogTag() {
    return controller.getDialogTag();
  }
}
