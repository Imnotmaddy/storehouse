package com.itechart.studlab.app.web.rest;

import com.itechart.studlab.app.config.Constants;
import com.itechart.studlab.app.domain.User;
import com.itechart.studlab.app.repository.UserRepository;
import com.itechart.studlab.app.security.AuthoritiesConstants;
import com.itechart.studlab.app.service.MailService;
import com.itechart.studlab.app.service.UserService;
import com.itechart.studlab.app.service.dto.UserDTO;
import com.itechart.studlab.app.web.rest.errors.BadRequestAlertException;
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
import java.util.List;

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

    @GetMapping("/companies")
    public ResponseEntity<List<UserDTO>> getAllCompanies(Pageable pageable) {
        log.debug("REST request to get all companies");
        final Page<UserDTO> page = userService.getAllCompanyAdmins(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/companies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/companies/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    @PostMapping("/companies")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", userDTO);
        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/companies/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert("companies.created", newUser.getLogin()))
                .body(newUser);
        }
    }
}
