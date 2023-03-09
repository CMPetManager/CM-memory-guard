package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * ImageReadDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class ImageReadDto {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("link")
  private String link;

  @JsonProperty("page_id")
  private Long pageId;

  @JsonProperty("cover_id")
  private Long coverId;

  public ImageReadDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ImageReadDto link(String link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
  */
  @NotNull
  @Schema(name = "link", example = "photo link", requiredMode = Schema.RequiredMode.REQUIRED)
  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public ImageReadDto pageId(Long pageId) {
    this.pageId = pageId;
    return this;
  }

  /**
   * Get pageId
   * @return pageId
  */
  
  @Schema(name = "page_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getPageId() {
    return pageId;
  }

  public void setPageId(Long pageId) {
    this.pageId = pageId;
  }

  public ImageReadDto coverId(Long coverId) {
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
    ImageReadDto imageReadDto = (ImageReadDto) o;
    return Objects.equals(this.id, imageReadDto.id) &&
        Objects.equals(this.link, imageReadDto.link) &&
        Objects.equals(this.pageId, imageReadDto.pageId) &&
        Objects.equals(this.coverId, imageReadDto.coverId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, link, pageId, coverId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageReadDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    link: ").append(toIndentedString(link)).append("\n");
    sb.append("    pageId: ").append(toIndentedString(pageId)).append("\n");
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

