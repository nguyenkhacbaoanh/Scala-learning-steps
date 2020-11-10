This package `JobStreaming` to demonstrate and to learning how does Spark Streaming Job work

It consist of 2 modules:
- Context: a trait object to store variables for: spark's session, spark's context
- StructuredStreamingAverage: this object is a streaming processing job, which calculate age's average of all people by sex. Pushing successively `JSON` file into `input_files` folder

Note: Modify `.json` method on line 22 to specify where `input_file` folder is on your system. It seem `System.getProperty("user.dir")` can't work with `local` mode (to verify), Errors raised: `Path Substring is null`