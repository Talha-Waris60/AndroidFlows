# Flows in Android Kotlin
In programming, we have scenarios where there is a stream of data, for example, video streaming, FM radio, etc. Flows are used in situations where sources like remote servers or local databases continuously send data.
## To support streams of data kotlin offers two approaches
### 1) Channels are hot
The producer continously generates data regardless of whether a consumer is actively observing or not.In this scenario, 
streams are considered hot, meaning that produced data is not re-emitted.
### 2) Flow are mostly Cold
Producer does not produce data until there is no any consumer, In flows every consumer get data from the start no matter when consumer is attached.
Cold Streams are preferred over hot streams because in hot stream, resources are wasted, and we have to manually close the producer since it
continously produce data
Each flow can have multiple consumer.
A Flow will be cancelled if there is no consumers, or we can cancel it using other approches, like cancelling the coroutine.
### Events in Flows
onStart{} - This event block is triggered only once when the flow starts.
onEach{} -  This event block is triggered before each element emitted by the flow.
onComplete{} - This event block is triggered when the flow, produced by the producer function, completes or when the collection is completed.
### Buffering Strategy
Buffering strategy - buffer(CAPACITY) is used to handle the scenarios when the producer emits elements at a rate that might be faster than the consumer can process.  
Buffering enables the consumer to collect elements at its own pace or speed, reducing the risk of losing elements in scenarios where the producer is fast and the consumer is slow.
The capacity in buffering refers to the maximum number of elements you want to store in the buffer for processing
