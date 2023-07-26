package org.msh.pdex2.model.newRegistration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "`external_users`")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ExternalUsers {
    public ExternalUsers() {
    }

    @Column(name = "userId", nullable = false)
    @Id
    @GeneratedValue(generator = "VAC2222721892C6453080045C")
    @org.hibernate.annotations.GenericGenerator(name = "VAC2222721892C6453080045C", strategy = "native")
    private long userId;

    @Column(name = "address", nullable = true, length = 500)
    private String address;

    @Column(name = "email", nullable = true, length = 255)
    @org.hibernate.annotations.Index(name = "UK_f9dvvibvpfsldnu8wh3enop4i")
    private String email;

    @Column(name = "enabled", nullable = false, length = 1)
    private boolean enabled;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "password")
    @Column(name = "password", nullable = true, length = 255)
    private String password;

    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Column(name="createdDate", nullable=true)
    private Date createdDate;

    @Column(name="updatedDate", nullable=true)
    private Date updatedDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "ExternalUsers{" +
                "userId=" + userId +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
