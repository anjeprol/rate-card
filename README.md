# Rate Card Library

#### You can easily add the alert dialog to your app. 

![SignatureCaption](/img/rateCard.gif)

##### Description:

This library handle all the UI from alert dialog, the developer only needs to be worry for implement it. 

### Java Documentation

[Download java doc project]()

### Versioning support

| Version | Level |
|  :---:  | :---: |
|Version | 1.0.0  |
|SDK Target Version | Pie (Api level 28)|
|SDK Minimal Support | Lollipop (Api level 21)|


### Features 

| Feature | Status |
|  :---:  | :---: |
|Internationalization (en/es) | ✓ |
|Multi screen support| ✓ |
|self-configuration color from system | ✓ |

#### 1. Add repository Gradle: 

```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://ic.amick.mx/nexus/repository/maven-public/"
        }
    }
```
**Path file:** `../yourApp/build.gradle`

#### 2. Add dependency

```gralde
dependencies {
    ...
    implementation 'com.amk.android:rate-card-and:1.0.0-20180919.162430-1'
    ...
}
```

#### 3. Add it into your activity or fragment
First add the import rate at your class

```java
import com.amk.ratecard.Rate;

```

##### Then create an object in order to implement it

```java
    private Rate mRateDialog;
```

#### Call the alert dialog.
```java
...

/**
* Constructor from Rate class, where it needs the activity source and int resource from image
* profile to ve cropped.
*
* @param context  Activity source where is called the class.
* @param resource Int id from mipmap resource.
*/

mRateDialog = new Rate(MainActivity.this, R.mipmap.im_sugus);
mRateDialog.showDialogRate("AC Installation", "How was your service?");

...

```

**Note:** `In case you can't or don't have the image, use the resource for empty picture  mRateDialog.NO_IMAGE or use your custom one.`

#### Get the rate value
Declare as member the braodcas receiver
```java
    /**
     * Broadcast receiver object to catch up rate from alert dialog.
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            // Get extra data included in the Intent
            final int rate = intent.getIntExtra(mRateDialog.RATE, 0);
            Log.d("receiver", "Got message: " + rate);
        }
    };

```

#### Register local broadcast receiver at the method onCreate
```java
@Override
    protected void onCreate(final Bundle savedInstanceState) {
    ...

    LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter(mRateDialog.EVENT_RATE));
    ...
}
```

#### Unregister local broadcast at onDrestroy method

```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
```

### Set custom color attributes

_For custom colors for stars rates add it at color.xml_
```xml
    <color name="star_border_color">...</color>
    <color name="start_fill_color">...</color>
```

### Set custom stars size from dialog. 

_You only need to add the next values at dimens.xml_

```xml
    <dimen name="star_square">...</dimen>
```

_Set custom text size from alert._
```xml
    <dimen name="text_size_alert">...</dimen>
```

### Issues :alien:
---
None registered 

### Warnings :warning:
---

### Functions included

| functionalities | Description | Returns |
|  :---  | :--- | :---: |
|showDialogRate(String service, String survey) | Display the alert dialog, the `service`, is to set up the service provided, and `survey` is the question of how do you rate it.| Void|


### Clone project example

```git
git clone ssh://git@ic.amick.mx:1022/antonioPrado/test-lib-ratecard.git
```

### Download APK 

[Try me](/apk/rateCard.apk)
