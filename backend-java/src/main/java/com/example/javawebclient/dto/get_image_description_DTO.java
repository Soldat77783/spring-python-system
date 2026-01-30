package com.example.javawebclient.dto;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class get_image_description_DTO {

    private Integer id;
    private String description;

    @SerializedName("updated_at")
    private LocalDateTime updatedAt;

    @SerializedName("created_at")
    private LocalDateTime createdAt;

    @SerializedName("image_id")
    private Integer imageId;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("image_name")
    private String imageName;

    @SerializedName("image_type")
    private String imageType;

    @SerializedName("uploaded_at")
    private LocalDateTime uploadedAt;

    // Getters
    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getImageId() { return imageId; }
    public Integer getUserId() { return userId; }
    public String getImageName() { return imageName; }
    public String getImageType() { return imageType; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }

    // Setters (optional but recommended)
    public void setId(Integer id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setImageId(Integer imageId) { this.imageId = imageId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public void setImageType(String imageType) { this.imageType = imageType; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}