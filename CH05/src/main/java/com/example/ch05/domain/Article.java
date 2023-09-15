package com.example.ch05.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="content", nullable = false)
    private String content;

    /*
        Builder 어노테이션은 Lombok에서 지원하는 어노테이션
        생성자 위에 입력하면 빌더 패턴 방식으로 객체를 생성할 수 있어서 편리

        빌더 패턴 (디자인 패턴임) :
        - 객체를 유연하고 직관적으로 생성할 수 있어서 애용
        - 빌더 패턴을 사용하면 어느 필드에 어떤 값이 들어가는지 명시적으로 파악
    */
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
