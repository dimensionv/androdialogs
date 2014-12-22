package de.dimensionv.android.androdialogs.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Base interface for all dialog fragments, to create an independency of the various build flavors.
 */
public interface DialogFragmentInterface extends DialogInterface.OnCancelListener, DialogInterface.OnClickListener {
  Activity getActivity();
  Bundle getArguments();
  void populateDialog(AlertDialog.Builder builder, Bundle arguments);
}
