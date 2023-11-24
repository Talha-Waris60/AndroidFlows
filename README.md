# Flows in Android Kotlin
In programming we have some scenarios where we have stream of data e.g video Streaming, FM radio etc there we use flows.
Flows is used in situation where sources like remote server, local db continously sends data. To support streams of data kotlin offers two approaches
channels and flows
### Channels are hot
The producer consistently generates data regardless of whether a consumer is actively observing or not.In this scenario, 
the streams are considered hot, meaning that produced data is not re-emitted
### Flow are mostly Cold
Producer not produce data until there is no any consumer, In flows every consumer get data from start not matter when consumer is attached.
Cold Streams are preferred over hot streams because in hot stream Resources are waste and we have to manually close the producer because producer
continously produce data.
Each flow can have multiple consumer 
Flow will be cancelled if there is not consumer or we can cancel using other approches like cancelling the coroutine.
### Events in Flows
onStart{} - This event block is trigger only once when the flow the start.
onEach{} - This event block is trigger before each element emitted by flow.
onComplete{} - This event block is triggered when the flow produced by the producer function completes or collection completed.

### Buffering Strategy
Buffering strategy - buffer(CAPACITY) is used to handle the scenarios when the producer emits elements at a rate that might be faster than the consumer can process.  
Buffering allows the consumer to collect elements at its own pace, reducing the risk of losing elements due to a fast producer and a slow consumer. Capacity in buffering refer to how much element you want to store in buffering for process. 
