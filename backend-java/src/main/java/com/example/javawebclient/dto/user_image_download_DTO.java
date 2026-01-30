package com.example.javawebclient.dto;

import java.util.Date;
import java.util.List;

public class user_image_download_DTO
{
    public int id;
    private int user_id;
    private String image_name;
    private String image_type;
    private String image_data;
    private Date uploaded_at;
    public List<get_image_description_DTO> descriptions;

    public int getImage_id() { return id; }
    public int getUser_id() { return user_id; }
    public String getImage_name() { return image_name; }
    public String getImage_type() { return image_type; }
    public String getImage_data() { return image_data; }
    public Date getUploaded_at() { return uploaded_at; }
    public List<get_image_description_DTO> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<get_image_description_DTO> descriptions) {
        this.descriptions = descriptions;
    }


}
