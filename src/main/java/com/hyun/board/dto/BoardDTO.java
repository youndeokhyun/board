package com.hyun.board.dto;

import com.hyun.board.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private int count;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public BoardEntity toEntity(){
        return BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .count(count)
                .build();
    }

    @Builder
    public BoardDTO(Long id, String title , String content , int count , LocalDateTime createdDate , LocalDateTime modifiedDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.count = count;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
