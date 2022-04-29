/*
 * Decompiled with CFR 0_96.
 */
package com.google.gson;

import com.google.gson.AnonymousAndLocalClassExclusionStrategy;
import com.google.gson.DisjunctionExclusionStrategy;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy2;
import com.google.gson.GsonToMiniGsonTypeAdapterFactory;
import com.google.gson.InstanceCreator;
import com.google.gson.JavaFieldNamingPolicy;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ModifierBasedExclusionStrategy;
import com.google.gson.SerializedNameAnnotationInterceptingNamingPolicy;
import com.google.gson.SyntheticFieldExclusionStrategy;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ParameterizedTypeHandlerMap;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.BigDecimalTypeAdapter;
import com.google.gson.internal.bind.BigIntegerTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.ExcludedTypeAdapterFactory;
import com.google.gson.internal.bind.JsonElementReader;
import com.google.gson.internal.bind.JsonElementWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.MiniGson;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Gson {
    static final AnonymousAndLocalClassExclusionStrategy DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY;
    private static final ExclusionStrategy DEFAULT_EXCLUSION_STRATEGY;
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = 0;
    static final ModifierBasedExclusionStrategy DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY;
    static final FieldNamingStrategy2 DEFAULT_NAMING_POLICY;
    static final SyntheticFieldExclusionStrategy DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY;
    static final ParameterizedTypeHandlerMap EMPTY_MAP;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ConstructorConstructor constructorConstructor;
    private final ExclusionStrategy deserializationExclusionStrategy;
    private final ParameterizedTypeHandlerMap<JsonDeserializer<?>> deserializers;
    private final boolean generateNonExecutableJson;
    private final boolean htmlSafe;
    private final MiniGson miniGson;
    private final boolean prettyPrinting;
    private final ExclusionStrategy serializationExclusionStrategy;
    private final boolean serializeNulls;
    private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;

    static {
        EMPTY_MAP = new ParameterizedTypeHandlerMap().makeUnmodifiable();
        DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY = new AnonymousAndLocalClassExclusionStrategy();
        DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY = new SyntheticFieldExclusionStrategy(true);
        DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY = new ModifierBasedExclusionStrategy(128, 8);
        DEFAULT_NAMING_POLICY = new SerializedNameAnnotationInterceptingNamingPolicy(new JavaFieldNamingPolicy());
        DEFAULT_EXCLUSION_STRATEGY = Gson.createExclusionStrategy();
    }

    public Gson() {
        this(DEFAULT_EXCLUSION_STRATEGY, DEFAULT_EXCLUSION_STRATEGY, DEFAULT_NAMING_POLICY, EMPTY_MAP, false, EMPTY_MAP, EMPTY_MAP, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    Gson(ExclusionStrategy exclusionStrategy, ExclusionStrategy exclusionStrategy2, final FieldNamingStrategy2 fieldNamingStrategy2, ParameterizedTypeHandlerMap<InstanceCreator<?>> parameterizedTypeHandlerMap, boolean bl, ParameterizedTypeHandlerMap<JsonSerializer<?>> parameterizedTypeHandlerMap2, ParameterizedTypeHandlerMap<JsonDeserializer<?>> parameterizedTypeHandlerMap3, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, LongSerializationPolicy longSerializationPolicy, List<TypeAdapter.Factory> list) {
        this.deserializationExclusionStrategy = exclusionStrategy;
        this.serializationExclusionStrategy = exclusionStrategy2;
        this.constructorConstructor = new ConstructorConstructor(parameterizedTypeHandlerMap);
        this.serializeNulls = bl;
        this.serializers = parameterizedTypeHandlerMap2;
        this.deserializers = parameterizedTypeHandlerMap3;
        this.generateNonExecutableJson = bl3;
        this.htmlSafe = bl4;
        this.prettyPrinting = bl5;
        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory = new ReflectiveTypeAdapterFactory(this.constructorConstructor){

            @Override
            public boolean deserializeField(Class<?> class_, Field field, Type type) {
                ExclusionStrategy exclusionStrategy = Gson.this.deserializationExclusionStrategy;
                if (!(exclusionStrategy.shouldSkipClass(field.getType()) || exclusionStrategy.shouldSkipField(new FieldAttributes(class_, field)))) {
                    return true;
                }
                return false;
            }

            @Override
            public String getFieldName(Class<?> class_, Field field, Type type) {
                return fieldNamingStrategy2.translateName(new FieldAttributes(class_, field));
            }

            @Override
            public boolean serializeField(Class<?> class_, Field field, Type type) {
                ExclusionStrategy exclusionStrategy = Gson.this.serializationExclusionStrategy;
                if (!(exclusionStrategy.shouldSkipClass(field.getType()) || exclusionStrategy.shouldSkipField(new FieldAttributes(class_, field)))) {
                    return true;
                }
                return false;
            }
        };
        MiniGson.Builder builder = new MiniGson.Builder().withoutDefaultFactories().factory(TypeAdapters.STRING_FACTORY).factory(TypeAdapters.INTEGER_FACTORY).factory(TypeAdapters.BOOLEAN_FACTORY).factory(TypeAdapters.BYTE_FACTORY).factory(TypeAdapters.SHORT_FACTORY).factory(TypeAdapters.newFactory(Long.TYPE, Long.class, this.longAdapter(longSerializationPolicy))).factory(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(bl6))).factory(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(bl6))).factory(new ExcludedTypeAdapterFactory(exclusionStrategy2, exclusionStrategy)).factory(TypeAdapters.NUMBER_FACTORY).factory(TypeAdapters.CHARACTER_FACTORY).factory(TypeAdapters.STRING_BUILDER_FACTORY).factory(TypeAdapters.STRING_BUFFER_FACTORY).typeAdapter(BigDecimal.class, new BigDecimalTypeAdapter()).typeAdapter(BigInteger.class, new BigIntegerTypeAdapter()).factory(TypeAdapters.JSON_ELEMENT_FACTORY).factory(ObjectTypeAdapter.FACTORY);
        Iterator<TypeAdapter.Factory> iterator = list.iterator();
        while (iterator.hasNext()) {
            builder.factory(iterator.next());
        }
        builder.factory(new GsonToMiniGsonTypeAdapterFactory(this, parameterizedTypeHandlerMap2, parameterizedTypeHandlerMap3)).factory(new CollectionTypeAdapterFactory(this.constructorConstructor)).factory(TypeAdapters.URL_FACTORY).factory(TypeAdapters.URI_FACTORY).factory(TypeAdapters.UUID_FACTORY).factory(TypeAdapters.LOCALE_FACTORY).factory(TypeAdapters.INET_ADDRESS_FACTORY).factory(TypeAdapters.BIT_SET_FACTORY).factory(DateTypeAdapter.FACTORY).factory(TypeAdapters.CALENDAR_FACTORY).factory(TimeTypeAdapter.FACTORY).factory(SqlDateTypeAdapter.FACTORY).factory(TypeAdapters.TIMESTAMP_FACTORY).factory(new MapTypeAdapterFactory(this.constructorConstructor, bl2)).factory(ArrayTypeAdapter.FACTORY).factory(TypeAdapters.ENUM_FACTORY).factory(reflectiveTypeAdapterFactory);
        this.miniGson = builder.build();
    }

    private static void assertFullConsumption(Object object, JsonReader jsonReader) {
        if (object != null) {
            try {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            }
            catch (MalformedJsonException var3_2) {
                throw new JsonSyntaxException(var3_2);
            }
            catch (IOException var2_3) {
                throw new JsonIOException(var2_3);
            }
        }
    }

    private void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("" + d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialDoubleValues() method.");
        }
    }

    private static ExclusionStrategy createExclusionStrategy() {
        LinkedList<ExclusionStrategy> linkedList = new LinkedList<ExclusionStrategy>();
        linkedList.add(DEFAULT_ANON_LOCAL_CLASS_EXCLUSION_STRATEGY);
        linkedList.add(DEFAULT_SYNTHETIC_FIELD_EXCLUSION_STRATEGY);
        linkedList.add(DEFAULT_MODIFIER_BASED_EXCLUSION_STRATEGY);
        return new DisjunctionExclusionStrategy(linkedList);
    }

    private TypeAdapter<Number> doubleAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Double read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                double d = number.doubleValue();
                Gson.this.checkValidFloatingPoint(d);
                jsonWriter.value(number);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Float read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                float f = number.floatValue();
                Gson.this.checkValidFloatingPoint(f);
                jsonWriter.value(number);
            }
        };
    }

    private TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(number.toString());
            }
        };
    }

    private JsonWriter newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> class_) throws JsonSyntaxException {
        T t = this.fromJson(jsonElement, (Type)class_);
        return Primitives.wrap(class_).cast(t);
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) throws JsonSyntaxException {
        if (jsonElement == null) {
            return null;
        }
        return this.fromJson(new JsonElementReader(jsonElement), type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public <T> T fromJson(JsonReader jsonReader, Type type) throws JsonIOException, JsonSyntaxException {
        boolean bl = true;
        boolean bl2 = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            jsonReader.peek();
            bl = false;
            Object obj = this.miniGson.getAdapter(TypeToken.get(type)).read(jsonReader);
            return (T)obj;
        }
        catch (EOFException var8_6) {
            if (!bl) throw new JsonSyntaxException(var8_6);
            return null;
        }
        catch (IllegalStateException var7_8) {
            throw new JsonSyntaxException(var7_8);
        }
        catch (IOException var5_9) {
            throw new JsonSyntaxException(var5_9);
        }
        finally {
            jsonReader.setLenient(bl2);
        }
    }

    public <T> T fromJson(Reader reader, Class<T> class_) throws JsonSyntaxException, JsonIOException {
        JsonReader jsonReader = new JsonReader(reader);
        T t = this.fromJson(jsonReader, class_);
        Gson.assertFullConsumption(t, jsonReader);
        return Primitives.wrap(class_).cast(t);
    }

    public <T> T fromJson(Reader reader, Type type) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = new JsonReader(reader);
        T t = this.fromJson(jsonReader, type);
        Gson.assertFullConsumption(t, jsonReader);
        return t;
    }

    public <T> T fromJson(String string, Class<T> class_) throws JsonSyntaxException {
        T t = this.fromJson(string, (Type)class_);
        return Primitives.wrap(class_).cast(t);
    }

    public <T> T fromJson(String string, Type type) throws JsonSyntaxException {
        if (string == null) {
            return null;
        }
        return this.fromJson((Reader)new StringReader(string), type);
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, (Appendable)stringWriter);
        return stringWriter.toString();
    }

    public String toJson(Object object) {
        if (object == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(object, object.getClass());
    }

    public String toJson(Object object, Type type) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(object, type, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(JsonElement jsonElement, JsonWriter jsonWriter) throws JsonIOException {
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, jsonWriter);
            return;
        }
        catch (IOException var7_6) {
            throw new JsonIOException(var7_6);
        }
        finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public void toJson(JsonElement jsonElement, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(jsonElement, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException var3_3) {
            throw new RuntimeException(var3_3);
        }
    }

    public void toJson(Object object, Appendable appendable) throws JsonIOException {
        if (object != null) {
            this.toJson(object, object.getClass(), appendable);
            return;
        }
        this.toJson((JsonElement)JsonNull.INSTANCE, appendable);
    }

    public void toJson(Object object, Type type, JsonWriter jsonWriter) throws JsonIOException {
        TypeAdapter typeAdapter = this.miniGson.getAdapter(TypeToken.get(type));
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            typeAdapter.write(jsonWriter, (Object)object);
            return;
        }
        catch (IOException var9_8) {
            throw new JsonIOException(var9_8);
        }
        finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public void toJson(Object object, Type type, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(object, type, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException var4_4) {
            throw new JsonIOException(var4_4);
        }
    }

    public JsonElement toJsonTree(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        return this.toJsonTree(object, object.getClass());
    }

    public JsonElement toJsonTree(Object object, Type type) {
        JsonElementWriter jsonElementWriter = new JsonElementWriter();
        this.toJson(object, type, jsonElementWriter);
        return jsonElementWriter.get();
    }

    public String toString() {
        return "{" + "serializeNulls:" + this.serializeNulls + ",serializers:" + this.serializers + ",deserializers:" + this.deserializers + ",instanceCreators:" + this.constructorConstructor + "}";
    }

}

