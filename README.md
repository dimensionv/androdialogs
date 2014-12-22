# AndroDialogs

## Synopsys
*AndroDialogs* is a library providing easy to use standard dialogs and
dialog-templates using DialogFragments.
It's goal is to make use of dialogs as simple as possible. By using
DialogFragments, it is ensured that the handling of the dialogs themselves
is properly done by the Android System. This means that certain side-effects
like leaked activities are less likely to happen than it was before the
introduction of the DialogFragments.

## Why should I use this library
Well, honestly, I don't know. Why use any library out there at all?
Probably because it will make your life easier, once you got the hang
of the library in question.
In the end it's up to you if you like to write the same code over and
over again, or simply use a library that will take care of most things
and lets you focus on what you really want to do.

## Wait, Dialog*Fragments*? That means API-Level 11 and above!?
Yes and no. This library actually comes in two flavors, and you need to decide
on which flavor to use.

* **_Native_ Android Fragments**: this flavor only works on Android API level
  11 and above. It uses the standard native Android DialogFragments
* **_Support_ Android Fragments**: this flavor works on all Android API levels,
  if you add the support-v4 libraries to your project.

## The Dialogs provided by *AndroDialogs*
### The Standard Dialogs
The *AndroDialogs* library provides a number of ready-to-use standard dialogs:

* **NotificationDialogFragment**: A DialogFragment to display a simple message
  or notificiation
* **HintDialogFragment**: A DialogFragment to display a hint, with the option
  to not display it again.
* **ConfirmationDeletionDialogFragment** A DialogFragment for confirmation
  questions, not just for deletion, though that's the default case.
* **DatePickerDialogFragment**: A DialogFragment for selecting a date.
* **TimePickerDialogFragment**: A DialogFragment for selecting a time of day.

### The Dialog Templates
In addition to the ready-to-use standard dialogs, *AndroDialogs* also provides two
distinct templates that can be filled with any arbitrary layout to either display
information (like a progress dialog), or to actualy have the user enter some data,
which is then processed by the app.

* **DisplayDialogFragment**: A Dialog without controls (i.e. no buttons), which is
  intended to display messages and/or progress bars / information and which is
  completely controlled by the app (hence no buttons)
* **InputDialogFragment**: A DialogFragment to have the user enter arbitrary data.
  The DialogFragment requires a customized layout and lets the caller control the
  dialog-elements as well as which button was pressed via callbacks.

