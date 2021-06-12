# Oculess

## Features
- Remove Oculus / FaceBook account
- Disable telemetry

## Tutorial

### Remove Account
Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
2. Click on "DISABLE COMPANION"
3. Click "OK"
4. Choose “Companion Server” from the List
5. Click “Deactivate this device admin app”
6. Restart your Quest

### Disable Telemetry [works on v29 and before v28]
Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
1. Download and install the latest apk of from the [releases tab](https://github.com/basti564/Oculess/releases/) on your Quest
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
2. Run this command ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
3. If the command doesn't work do the [Remove Account](https://github.com/basti564/Oculess#remove-account) section first and tepeat step 1.
4. Click on "DISABLE TELEMETRY" in the app
5. Choose "DISABLE TELEMETRY"
