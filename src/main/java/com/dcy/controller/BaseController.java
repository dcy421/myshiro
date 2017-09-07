package com.dcy.controller;

import com.dcy.config.Global;
import com.dcy.model.FileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器支持类
 * Created by Administrator on 2017/8/31.
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    private String allowSuffix = "jpg,png,gif,jpeg,bmp";//允许文件格式
    private long allowSize = 2L;//允许文件大小
    private String fileName;
    private String[] fileNames;
    private FileModel fileModel;

    public String getAllowSuffix() {
        return allowSuffix;
    }

    public void setAllowSuffix(String allowSuffix) {
        this.allowSuffix = allowSuffix;
    }

    public long getAllowSize() {
        return allowSize*1024*1024;
    }

    public void setAllowSize(long allowSize) {
        this.allowSize = allowSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModels(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    /**
     *
     * <p class="detail">
     * 功能：重新命名文件
     * </p>
     * @author wangsheng
     * @date 2014年9月25日
     * @return
     */
    private String getFileNameNew(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    private String getFileFolder(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(new Date());
    }

    /**
     *
     * <p class="detail">
     * 功能：文件上传
     * </p>
     * @author wangsheng
     * @date 2014年9月25日
     * @param files
     * @param destDir
     * @throws Exception
     */
    public void uploads(MultipartFile[] files, String destDir, HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        fileModel = new FileModel();
        try {
            fileNames = new String[files.length];
            int index = 0;
            for (MultipartFile file : files) {
                //FileModel fileModel = new FileModel();
                //文件后缀名
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                int length = getAllowSuffix().indexOf(suffix.toLowerCase());//转成小写比较
                if(file.getSize() > getAllowSize()){
                    throw new Exception("您上传的文件大小已经超出范围");
                }
                String realPath = request.getSession().getServletContext().getRealPath("/");
                File destFile = new File(realPath+ Global.FILEUPDATE+"/"+destDir+"/"+getFileFolder());
                if(!destFile.exists()){
                    destFile.mkdirs();
                }
                //新文件名
                String fileNameNew =getFileNameNew()+"."+suffix;
                fileModel = new FileModel(Global.GenerateGUID(),file.getOriginalFilename(),getFileFolder()+"/"+fileNameNew,file.getSize(),file.getContentType(),suffix);
                File f = new File(destFile.getAbsoluteFile()+"/"+fileNameNew);
                file.transferTo(f);
                f.createNewFile();

                fileNames[index++] =getFileFolder()+"/"+fileNameNew;

            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * <p class="detail">
     * 功能：文件上传   返回一个文件地址  用于头像使用
     * </p>
     * @author wangsheng
     * @date 2014年9月25日
     * @param file
     * @param destDir
     * @throws Exception
     */
    public void upload(MultipartFile file, String destDir, HttpServletRequest request) throws Exception {
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            if(file.getSize() > getAllowSize()){
                throw new Exception("您上传的文件大小已经超出范围");
            }
            String realPath = request.getSession().getServletContext().getRealPath("/");
            File destFile = new File(realPath+Global.FILEUPDATE+"/"+destDir+"/"+getFileFolder());
            if(!destFile.exists()){
                destFile.mkdirs();
            }
            String fileNameNew = getFileNameNew()+"."+suffix;
            File f = new File(destFile.getAbsoluteFile()+"/"+fileNameNew);
            file.transferTo(f);
            f.createNewFile();
            fileName = getFileFolder()+"/"+fileNameNew;
        } catch (Exception e) {
            throw e;
        }
    }
}
