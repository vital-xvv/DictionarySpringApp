package com.spring.dictionary.actors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NamedQueries({@NamedQuery(name = "User.updateUserFirstNameAndLastNameByUsername", query = "update User u set u.firstName=?1, u.lastName=?2 where u.username=?3")})
@Entity(name="User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table( uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "user_username_unique")})
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence")
    @Column(updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(
            name = "username",
            nullable = false
    )
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();
}
