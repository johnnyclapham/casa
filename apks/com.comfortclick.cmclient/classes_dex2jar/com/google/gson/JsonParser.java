/*
 * Decompiled with CFR 0_96.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        boolean bl = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            JsonElement jsonElement = Streams.parse(jsonReader);
            return jsonElement;
        }
        catch (StackOverflowError var7_4) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", var7_4);
        }
        catch (OutOfMemoryError var6_6) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", var6_6);
        }
        catch (JsonParseException var3_7) {
            if (var3_7.getCause() instanceof EOFException) {
                JsonNull jsonNull = JsonNull.INSTANCE;
                return jsonNull;
            }
            throw var3_7;
        }
        finally {
            jsonReader.setLenient(bl);
        }
    }

    public JsonElement parse(Reader reader) throws JsonIOException, JsonSyntaxException {
        JsonElement jsonElement;
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonElement = this.parse(jsonReader);
            if (!(jsonElement.isJsonNull() || jsonReader.peek() == JsonToken.END_DOCUMENT)) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
        }
        catch (MalformedJsonException var5_4) {
            throw new JsonSyntaxException(var5_4);
        }
        catch (IOException var4_5) {
            throw new JsonIOException(var4_5);
        }
        catch (NumberFormatException var3_6) {
            throw new JsonSyntaxException(var3_6);
        }
        return jsonElement;
    }

    public JsonElement parse(String string) throws JsonSyntaxException {
        return this.parse(new StringReader(string));
    }
}

