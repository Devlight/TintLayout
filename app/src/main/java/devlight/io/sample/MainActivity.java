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


package devlight.io.sample;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final TintLayout tintLayout = (TintLayout) findViewById(R.id.tint_layout);
//
//        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0F, 360.0F);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
//        valueAnimator.setDuration(10000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
//                tintLayout.setAngle((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//        valueAnimator.start();
    }
}
