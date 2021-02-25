# Mixtape
##requirements
* JDK 14+
* input file is a valid json object, consisting of `User`, `Playlist` and `Song` arrays.  
##installation / run
to build from source dir
`gradlew fatJar` win or `./gradlew fatJar` linux/macOS

to run from source dir
`java -jar ./dist/mixtape-cli-0.1.0.jar --input=./src/test/resources/mixtape-data.json --edits=./src/test/resources/mixtape-edits.json`

After execution, `mixtape-output.json` should appear in the working dir.

To pretty-print `mixtape-output.json` set environment variable: `export MIXTAPE_PRETTY_JSON=true`
##args supported
* `--input=` sets the path of input json file
* `--output=`  sets the path of output json file
* `--edits=` sets the path of edits json file
###tests
`./gradlew test`.

Tested on
* macOS

##design notes

Scalability of large files


Current implementation uses iterator-based streaming GSON library to read input files, apply changes non-destructively and then persist changes to disk. 
It uses an In-Memory DataStore, which assumes that entire data and edits files object models fit into application's RAM.
Should this be not the case, optimization would need to avoid loading everything into RAM and thus we could do the following:

1. For large data file: load content of the data file into a persistent data store, that supports durable update. JSON doesn't suit this well, since even if we store an index of item's offsets in JSON, in-place update would be problematic and CPU intensive. We'd also want to keep input data intact.
Current design supports this optimization by abstracting Content Source as well as Content Store. In this case Content Store would need to write to a DB(instead of keeping the in-mem hash-map).
   
2. For large edits file: current code can be used as is: it already reads each update 1 by 1, applying the change in isolation with other changes and persisting it to the Store.

Finally, serialization routine is also iterator-based, writing items found in DataStore 1 by 1, so it's up to the DataStore to fetch items from itself in a memory-optimized manner.

General Edits file design

I've intentionally made the system more extensible to handle different types of edits uniformly. Current design easily supports Add/Remove/Edit of not only Playlist, but also Song and User objects, with
separated and abstracted locations in code for business rules. While this was not a requirement of the initial task, this also made some of the implementations of Add/Edit more "generic" and less "typed".
That is due to my lack of experience with GSON library and it's capabilities combined with little time allocated to explore.
I'm quite sure Playlist edit and object Add actions can be stricter typed, relying less on convention and more on object structure. It also felt like edits file processing code should be extensible to edits of any kind, not just Playlist.

Final notes.

Rudimentary unit tests and integration tests are present and available with `./gradlew clean test`
