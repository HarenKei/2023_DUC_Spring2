package com.example.ch01;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Member {
    @Id //Entity는 key를 가지고 있어야 함
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="name", nullable = false) //NN
    private String name;


}
