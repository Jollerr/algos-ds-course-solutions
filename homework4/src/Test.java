import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test {

    public static void main(String[] args) {
        Quaternion q = new Quaternion(0.,0,0.,0.);
        q.divideByLeft(q);
    }
}
