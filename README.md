[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TintLayout-blue.svg?style=flat-square)](http://android-arsenal.com/details/1/3315)

TintLayout
===================
This library help you to achieve popular tint effect from view.

![enter image description here](https://lh3.googleusercontent.com/zwCLyDjZMVM2O571j6gwReHbkAolr7DV3XyKy5hqTug=s308-no)

U can check the sample app [here](https://github.com/GIGAMOLE/TintLayout/tree/master/app).

Download
------------

You can download a .jar from GitHub's [releases page](https://github.com/GIGAMOLE/TintLayout/releases).

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
```xml
<dependency>
    <groupId>com.github.gigamole.tintlayout</groupId>
    <artifactId>library</artifactId>
    <version>1.0.2</version>
    <type>aar</type>
</dependency>
```
Android SDK Version
=========
TintLayout requires a minimum sdk version of 11.

Sample
========

![enter image description here](https://lh3.googleusercontent.com/7AnEeEK7ADxoSEP4P5UUsjThLVTPTlQM_4IDCsng6_A=w454-h667-no)

You can set such parameters as:

 - colors:
 
    allows you to create shadow with transparent etc.
 - angle:
 
    allows you to set the angle of tint.

TintLayout must have child. Only one child.

The angle can only be positive and be in range from 0 to 360.

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

If you want to look at circular tint animation just remove comment block in sample from XML and MainActivity.

Getting Help
======

To report a specific problem or feature request, [open a new issue on Github](https://github.com/GIGAMOLE/TintLayout/issues/new).

License
======
Apache 2.0. See LICENSE file for details.


Author
=======
Basil Miller - @gigamole
