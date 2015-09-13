package ar.com.kfgodel.eqtoken.objects;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by tenpines on 13/09/15.
 */
public class TraditionalSimpleObject {

    private Integer number;
    private String text;
    private List<Long> list;

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public List<Long> getList() {
        return list;
    }

    public static TraditionalSimpleObject create(){
        TraditionalSimpleObject object = new TraditionalSimpleObject();
        object.number = 0;
        object.text = "Some nice random text for testing";
        object.list = LongStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        return object;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!TraditionalSimpleObject.class.isInstance(obj)){
            return false;
        }
        TraditionalSimpleObject that = (TraditionalSimpleObject) obj;
        return this.number.equals(that.number) && this.text.equals(that.text) && this.list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number, this.text, this.list);
    }
}
