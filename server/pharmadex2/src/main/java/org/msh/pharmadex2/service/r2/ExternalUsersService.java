package org.msh.pharmadex2.service.r2;

import org.msh.pdex2.model.newRegistration.ExternalUsers;
import org.msh.pdex2.model.old.User;
import org.msh.pdex2.repository.newRegistration.ExternalUsersRepo;
import org.msh.pharmadex2.dto.auth.ExternalUserDetailsDTO;
import org.msh.pharmadex2.dto.auth.UserDetailsDTO;
import org.msh.pharmadex2.dto.auth.UserRoleDto;
import org.msh.pharmadex2.service.common.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ExternalUsersService implements UserDetailsService {
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public ExternalUsersRepo externalUsersRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public ExternalUsers createExternalUser(ExternalUsers externalUsers){
        externalUsers.setPassword(passwordEncoder.encode(externalUsers.getPassword()));
        externalUsers.setUpdatedDate(new Date());
        externalUsers.setCreatedDate(new Date());
        return externalUsersRepo.save(externalUsers);
    }

    @Transactional
    public ExternalUsers updateExternalUser(ExternalUsers externalUsers, long id){
        ExternalUsers users =externalUsersRepo.findByUserId(id);
        if(users!=null) {
            users.setAddress(externalUsers.getAddress());
            users.setEmail(externalUsers.getEmail());
            users.setName(externalUsers.getName());
            users.setPhone(externalUsers.getPhone());
            users.setUpdatedDate(new Date());
            users.setPassword(passwordEncoder.encode(externalUsers.getPassword()));
            return externalUsersRepo.save(users);
        }else{
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails ret =  loadUserDetailsDTO(username);
        return ret;
    }
    @Transactional
    public ExternalUserDetailsDTO loadUserDetailsDTO(String login) {
        try {
            ExternalUsers u = loadUserByLogin(login);
            return externalUserToDTO(u);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(login);
        }
    }

    @Transactional
    public ExternalUsers loadUserByLogin(String login) {
        ExternalUsers usero = externalUsersRepo.findByEmail(login);
        if (usero!=null) {
            return usero;
        }else {
            throw new UsernameNotFoundException(login);
        }
    }
    @Transactional
    public ExternalUserDetailsDTO externalUserToDTO(ExternalUsers user) {
        ExternalUserDetailsDTO ret = new ExternalUserDetailsDTO();
        ret.setActive(user.isEnabled());
        ret.setExpired(!user.isEnabled());
        ret.setLocked(!user.isEnabled());
        ret.setName(user.getName());
        ret.setPassword(user.getPassword());
        ret.getGranted().clear();
        Set<UserRoleDto> set =new HashSet<>();
        set.add(UserRoleDto.guestUser());
        ret.getGranted().addAll(set);
        if(ret.getGranted().size()==0) {
            ret.getGranted().add(UserRoleDto.guestUser());
        }
        return ret;
    }
}
