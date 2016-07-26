/*
 * Copyright (C) 2015 Basil Miller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.gigamole.tintlayout;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

//    private double i;
//    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final TintLayout tintLayout = (TintLayout) findViewById(R.id.tint_layout);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                tintLayout.setAngle(i += 5);
//
//                if (i == 360) {
//                    i = 0;
//                }
//
//                handler.postDelayed(this, 1);
//            }
//        });
    }
}
