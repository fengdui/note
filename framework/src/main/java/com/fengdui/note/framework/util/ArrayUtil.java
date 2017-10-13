package com.fengdui.note.framework.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * Created by fd on 2016/7/5.
 */
public final class ArrayUtil {
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
