package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

@RestController // 結合了 @Controller 和 @ResponseBody，表示這個類別所有方法的回傳值都會被自動序列化成 JSON。類似於 .NET 的 [ApiController]。
@RequestMapping("/api/users") // 定義這個 Controller 中所有 API 的基礎路徑 (Base Route)。類似於 .NET 的 [Route("api/users")]。
@Slf4j
public class UserController {

    // 依賴注入 (Dependency Injection)
    // Spring 會自動尋找一個 UserRepository 型別的 Bean (我們剛才定義的) 並注入進來。
    // 雖然 @Autowired 可以直接用在欄位上，但更推薦的做法是使用建構子注入 (Constructor Injection)，這有助於測試和確保不變性。
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // GET /api/users
    @GetMapping // 標示這個方法處理 HTTP GET 請求。類似 .NET 的 [HttpGet]。
    public List<User> getAllUsers() {
        log.info("Request to get all users");
        // Spring Data JPA 提供的 findAll() 方法
        return userRepository.findAll();
    }

    // GET /api/users/async
    @GetMapping("/async")
    public ResponseEntity<List<User>> getAllUsersAsync() {
        try {
            return ResponseEntity.ok().body(userService.getAllUsersAsync().get());
        } catch (InterruptedException | ExecutionException e) {
            // Log the error if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/users/{id}
    @GetMapping("/{id}")
    // @PathVariable 會將 URL 中的 {id} 值綁定到方法的 id 參數上。類似 .NET 的 [FromRoute]。
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Request to get user by id: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("User found with id: {}", id);
                    return ResponseEntity.ok().body(user);
                }) // 如果找到，回傳 200 OK 和 user 物件
                .orElseGet(() -> {
                    log.warn("User not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                }); // 如果找不到，回傳 404 Not Found
    }

    // POST /api/users
    @PostMapping // 類似 .NET 的 [HttpPost]
    // @RequestBody 會將 HTTP 請求的 body (JSON) 內容反序列化成 User 物件。類似 .NET 的 [FromBody]。
    public User createUser(@RequestBody User user) {
        log.info("Request to create user: {}", user);
        return userRepository.save(user);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/{id}") // 類似 .NET 的 [HttpDelete("{id}")]
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    log.info("Request to delete user with id: {}", id);
                    userRepository.delete(user);
                    log.info("User deleted successfully with id: {}", id);
                    return ResponseEntity.ok().build(); // 回傳 200 OK
                }).orElse(ResponseEntity.notFound().build());
    }
}