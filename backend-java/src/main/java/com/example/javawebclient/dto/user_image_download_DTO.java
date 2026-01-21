package com.example.javawebclient.dto;

import java.util.Date;

public class user_image_download_DTO
{
    private int user_id;
    private String image_name;
    private String image_type;
    private String image_data;
    private Date uploaded_at;

    public int getUser_id() { return user_id; }
    public String getImage_name() { return image_name; }
    public String getImage_type() { return image_type; }
    public String getImage_data() { return image_data; }
    public Date getUploaded_at() { return uploaded_at; }

}
