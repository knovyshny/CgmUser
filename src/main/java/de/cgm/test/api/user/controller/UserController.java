package de.cgm.test.api.user.controller;

import de.cgm.test.api.user.model.dto.UserIncomingDto;
import de.cgm.test.api.user.model.dto.UserOutcomingDto;
import de.cgm.test.api.user.model.entity.User;
import de.cgm.test.base.controller.BaseCRUDController;
import de.cgm.test.api.user.model.dto.AuthentificationDto;
import de.cgm.test.api.user.service.IUserService;
import de.cgm.test.base.lang.util.IPasswordHashService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController extends BaseCRUDController<UserIncomingDto, UserOutcomingDto, User> {


    private final IPasswordHashService passwordHashService;
    private final IUserService userService;

    public UserController(IPasswordHashService passwordHashService, IUserService userService) {
        super(userService);
        this.userService = userService;
        this.passwordHashService = passwordHashService;
    }

    @Override
    @GetMapping(value="/users", produces = "application/json")
    public ResponseEntity<List<UserOutcomingDto>> findAllIncomingDto() {
        return super.findAllIncomingDto();
    }

    @Override
    @GetMapping(value="/users/{Id}", produces = "application/json")
    public ResponseEntity<UserOutcomingDto> findIncomingDtoById(@NonNull@PathVariable final String Id) {
        return super.findIncomingDtoById(Id);
    }

    @Override
    @DeleteMapping(value="/users/{Id}", produces = "application/json")
    public ResponseEntity<Boolean> delete(@NonNull @PathVariable final String Id) {
        return super.delete(Id);
    }


    @Override
    @PostMapping(value="/users", produces = "application/json")
    public ResponseEntity<UserOutcomingDto> addIncomingDto(@Valid @RequestBody UserIncomingDto user) {
        try {
            user.setPassword(passwordHashService.generateMD5Hash(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return super.addIncomingDto(user);
    }

    @PostMapping(value="/users/authenticateUser", produces = "application/json")
    public ResponseEntity<UserOutcomingDto> authenticateUser(@RequestBody AuthentificationDto authentificationDto) {
        UserOutcomingDto result = null;
        try {
            var user = this.userService.findByLoginAndPassword(authentificationDto.login(), passwordHashService.generateMD5Hash(authentificationDto.password()));
            if (user != null){
                result = this.getObjectCopyService().copyFromEntityToOutcomingDto(user, UserOutcomingDto.class);
            }
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return result != null ? ResponseEntity.ok(result) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @Override
    @PutMapping(value="/users/{Id}", produces = "application/json")
    public ResponseEntity<UserOutcomingDto> updateIncomingDto(@PathVariable final String Id, @Valid @RequestBody UserIncomingDto user) {
        try {
            user.setPassword(passwordHashService.generateMD5Hash(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return super.updateIncomingDto(Id, user);
    }
}
