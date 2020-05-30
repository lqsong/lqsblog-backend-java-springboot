package cc.liqingsong.common.utils;

import cc.liqingsong.common.exception.CommonRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@ConfigurationProperties("lqsblog.file")
@EnableConfigurationProperties(Upload.class)
public class Upload {
    private static final Logger logger = LoggerFactory.getLogger(Upload.class);

    // 网址 - application 中进行配置
    @Value("${lqsblog.weburl}")
    private String webUrl;
    public String getWebUrl() {
        return webUrl;
    }

    // 静态资源访问路径 - application 中进行配置
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    // 获取静态地址
    public String getWebUrlStatic() {
        // 如果没有设置 文件上传目录绑定的自定义网址，上传目录网址就为自定义映射的本地网址
        if(null == uploadWeburl || uploadWeburl.equals("")) {
            return webUrl + staticPathPattern.replace("**", "");
        } else {
            return uploadWeburl;
        }
    }

    // 文件上传目录绑定的自定义网址  - application 中进行配置
    private String uploadWeburl;
    public void setUploadWeburl(String uploadWeburl) { this.uploadWeburl = uploadWeburl; }
    public String getUploadWeburl() { return uploadWeburl; }

    // 允许图片上传的类型 - application 中进行配置
    private ArrayList<String> imgType;
    public void setImgType(ArrayList<String> imgType) { this.imgType = imgType; }
    public ArrayList<String> getImgType() {  return imgType; }

    // 上传目录  - application 中进行配置
    private String uploadDir;
    public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir;  }
    public String getUploadDir() { return uploadDir; }


    // 子目录 - 自动生成
    private String subdirectory;

    // 文件在本地存储的地址
    private Path fileStorageLocation;

    public void setFileStorageLocation() {
        Date now = new Date();
        // SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String folder = yyyyMMdd.format(now);
        this.setFileStorageLocation(folder);
    }

    public void setFileStorageLocation(String subdirectory) {
        this.subdirectory = subdirectory;
        this.fileStorageLocation = Paths.get(this.uploadDir + this.subdirectory).toAbsolutePath().normalize();
    }

    /**
     * 上传创建文件
     * @param  file 文件
     * @param  type 允许上传的类型
     * @return 文件
     */
    public Map<String,String> createFile(MultipartFile file, ArrayList<String> type){
        // System.out.println(this.fileStorageLocation);

        if (file.isEmpty()) {
             throw new CommonRuntimeException(99999,"请选择上传文件");
        }

        // 获取文件类型
        String contentType = file.getContentType();

        if(null != type && type.size() > 0) {
            if(!type.contains(contentType)) {
                throw new CommonRuntimeException(99999,"文件上传类型要求：" + org.apache.tomcat.util.buf.StringUtils.join(type, ','));
            }
        }

        // 获取原文件名
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(originalFileName.contains("..")) {
                throw new CommonRuntimeException(99999,"对不起!文件名包含无效的路径序列 " +  originalFileName);
            }

            // 获取文件后缀名
            String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));

            //重新生成文件名
            String newFileName = UUID.randomUUID() + suffixName;

            // 重置上传路径
            this.setFileStorageLocation();
            // 创建目录
            Files.createDirectories(this.fileStorageLocation);

            // 将文件复制到目标位置
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> map = new HashMap<>();
            map.put("type",contentType);
            map.put("suffix",suffixName);
            map.put("originalFileName", originalFileName);
            map.put("size",String.valueOf(file.getSize())); // file.getSize()/1024 + "KB"
            map.put("fileName",newFileName);
            map.put("subDir",this.subdirectory);
            return map;
        } catch (Exception e) {
            throw new CommonRuntimeException(99999,"文件上传失败: " + e.getMessage());
        }

    }

    /**
     * 加载文件
     * @param subDir 子目录
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileResource(String subDir, String fileName) {
        if(null == subDir || "" == subDir || null == fileName || "" == fileName) {
            throw new CommonRuntimeException(99999,"参数错误");
        }

        try {
            // 重置上传路径
            this.setFileStorageLocation(subDir);
            // 获取文件路径
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new CommonRuntimeException(99999,"File not found " + fileName);
            }
        } catch (Exception e) {
            throw new CommonRuntimeException(99999,"File not found " + fileName + ". " + e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    public ResponseEntity<Resource> downloadFile(String subDir, String fileName, HttpServletRequest request) {
        Resource resource = loadFileResource(subDir, fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
