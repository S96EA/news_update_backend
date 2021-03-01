package com.news.update.payload;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.news.update.entity.Attachment;
import com.news.update.entity.Category;
import com.news.update.entity.Comments;
import com.news.update.entity.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsResponse {

    private String id;

    private  String Content;

    private String title;

    private Attachment headAttachment;

    private String youtube;

    private Long likesCount;

    private Long viewsCount;

    private Category category;

    private List<Tags> tags;

    private List<Comments> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private Date createAt;

}