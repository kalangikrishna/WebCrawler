package com.buildit.crawler.service;

import com.buildit.crawler.exception.ClientException;
import com.buildit.crawler.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.tls.Certificates;
import okhttp3.tls.HandshakeCertificates;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClientBuilder {

    private static final String CERTIFICATES_DELIMITER = "-----END CERTIFICATE-----";
    private static final String LOOK_BEHIND_CERT_REGEX = "(?<="+CERTIFICATES_DELIMITER+")";

    public OkHttpClient getSslEnabledHttpclient() throws ClientException {
        HandshakeCertificates.Builder handshakeCertificateBuilder = new HandshakeCertificates.Builder();
        List<X509Certificate> certificates = getCertificateAuthoritiesForSslHandshake();
        certificates.forEach(item -> handshakeCertificateBuilder.addTrustedCertificate(item));

        return new OkHttpClient.Builder()
                .sslSocketFactory(handshakeCertificateBuilder.build().sslSocketFactory(), handshakeCertificateBuilder.build().trustManager())
                .build();
    }

    private List<X509Certificate> getCertificateAuthoritiesForSslHandshake() throws ClientException {
        Resource resource = getCertificatesResource();
        return getCAFromResource(resource);
    }

    private Resource getCertificatesResource() {
        return new ClassPathResource("wipro.cert");
    }

    private List<X509Certificate> getCAFromResource(Resource resource) throws ClientException {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String certifatesListString = FileCopyUtils.copyToString(reader);
            // look behind regex
            return Arrays.stream(certifatesListString.split(LOOK_BEHIND_CERT_REGEX)).map(cert -> Certificates.decodeCertificatePem(cert)).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error preparing Certificate Authorities");
            throw new ClientException("Error preparing Certificate Authorities", e);
        }
    }
}
