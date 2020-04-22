package io.theclouddeveloper.memorycards.model;

import lombok.Builder;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;


@ToString
@Builder
@DynamoDbBean
public class MemoryCard {

    private String author;

    private String categoryCreatedTimestamp;
    private String category;
    private String createdTimestamp;
    private String memoryText;
    private String uuid;

    public MemoryCard() {
    }

    public MemoryCard(String author, String categoryCreatedTimestamp, String category, String createdTimestamp, String memoryText, String uuid) {
        this.author = author;
        this.categoryCreatedTimestamp = categoryCreatedTimestamp;
        this.category = category;
        this.createdTimestamp = createdTimestamp;
        this.memoryText = memoryText;
        this.uuid = uuid;
    }

    @DynamoDbPartitionKey
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value="category_createdTimestamp")
    public String getCategoryCreatedTimestamp() {
        return categoryCreatedTimestamp;
    }

    public void setCategoryCreatedTimestamp(String categoryCreationTimestamp) {
        this.categoryCreatedTimestamp = categoryCreationTimestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    @DynamoDbSecondarySortKey(indexNames = "createdTimestampIndex")
    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getMemoryText() {
        return memoryText;
    }

    public void setMemoryText(String memoryText) {
        this.memoryText = memoryText;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "uuidIndex")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
