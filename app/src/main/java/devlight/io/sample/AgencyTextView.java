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

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by GIGAMOLE on 22.06.2015.
 */
public class AgencyTextView extends TextView {

    private static final String SCHEME = "http://schemas.android.com/apk/res-auto";
    private static final String ATTRIBUTE = "agencyFont";

    static enum AgencyFont {
        AGENCY_THIN("fonts/AGENCYR.TTF"),
        AGENCY_BOLD("fonts/AGENCYB.TTF");

        private final String fileName;

        AgencyFont(String fileName) {
            this.fileName = fileName;
        }

        static AgencyFont fromString(String fontName) {
            return AgencyFont.valueOf(fontName.toUpperCase(Locale.US));
        }

        public Typeface asTypeface(Context context) {
            return Typeface.createFromAsset(context.getAssets(), fileName);
        }
    }

    public AgencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        } else {
            final String fontName = attrs.getAttributeValue(SCHEME, ATTRIBUTE);

            if (fontName == null) {
                throw new IllegalArgumentException("You must provide \"" + ATTRIBUTE + "\" for your text view");
            } else {
                final Typeface customTypeface = AgencyFont.fromString(fontName).asTypeface(context);
                setTypeface(customTypeface);
            }
        }
    }
}