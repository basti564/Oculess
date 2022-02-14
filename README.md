# Oculess
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/R6R1657BK)
## Features
- (Temporarily/Partially) Remove Oculus / FaceBook account (only use for disabling updates and telemetry)
- (Fully) Log Out OF Oculus / FaceBook account (aka. Disable Oculus Companion)
- Disable telemetry
- Disable Updates
- (New) Enable Audio in Background

## Tutorial

### Full Account Logout + Disable Most FB Stuff (Disable Device Companion)
Text tutorial: https://basti564.github.io/Quest-Account-Logout/

Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Change your system theme to light mode
2. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
3. Click on "DISABLE COMPANION"
4. Click "OK"
5. Choose “Companion Server” from the List
6. Click “Deactivate this device admin app”
7. Restart your Quest
(If you want your account back just restart your Quest and connect with the Oculus Phone App)

### Disable Telemetry
Text tutorial: https://basti564.github.io/Disable-Telemetry/

Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts-temporary) section first.
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```\
**Note: After running this command, you won't be able to remove this app until you revert it with ```adb shell dpm remove-active-admin com.bos.oculess/.DevAdminReceiver```**
4. Click on "TELEMETRY" in the app
5. Choose "DISABLE TELEMETRY"

### Disable Updates
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts-temporary) section first.
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```\
**Note: After running this command, you won't be able to remove this app until you revert it with ```adb shell dpm remove-active-admin com.bos.oculess/.DevAdminReceiver```**
4. Click on "DISABLE UPDATES" in the app

### Remove Accounts (temporary)
Only use for to [disable telemetry](https://github.com/basti564/Oculess#disable-telemetry) and [updates](https://github.com/basti564/Oculess#disable-updates)
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "REMOVE ACCOUNT"
3. Click "OK"
4. Click on every account (typically only Oculus and Facebook)
5. Click “REMOVE ACCOUNT”
(Your accounts should return after like 5 minutes or a restart)

### Enable Background Audio (temporary)
1. Follow steps 1-3 of "Disable Telemetry" to set device owner. This only needs to be done once.
2. Click on "ENABLE BACKGROUND AUDIO"

Apps will now be allowed to play audio in the background, and record audio as if they have been given microphone access.
Music will be paused when a game is quit. If this happens, you will have to reopen the music app and click play.
Note that apps might still be killed. You may need to reopen apps or click the button again if oculess is killed.
Setting applies to all apps until the next reboot.

## Screenshot
![Screenshot](https://user-images.githubusercontent.com/12588584/152667664-40db8b5b-1e93-4518-836f-e1de3782a07a.jpg)
