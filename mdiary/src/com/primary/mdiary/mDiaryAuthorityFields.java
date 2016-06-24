package com.primary.mdiary;

import android.net.Uri;
import android.provider.BaseColumns;

public final class mDiaryAuthorityFields {

    public static final String AUTHORITY = "com.primary.mdiary.mdiarycontentprovider";

    private mDiaryAuthorityFields() {}
    /*�ڲ���̬��(����������)
     * Universal Resource Identifierͳһ��Դ��ʶ��
     */
    public static final class mDiariesColumns implements BaseColumns {
        
        private mDiariesColumns() {}
        /*��ʽ������contentURI
         * Uri����Ҫ����������
         * Uri.parse���ַ���ת��ΪUri
         * Ĭ�ϱ�ʾ������������
         */
        public static final Uri WHICH_URI = Uri.parse("content://" + AUTHORITY + "/diaries");

        public static final String THROUGH_DIR = "vnd.android.cursor.dir/vnd.google.diary";

        public static final String THROUGH_ITEM = "vnd.android.cursor.item/vnd.google.diary";

        public static final String SORT_ORDER = "created DESC";//����ʽ

        public static final String TITLE = "title";

        public static final String BODY = "body";

        public static final String CREATED = "created";   
    }

}
