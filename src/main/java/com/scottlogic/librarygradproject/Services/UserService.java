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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {

    private final LibraryUserRepository userRepo;
    private final ReservationRepository reservationRepo;
    private final BorrowRepository borrowRepo;

    @Autowired
    public UserService(LibraryUserRepository userRepo, ReservationRepository reservationRepo,
                       BorrowRepository borrowRepo) {
        this.userRepo = userRepo;
        this.reservationRepo = reservationRepo;
        this.borrowRepo = borrowRepo;
    }

    public Map<String, Object> userInfo(OAuth2Authentication authentication) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userDetails", loggedIn(authentication));
        userData.put("reservations", findUserReservations(authentication));
        userData.put("borrows", findUserBorrows(authentication));
        return userData;
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
        return reservationRepo.findAllByUserId(userId);
    }

    public List<Borrow> findUserBorrows(OAuth2Authentication authentication) {
        String userId = getUserDetails(authentication).getUserId();
        return borrowRepo.findAllByUserId(userId);
    }
}

