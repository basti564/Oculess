# Oculess
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/R6R1657BK)
## Features
- (Fully) [Log Out OF Oculus / FaceBook account](https://github.com/basti564/Oculess#full-account-logout--disable-most-fb-stuff-disable-device-companion)
- [Disable telemetry](https://github.com/basti564/Oculess#disable-telemetry)
- [Disable Updates](https://github.com/basti564/Oculess#disable-updates)
- (New) [Enable Audio in Background](https://github.com/basti564/Oculess#enable-background-audio)

## Full Account Logout + Disable Most FB Stuff (Disable Device Companion)
Text tutorial: https://basti564.github.io/Quest-Account-Logout/

Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Change your system theme to light mode (sideload [Settings Shortcut](https://github.com/basti564/SettingsShortcut/releases/) and navigate to Display>Dark Theme and turn it off)
2. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
3. Click on "DISABLE COMPANION"
4. Click "OK"
5. Choose “Companion Server” from the List
6. Click “Deactivate this device admin app”
7. Restart your Quest
(If you want your account back just restart your Quest and connect with the Oculus Phone App)

## Disable Telemetry
Text tutorial: https://basti564.github.io/Disable-Telemetry/

Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section first.
2. Click on "TELEMETRY" in the app
3. Choose "DISABLE TELEMETRY"

## Disable Updates
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section first.
2. Click on "DISABLE UPDATES" in the app

## Enable Background Audio
Video tutorial: https://www.youtube.com/watch?v=aMnHgz2Zo3E
1. Follow the steps in the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section first.
2. Click on "ENABLE BACKGROUND AUDIO" (you will need to repeat this step after each reboot)

All Apps will now be allowed to play audio in the background, and record audio as if they have been given microphone access.
Music will be paused when a game is quit. If this happens, you will have to reopen the music app and click play.
Note that apps might still be killed. You may need to reopen apps or click the button again if oculess is killed.

## Make Oculess a "Device Owner"
This only needs to be done once and after that you will never need to do it again.
1. Follow the steps in the [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts-temporary) section first.
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
2. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```

## Remove Accounts (temporary)
This option is only needed for the [Make Oculess a "Device Owner"](https://github.com/basti564/Oculess#make-oculess-a-device-owner) section.
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "REMOVE ACCOUNT"
3. Click "OK"
4. Click on every account (typically only Oculus and Facebook) <-- Oculus Removed this section in the latest v38 version, you will not be able to proceed if you are on it.
5. Click “REMOVE ACCOUNT”
(Your accounts should return after like 5 minutes or a restart)

## Screenshot
![Screenshot](https://user-images.githubusercontent.com/12588584/152667664-40db8b5b-1e93-4518-836f-e1de3782a07a.jpg)
