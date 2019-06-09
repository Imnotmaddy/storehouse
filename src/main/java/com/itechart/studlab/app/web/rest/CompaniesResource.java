package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.config.Constants;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.repository.UserRepository;
import com.itechart.studlab.app.security.AuthoritiesConstants;
import com.itechart.studlab.app.service.MailService;
import com.itechart.studlab.app.service.UserService;
import com.itechart.studlab.app.service.dto.UserDTO;
import com.itechart.studlab.app.web.rest.errors.EmailAlreadyUsedException;
import com.itechart.studlab.app.web.rest.errors.LoginAlreadyUsedException;
import com.itechart.studlab.app.web.rest.util.HeaderUtil;
import com.itechart.studlab.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CompaniesResource {

    private final Logger log = LoggerFactory.getLogger(CompaniesResource.class);

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;


    public CompaniesResource(UserService userService, UserRepository userRepository, MailService mailService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    private Set<String> getStorehouseAdminAuthority() {
        Set<String> authority = new HashSet<>(1);
        authority.add(AuthoritiesConstants.STOREHOUSE_ADMIN);
        return authority;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<UserDTO>> getAllCompanies(Pageable pageable) {
        log.debug("REST request to get all companies");
        final Page<UserDTO> page = userService.getAllCompanyAdmins(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/companies/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getCompany(@PathVariable String login) {
        log.debug("REST request to get company : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    @PostMapping("/companies")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createCompany(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", userDTO);
        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            userDTO.setAuthorities(getStorehouseAdminAuthority());
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/companies/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("companies.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    @DeleteMapping("/companies/{login:" + Constants.LOGIN_REGEX + "}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCompany(@PathVariable String login) {
        log.debug("REST request to delete company: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("companies.deleted", login)).build();
    }

    @PutMapping("/companies")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update company : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        userDTO.setAuthorities(getStorehouseAdminAuthority());
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("companies.updated", userDTO.getLogin()));
    }

    @GetMapping("companies/{company}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> toggleEmployees(@PathVariable String company, @RequestParam("isActive") Boolean isActive) {
        log.debug("REST request to toggle {} employees with isActive: {}", company, isActive);
        userService.toggleEmployees(company, isActive);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("companies.employeesToggled", company)).build();
    }
}
