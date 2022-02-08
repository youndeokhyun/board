package com.hyun.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table( name= "board")
public class BoardEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100 , nullable= false)
    private String title;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String content;

    @Column(length = 10)
    @ColumnDefault("0") //default 0
    private int count;


    @Builder
    public BoardEntity(Long id , String title , String content , int count){
        this.id =id;
        this.title= title;
        this.content =content;
        this.count =count;
    }
}
