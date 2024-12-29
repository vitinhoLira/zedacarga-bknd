package br.com.zedacarga.zedacarga_api.modelo.payPal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

@Service
public class DistanciaService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public double calcularDistancia(String origem, String destino) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);

        DistanceMatrix result = request.origins(origem).destinations(destino).await();
        long distanciaEmMetros = result.rows[0].elements[0].distance.inMeters;
        context.shutdown();

        return distanciaEmMetros / 1000.0; // Retorna a distância em quilômetros
    }

    public double calcularPreco(double distancia, double precoPorKm) {
        return distancia * precoPorKm;
    }
}

