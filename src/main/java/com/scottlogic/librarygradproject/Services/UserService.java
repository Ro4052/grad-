package com.scottlogic.librarygradproject.Services;

import com.scottlogic.librarygradproject.Entities.Borrow;
import com.scottlogic.librarygradproject.Entities.LibraryUser;
import com.scottlogic.librarygradproject.Entities.Reservation;
import com.scottlogic.librarygradproject.Exceptions.UserNotFoundException;
import com.scottlogic.librarygradproject.Repositories.BorrowRepository;
import com.scottlogic.librarygradproject.Repositories.LibraryUserRepository;
import com.scottlogic.librarygradproject.Repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {

    private final LibraryUserRepository userRepo;
    private final ReservationRepository reservationRepository;
    private final BorrowRepository borrowRepository;

    @Autowired
    public UserService(LibraryUserRepository userRepo, ReservationRepository reservationRepository,
                       BorrowRepository borrowRepository) {
        this.userRepo = userRepo;
        this.reservationRepository = reservationRepository;
        this.borrowRepository = borrowRepository;
    }
    @SuppressWarnings("unchecked")
    public LibraryUser getUserDetails(OAuth2Authentication authentication) {
        Map<String, String> userDetails = (Map<String, String>) authentication.getUserAuthentication().getDetails();
        LibraryUser newUser = LibraryUser.builder()
                .userId(userDetails.get("login"))
                .name(userDetails.get("name"))
                .avatarUrl(userDetails.get("avatar_url"))
                .build();
        return newUser;
    }

    public List<LibraryUser> findAll() {
        return userRepo.findAll();
    }

    public LibraryUser findOne(String userId) {
        Optional<LibraryUser> user = userRepo.findById(userId);
        return user.orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void add(LibraryUser user) {
        userRepo.save(user);
    }

    public LibraryUser loggedIn(OAuth2Authentication authentication) {
        return userRepo.save(getUserDetails(authentication));
    }

    public List<Reservation> findUserReservations(OAuth2Authentication authentication) {
        String userId = getUserDetails(authentication).getUserId();
        return reservationRepository.findAllByUserId(userId);
    }

    public List<Borrow> findUserBorrows(OAuth2Authentication authentication) {
        String userId = getUserDetails(authentication).getUserId();
        return borrowRepository.findAllByUserId(userId);
    }
}

