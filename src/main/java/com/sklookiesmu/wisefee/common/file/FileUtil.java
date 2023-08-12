package com.sklookiesmu.wisefee.common.file;


import com.sklookiesmu.wisefee.common.constant.FileConstant;

public class FileUtil {
    public static boolean isImageFile(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return FileConstant.FILE_MIME_TYPE_LIST_IMG.contains(fileExtension);
    }
}
