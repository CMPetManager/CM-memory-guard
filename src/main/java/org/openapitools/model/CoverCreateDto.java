package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;

import java.util.Objects;

/**
 * CoverCreateDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class CoverCreateDto {

  @JsonProperty("description")
  private String description;

  @JsonProperty("album_id")
  private Long albumId;

  public CoverCreateDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "place description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CoverCreateDto albumId(Long albumId) {
    this.albumId = albumId;
    return this;
  }

  /**
   * Get albumId
   * @return albumId
  */
  
  @Schema(name = "album_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(Long albumId) {
    this.albumId = albumId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CoverCreateDto coverCreateDto = (CoverCreateDto) o;
    return Objects.equals(this.description, coverCreateDto.description) &&
        Objects.equals(this.albumId, coverCreateDto.albumId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, albumId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoverCreateDto {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    albumId: ").append(toIndentedString(albumId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

