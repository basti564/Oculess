# Oculess
### [Support Basti (Creator of Oculess, Everything but Audio) on Ko-Fi](https://ko-fi.com/basti564)
### [Support Threethan (Developed Oculess Background Audio) on Ko-Fi](https://ko-fi.com/threethandev)

> **Note**
> Some documentation is for version 1.5.0, which does not currently have a released build. This will be updated soon!


## Features
- [Disable Telemetry Apps](https://github.com/basti564/Oculess#disable-telemetry-apps)
- [Enable Audio in Background](https://github.com/basti564/Oculess#enable-background-audio)
- (v41 or lower) [Log Out OF Oculus / FaceBook account](https://github.com/basti564/Oculess#full-account-logout--disable-most-fb-stuff-disable-device-companion)

> **Note**
> The setup process for newer versions is quite involved, but it should not take too long if you follow the guide, and only needs to be performed once.
> If you're just here for background audio, you want to:
> 1. Remove Accounts
> 2. Make Oculess a "Device Owner"
> 3. Restore Meta Account
> 4. Enable Background Audio (Finally!)

## Enable Background Audio
Video tutorial: https://www.youtube.com/watch?v=aMnHgz2Zo3E *(Slightly outdated, so it doesn't include the Restore Meta Account step, but still very helpful)*
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section.
2. Press the "Background Audio for All" button in Oculess, and click okay to open accessibility settings
3. Select "Oculess" and enable it (or disable it, if you want to turn this feature off)
4. Play audio through any sideloaded android app, such as Discord or Spotify

> **Note**
> All Apps will now be allowed to play audio in the background. This functions similarly to the Background Audio experimental setting available to Quest Pro, and formerly available on Quest 2
> Music may be paused when a game is quit. If this happens, you will have to reopen the music app and click play.
> Note that apps might still occasionally be killed. You may need to reopen apps if Oculess is killed.

## Remove Accounts
This option is only needed for the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section.
> **Note**
> If the the "Remove/Check Accounts" button brings you to an empty settings page try the following ADB command to get to the Settings app and manually navigate to the Accounts section.
> monkey -p com.android.settings -c android.intent.category.LAUNCHER 1

> **Note**
> If you cannot access the app launcher after this, restart your device
> OR: open Oculess using `adb shell am start -n com.bos.Oculess/com.bos.Oculess.MainActivity` and follow the steps to [Restore Accounts](https://github.com/basti564/Oculess#restore-meta-account)

> **Warning**
> Removing a Meta account will disable some features, depending on system version.
> If you are unable to perform the restore process listed below, it can only be returned by a [Factory Reset](https://www.asurion.com/connect/tech-tips/how-to-reset-your-oculus-vr-headset/)

> **Warning**
> You will permanently delete all non-primary users on the device. You will be unable to enable library sharing unless device owner is disabled.

1. Open Settings
2. Click "Accounts"
3. If there is more than one account: Click the three-dot icon on every account but the primary account and remove it.
4. Open Oculess
5. Click on "Remove/Check Accounts" then "OK"
6. Click on every account (typically only Oculus and Meta), then "REMOVE ACCOUNT"
   (Your Oculus account should return after like 5 minutes or a restart. Meta account seems unrecoverable.)

## Make Oculess a "Device Owner"
This only needs to be done once and after that you will never need to do it again.
1. Follow the steps in the [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts) section first.
> **Warning**
> After running this command, you will be unable to uninstall the app until you open it and select "Remove Device Owner"
> **Note**
> After running this command, Oculess may show up under "Company Managed" rather than "Unknown Sources"

2. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
(if  the  command fails try to delete apps with accounts like Prime Video, VRChat, AltspaceVR, Whatsapp or similar)
3. (RECOMMENDED) Follow the steps in [Restore Meta Account](https://github.com/basti564/Oculess#restore-meta-account)

## Restore Meta Account
> **Warning**
> This might not work, be warned!
1. Open Oculess
2. Click on "Remove/Check Accounts" then "OK" *If "Meta" account is there, you do not need to restore it. Stop here!*
3. If "Oculus" account is there: Click "Oculus", then "REMOVE ACCOUNT" *Otherwise, continue*
4. Open the Meta Quest app on your phone.
5. On the phone app, navigate to Menu > Devices. Pair the Quest if prompted, then ensure it is connected.
Keep the phone and the quest on, and wait. Wake up your phone if/when it goes to sleep.
In a few minutes, both Oculus and Meta accounts should be restored. If the accounts page closes, it probably worked. Open it again using "Remove/Check Accounts" in Oculess to confirm.

## Disable Telemetry Apps
Text tutorial: https://basti564.github.io/Disable-Telemetry/

> **Note**
> As of v50, not all apps will be disabled, and doing so may cause issues with WiFi settings.
> If you encounter issues re-enable telemetry apps, then disable again if/when desired.

### Disable/Enable Telemetry Apps using Oculess
Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section first.
2. Click on "TELEMETRY" in the app
3. Choose "DISABLE TELEMETRY" (or "ENABLE TELEMETRY")

### Disable/Enable Telemetry Apps using ADB
Here's an alternative that achieves the same effect with less setup.
**Disable Telemetry Apps**
```
adb shell pm disable com.oculus.unifiedtelemetry
adb shell pm disable com.oculus.gatekeeperservice
adb shell pm disable com.oculus.notification_proxy
adb shell pm disable com.oculus.bugreporter
adb shell pm disable com.oculus.os.logcollector
adb shell pm disable com.oculus.appsafety
```
**Enable Telemetry Apps**
```
adb shell pm enable com.oculus.unifiedtelemetry
adb shell pm enable com.oculus.gatekeeperservice
adb shell pm enable com.oculus.notification_proxy
adb shell pm enable com.oculus.bugreporter
adb shell pm enable com.oculus.os.logcollector
adb shell pm enable com.oculus.appsafety
```

## Disable Device Companion
> **Note**
> This fully logs you out and disables most FB/meta stuff on **v41 or lower**.
> It can be done safely on newer versions, but doesn't seem to have much of an effect, and may be reverted at random

Text tutorial: https://basti564.github.io/Quest-Account-Logout/

Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. (Recommended) Change your system theme to light mode (sideload [Settings Shortcut](https://github.com/basti564/SettingsShortcut/releases/) and navigate to Display>Dark Theme and turn it off)
2. Open Oculess
3. Click on "DISABLE COMPANION", then "OK"
4. Choose “Companion Server” from the List
5. Click “Deactivate this device admin app” (If you're on dark mode, this is the upper of two white-on-white buttons)
6. (v41 or lower) Restart your device
(If you want your account back just restart your Quest and connect with the Oculus Phone App)

## Disable Updates
> **Warning**
> Oculess can no longer disable the updater service, as it can now make your device unusable.
> If you have a device on v42 or greater, the home menu will not load while the updater is disabled.

**Disable:** ```adb shell pm disable --user 0 com.oculus.updater```
**Enable:** ```adb shell pm enable --user 0 com.oculus.updater```

## Workaround for Multiple Users
If you have Oculess set as device owner, you cannot setup new users normally. However, they can still be added through the hidden android settings.
1. (Recommended) Change your system theme to dark mode & restart if necessary, otherwise text may be invisible.
2. Sideload [Settings Shortcut](https://github.com/basti564/SettingsShortcut/releases/
3. Open android settings using Settings Shortcut
4. Scroll down and click "System"
5. Click "Multiple Users"
6. Click "Add User" (not add guest)
7. Setup the new user as normal

## Screenshot
![image](https://github.com/basti564/Oculess/assets/12588584/22a19a1d-9300-4812-82a7-b5190de74af0)

