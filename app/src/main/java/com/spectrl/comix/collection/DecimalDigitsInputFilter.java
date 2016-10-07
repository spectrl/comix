package com.spectrl.comix.collection;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kavi @ SPECTRL Ltd. on 04/10/2016.
 */
/* See: http://stackoverflow.com/a/24632346/576316 */
public class DecimalDigitsInputFilter implements InputFilter {

    private static final int DIGITS_BEFORE_ZERO_DEFAULT = 100;
    private static final int DIGITS_AFTER_ZERO_DEFAULT = 100;

    private Pattern pattern;

    public DecimalDigitsInputFilter(Integer digitsBeforeZero, Integer digitsAfterZero) {
        int beforeZero = (digitsBeforeZero != null ? digitsBeforeZero : DIGITS_BEFORE_ZERO_DEFAULT);
        int afterZero = (digitsAfterZero != null ? digitsAfterZero : DIGITS_AFTER_ZERO_DEFAULT);
        pattern = Pattern.compile("-?[0-9]{0," + (beforeZero) + "}+((\\.[0-9]{0," + (afterZero)
                + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String replacement = source.subSequence(start, end).toString();
        String newVal = dest.subSequence(0, dstart).toString() + replacement
                + dest.subSequence(dend, dest.length()).toString();
        Matcher matcher = pattern.matcher(newVal);
        if (matcher.matches()) {
            return null;
        }

        if (TextUtils.isEmpty(source)) {
            return dest.subSequence(dstart, dend);
        } else {
            return "";
        }
    }
}
