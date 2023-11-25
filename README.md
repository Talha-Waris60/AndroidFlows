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
Each flow can have multiple consumer, every consumer will get independent flow object and get data from starting 
A Flow will be cancelled if there is no consumers, or we can cancel it using other approches, like cancelling the coroutine.
### Events In Flows
onStart{} - This event block is triggered only once when the flow starts.
onEach{} -  This event block is triggered before each element emitted by the flow.
onComplete{} - This event block is triggered when the flow, produced by the producer function, completes or when the collection is completed.
### Buffering Strategy
Buffering strategy - buffer(CAPACITY) is used to handle the scenarios when the producer emits elements at a rate that might be faster than the consumer can process.  
Buffering enables the consumer to collect elements at its own pace or speed, reducing the risk of losing elements in scenarios where the producer is fast and the consumer is slow.
The capacity in buffering refers to the maximum number of elements you want to store in the buffer for processing
### Flow Context Preservation ( flowon() )
When dealing with flows in Kotlin, it's important to consider the thread or context in which the flow is emitted and collected.
Generally flows preserve the context - (coroutine scope) in which they are emitting and expect to collect the elements in the same context. However, you can switch the context based on your scenario and requirements.
If you emit elements on one thread (e.g., in the background or IO thread), and you try to collect or perform operations on those elements on a different thread (e.g., the main thread), you might run into issues, including potential crashes.
To address this, you can use the flowOn operator to explicitly specify the thread or dispatcher where certain parts of the flow should execute. 
### Shared Flows
Shared Flows are hot flows. Each shared flow has multiple consumers, and each consumer gets the same flow object. For example, in a movie theater, if a movie starts at 3:00 o'clock, every person in the theater who arrives at 3:00 will consume the same data. However, if a person arrives at 3:30, they will start consuming the data from that time onward, missing the data that was emitted before their arrival. Flow object will shared among all consumers.
Replay feature - (replay = 1) in sharedFlow allows you to specify how many previous elements should be replayed to new collectors when they subscribe to the shared flow.
### State Flow
StateFlow, similar to LiveData, stays 'active' with multiple consumers. The special thing about StateFlow is how it keeps and maintain the state of the latest data. Imagine a box with a value inside – every time a new value comes in, it swaps the old one. StateFlow ensures all consumers get this newest value, making it great for handling and sharing the most up-to-date information.
