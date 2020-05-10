package io.theclouddeveloper.memorycards.di;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import io.theclouddeveloper.memorycards.model.MemoryCard;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class DIModule {

    @Provides
    @Named("Region")
    Region provideRegion(){
        return System.getenv("REGION") != null ? Region.of(System.getenv("REGION")) : Region.of("us-east-1");
    }

    @Provides
    @Named("TableName")
    String provideTableName(){
        return System.getenv("TABLE_NAME");
    }

    @Provides
    @Singleton
    @Named("MemoryCardTableSchema")
    TableSchema<MemoryCard> provideMemoryCardTableSchema(){
        return TableSchema.fromBean(MemoryCard.class);
    }

    @Provides
    @Singleton
    DynamoDbClient provideDynamoDBClient(@Named("Region") Region region){
        return DynamoDbClient.builder()
                .region(region)
                .build();
    }

    @Provides
    @Singleton
    DynamoDbEnhancedClient provideDynamoDbEnhancedClient(DynamoDbClient dynamoDbClient){
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Provides
    @Singleton
    DynamoDbTable<MemoryCard> provideDynamoDbTable(DynamoDbEnhancedClient dynamoDbEnhancedClient,
                                                   @Named("TableName") String tableName,
                                                   @Named("MemoryCardTableSchema") TableSchema<MemoryCard> tableSchema ){
        return dynamoDbEnhancedClient.table(tableName, tableSchema);
    }


    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder().create();
    }
}
