package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity // 告訴 JPA 這是一個 Entity Class，它會對應到資料庫中的一個資料表。在 .NET 中，這類似於 EF Core 的 POCO class。
@Table(name = "users") // 可選：指定對應的資料表名稱。如果省略，預設會使用 class 名稱 (user)。類似 EF Core 的 [Table("users")]。
@Getter
@Setter
public class User {

    @Id // 標示這個欄位是主鍵 (Primary Key)。類似 EF Core 的 [Key]。
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 告訴 JPA 主鍵的生成策略。IDENTITY 表示讓資料庫自動增長。
    private Long id;

    private String name;
    private String email;

    // JPA 需要一個無參數的建構子
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}