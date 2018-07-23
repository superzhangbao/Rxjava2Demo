package com.hengsheng.rxjava2demo.rxhttp.cache.converter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.hengsheng.rxjava2demo.rxhttp.utils.HttpLog;
import com.hengsheng.rxjava2demo.rxhttp.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ConcurrentModificationException;

/**
 * Created by zhangb on 2018/7/18/017
 * 描述：GSON-数据转换器
 * 1.GSON-数据转换器其实就是存储字符串的操作
 * 2.如果你的Gson有特殊处理，可以自己创建一个，否则用默认。
 *
 * 优点：
 * 相对于SerializableDiskConverter转换器，存储的对象不需要进行序列化（Serializable），
 * 特别是一个对象中又包含很多其它对象，每个对象都需要Serializable，比较麻烦
 *
 * 缺点：
 * 就是存储和读取都要使用Gson进行转换，object->String->Object的给一个过程，相对来说
 * 每次都要转换性能略低，但是以现在的手机性能可以忽略不计了。
 */

@SuppressWarnings(value={"unchecked", "deprecation"})
public class GsonDiskConverter implements IDiskConverter {
    private Gson gson = new Gson();

    public GsonDiskConverter() {
        this.gson = new Gson();
    }

    public GsonDiskConverter(Gson gson) {
        Utils.checkNotNull(gson, "gson ==null");
        this.gson = gson;
    }

    @Override
    public <T> T load(InputStream source, Type type) {
        T value = null;
        try {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(source));
            value = (T) adapter.read(jsonReader);
            //value = gson.fromJson(new InputStreamReader(source), type);
        } catch (JsonIOException | IOException | ConcurrentModificationException | JsonSyntaxException e) {
            HttpLog.e(e.getMessage());
        } catch (Exception e){
            HttpLog.e(e.getMessage());
        }finally {
            Utils.close(source);
        }
        return value;
    }

    @Override
    public boolean writer(OutputStream sink, Object data) {
        try {
            String json = gson.toJson(data);
            byte[] bytes = json.getBytes();
            sink.write(bytes, 0, bytes.length);
            sink.flush();
            return true;
        } catch (JsonIOException | JsonSyntaxException | ConcurrentModificationException| IOException e) {
            HttpLog.e(e.getMessage());
        }catch (Exception e){
            HttpLog.e(e.getMessage());
        } finally {
            Utils.close(sink);
        }
        return false;
    }

}
