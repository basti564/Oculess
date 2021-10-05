# Oculess

## Features
- (Temporarily/Partially) Remove Oculus / FaceBook account (only use for disabeling updates and telemetry)
- (Fully) Log Out OF Oculus / FaceBook account (aka. Disable Oculus Companion)
- Disable telemetry
- Disable Updates

## Tutorial

### Full Account Logout + Disable Most FB Stuff (Disable Device Companion)
Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "DISABLE COMPANION"
3. Click "OK"
4. Choose “Companion Server” from the List
5. Click “Deactivate this device admin app”
6. Restart your Quest
(If you want your account back just restart your Quest and connect with the Oculus Phone App)

### Disable Telemetry
Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts-temporary) section first.
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
4. Click on "TELEMETRY" in the app
5. Choose "DISABLE TELEMETRY"

### Disable Updates
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts-temporary) section first.
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
4. Click on "DISABLE UPDATES" in the app

### Remove Accounts (temporary)
Only use for to [disable telemetry](https://github.com/basti564/Oculess#disable-telemetry) and [updates](https://github.com/basti564/Oculess#disable-updates)
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "REMOVE ACCOUNT"
3. Click "OK"
4. Click on every account (typically only Oculus and Facebook)
5. Click “REMOVE ACCOUNT”
(Your accounts should return after like 5 minutes or a restart)

## Screenshot
![Screenshot](https://user-images.githubusercontent.com/34898868/135829932-f043a990-3fdc-433e-8eb4-e6f34d997e52.png)
