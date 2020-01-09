package com.baizhi.hyh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private String id;
    private String phone;
    private String password;
    private String salt;
    private String status;
    private String photo;
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    private String sex;
    private String sign;
    private String location;
    @Column(name = "rigest_date")
    private Date rigestDate;
    @Column(name = "last_login")
    private Date lastLogin;
    private String filename;

}
