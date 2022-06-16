package com.example.pki.model;

import com.example.pki.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
//    @SequenceGenerator(name="user_generator", sequenceName = "user_seq", allocationSize=50, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CertificateInDatabase> certifications = new HashSet<>();

    protected boolean isActivated = true;
    protected Integer forgotten;
    protected String pin;
    protected String salt;
    protected Integer missedPasswordCounter;
    protected boolean isBlocked;
    protected Date blockedDate;
    protected Date pinCreatedDate;



    public User(UserDTO userDto) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        setId(userDto.getId());
        setEmail(userDto.getEmail());
        setFirstName(userDto.getFirstName());
        setPassword(userDto.getPassword());
        setLastName(userDto.getLastName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role r = this.role;
        authorities.add(new SimpleGrantedAuthority(r.name));
        for(Permission p : r.getPermissions())
            authorities.add(new SimpleGrantedAuthority(p.name));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActivated;
    }
}
