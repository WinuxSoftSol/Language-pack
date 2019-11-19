package com.winux.languagepack.util;

import android.content.Context;

public class ResourceCreator {

    public static final String DEF_TYPE_STRING = "string";
    public static final String DEF_TYPE_COLOR = "color";
    public static final String DEF_TYPE_DRAWABLE = "drawable";
    public static final String DEF_TYPE_STYLE = "style";
    public static final String DEF_TYPE_RAW = "raw";


    /**
     * create resource from string value
     *
     * @param resourceName resource name (make sure same resource name is available in res folder)
     * @param defType      define type of resource (string, color, drawable, style)
     * @return resource id
     */
    public static int create(Context context, String resourceName, String defType) {
        return context.getResources()
                .getIdentifier(resourceName, defType, context.getPackageName());
    }

}
