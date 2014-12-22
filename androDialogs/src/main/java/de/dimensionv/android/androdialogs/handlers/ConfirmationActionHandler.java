// //////////////////////////////////////////////////////////////////////////
// $Id$
//
// Author: Volkmar Seifert
// Description:
// Interface for event-handling of the confirmation-dialog
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
// $Log: ConfirmationActionHandler.java,v $
// Revision 1.1  2013/12/05 21:28:52  mjoellnir
// Restructuring
//
// Revision 1.1  2013/10/12 07:29:17  mjoellnir
// Initial commit of the project into the repository
//
//
// //////////////////////////////////////////////////////////////////////////
package de.dimensionv.android.androdialogs.handlers;

import de.dimensionv.android.androdialogs.common.DialogFragmentInterface;

/**
 * Interface for event-handling of the confirmation-dialog
 * 
 * @author mjoellnir
 * @version 1.0
 */
public interface ConfirmationActionHandler  extends ActionHandler {
  /**
   * Event-handler method when the user presses the confirmation-button in the
   * confirmation-dialog.
   * 
   * @param dialog
   *          The Dialog in which the button was pressed.
   */
  public void onConfirm(DialogFragmentInterface dialog);

  /**
   * Event-handler method when the user presses the cancel-button in the
   * confirmation-dialog.
   * 
   * @param dialog
   *          The Dialog in which the button was pressed.
   */
  public void onDiscard(DialogFragmentInterface dialog);
}
