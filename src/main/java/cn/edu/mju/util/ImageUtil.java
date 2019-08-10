package cn.edu.mju.util;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
* 图片处理工具类
* */
public class ImageUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random r = new Random();

    public static String generateThumbnail(CommonsMultipartFile thumbnail,String targetAddr){

        //图片真实名称
        String realFileName = getRandomFileName();

        //扩展名
        String extension = getFileExtension(thumbnail);

        makeDirPath(targetAddr);

        //相对路径
        String relativeAddr = targetAddr + realFileName + extension;

        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        try{
            Thumbnails.of(thumbnail.getInputStream()).size(200,200)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }

        return relativeAddr;
    }

    public static String generateNormalImg(CommonsMultipartFile thumbnail,String targetAddr){

        //图片真实名称
        String realFileName = getRandomFileName();

        //扩展名
        String extension = getFileExtension(thumbnail);

        makeDirPath(targetAddr);

        //相对路径
        String relativeAddr = targetAddr + realFileName + extension;

        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);

        try{
            Thumbnails.of(thumbnail.getInputStream()).size(337,640)
                    .outputQuality(0.9f).toFile(dest);
        }catch (IOException e){
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }

        return relativeAddr;
    }

    /*
    * 创建目标路径所涉及到的目录
    * */
    private static void makeDirPath(String targetAddr) {

        String realFimeParentPath = PathUtil.getImgBasePath() + targetAddr;

        File dirPath =  new File(realFimeParentPath);

        if(!dirPath.exists()){

            dirPath.mkdirs();

        }
    }


    /*
    * 获取输入文件流的扩展名
    *
    * */
    private static String getFileExtension(CommonsMultipartFile cFile) {

        String originalFileName = cFile.getOriginalFilename();

        return originalFileName.substring(originalFileName.lastIndexOf("."));

    }



    /*
    * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
    * */
    private static String getRandomFileName() {

        //获取随机的五位数
        int rannum = r.nextInt(89999);

        String nowTimeStr = sdf.format(new Date());

        return nowTimeStr + rannum;


    }


    /*
    *
    * storePath 是文件路径还是目录路径
    * 文件：删除
    * 目录：删除目录下的所有文件
    * */

    public static void deleteFileOrPath(String storePath){

        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);

        if(fileOrPath.exists()){
            File files[] = fileOrPath.listFiles();
            for(int i= 0;i<files.length;i++){
                files[i].delete();
            }
        }
        fileOrPath.delete();

    }


}
