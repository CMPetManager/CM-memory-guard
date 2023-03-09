package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * AlbumCreateDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class AlbumCreateDto {

  @JsonProperty("created_at")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @JsonProperty("cover_id")
  private Long coverId;

  public AlbumCreateDto createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @Valid
  @Schema(name = "created_at", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public AlbumCreateDto coverId(Long coverId) {
    this.coverId = coverId;
    return this;
  }

  /**
   * Get coverId
   * @return coverId
  */
  
  @Schema(name = "cover_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getCoverId() {
    return coverId;
  }

  public void setCoverId(Long coverId) {
    this.coverId = coverId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlbumCreateDto albumCreateDto = (AlbumCreateDto) o;
    return Objects.equals(this.createdAt, albumCreateDto.createdAt) &&
        Objects.equals(this.coverId, albumCreateDto.coverId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, coverId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumCreateDto {\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    coverId: ").append(toIndentedString(coverId)).append("\n");
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

