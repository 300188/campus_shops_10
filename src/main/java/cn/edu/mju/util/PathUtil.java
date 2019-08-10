package cn.edu.mju.util;

import java.io.File;

public class PathUtil {

    //不同系统的分隔符
    private static String separator = System.getProperty("file.separator");

    //获取图片存放的根路径
    public static String getImgBasePath(){

        String os = System.getProperty("os.name");

        String basePath = "";

        if(os.toLowerCase().startsWith("win")){

            basePath = "D:/projectdev/image/";

        }else{
            basePath = "/home/cs/image/";
        }

        basePath = basePath.replace("/",separator);

        return basePath;

    }


    //获取店铺图片的子路径
    public static String getShopImagePath(Long shopId){

        String imagePath = "/upload/item/shop/" + shopId + "/";


        return imagePath.replace("/",separator);


    }


    public static void deleteFile(String storePath) {
        File file = new File(getImgBasePath() + storePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }



}
