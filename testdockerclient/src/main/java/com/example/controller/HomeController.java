package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

/**
 * Created by SukkyoSuh on 2017-03-02.
 */
@RestController
@RequestMapping(value = "/")
public class HomeController {

    @Value("${app.server.name}")
    private String serverName;

    @Value("${app.server.port}")
    private String serverPort;

    @RequestMapping(value = "/home")
    public String home(){
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
	restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

	restTemplate.setErrorHandler(new ResponseErrorHandler() {
	    @Override
	    public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
	    }

	    @Override
	    public void handleError(ClientHttpResponse response) throws IOException {
		String result = new BufferedReader(new InputStreamReader(response.getBody())).lines().parallel()
				.collect(Collectors.joining("\n"));
		throw new RestClientException(result);
	    }
	});
	return restTemplate.getForObject("http://" + serverName + "/server", String.class);
    }

    @RequestMapping(value = "/test")
    public String test(){
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
	restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

	restTemplate.setErrorHandler(new ResponseErrorHandler() {
	    @Override
	    public boolean hasError(ClientHttpResponse response) throws IOException {
		return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
	    }

	    @Override
	    public void handleError(ClientHttpResponse response) throws IOException {
		String result = new BufferedReader(new InputStreamReader(response.getBody())).lines().parallel()
				.collect(Collectors.joining("\n"));
		throw new RestClientException(result);
	    }
	});
	return restTemplate.getForObject("http://localhost:8010/server", String.class);
    }
}
