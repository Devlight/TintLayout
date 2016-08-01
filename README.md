TintLayout
========== 

This library help you to achieve popular tint effect from view.

[![DevLight](https://lh4.googleusercontent.com/-9btnRFp_eVo/V5cfwZsBpMI/AAAAAAAAC4E/s4NGoezKhpAVdVofAoez1QWpzK5Na8_cQCL0B/w147-h20-no/devlight-badge.png)](http://devlight.com.ua)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TintLayout-yellow.svg?style=flat)](http://android-arsenal.com/details/1/3315)
[![Android](https://img.shields.io/badge/platform-android-brightgreen.svg?style=flat&label=Platform)](https://github.com/DevLight-Mobile-Agency)
[![Download](https://api.bintray.com/packages/gigamole/maven/tintlayout/images/download.svg)](https://bintray.com/gigamole/maven/tintlayout/_latestVersion)
[![Crates.io](https://img.shields.io/crates/l/rustc-serialize.svg?maxAge=2592000&label=License)](https://github.com/DevLight-Mobile-Agency/TintLayout/blob/master/LICENSE.txt)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/712383673b304fd6a427422ec1838db3)](https://www.codacy.com/app/gigamole53/TintLayout?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DevLight-Mobile-Agency/TintLayout&amp;utm_campaign=Badge_Grade)

![](https://lh3.googleusercontent.com/zwCLyDjZMVM2O571j6gwReHbkAolr7DV3XyKy5hqTug=s308-no)

You can check the sample app [here](https://github.com/GIGAMOLE/TintLayout/tree/master/app).

Download
------------

You can download a `.jar` from GitHub's [releases page](https://github.com/GIGAMOLE/TintLayout/releases).

Or use Gradle jCenter:

```groovy
dependencies {
    repositories {
        mavenCentral()
        maven {
            url  'http://dl.bintray.com/gigamole/maven/'
        }
    }
    compile 'com.github.gigamole.tintlayout:library:+'
}
```

Or Gradle Maven Central:

```groovy
compile 'com.github.gigamole.tintlayout:library:1.0.2'
```

Or Maven:

```groovy
<dependency>
    <groupId>com.github.gigamole.tintlayout</groupId>
    <artifactId>library</artifactId>
    <version>1.0.2</version>
    <type>aar</type>
</dependency>
```

Android SDK Version
===================

`TintLayout` requires a minimum SDK version of 11.

Sample
========

![](https://lh3.googleusercontent.com/7AnEeEK7ADxoSEP4P5UUsjThLVTPTlQM_4IDCsng6_A=w454-h667-no)

<b>Parameters</b>

You can set such parameters as:

 - colors:
 
    allows you to create shadow with transparent etc.
    
 - angle:
 
    allows you to set the angle of tint.

<b>Tips</b>

TintLayout must have child. Only one child.

The angle can only be positive and be in range from 0 to 360.

<b>Init</b>

Check out in code init:

```java
final TintLayout tintLayout = (TintLayout) findViewById(R.id.tint_layout);
tintLayout.setAngle(145);
```

And XML init:

```xml
<com.gigamole.tintlayout.lib.TintLayout
    android:id="@+id/tint_layout"
    android:layout_width="300dp"
    android:layout_height="300dp"
    android:layout_gravity="center"
    android:background="@drawable/circle"
    libs:colors="@array/tint_layout_colors">

    <com.gigamole.tintlayout.AgencyTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:gravity="center"
        android:text="@string/tint_layout_title"
        android:textColor="@color/white"
        android:textSize="50sp"
        libs:agencyFont="agency_bold" />

</com.gigamole.tintlayout.lib.TintLayout>
```
    
If you want to look at circular tint animation just remove comment block in sample from `XML` and `MainActivity`.

Getting Help
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/DevLight-Mobile-Agency/TintLayout/issues/new).

License
======
Apache 2.0 and MIT. See [LICENSE](https://github.com/DevLight-Mobile-Agency/TintLayout/blob/master/LICENSE.txt) file for details.

Author
=======

Made in [DevLight Mobile Agency](https://github.com/DevLight-Mobile-Agency)

Created by [Basil Miller](https://github.com/GIGAMOLE) - [@gigamole](mailto:gigamole53@gmail.com)
## Support on Beerpay
Hey dude! Help me out for a couple of :beers:!

[![Beerpay](https://beerpay.io/DevLight-Mobile-Agency/TintLayout/badge.svg?style=beer-square)](https://beerpay.io/DevLight-Mobile-Agency/TintLayout)  [![Beerpay](https://beerpay.io/DevLight-Mobile-Agency/TintLayout/make-wish.svg?style=flat-square)](https://beerpay.io/DevLight-Mobile-Agency/TintLayout?focus=wish)