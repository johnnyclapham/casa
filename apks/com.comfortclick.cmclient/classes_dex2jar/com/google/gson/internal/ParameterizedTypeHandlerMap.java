/*
 * Decompiled with CFR 0_96.
 */
package com.google.gson.internal;

import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ParameterizedTypeHandlerMap<T> {
    private static final Logger logger = Logger.getLogger(ParameterizedTypeHandlerMap.class.getName());
    private boolean modifiable = true;
    private final Map<Type, T> systemMap = new HashMap<Type, T>();
    private final List<Pair<Class<?>, T>> systemTypeHierarchyList = new ArrayList();
    private final Map<Type, T> userMap = new HashMap<Type, T>();
    private final List<Pair<Class<?>, T>> userTypeHierarchyList = new ArrayList();

    /*
     * Enabled aggressive block sorting
     */
    private void appendList(StringBuilder stringBuilder, List<Pair<Class<?>, T>> list) {
        boolean bl = true;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Pair pair = iterator.next();
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.typeToString((Type)pair.first)).append(':');
            stringBuilder.append(pair.second);
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void appendMap(StringBuilder stringBuilder, Map<Type, T> map) {
        boolean bl = true;
        Iterator<Map.Entry<Type, T>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Type, T> entry = iterator.next();
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.typeToString(entry.getKey())).append(':');
            stringBuilder.append(entry.getValue());
        }
        return;
    }

    private T getHandlerForTypeHierarchy(Class<?> class_, boolean bl) {
        if (!bl) {
            for (Pair pair : this.userTypeHierarchyList) {
                if (!((Class)pair.first).isAssignableFrom(class_)) continue;
                return (T)pair.second;
            }
        }
        for (Pair pair : this.systemTypeHierarchyList) {
            if (!((Class)pair.first).isAssignableFrom(class_)) continue;
            return (T)pair.second;
        }
        return null;
    }

    private static <T> int getIndexOfAnOverriddenHandler(Class<?> class_, List<Pair<Class<?>, T>> list) {
        for (int i = -1 + list.size(); i >= 0; --i) {
            if (!class_.isAssignableFrom((Class)list.get((int)i).first)) continue;
            return i;
        }
        return -1;
    }

    private static <T> int getIndexOfSpecificHandlerForTypeHierarchy(Class<?> class_, List<Pair<Class<?>, T>> list) {
        for (int i = -1 + list.size(); i >= 0; --i) {
            if (!class_.equals(list.get((int)i).first)) continue;
            return i;
        }
        return -1;
    }

    private String typeToString(Type type) {
        return $Gson$Types.getRawType(type).getSimpleName();
    }

    public ParameterizedTypeHandlerMap<T> copyOf() {
        synchronized (this) {
            ParameterizedTypeHandlerMap<T> parameterizedTypeHandlerMap = new ParameterizedTypeHandlerMap<T>();
            parameterizedTypeHandlerMap.systemMap.putAll(this.systemMap);
            parameterizedTypeHandlerMap.userMap.putAll(this.userMap);
            parameterizedTypeHandlerMap.systemTypeHierarchyList.addAll(this.systemTypeHierarchyList);
            parameterizedTypeHandlerMap.userTypeHierarchyList.addAll(this.userTypeHierarchyList);
            return parameterizedTypeHandlerMap;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public T getHandlerFor(Type type, boolean bl) {
        synchronized (this) {
            T t;
            Class class_;
            T t2;
            T t3;
            T t4;
            T t5 = !bl && (t2 = this.userMap.get(type)) != null ? t2 : ((t4 = this.systemMap.get(type)) != null ? t4 : ((class_ = $Gson$Types.getRawType(type)) != type && (t = this.getHandlerFor(class_, bl)) != null ? t : (t3 = this.getHandlerForTypeHierarchy(class_, bl))));
            return t5;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasSpecificHandlerFor(Type type) {
        synchronized (this) {
            if (this.userMap.containsKey(type)) return true;
            boolean bl = this.systemMap.containsKey(type);
            if (!bl) return false;
            return true;
        }
    }

    public ParameterizedTypeHandlerMap<T> makeUnmodifiable() {
        synchronized (this) {
            this.modifiable = false;
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void register(Type type, T t, boolean bl) {
        synchronized (this) {
            if (!this.modifiable) {
                throw new IllegalStateException("Attempted to modify an unmodifiable map.");
            }
            if (this.hasSpecificHandlerFor(type)) {
                logger.log(Level.WARNING, "Overriding the existing type handler for {0}", type);
            }
            Map<Type, T> map = bl ? this.systemMap : this.userMap;
            map.put(type, t);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void registerForTypeHierarchy(Pair<Class<?>, T> pair, boolean bl) {
        synchronized (this) {
            int n;
            if (!this.modifiable) {
                throw new IllegalStateException("Attempted to modify an unmodifiable map.");
            }
            List list = bl ? this.systemTypeHierarchyList : this.userTypeHierarchyList;
            int n2 = ParameterizedTypeHandlerMap.getIndexOfSpecificHandlerForTypeHierarchy((Class)pair.first, list);
            if (n2 >= 0) {
                logger.log(Level.WARNING, "Overriding the existing type handler for {0}", pair.first);
                list.remove(n2);
            }
            if ((n = ParameterizedTypeHandlerMap.getIndexOfAnOverriddenHandler((Class)pair.first, list)) >= 0) {
                throw new IllegalArgumentException("The specified type handler for type " + pair.first + " hides the previously registered type hierarchy handler for " + list.get((int)n).first + ". Gson does not allow this.");
            }
            list.add(0, pair);
            return;
        }
    }

    public void registerForTypeHierarchy(Class<?> class_, T t, boolean bl) {
        synchronized (this) {
            this.registerForTypeHierarchy(new Pair(class_, t), bl);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     */
    public void registerIfAbsent(ParameterizedTypeHandlerMap<T> parameterizedTypeHandlerMap) {
        synchronized (this) {
            if (!this.modifiable) {
                throw new IllegalStateException("Attempted to modify an unmodifiable map.");
            }
            for (Map.Entry<Type, T> entry : parameterizedTypeHandlerMap.userMap.entrySet()) {
                if (this.userMap.containsKey(entry.getKey())) continue;
                this.register(entry.getKey(), entry.getValue(), false);
            }
            for (Map.Entry<Type, T> entry2 : parameterizedTypeHandlerMap.systemMap.entrySet()) {
                if (this.systemMap.containsKey(entry2.getKey())) continue;
                this.register(entry2.getKey(), entry2.getValue(), true);
            }
            int n = -1 + parameterizedTypeHandlerMap.userTypeHierarchyList.size();
            do {
                if (n >= 0) {
                    Pair pair = parameterizedTypeHandlerMap.userTypeHierarchyList.get(n);
                    if (ParameterizedTypeHandlerMap.getIndexOfSpecificHandlerForTypeHierarchy((Class)pair.first, this.userTypeHierarchyList) < 0) {
                        this.registerForTypeHierarchy(pair, false);
                    }
                } else {
                    int n2 = -1 + parameterizedTypeHandlerMap.systemTypeHierarchyList.size();
                    do {
                        if (n2 < 0) {
                            return;
                        }
                        Pair pair = parameterizedTypeHandlerMap.systemTypeHierarchyList.get(n2);
                        if (ParameterizedTypeHandlerMap.getIndexOfSpecificHandlerForTypeHierarchy((Class)pair.first, this.systemTypeHierarchyList) < 0) {
                            this.registerForTypeHierarchy(pair, true);
                        }
                        --n2;
                    } while (true);
                }
                --n;
            } while (true);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{userTypeHierarchyList:{");
        this.appendList(stringBuilder, this.userTypeHierarchyList);
        stringBuilder.append("},systemTypeHierarchyList:{");
        this.appendList(stringBuilder, this.systemTypeHierarchyList);
        stringBuilder.append("},userMap:{");
        this.appendMap(stringBuilder, this.userMap);
        stringBuilder.append("},systemMap:{");
        this.appendMap(stringBuilder, this.systemMap);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

