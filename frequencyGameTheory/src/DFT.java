public class DFT {
  public static Complex[] fourier(int[] vals) {
    int n = vals.length;
    Complex[] result = new Complex[n];
    
    for (int k = 0; k < n; k++) {
      double realSum = 0;
      double imagSum = 0;
      for (int t = 0; t < n; t++) {
        double angle = 2 * Math.PI * t * k / n;
        realSum += vals[t] * Math.cos(angle);
        imagSum -= vals[t] * Math.sin(angle);
      }
      result[k] = new Complex(realSum, imagSum);
    }
    
    return result;
  }
  
  public static double[] magnitudes(Complex[] coeffs) {
    double[] magnitudes = new double[coeffs.length];
    for (int i = 0; i < coeffs.length; i++) {
      magnitudes[i] = Math.sqrt(coeffs[i].getReal() * coeffs[i].getReal() + coeffs[i].getImag() * coeffs[i].getImag());
    }
    return magnitudes;
  }
  
  public static class Complex {
    private final double real;
    private final double imag;
    
    public Complex(double real, double imag) {
      this.real = real;
      this.imag = imag;
    }
    
    public double getReal() {
      return real;
    }
    
    public double getImag() {
      return imag;
    }
    
    @Override
    public String toString() {
      return String.format("%f + %fi", real, imag);
    }
  }
}
