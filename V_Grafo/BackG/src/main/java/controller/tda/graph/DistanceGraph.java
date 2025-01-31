package controller.tda.graph;

public class DistanceGraph {
    private static final double A = 6378137.0;
    private static final double F = 1 / 298.257223563;
    private static final double B = A * (1 - F);

    public static double distanciaVincenty(double lat1, double lon1, double lat2, double lon2) {
        double U1 = Math.atan((1 - F) * Math.tan(Math.toRadians(lat1)));
        double U2 = Math.atan((1 - F) * Math.tan(Math.toRadians(lat2)));
        double L = Math.toRadians(lon2 - lon1);
        double lambda = L;
        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);

        double sinLambda, cosLambda, sinSigma, cosSigma, sigma, sinAlpha, cos2Alpha, cos2SigmaM, C;
        double lambdaP;
        int iterLimit = 100;
        do {
            sinLambda = Math.sin(lambda);
            cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) +
                    (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
            if (sinSigma == 0)
                return 0;
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cos2Alpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cos2Alpha;
            if (Double.isNaN(cos2SigmaM))
                cos2SigmaM = 0;
            C = F / 16 * cos2Alpha * (4 + F * (4 - 3 * cos2Alpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * F * sinAlpha *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

        if (iterLimit == 0)
            return Double.NaN;

        double uSquared = cos2Alpha * (A * A - B * B) / (B * B);
        double A_ = 1 + uSquared / 16384 * (4096 + uSquared * (-768 + uSquared * (320 - 175 * uSquared)));
        double B_ = uSquared / 1024 * (256 + uSquared * (-128 + uSquared * (74 - 47 * uSquared)));
        double deltaSigma = B_ * sinSigma * (cos2SigmaM + B_ / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
                B_ / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

        double s = B * A_ * (sigma - deltaSigma);

        return s / 1000;
    }

    // ========= fWarshall =========
    public static double[][] fWarshall(double[][] dist) {
        int V = dist.length;
        double[][] clonDistancia = new double[V][V];

        for (int i = 0; i < V; i++) {
            System.arraycopy(dist[i], 0, clonDistancia[i], 0, V);
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (clonDistancia[i][k] + clonDistancia[k][j] < clonDistancia[i][j]) {
                        clonDistancia[i][j] = clonDistancia[i][k] + clonDistancia[k][j];
                    }
                }
            }
        }
        return clonDistancia;
    }

    //========= bellFord =========
    public static double[] bellFord(double[][] dist, int vInicio) { //vr Inico
        int V = dist.length;
        double[] cDistances = new double[V];
        for (int i = 0; i < V; i++) {
            cDistances[i] = Double.MAX_VALUE;
        }
        cDistances[vInicio] = 0;

        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (dist[u][v] != Double.MAX_VALUE && cDistances[u] != Double.MAX_VALUE &&
                            cDistances[u] + dist[u][v] < cDistances[v]) {
                        cDistances[v] = cDistances[u] + dist[u][v];
                    }
                }
            }
        }
        return cDistances;
    }
}