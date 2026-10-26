// Marco Antonio Pavia Flores
// EvaluacionRiesgoDataScience.java

public class EvaluacionRiesgoDataScience {

    // Función de densidad conjunta de probabilidad f(x, y) (estrictamente positiva)
    public static double densidadProbabilidadConjunta(double x, double y) {
        // Distribución exponencial decreciente acotada en el primer cuadrante
        return Math.exp(-(x + y));
    }

    // Función de costo o pérdida g(x, y) estructurada para ser siempre positiva
    public static double funcionPerdida(double x, double y) {
        // Costo base fijo de penalización + penalización cuadrática por nivel de endeudamiento
        return 5000.0 + 1200.0 * Math.pow(x, 2) + 800.0 * Math.pow(y, 2);
    }

    public static void main(String[] args) {
        // Límites del espacio de características en el primer cuadrante [a, b] y [c, d]
        double a = 0.0, b = 3.0; // Rango de riesgo bajo a alto (positivo)
        double c = 0.0, d = 3.0; // Rango de endeudamiento (positivo)

        int n = 100; // Subintervalos en X
        int m = 100; // Subintervalos en Y

        double hx = (b - a) / n;
        double hy = (d - c) / m;

        double sumaEsperanza = 0.0;

        // Implementación de integración numérica bidimensional (Regla de Simpson 1/3)
        for (int i = 0; i <= n; i++) {
            double x = a + i * hx;
            double wx = (i == 0 || i == n) ? 1.0 : (i % 2 == 1 ? 4.0 : 2.0);

            for (int j = 0; j <= m; j++) {
                double y = c + j * hy;
                double wy = (j == 0 || j == n) ? 1.0 : (j % 2 == 1 ? 4.0 : 2.0);

                // Integrando: g(x, y) * f(x, y)
                double valorIntegrando = funcionPerdida(x, y) * densidadProbabilidadConjunta(x, y);

                sumaEsperanza += wx * wy * valorIntegrando;
            }
        }

        // Multiplicación por el factor diferencial compuesto de Simpson (hx * hy / 9)
        double perdidaEsperadaTotal = (hx * hy / 9.0) * sumaEsperanza;

        System.out.println("=== CIENCIA DE DATOS: INFERENCIA NUMÉRICA DE RIESGO ===");
        System.out.printf("Pérdida esperada estimada en la región de riesgo: $%.4f MXN\n", perdidaEsperadaTotal);
    }
}