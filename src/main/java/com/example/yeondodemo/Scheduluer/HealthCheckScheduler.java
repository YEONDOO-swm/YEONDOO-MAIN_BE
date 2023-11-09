package com.example.yeondodemo.Scheduluer;

import com.example.yeondodemo.utils.ConnectPythonServer;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor @Component
public class HealthCheckScheduler {
    @Value("${python.address}")
    String pythonapi;

}
