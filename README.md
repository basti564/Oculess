# Oculess

## Features
- Remove Oculus / FaceBook account
- Disable telemetry

## Tutorial

### Remove Account
Video tutorial: https://www.youtube.com/watch?v=vIwUvtxd2-U
1. Click on "DISABLE COMPANION"
2. Click "OK"
3. Choose “Companion Server” from the List
4. Click “Deactivate this device admin app”
5. Restart your Quest

### Disable Telemetry [works on v29 and before v28]
Video tutorial: https://www.youtube.com/watch?v=ArXk_hob4RE
```diff 
!Warning! You won't be able to remove this app without a factory reset after running the following command
```
1. Run this command ```adb shell dpm set-device-owner com.bos.oculess/.DevAdminReceiver```
2. Click on "DISABLE TELEMETRY" in the app
3. Choose "DISABLE TELEMETRY"
