package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;

import java.util.Objects;

/**
 * ImageCreateDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class ImageCreateDto {

  @JsonProperty("link")
  private String link;

  @JsonProperty("page_id")
  private Long pageId;

  @JsonProperty("cover_id")
  private Long coverId;

  public ImageCreateDto link(String link) {
    this.link = link;
    return this;
  }

  /**
   * Get link
   * @return link
  */
  
  @Schema(name = "link", example = "photo link", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public ImageCreateDto pageId(Long pageId) {
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

  public ImageCreateDto coverId(Long coverId) {
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
    ImageCreateDto imageCreateDto = (ImageCreateDto) o;
    return Objects.equals(this.link, imageCreateDto.link) &&
        Objects.equals(this.pageId, imageCreateDto.pageId) &&
        Objects.equals(this.coverId, imageCreateDto.coverId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(link, pageId, coverId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImageCreateDto {\n");
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

