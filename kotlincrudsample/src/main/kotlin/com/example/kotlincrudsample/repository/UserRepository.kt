package com.example.kotlincrudsample.repository

import org.springframework.data.repository.CrudRepository
import com.example.kotlincrudsample.model.User

interface UserRepository : CrudRepository<User, Int>