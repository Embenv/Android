package com.primary.mdiary;

import android.net.Uri;
import android.provider.BaseColumns;

public final class mDiaryAuthorityFields {

    public static final String AUTHORITY = "com.primary.mdiary.mdiarycontentprovider";

    private mDiaryAuthorityFields() {}
    /*内部静态类(列名辅助类)
     * Universal Resource Identifier统一资源标识符
     */
    public static final class mDiariesColumns implements BaseColumns {
        
        private mDiariesColumns() {}
        /*正式声明了contentURI
         * Uri代表要操作的数据
         * Uri.parse把字符串转换为Uri
         * 默认表示访问所有数据
         */
        public static final Uri WHICH_URI = Uri.parse("content://" + AUTHORITY + "/diaries");

        public static final String THROUGH_DIR = "vnd.android.cursor.dir/vnd.google.diary";

        public static final String THROUGH_ITEM = "vnd.android.cursor.item/vnd.google.diary";

        public static final String SORT_ORDER = "created DESC";//排序方式

        public static final String TITLE = "title";

        public static final String BODY = "body";

        public static final String CREATED = "created";   
    }

}
