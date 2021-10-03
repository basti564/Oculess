# Oculess

## Features
- Remove Oculus / FaceBook account
- Disable Oculus Companion
- Disable telemetry
- Disable Updates

## Tutorial

### Remove Accounts
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "REMOVE ACCOUNT"
3. Click "OK"
4. Click on every account (typically only Oculus and Facebook)
5. Click “REMOVE ACCOUNT”
(If you want your accounts back just restart your Quest and connect with the Oculus Phone App)

### Disable Telemetry
Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts) section first.
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
4. Click on "DISABLE TELEMETRY" in the app
5. Choose "DISABLE TELEMETRY"

### Disable Updates
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Follow the steps in [Remove Accounts](https://github.com/basti564/Oculess#remove-accounts) section first.
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
3. Run this command (if you haven't before) ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
4. Click on "DISABLE UPDATES" in the app

### Disable Device Companion
Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "DISABLE COMPANION"
3. Click "OK"
4. Choose “Companion Server” from the List
5. Click “Deactivate this device admin app”
6. Restart your Quest
