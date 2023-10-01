package com.example.kotlincrudsample.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.kotlincrudsample.model.User
import com.example.kotlincrudsample.repository.UserRepository

@RestController
@RequestMapping("/api/users")
class UserController(@Autowired private val userRepository: UserRepository){

    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val createdUser = userRepository.save(user)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }

    @GetMapping("")
    fun getAllUsers(): List<User> =
        userRepository.findAll().toList()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null)
            ResponseEntity(user, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val existingUser = userRepository.findById(userId).orElse(null)
        
        if (existingUser == null)
            return ResponseEntity(HttpStatus.NOT_FOUND)
        
        val newUser = existingUser.copy(name = user.name, email = user.email)
        userRepository.save(newUser)
        return ResponseEntity(newUser, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        if (!userRepository.existsById(userId))
            return ResponseEntity(HttpStatus.NOT_FOUND)
        
        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}