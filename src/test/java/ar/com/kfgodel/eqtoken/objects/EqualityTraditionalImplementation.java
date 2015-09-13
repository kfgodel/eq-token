package ar.com.kfgodel.eqtoken.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by tenpines on 12/09/15.
 */
public class EqualityTraditionalImplementation {

    private Integer number;
    private String text;
    private List<EqualityTraditionalImplementation> children;

    public Integer getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public List<EqualityTraditionalImplementation> getChildren() {
        return children;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static EqualityTraditionalImplementation create(){
        EqualityTraditionalImplementation root = create(null,0);
        for (int i = 0; i < 10; i++) {
            EqualityTraditionalImplementation child = create(root, i);
            root.children.add(child);
            for (int j = 0; j < 10; j++) {
                EqualityTraditionalImplementation subChild = create(child, j);
                child.children.add(subChild);
            }
        }
        return root;
    }


    public static EqualityTraditionalImplementation create(EqualityTraditionalImplementation parent, Integer numero){
        EqualityTraditionalImplementation object = new EqualityTraditionalImplementation();
        object.number = numero;
        object.text = createTextFromParent(parent, numero);
        object.children = new ArrayList<>();
        return object;
    }

    private static String createTextFromParent(EqualityTraditionalImplementation parent, Integer numero) {
        if(parent == null){
            return "Root_" + String.valueOf(numero);
        }
        return parent.text + "_child_" + String.valueOf(numero);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof EqualityTraditionalImplementation)){
            return false;
        }
        EqualityTraditionalImplementation that = (EqualityTraditionalImplementation) obj;
        return this.number.equals(that.number) && this.text.equals(that.text) && this.children.equals(that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number, this.text, this.children);
    }
}
