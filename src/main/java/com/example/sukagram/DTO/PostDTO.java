package com.example.sukagram.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class PostDTO {

    private String text;
    private List<MultipartFile> pictures;

}
