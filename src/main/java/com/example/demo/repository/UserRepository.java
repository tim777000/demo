package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 標示這是一個 Spring 的 Repository 元件，主要用於語意化，讓 Spring 容器管理它。
// 繼承 JpaRepository<User, Long>，Spring Data JPA 就會自動提供操作 User Entity 的 CRUD 方法。
public interface UserRepository extends JpaRepository<User, Long> {
}