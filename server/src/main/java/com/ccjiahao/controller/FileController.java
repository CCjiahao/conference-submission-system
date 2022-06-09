package com.ccjiahao.controller;


import com.ccjiahao.dto.Feedback;
import com.ccjiahao.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
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
        }
        catch (IOException e) {
            return Feedback.error(e.toString());
        }
    }
}
