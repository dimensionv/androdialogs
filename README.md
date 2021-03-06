# AndroDialogs

## Synopsys
*AndroDialogs* is a library providing easy to use standard dialogs and
dialog-templates using DialogFragments.
It's goal is to make use of dialogs as simple as possible. By using
DialogFragments, it is ensured that the handling of the dialogs themselves
is properly done by the Android System. This means that certain side-effects
like leaked activities are less likely to happen than it was before the
introduction of the DialogFragments.

## Prebuilt Version
*AndroDialogs* is available via Maven Central. You can use it no matter
if you use maven, gradle or ant/ivy. Simply add it as a dependency into
you build-system's files.

Simply use these values:

* **Group-ID**: de.dimensionv
* **Artifact-ID**: androDialogs-nativelibs or androDialogs-supportlibs
* **Classifier**: nativelibsRelease or supportLibsRelease
* **Version**: 1.0.0-196 (You may want to check for the latest version in the repo itself...)
* **Packaging**: aar

For gradle, this would look like

```
compile 'de.dimensionv:androDialogs-nativelibs:1.0.0-196:nativelibsRelease@aar'
```
for the nativelibs flavor, and like

```
compile 'de.dimensionv:androDialogs-supportlibs:1.0.0-196:supportlibsRelease@aar'
```
for the supportlibs flavor.


For maven, this would look like

```
<dependency>
  <groupId>de.dimensionv</groupId>
  <artifactId>androDialogs-nativelibs</artifactId>
  <version>1.0.0-196</version>
  <classifier>nativelibsRelease</classifier>
  <type>aar</type>
</dependency>
```
for the nativelibs flavor, and like

```
<dependency>
  <groupId>de.dimensionv</groupId>
  <artifactId>androDialogs-supportlibs</artifactId>
  <version>1.0.0-196</version>
  <classifier>supportlibsRelease</classifier>
  <type>aar</type>
</dependency>
```
for the supportlibs flavor.

For other dependency management systems and/or build-systems, please refer to their respective documentations.

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

