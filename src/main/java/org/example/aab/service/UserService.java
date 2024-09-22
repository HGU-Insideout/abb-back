package org.example.aab.service;

import lombok.RequiredArgsConstructor;
import org.example.aab.domain.User;
import org.example.aab.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


}
