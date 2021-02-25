package com.vhalitsyn.service.store;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.vhalitsyn.data.gson.GsonProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Iterator;

/***
 * This Json iteratos expects JSON files of next structure
 * {
 *     "name1" : [],
 *     "name2" : [],
 *     ....
 *     "nameN" : []
 * }
 * attempting to parse any other file structures may produce unexpected errors.
 * @param <T>
 */
public class JsonFileIterator<T> implements Iterable<T>, AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(JsonFileIterator.class);

    private JsonReader reader;
    private final Class<T> type;
    private final Gson gson = GsonProvider.getGSON();

    public JsonFileIterator(File inputFile, Class<T> aType, String arrayObjectFieldName) throws IOException
    {
        LOGGER.debug("initializing JSON file iterator for {}", arrayObjectFieldName);
        type = aType;
        reader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream(inputFile))));
        // opening {
        reader.beginObject();

        String resourceName = reader.nextName();
        while (!resourceName.equals(arrayObjectFieldName))
        {
            reader.skipValue();
            resourceName = reader.nextName();
        }
        // every item in json is `resource: [],`
        reader.beginArray();
        LOGGER.debug("JSON file iterator for {} initialized", arrayObjectFieldName);

    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<>() {
            @Override
            public boolean hasNext()
            {
                try
                {
                    return reader.hasNext();
                } catch (IOException e)
                {
                    throw new RuntimeException("could not execute hasNext", e);
                }
            }

            @Override
            public T next()
            {
                try
                {
                    final boolean hasNext = reader.hasNext();
                    if (hasNext)
                    {
                        return gson.fromJson(reader, type);
                    } else
                    {
                        close();
                        return null;
                    }
                } catch (Exception e)
                {
                    throw new RuntimeException("could not read next item", e);
                }
            }
        };
    }

    @Override
    public void close()
    {
        try
        {
            if (reader != null)
            {
                reader.close();
            }
        } catch (IOException e)
        {
            throw new RuntimeException("failed to close file", e);
        } finally
        {
            reader = null;
        }
    }
}
