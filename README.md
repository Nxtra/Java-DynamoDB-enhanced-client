# Java enhanced dynamodb client test

## Architecture
This project contains multiple lambda functions that each execute an other operation on DynamoDB.  
They all use the new **DynamoDB Enhanced Client** to execute the operations.  
The project was built to explore the new **DynamoDB Enhanced Client**.

Operations:
* putItem: create a new item
* query by partitionKey and a condition on the sort key
* query on a GSI
* full table scan
* deleteItem

![Architecture](.img/Architecture.png)

## Build and deploy

*Prerequisits*:
* Gradle or gradle wraper
* Java11

*Build and deploy:*
* build: `./gradlew clean build`
* deploy first time: `sam deploy --guided`
* deploy: `sam deploy`
* build and deploy. After deploying for the first time you can build and deploy wit using the `build-and-deploy.sh` script.
* delete the stack: `aws cloudformation delete-stack --stack-name {{name-of-your-stack}}`

OUTPUTS: The stack will output the baseURL for the application on which you can execute requests.

**Deploying in a region other than** `us-east-1`: in `template.yml` uncomment the env variables for REGION and set it your region. 


## Playing with the application
Once deployed copy the ApiGateway url and start sending requests to it.
You can post a basic "memoryCard" that looks like this: 
```
{
  "author": "nick",
  "memoryText": "Use the new DynamoDB enhanced client",
  "category": "cloud"
}
```
This will then be saved in the database.  
You can create more memoryCards.  
Or you can delete, getOne, getAll.  
These endpoints will all trigger different operations on DynamoDB using the `enhanced-client` for DynamoDB.

**Intellij Http**
If you are using IntelliJ you can use the Http Requests in the `http` folder to trigger the endpoints.  
* Update the URL in `http-client.env.json` to your own URL.
You can get this from the outputs of the stack.
* run all requests in `api.http` using the `test` environment



## Stackery
* edit the stack: `stackery edit`
* deploy from local: `stackery deploy -e dev --strategy local --aws-profile default`
* deploy for the first time: `stackery deploy`