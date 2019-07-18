package com.building.ipi.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Toning
 * @Description: 附件上传
 * @date 2019/3/27
 */
public class FileUploadUtil {

    private static final Logger log = Logger.getLogger(FileUploadUtil.class);

    private static final String UPLOAD = "/upload/import/";

    private FileUploadUtil() {

    }

    public static Map fileUpload(HttpServletRequest request) {
        Map<String, Map> attachMap = new HashMap<>();

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    //按命名规则生成 新的文件名
                    String fileName = getFileName(file.getOriginalFilename());
                    String path = FileServerUtil.fileServerFilePath + UPLOAD;
                    //如果没有路径，先创建路径
                    File parent = new File(path);
                    if (!parent.exists() || !parent.isDirectory()) {
                        parent.mkdirs();
                    }
                    path += fileName;
                    //获取文件信息，附件表需求
                    Map<String, String> fileMap = new HashMap<>();
                    //文件的扩展名/即文件类型
                    fileMap.put("fileExt", getExtName(file.getOriginalFilename()));
                    //上传文件的原文件名，带扩展名
                    fileMap.put("originalFileName", file.getOriginalFilename());
                    //上传后的文件名，带扩展名
                    fileMap.put("fileName", fileName);
                    //服务器上存放的物理路径
                    fileMap.put("filePath", path);
                    //字节数。指上传后的文件大小
                    fileMap.put("fileSize", file.getSize() + "");
                    //url虚拟路径
                    fileMap.put("virtualPath", FileServerUtil.fileServerUrl + UPLOAD + fileName);

                    //上传
                    try {
                        file.transferTo(new File(path));
                        attachMap.put(file.getName(), fileMap);
                    } catch (Exception e) {
                        log.info("---------------------------------文件上传失败！");
                        log.error("FileOperationUtils类-fileUpload方法-Exception异常信息输出：", e);
                    }
                }
            }
        }
        return attachMap;

    }

    //按命名规则生成 新的文件名
    public static String getFileName(String strOriginalFileName) {
        String fileExt = getExtName(strOriginalFileName);
        String strOriginalFileNameNoExt = getFileNameExceptExt(strOriginalFileName);
        //当前毫秒数
        long currentTimeMillisLong = System.currentTimeMillis();
        String strCurrentTimeMillis = Long.toString(currentTimeMillisLong);
        String strFileNamePinyin = "";
        String strFileNamePinyinAll = "";
        //中文转拼音
        strFileNamePinyinAll = Pinyin4jUtil.converterToSpell(strOriginalFileNameNoExt);
        //全角转半角
        strFileNamePinyinAll = StringUtil.ToDBC(strFileNamePinyinAll);

        //多音字进行处理-返回结果都是用逗号隔开，使用第一个返回结果
        int posDouhao = strFileNamePinyinAll.indexOf(',');
        if (posDouhao > -1) {
            strFileNamePinyin = strFileNamePinyinAll.substring(0, posDouhao);
        } else {
            strFileNamePinyin = strFileNamePinyinAll;
        }

        //上传后文件名
        return strFileNamePinyin + "_" + strCurrentTimeMillis + fileExt;
    }

    //获取除扩展名之外的文件名
    public static String getFileNameExceptExt(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return "";
    }

    //获取扩展名
    public static String getExtName(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return "";
    }

}
