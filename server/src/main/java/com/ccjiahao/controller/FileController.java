package com.ccjiahao.controller;


import com.ccjiahao.dto.Feedback;
import com.ccjiahao.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class FileController {

    private final FileMapper fileMapper;

    @Autowired
    public FileController(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    private static final String UPLOADED_FOLDER = new File(System.getProperty("user.dir")).getParent() + File.separator + "uploaded";

    @PostMapping("/api/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            return Feedback.error("上传文件为空！");
        }
        try {
            byte[] bytes = file.getBytes();
            String uuid = UUID.randomUUID().toString();
            Path dir = Paths.get(UPLOADED_FOLDER);
            Path path = Paths.get(UPLOADED_FOLDER + File.separator + uuid);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Files.write(path, bytes);
            fileMapper.insert(new com.ccjiahao.entity.File(uuid, file.getOriginalFilename()));
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("uuid", uuid);
            return Feedback.info(data);
        } catch (IOException e) {
            return Feedback.error(e.toString());
        }
    }

    @GetMapping("/api/download")
    public String download(String uuid, HttpServletResponse response) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(UPLOADED_FOLDER, uuid));
            com.ccjiahao.entity.File file = fileMapper.selectById(uuid);
            if (file == null) {
                return Feedback.error("文件id不正确！");
            }
            response.setHeader("content-disposition", "attachment; fileName=" + file.getFilename());
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] data = new byte[1024];
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            return Feedback.error(e.toString());
        }
        return Feedback.info(null);
    }
}
