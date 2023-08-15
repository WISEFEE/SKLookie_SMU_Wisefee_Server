package com.sklookiesmu.wisefee.common.file;


import com.sklookiesmu.wisefee.common.constant.FileConstant;

public class FileUtil {
    public static boolean isImageFile(String mimeType) {
        return mimeType != null && FileConstant.FILE_MIME_TYPE_LIST_IMG.contains(mimeType);
    }
}
