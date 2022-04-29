// 
// Decompiled by Procyon v0.5.29
// 

package com.google.gson.stream;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.Closeable;

public class JsonReader implements Closeable
{
    private static final char[] NON_EXECUTE_PREFIX;
    private final char[] buffer;
    private int bufferStartColumn;
    private int bufferStartLine;
    private boolean hasToken;
    private final Reader in;
    private boolean lenient;
    private int limit;
    private String name;
    private int pos;
    private boolean skipping;
    private final List<JsonScope> stack;
    private final StringPool stringPool;
    private JsonToken token;
    private String value;
    
    static {
        NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
    }
    
    public JsonReader(final Reader in) {
        this.stringPool = new StringPool();
        this.lenient = false;
        this.buffer = new char[1024];
        this.pos = 0;
        this.limit = 0;
        this.bufferStartLine = 1;
        this.bufferStartColumn = 1;
        this.stack = new ArrayList<JsonScope>();
        this.push(JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        this.in = in;
    }
    
    private JsonToken advance() throws IOException {
        this.quickPeek();
        final JsonToken token = this.token;
        this.hasToken = false;
        this.token = null;
        this.value = null;
        this.name = null;
        return token;
    }
    
    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }
    
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace();
        --this.pos;
        if (this.pos + JsonReader.NON_EXECUTE_PREFIX.length <= this.limit || this.fillBuffer(JsonReader.NON_EXECUTE_PREFIX.length)) {
            for (int i = 0; i < JsonReader.NON_EXECUTE_PREFIX.length; ++i) {
                if (this.buffer[i + this.pos] != JsonReader.NON_EXECUTE_PREFIX[i]) {
                    return;
                }
            }
            this.pos += JsonReader.NON_EXECUTE_PREFIX.length;
        }
    }
    
    private void decodeLiteral() throws IOException {
        if (this.value.equalsIgnoreCase("null")) {
            this.token = JsonToken.NULL;
            return;
        }
        if (this.value.equalsIgnoreCase("true") || this.value.equalsIgnoreCase("false")) {
            this.token = JsonToken.BOOLEAN;
            return;
        }
        try {
            Double.parseDouble(this.value);
            this.token = JsonToken.NUMBER;
        }
        catch (NumberFormatException ex) {
            this.checkLenient();
            this.token = JsonToken.STRING;
        }
    }
    
    private void expect(final JsonToken jsonToken) throws IOException {
        this.quickPeek();
        if (this.token != jsonToken) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + this.peek());
        }
        this.advance();
    }
    
    private boolean fillBuffer(final int n) throws IOException {
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                ++this.bufferStartLine;
                this.bufferStartColumn = 1;
            }
            else {
                ++this.bufferStartColumn;
            }
        }
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(this.buffer, this.pos, this.buffer, 0, this.limit);
        }
        else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            final int read = this.in.read(this.buffer, this.limit, this.buffer.length - this.limit);
            if (read == -1) {
                return false;
            }
            this.limit += read;
            if (this.bufferStartLine != 1 || this.bufferStartColumn != 1 || this.limit <= 0 || this.buffer[0] != '\ufeff') {
                continue;
            }
            ++this.pos;
            --this.bufferStartColumn;
        } while (this.limit < n);
        return true;
    }
    
    private int getColumnNumber() {
        int bufferStartColumn = this.bufferStartColumn;
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                bufferStartColumn = 1;
            }
            else {
                ++bufferStartColumn;
            }
        }
        return bufferStartColumn;
    }
    
    private int getLineNumber() {
        int bufferStartLine = this.bufferStartLine;
        for (int i = 0; i < this.pos; ++i) {
            if (this.buffer[i] == '\n') {
                ++bufferStartLine;
            }
        }
        return bufferStartLine;
    }
    
    private CharSequence getSnippet() {
        final StringBuilder sb = new StringBuilder();
        final int min = Math.min(this.pos, 20);
        sb.append(this.buffer, this.pos - min, min);
        sb.append(this.buffer, this.pos, Math.min(this.limit - this.pos, 20));
        return sb;
    }
    
    private JsonToken nextInArray(final boolean b) throws IOException {
        if (b) {
            this.replaceTop(JsonScope.NONEMPTY_ARRAY);
        }
        else {
            switch (this.nextNonWhitespace()) {
                case 59: {
                    this.checkLenient();
                }
                case 44: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated array");
                }
                case 93: {
                    this.pop();
                    this.hasToken = true;
                    return this.token = JsonToken.END_ARRAY;
                }
            }
        }
        switch (this.nextNonWhitespace()) {
            default: {
                --this.pos;
                return this.nextValue();
            }
            case 93: {
                if (b) {
                    this.pop();
                    this.hasToken = true;
                    return this.token = JsonToken.END_ARRAY;
                }
            }
            case 44:
            case 59: {
                this.checkLenient();
                --this.pos;
                this.hasToken = true;
                this.value = "null";
                return this.token = JsonToken.NULL;
            }
        }
    }
    
    private JsonToken nextInObject(final boolean b) throws IOException {
        if (b) {
            switch (this.nextNonWhitespace()) {
                default: {
                    --this.pos;
                    break;
                }
                case 125: {
                    this.pop();
                    this.hasToken = true;
                    return this.token = JsonToken.END_OBJECT;
                }
            }
        }
        else {
            switch (this.nextNonWhitespace()) {
                case 44:
                case 59: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated object");
                }
                case 125: {
                    this.pop();
                    this.hasToken = true;
                    return this.token = JsonToken.END_OBJECT;
                }
            }
        }
        final int nextNonWhitespace = this.nextNonWhitespace();
        switch (nextNonWhitespace) {
            default: {
                this.checkLenient();
                --this.pos;
                this.name = this.nextLiteral();
                if (this.name.length() == 0) {
                    throw this.syntaxError("Expected name");
                }
                break;
            }
            case 39: {
                this.checkLenient();
            }
            case 34: {
                this.name = this.nextString((char)nextNonWhitespace);
                break;
            }
        }
        this.replaceTop(JsonScope.DANGLING_NAME);
        this.hasToken = true;
        return this.token = JsonToken.NAME;
    }
    
    private String nextLiteral() throws IOException {
        StringBuilder sb = null;
        do {
            final int pos = this.pos;
            while (this.pos < this.limit) {
                switch (this.buffer[this.pos++]) {
                    default: {
                        continue;
                    }
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}': {
                        --this.pos;
                        if (this.skipping) {
                            return "skipped!";
                        }
                        if (sb == null) {
                            return this.stringPool.get(this.buffer, pos, this.pos - pos);
                        }
                        sb.append(this.buffer, pos, this.pos - pos);
                        return sb.toString();
                    }
                }
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(this.buffer, pos, this.pos - pos);
        } while (this.fillBuffer(1));
        return sb.toString();
    }
    
    private int nextNonWhitespace() throws IOException {
        while (this.pos < this.limit || this.fillBuffer(1)) {
            final char c = this.buffer[this.pos++];
            switch (c) {
                case 35: {
                    this.checkLenient();
                    this.skipToEndOfLine();
                }
                case 9:
                case 10:
                case 13:
                case 32: {
                    continue;
                }
                case 47: {
                    if (this.pos == this.limit && !this.fillBuffer(1)) {
                        break;
                    }
                    this.checkLenient();
                    switch (this.buffer[this.pos]) {
                        default: {
                            return c;
                        }
                        case '*': {
                            ++this.pos;
                            if (!this.skipTo("*/")) {
                                throw this.syntaxError("Unterminated comment");
                            }
                            this.pos += 2;
                            continue;
                        }
                        case '/': {
                            ++this.pos;
                            this.skipToEndOfLine();
                            continue;
                        }
                    }
                    break;
                }
            }
            return c;
        }
        throw new EOFException("End of input");
    }
    
    private String nextString(final char c) throws IOException {
        StringBuilder sb = null;
        do {
            int n = this.pos;
            while (this.pos < this.limit) {
                final char c2 = this.buffer[this.pos++];
                if (c2 == c) {
                    if (this.skipping) {
                        return "skipped!";
                    }
                    if (sb == null) {
                        return this.stringPool.get(this.buffer, n, -1 + (this.pos - n));
                    }
                    sb.append(this.buffer, n, -1 + (this.pos - n));
                    return sb.toString();
                }
                else {
                    if (c2 != '\\') {
                        continue;
                    }
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.buffer, n, -1 + (this.pos - n));
                    sb.append(this.readEscapeCharacter());
                    n = this.pos;
                }
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(this.buffer, n, this.pos - n);
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }
    
    private JsonToken nextValue() throws IOException {
        final int nextNonWhitespace = this.nextNonWhitespace();
        switch (nextNonWhitespace) {
            default: {
                --this.pos;
                return this.readLiteral();
            }
            case 123: {
                this.push(JsonScope.EMPTY_OBJECT);
                this.hasToken = true;
                return this.token = JsonToken.BEGIN_OBJECT;
            }
            case 91: {
                this.push(JsonScope.EMPTY_ARRAY);
                this.hasToken = true;
                return this.token = JsonToken.BEGIN_ARRAY;
            }
            case 39: {
                this.checkLenient();
            }
            case 34: {
                this.value = this.nextString((char)nextNonWhitespace);
                this.hasToken = true;
                return this.token = JsonToken.STRING;
            }
        }
    }
    
    private JsonToken objectValue() throws IOException {
        switch (this.nextNonWhitespace()) {
            default: {
                throw this.syntaxError("Expected ':'");
            }
            case 61: {
                this.checkLenient();
                if ((this.pos < this.limit || this.fillBuffer(1)) && this.buffer[this.pos] == '>') {
                    ++this.pos;
                }
            }
            case 58: {
                this.replaceTop(JsonScope.NONEMPTY_OBJECT);
                return this.nextValue();
            }
        }
    }
    
    private JsonScope peekStack() {
        return this.stack.get(-1 + this.stack.size());
    }
    
    private JsonScope pop() {
        return this.stack.remove(-1 + this.stack.size());
    }
    
    private void push(final JsonScope jsonScope) {
        this.stack.add(jsonScope);
    }
    
    private JsonToken quickPeek() throws IOException {
        JsonToken jsonToken = null;
        if (this.hasToken) {
            jsonToken = this.token;
        }
        else {
            switch (this.peekStack()) {
                default: {
                    throw new AssertionError();
                }
                case EMPTY_DOCUMENT: {
                    if (this.lenient) {
                        this.consumeNonExecutePrefix();
                    }
                    this.replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                    jsonToken = this.nextValue();
                    if (!this.lenient && jsonToken != JsonToken.BEGIN_ARRAY && jsonToken != JsonToken.BEGIN_OBJECT) {
                        this.syntaxError("Expected JSON document to start with '[' or '{'");
                        return jsonToken;
                    }
                    break;
                }
                case EMPTY_ARRAY: {
                    return this.nextInArray(true);
                }
                case NONEMPTY_ARRAY: {
                    return this.nextInArray(false);
                }
                case EMPTY_OBJECT: {
                    return this.nextInObject(true);
                }
                case DANGLING_NAME: {
                    return this.objectValue();
                }
                case NONEMPTY_OBJECT: {
                    return this.nextInObject(false);
                }
                case NONEMPTY_DOCUMENT: {
                    try {
                        final JsonToken nextValue = this.nextValue();
                        if (this.lenient) {
                            return nextValue;
                        }
                        throw this.syntaxError("Expected EOF");
                    }
                    catch (EOFException ex) {
                        this.hasToken = true;
                        return this.token = JsonToken.END_DOCUMENT;
                    }
                }
                case CLOSED: {
                    throw new IllegalStateException("JsonReader is closed");
                }
            }
        }
        return jsonToken;
    }
    
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        final char c = this.buffer[this.pos++];
        switch (c) {
            default: {
                return c;
            }
            case 117: {
                if (4 + this.pos > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                final String value = this.stringPool.get(this.buffer, this.pos, 4);
                this.pos += 4;
                return (char)Integer.parseInt(value, 16);
            }
            case 116: {
                return '\t';
            }
            case 98: {
                return '\b';
            }
            case 110: {
                return '\n';
            }
            case 114: {
                return '\r';
            }
            case 102: {
                return '\f';
            }
        }
    }
    
    private JsonToken readLiteral() throws IOException {
        final String nextLiteral = this.nextLiteral();
        if (nextLiteral.length() == 0) {
            throw this.syntaxError("Expected literal value");
        }
        this.value = nextLiteral;
        this.hasToken = true;
        return this.token = null;
    }
    
    private void replaceTop(final JsonScope jsonScope) {
        this.stack.set(-1 + this.stack.size(), jsonScope);
    }
    
    private boolean skipTo(final String s) throws IOException {
    Label_0000:
        while (this.pos + s.length() <= this.limit || this.fillBuffer(s.length())) {
            for (int i = 0; i < s.length(); ++i) {
                if (this.buffer[i + this.pos] != s.charAt(i)) {
                    ++this.pos;
                    continue Label_0000;
                }
            }
            return true;
        }
        return false;
    }
    
    private void skipToEndOfLine() throws IOException {
        while (this.pos < this.limit || this.fillBuffer(1)) {
            final char c = this.buffer[this.pos++];
            if (c == '\r' || c == '\n') {
                break;
            }
        }
    }
    
    private IOException syntaxError(final String s) throws IOException {
        throw new MalformedJsonException(s + " at line " + this.getLineNumber() + " column " + this.getColumnNumber());
    }
    
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
    }
    
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
    }
    
    @Override
    public void close() throws IOException {
        this.hasToken = false;
        this.value = null;
        this.token = null;
        this.stack.clear();
        this.stack.add(JsonScope.CLOSED);
        this.in.close();
    }
    
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
    }
    
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
    }
    
    public boolean hasNext() throws IOException {
        this.quickPeek();
        return this.token != JsonToken.END_OBJECT && this.token != JsonToken.END_ARRAY;
    }
    
    public final boolean isLenient() {
        return this.lenient;
    }
    
    public boolean nextBoolean() throws IOException {
        this.quickPeek();
        if (this.value == null || this.token == JsonToken.STRING) {
            throw new IllegalStateException("Expected a boolean but was " + this.peek());
        }
        boolean b;
        if (this.value.equalsIgnoreCase("true")) {
            b = true;
        }
        else {
            if (!this.value.equalsIgnoreCase("false")) {
                throw new IllegalStateException("Not a boolean: " + this.value);
            }
            b = false;
        }
        this.advance();
        return b;
    }
    
    public double nextDouble() throws IOException {
        this.quickPeek();
        if (this.value == null) {
            throw new IllegalStateException("Expected a double but was " + this.peek());
        }
        final double double1 = Double.parseDouble(this.value);
        if (double1 >= 1.0 && this.value.startsWith("0")) {
            throw new NumberFormatException("JSON forbids octal prefixes: " + this.value);
        }
        if (!this.lenient && (Double.isNaN(double1) || Double.isInfinite(double1))) {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + this.value);
        }
        this.advance();
        return double1;
    }
    
    public int nextInt() throws IOException {
        this.quickPeek();
        if (this.value == null) {
            throw new IllegalStateException("Expected an int but was " + this.peek());
        }
        int int1;
        while (true) {
            try {
                int1 = Integer.parseInt(this.value);
                if (int1 >= 1L && this.value.startsWith("0")) {
                    throw new NumberFormatException("JSON forbids octal prefixes: " + this.value);
                }
            }
            catch (NumberFormatException ex) {
                final double double1 = Double.parseDouble(this.value);
                int1 = (int)double1;
                if (int1 != double1) {
                    throw new NumberFormatException(this.value);
                }
                continue;
            }
            break;
        }
        this.advance();
        return int1;
    }
    
    public long nextLong() throws IOException {
        this.quickPeek();
        if (this.value == null) {
            throw new IllegalStateException("Expected a long but was " + this.peek());
        }
        long long1;
        while (true) {
            try {
                long1 = Long.parseLong(this.value);
                if (long1 >= 1L && this.value.startsWith("0")) {
                    throw new NumberFormatException("JSON forbids octal prefixes: " + this.value);
                }
            }
            catch (NumberFormatException ex) {
                final double double1 = Double.parseDouble(this.value);
                long1 = (long)double1;
                if (long1 != double1) {
                    throw new NumberFormatException(this.value);
                }
                continue;
            }
            break;
        }
        this.advance();
        return long1;
    }
    
    public String nextName() throws IOException {
        this.quickPeek();
        if (this.token != JsonToken.NAME) {
            throw new IllegalStateException("Expected a name but was " + this.peek());
        }
        final String name = this.name;
        this.advance();
        return name;
    }
    
    public void nextNull() throws IOException {
        this.quickPeek();
        if (this.value == null || this.token == JsonToken.STRING) {
            throw new IllegalStateException("Expected null but was " + this.peek());
        }
        if (!this.value.equalsIgnoreCase("null")) {
            throw new IllegalStateException("Not a null: " + this.value);
        }
        this.advance();
    }
    
    public String nextString() throws IOException {
        this.peek();
        if (this.value == null || (this.token != JsonToken.STRING && this.token != JsonToken.NUMBER)) {
            throw new IllegalStateException("Expected a string but was " + this.peek());
        }
        final String value = this.value;
        this.advance();
        return value;
    }
    
    public JsonToken peek() throws IOException {
        this.quickPeek();
        if (this.token == null) {
            this.decodeLiteral();
        }
        return this.token;
    }
    
    public final void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public void skipValue() throws IOException {
        this.skipping = true;
        int n = 0;
        try {
            do {
                final JsonToken advance = this.advance();
                if (advance == JsonToken.BEGIN_ARRAY || advance == JsonToken.BEGIN_OBJECT) {
                    ++n;
                }
                else if (advance == JsonToken.END_ARRAY || advance == JsonToken.END_OBJECT) {
                    --n;
                }
            } while (n != 0);
        }
        finally {
            this.skipping = false;
        }
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " near " + (Object)this.getSnippet();
    }
}
