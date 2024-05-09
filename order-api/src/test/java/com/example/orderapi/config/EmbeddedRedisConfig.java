package com.example.orderapi.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;
import redis.embedded.exceptions.EmbeddedRedisException;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @Value("${redis.port}")
    private int redisPort;

    @PostConstruct
    public void redisServer() throws IOException {
        int port = isRedisRunning() ? findAvailablePort() : redisPort;

        if(isArmMac()) {
            redisServer = new RedisServer(getRedisFileForArmMac(), port);
        }else{
            redisServer = new RedisServer(port);
        }
        redisServer.start();
    }
    private boolean isArmMac(){
        return Objects.equals(System.getProperty("os.arch"), "aarch64") &&
                Objects.equals(System.getProperty("os.name"), "Mac OS X");
    }
    private File getRedisFileForArmMac() {
        try {
            return new ClassPathResource("binary/redis/redis-server-7.2.3-mac-arm64").getFile();
        } catch (Exception e) {
            throw new EmbeddedRedisException(e.getMessage());
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isRedisRunning() throws IOException {
        return isRunning(excuteGrepProcessCommand(redisPort));
    }

    private int findAvailablePort() throws  IOException{
        for(int port = 10000; port <= 65535; port++){
            Process process = excuteGrepProcessCommand(port);
            if(!isRunning(process)){
                return port;
            }
        }
        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    private Process excuteGrepProcessCommand(int port) throws IOException {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if(os.contains("win")){
            String command = String.format("netstat -nao | find \"LISTEN\" | find \"%d\"", port);
            String[] shell = {"cmd.exe", "/y", "/c", command};
            return Runtime.getRuntime().exec(shell);
        }else if(os.contains("linux") || os.contains("mac")) {
            String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
            String[] shell = {"/bin/sh", "-c", command};
            return Runtime.getRuntime().exec(shell);
        }else{
            throw new RuntimeException("해결되지 않은 OS 입니다. executeGrepProcessCommand() os 명령어 코드를 추가해주세요.");
        }
    }
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception e) {
        }

        return !StringUtils.isEmpty(pidInfo.toString());
    }
}



