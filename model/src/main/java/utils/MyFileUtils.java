package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public class MyFileUtils {

    private String uploadPath = System.getProperty("user.home") + File.separator + "files";


    public void saveFiles(MultipartFile[] files, Long contactId) {

        if(files.length > 0){
            File uploadDir = new File(uploadPath + File.separator + contactId);
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
            File uploadDir = new File(uploadPath + File.separator + contactId + File.separator + "avatar");
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

}
