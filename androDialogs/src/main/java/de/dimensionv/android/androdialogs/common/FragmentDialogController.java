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
package de.dimensionv.android.androdialogs.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.ParameterizedType;

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
 * @author Volkmar Seifert
 * @version 1.0
 * @since API 1.0.0
 */
@SuppressWarnings("UnusedDeclaration")
public class FragmentDialogController<T extends ActionHandler> {

  private String dialogTag = null;
  private ViewInterceptor viewInterceptor = null;
  private DialogFragmentInterface parent = null;

  private T actionHandler = null;
  private boolean registerHandler = true;
  private Class typeClass = null;

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
  public FragmentDialogController(boolean registerHandler, DialogFragmentInterface parent) {
    this.registerHandler = registerHandler;
    this.parent = parent;
    dialogTag = parent.getClass().getName() + ".TAG";
    typeClass = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
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
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    Builder builder = new Builder(parent.getActivity());
    parent.populateDialog(builder, parent.getArguments());
    builder.setOnCancelListener(parent);
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
  @SuppressWarnings("unchecked")
  public void onAttach(Activity activity) {
    if((actionHandler == null) && registerHandler) {
      try {
        actionHandler = (T) activity;
      } catch(ClassCastException ex) {
        throw new ClassCastException(String.format("%s does not implement an %s interface.", activity.getClass().getName(), typeClass.getSimpleName()));
      }
    }
  }

  /**
   * Sets the <code>ActionHandler</code> explicitly.
   *
   * @param handler the <code>ActionHandler</code> to set
   */
  public void setActionHandler(T handler) {
    this.actionHandler = handler;
  }

  /**
   * Returns the registerHandler flag, which is used to determine whether an <code>ActionHandler</code>
   * should be registered or not.
   *
   * @return the registerHandler
   */
  public boolean canRegisterHandler() {
    return registerHandler;
  }

  /**
   * Sets the flag whether to allow automatic implicit registering of an <code>ActionHandler</code>.
   *
   * @param registerHandler Flag whether to register an <code>ActionHandler</code> or not.
   *
   * @see ActionHandler
   */
  public void setRegisterHandler(boolean registerHandler) {
    this.registerHandler = registerHandler;
  }

  /**
   * <p>Sets the <code>ViewInterceptor</code> for the parent dialog fragment.</p>
   *
   * @param interceptor The <code>ViewInterceptor</code> for the parent dialog fragment.
   *
   * @see ViewInterceptor
   */
  public void setViewInterceptor(ViewInterceptor interceptor) {
    viewInterceptor = interceptor;
  }

  /**
   * <p>Returns the <code>ViewInterceptor</code> of the parent dialog.</p>
   *
   * @return The <code>ViewInterceptor</code> of the parent dialog fragment.
   */
  public ViewInterceptor getViewInterceptor() {
    return viewInterceptor;
  }

  /**
   * <p>Calls the <code>ViewInterceptor</code> of the parent dialog fragment.</p>
   *
   * @param view The <code>View</code> containing this dialog's UI elements
   * @return The view that's been handed over to this method.
   */
  public View callInterceptor(View view) {
    if(viewInterceptor != null) {
      viewInterceptor.intercept(view);
    }
    return view;
  }

  /**
   * Returns the tag under which the parent dialog can be registered with the fragment manager.
   *
   * @return The default tag under which the parent dialog can be registered with the <code>FragmentManager</code>.
   */
  public String getDialogTag() {
    return dialogTag;
  }

  /**
   * Returns the currently set <code>ActionHandler</code>, or null if none was set.
   *
   * @return The <code>ActionHandler</code> of the parent dialog, or null if none was set.
   */
  public T getActionHandler() {
    return actionHandler;
  }
}
