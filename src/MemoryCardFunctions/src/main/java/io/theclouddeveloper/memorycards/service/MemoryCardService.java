package io.theclouddeveloper.memorycards.service;

import io.theclouddeveloper.memorycards.model.MemoryCard;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class MemoryCardService {

    MemoryCardDynamoDBService memoryCardDynamoDBService;

    public MemoryCardService() {
        memoryCardDynamoDBService = new MemoryCardDynamoDBService();
    }

    public MemoryCard processCreateNewMemoryCardRequest(MemoryCard newMemoryCard){
        log.info("Processing memory card: {}", newMemoryCard);

        String createdTimestamp = Long.toString(Instant.now().toEpochMilli());
        String categoryCreatedTimestamp = newMemoryCard.getCategory() + '_' + createdTimestamp;
        String uuid = UUID.randomUUID().toString();

        MemoryCard memoryCard = MemoryCard.builder()
                .author(newMemoryCard.getAuthor())
                .categoryCreatedTimestamp(categoryCreatedTimestamp)
                .category(newMemoryCard.getCategory())
                .createdTimestamp(createdTimestamp)
                .memoryText(newMemoryCard.getMemoryText())
                .uuid(uuid)
                .build();

        log.info("Instantiated memoryCard: {}",memoryCard);

        memoryCardDynamoDBService.saveMemoryCard(memoryCard);

        return memoryCard;
    }

    public List<MemoryCard> processGetAllMemoryCardsRequest(){
        return memoryCardDynamoDBService.scanAllMemoryCards();
    }

    public Optional<MemoryCard> processGetMemoryCardByUuid(String uuid){
        log.info("Processing retrieval of memoryCard with id: {}", uuid);

        Optional<MemoryCard> memoryCardOptional = memoryCardDynamoDBService.getMemoryCardByUuid(uuid);

        return memoryCardOptional;
    }

    public boolean processDeleteMemoryCardByUuidRequest(String uuid){
        log.info("Processing removal of memoryCard with id: {}", uuid);

        Optional<MemoryCard> memoryCardOptional = memoryCardDynamoDBService.getMemoryCardByUuid(uuid);
        if(memoryCardOptional.isEmpty()){
            return false;
        }
        MemoryCard memoryCardThatWasDeleted = memoryCardDynamoDBService.deleteMemoryCard(memoryCardOptional.get());
        return true;
    }

    public List<MemoryCard> processGetAllFromAuthorWithCategoryRequest(String author, String category){
        log.info("Processing fetch request of author-category combination");

        List<MemoryCard> memoryCards = memoryCardDynamoDBService.getMemoryCardsFromAuthorWithCategory(author, category);

        return memoryCards;
    }
}
