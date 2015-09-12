package ar.com.kfgodel.eqtoken;

/**
 * Created by tenpines on 12/09/15.
 */
public class TestObject {

    private int[] predefinedHashCodes;
    private int nextPredefinedHashcodeIndex;
    private int numberOfHashcodeCalls;

    public static TestObject create(){
        TestObject testObject = new TestObject();
        testObject.nextPredefinedHashcodeIndex = 0;
        testObject.predefinedHashCodes = new int[0];
        testObject.numberOfHashcodeCalls = 0;
        return testObject;
    }

    public void answerAsHashcode(int... predefinedHashcodes) {
        this.predefinedHashCodes = predefinedHashcodes;
    }

    @Override
    public int hashCode() {
        numberOfHashcodeCalls++;
        if(nextPredefinedHashcodeIndex == predefinedHashCodes.length){
            // No more pre-defined hashcodes
            return super.hashCode();
        }
        return predefinedHashCodes[nextPredefinedHashcodeIndex++];
    }

    public int numberOfHashcodeCalls() {
        return numberOfHashcodeCalls;
    }
}
