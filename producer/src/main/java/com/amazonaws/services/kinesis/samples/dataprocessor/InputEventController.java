/**
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

package com.amazonaws.services.kinesis.samples.dataprocessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.*;

import com.amazonaws.services.kinesis.producer.*;

@RestController
public class InputEventController {

    //Read Config from env variables
    String region = System.getenv("REGION");
    static String streamName = System.getenv("STREAM_NAME");



    private final KinesisProducerConfiguration config = new KinesisProducerConfiguration().setRegion(region);
    private final KinesisProducer kinesis = new KinesisProducer(config);

    //Healthcheck
    @GetMapping(value="/healthcheck")
    public void returnHealthy() {
        //Returns HTTP Status 200 
    }

    //Handler for post requests with String
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void processInputEvent(@RequestBody Map<String, Object> payload) throws UnsupportedEncodingException {

        String element = (String) payload.get("data");
        String key = UUID.randomUUID().toString();
        ByteBuffer data = ByteBuffer.wrap(element.getBytes("UTF-8"));
        kinesis.addUserRecord(streamName, key, data);
        /*
        * You can implement a synchronous or asynchronous response to results
        * https://docs.aws.amazon.com/streams/latest/dev/kinesis-kpl-writing.html 
        */

        return;
    }

    //Handler for post requests with JSON
    @PostMapping(value = "/obj", produces = MediaType.APPLICATION_JSON_VALUE)
    public void processInputJSONEvent(@RequestBody Map<String, Object> payload) throws UnsupportedEncodingException {
        System.out.println("Data=" + payload);
        //JSONObject jsonObj = new JSONObject(inputData);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        JSONObject jsonObject = mapper.readValue(json, JSONObject.class);
//
//        String element = (String) payload.get("data");
//        String key = UUID.randomUUID().toString();
//        ByteBuffer data = ByteBuffer.wrap(element.getBytes("UTF-8"));
//        kinesis.addUserRecord(streamName, key, data);
        /*
         * You can implement a synchronous or asynchronous response to results
         * https://docs.aws.amazon.com/streams/latest/dev/kinesis-kpl-writing.html
         */

        return;
    }
} 