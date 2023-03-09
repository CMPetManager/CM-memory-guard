package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

import java.util.Objects;

/**
 * PageReadDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class PageReadDto {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("description")
  private String description;

  @JsonProperty("color")
  private String color;

  @JsonProperty("template_page")
  private String templatePage;

  @JsonProperty("tag_people")
  private String tagPeople;

  @JsonProperty("tag_place")
  private String tagPlace;

  @JsonProperty("animation")
  private String animation;

  @JsonProperty("album")
  private AlbumReadDto album;

  public PageReadDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", example = "11", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PageReadDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "some words about page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PageReadDto color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
  */
  
  @Schema(name = "color", example = "red", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public PageReadDto templatePage(String templatePage) {
    this.templatePage = templatePage;
    return this;
  }

  /**
   * Get templatePage
   * @return templatePage
  */
  
  @Schema(name = "template_page", example = "Horizontal image and text field below", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTemplatePage() {
    return templatePage;
  }

  public void setTemplatePage(String templatePage) {
    this.templatePage = templatePage;
  }

  public PageReadDto tagPeople(String tagPeople) {
    this.tagPeople = tagPeople;
    return this;
  }

  /**
   * Get tagPeople
   * @return tagPeople
  */
  
  @Schema(name = "tag_people", example = "Andrew,Egor", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTagPeople() {
    return tagPeople;
  }

  public void setTagPeople(String tagPeople) {
    this.tagPeople = tagPeople;
  }

  public PageReadDto tagPlace(String tagPlace) {
    this.tagPlace = tagPlace;
    return this;
  }

  /**
   * Get tagPlace
   * @return tagPlace
  */
  
  @Schema(name = "tag_place", example = "Germany,Berlin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTagPlace() {
    return tagPlace;
  }

  public void setTagPlace(String tagPlace) {
    this.tagPlace = tagPlace;
  }

  public PageReadDto animation(String animation) {
    this.animation = animation;
    return this;
  }

  /**
   * Get animation
   * @return animation
  */
  
  @Schema(name = "animation", example = "slide", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getAnimation() {
    return animation;
  }

  public void setAnimation(String animation) {
    this.animation = animation;
  }

  public PageReadDto album(AlbumReadDto album) {
    this.album = album;
    return this;
  }

  /**
   * Get album
   * @return album
  */
  @Valid
  @Schema(name = "album", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public AlbumReadDto getAlbum() {
    return album;
  }

  public void setAlbum(AlbumReadDto album) {
    this.album = album;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageReadDto pageReadDto = (PageReadDto) o;
    return Objects.equals(this.id, pageReadDto.id) &&
        Objects.equals(this.description, pageReadDto.description) &&
        Objects.equals(this.color, pageReadDto.color) &&
        Objects.equals(this.templatePage, pageReadDto.templatePage) &&
        Objects.equals(this.tagPeople, pageReadDto.tagPeople) &&
        Objects.equals(this.tagPlace, pageReadDto.tagPlace) &&
        Objects.equals(this.animation, pageReadDto.animation) &&
        Objects.equals(this.album, pageReadDto.album);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, color, templatePage, tagPeople, tagPlace, animation, album);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageReadDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    templatePage: ").append(toIndentedString(templatePage)).append("\n");
    sb.append("    tagPeople: ").append(toIndentedString(tagPeople)).append("\n");
    sb.append("    tagPlace: ").append(toIndentedString(tagPlace)).append("\n");
    sb.append("    animation: ").append(toIndentedString(animation)).append("\n");
    sb.append("    album: ").append(toIndentedString(album)).append("\n");
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

