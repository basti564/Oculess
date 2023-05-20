# Oculess (UNSAFE ON V50+)
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/R6R1657BK)
## Features
- [Disable Telemetry Apps](https://github.com/basti564/Oculess#disable-telemetry-apps)
- [Enable Audio in Background](https://github.com/basti564/Oculess#enable-background-audio)
- (v41 or lower) [Log Out OF Oculus / FaceBook account](https://github.com/basti564/Oculess#full-account-logout--disable-most-fb-stuff-disable-device-companion)

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
Since Oculess has issues with newer versions, here's an alternative that achieves the same effect.
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

## Enable Background Audio
Video tutorial: https://www.youtube.com/watch?v=aMnHgz2Zo3E
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section first.
2. Reboot the device at least once after granting device owner (Hold Power > Restart)
3. Play audio through any sideloaded android app, such as Discord or Spotify

> **Note**
> All Apps will now be allowed to play audio in the background. This functions similarly to the Background Audio experimental setting available to Quest Pro & Quest 2.
> Music may be paused when a game is quit. If this happens, you will have to reopen the music app and click play.
> Note that apps might still occasionally be killed. You may need to reopen apps or reboot again if Oculess is killed.

## Make Oculess a "Device Owner"
This only needs to be done once and after that you will never need to do it again.
1. Follow the steps in the [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts) section first.
> **Warning**
> You won't be able to uninstall the app normally after running the following command

2. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
(if  the  command fails try to delete apps with accounts like Prime Video, VRChat, AltspaceVR, Whatsapp or similar)

## Remove Accounts
This option is only needed for the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section.
> **Note**
> If the the "REMOVE ACCOUNT" button brings you to an empty settings page try the following ADB command to get to the Settings app and manually navigate to the Accounts section.
> monkey -p com.android.settings -c android.intent.category.LAUNCHER 1

> **Warning**
> Removing a Meta account seems to be permanent. It will disable some features, such as the share/screen recording interface, certain social features, and possibly more in the future.
> You can work around this by creating and setting up a second account on the device *before* performing the following steps, but **you will lose access to your primary Meta account unless you perform a [factory reset](https://www.youtube.com/watch?v=in3ex33ntAQ).**

1. Click on "REMOVE ACCOUNT"
2. Click "OK"
3. Click on every account (typically only Oculus and Meta)
4. Click “REMOVE ACCOUNT”
(Your Oculus account should return after like 5 minutes or a restart. Meta account seems unrecoverable.)

## Full Account Logout + Disable Most FB Stuff (Disable Device Companion)
> **Note**
> This only properly works on v41 or lower.
> It can be done safely on newer versions, but doesn't seem to have much of an effect.

Text tutorial: https://basti564.github.io/Quest-Account-Logout/

Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. (Recommended) Change your system theme to light mode (sideload [Settings Shortcut](https://github.com/basti564/SettingsShortcut/releases/) and navigate to Display>Dark Theme and turn it off)
2. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
3. Click on "DISABLE COMPANION"
4. Click "OK"
5. Choose “Companion Server” from the List
6. Click “Deactivate this device admin app” (If you're on dark mode, this is the upper of two white-on-white buttons)
7. Restart your Quest
(If you want your account back just restart your Quest and connect with the Oculus Phone App)

## Disable Updates
> **Warning**
> Oculess can no longer disable the updater service, as it can now make your device unusable.
> If you have a device on v42 or greater, the home menu will not load while the updater is disabled.

**Disable:** ```adb shell pm disable --user 0 com.oculus.updater```
**Enable:** ```adb shell pm enable --user 0 com.oculus.updater```


## Screenshot
![Screenshot](https://user-images.githubusercontent.com/12588584/152667664-40db8b5b-1e93-4518-836f-e1de3782a07a.jpg)

