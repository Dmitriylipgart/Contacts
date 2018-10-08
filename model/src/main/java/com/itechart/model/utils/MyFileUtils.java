package com.itechart.model.utils;


import com.itechart.model.dto.AttachmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public class MyFileUtils {

    private final String uploadPath = System.getProperty("user.home") + File.separator + "files" + File.separator;


    public void saveFiles(MultipartFile[] files, Long contactId) {

        if(files.length > 0){
            File uploadDir = new File(uploadPath + contactId);
            if(!uploadDir.exists()){
                uploadDir.mkdirs();
            }

            for (MultipartFile file: files) {
                try {
                    file.transferTo(new File(uploadDir + File.separator + file.getOriginalFilename()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void saveAvatar(MultipartFile avatar, Long contactId)  {

        if(avatar != null){
            File uploadDir = new File(uploadPath + contactId + File.separator + "avatar");
            if(!uploadDir.exists()){
                uploadDir.mkdirs();
            }
            try {
                avatar.transferTo(new File(uploadDir  + File.separator
                                  + avatar.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteFiles(AttachmentDto[] attachments, Long contactId){
        for (AttachmentDto attachment: attachments) {
            File file = new File(uploadPath + contactId + File.separator + attachment.getFileName());
            if(file.exists()){
                file.delete();
            }
        }
    }

}
