package com.spring.afterend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.afterend.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin    //注意这里上传下载功能要处理这个跨域问题
@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port; //获取端口号
    private static final String ip = "http://localhost";

    @PostMapping("/upload")
    //文件上传，注意有个参数需要
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //获取源文件名称
        // 定义文件得唯一标识，防止上传文件重名后覆盖
        //比如用时间戳？但是如果并发进程进程同时调用该接口得话，可能效果不好 System.currentTimeMillis()
        //所以用唯一ID标识？？
        String uuid = IdUtil.fastSimpleUUID();
        //拼装文件路径
        //存储文件得绝对路径
        //用个第三方库来上传，没必要写输入输出流 S
        String rootFilePath = System.getProperty("user.dir") + "/AfterEnd/src/main/resources/files/"+uuid+"_"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);  //把文件上传到该路径目录下
        return Result.success(ip + ":" + port + "/files/" + uuid); //返回结果url
    }

    /**
     * 文件下载
      * @param response
     * @param flag
     * @return
     */
    @GetMapping("/{flag}")
    public void getfiles(HttpServletResponse response,@PathVariable String flag){
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/AfterEnd/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}
