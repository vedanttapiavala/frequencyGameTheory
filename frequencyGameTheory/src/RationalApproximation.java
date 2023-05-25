import java.math.BigInteger;

public class RationalApproximation {

    public static void main(String[] args) {
        RationalApproximation app = new RationalApproximation();
        app.findRationalApproximation(334560, 5, 1000000);

        System.out.println("Test 1:");
        app.findRationalApproximation(334560, 5, 1000000);

        System.out.println("Test 2:");
        app.findRationalApproximation(1, 2, 3);

        System.out.println("Test 3:");
        app.findRationalApproximation(7, 3, 10);

        System.out.println("Test 4:");
        app.findRationalApproximation(31415, 100, 100000);

        System.out.println("Test 5:");
        app.findRationalApproximation(14142, 100, 100000);
    }

    public void findRationalApproximation(int alphaNum, int dNum, int denum) {
        BigInteger alpha = BigInteger.valueOf(alphaNum);
        BigInteger d = BigInteger.valueOf(dNum);
        BigInteger denom = BigInteger.valueOf(denum);

        BigInteger pA = BigInteger.ZERO;
        BigInteger qA = BigInteger.ONE;
        BigInteger pB = BigInteger.ONE;
        BigInteger qB = BigInteger.ONE;

        while (true) {
            BigInteger xNum = denom.multiply(pB).subtract(alpha.multiply(qB));
            BigInteger xDenom = denom.multiply(pA).negate().add(alpha.multiply(qA));
            BigInteger x = (xNum.add(xDenom).subtract(BigInteger.ONE)).divide(xDenom).add(BigInteger.ONE);

            boolean aa = matches(pB.add(x.multiply(pA)), qB.add(x.multiply(qA)), alpha, d, denom);
            boolean bb = matches(pB.add(x.subtract(BigInteger.ONE).multiply(pA)), qB.add(x.subtract(BigInteger.ONE).multiply(qA)), alpha, d, denom);

            if (aa || bb) {
                findExactSolutionLeft(pA, qA, pB, qB, alpha, d, denom);
                break;
            }

            BigInteger newPA = pB.add(x.subtract(BigInteger.ONE).multiply(pA));
            BigInteger newQA = qB.add(x.subtract(BigInteger.ONE).multiply(qA));
            BigInteger newPB = pB.add(x.multiply(pA));
            BigInteger newQB = qB.add(x.multiply(qA));

            pA = newPA;
            qA = newQA;
            pB = newPB;
            qB = newQB;

            xNum = alpha.multiply(qB).subtract(denom.multiply(pB));
            xDenom = alpha.multiply(qA).negate().add(denom.multiply(pA));
            x = (xNum.add(xDenom).subtract(BigInteger.ONE)).divide(xDenom).add(BigInteger.ONE);

            aa = matches(pB.add(x.multiply(pA)), qB.add(x.multiply(qA)), alpha, d, denom);
            bb = matches(pB.add(x.subtract(BigInteger.ONE).multiply(pA)), qB.add(x.subtract(BigInteger.ONE).multiply(qA)), alpha, d, denom);

            if (aa || bb) {
                findExactSolutionRight(pA, qA, pB, qB, alpha, d, denom);
                break;
            }

            newPA = pB.add(x.subtract(BigInteger.ONE).multiply(pA));
            newQA = qB.add(x.subtract(BigInteger.ONE).multiply(qA));
            newPB = pB.add(x.multiply(pA));
            newQB = qB.add(x.multiply(qA));

            pA = newPA;
            qA = newQA;
            pB = newPB;
            qB = newQB;
        }
    }

    private boolean matches(BigInteger a, BigInteger b, BigInteger alphaNum, BigInteger dNum, BigInteger denum) {
        if (lessOrEqual(a, b, alphaNum.subtract(dNum), denum))
            return false;
        if (less(a, b, alphaNum.add(dNum), denum))
            return true;
        return false;
    }

    private boolean less(BigInteger a, BigInteger b, BigInteger c, BigInteger d) {
        return a.multiply(d).compareTo(b.multiply(c)) < 0;
    }

    private boolean lessOrEqual(BigInteger a, BigInteger b, BigInteger c, BigInteger d) {
        return a.multiply(d).compareTo(b.multiply(c)) <= 0;
    }

    private void findExactSolutionLeft(BigInteger pA, BigInteger qA, BigInteger pB, BigInteger qB, BigInteger alphaNum, BigInteger dNum, BigInteger denum) {
        BigInteger kNum = denum.multiply(pB).subtract(alphaNum.add(dNum).multiply(qB));
        BigInteger kDenom = alphaNum.add(dNum).multiply(qA).subtract(denum.multiply(pA));
        BigInteger k = kNum.divide(kDenom).add(BigInteger.ONE);
        System.out.println(pB.add(k.multiply(pA)) + " " + qB.add(k.multiply(qA)));
    }

    private void findExactSolutionRight(BigInteger pA, BigInteger qA, BigInteger pB, BigInteger qB, BigInteger alphaNum, BigInteger dNum, BigInteger denum) {
        BigInteger kNum = denum.multiply(pB).negate().add(alphaNum.subtract(dNum).multiply(qB));
        BigInteger kDenom = alphaNum.subtract(dNum).multiply(qA).negate().add(denum.multiply(pA));
        BigInteger k = kNum.divide(kDenom).add(BigInteger.ONE);
        System.out.println(pB.add(k.multiply(pA)) + " " + qB.add(k.multiply(qA)));
    }
}