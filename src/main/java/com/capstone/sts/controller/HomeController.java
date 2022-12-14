package com.capstone.sts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/")
    public String home() throws IOException, InterruptedException, TimeoutException {
//        String output = new ProcessExecutor().command("python3", "./model/service.py", "./model/sample_test_files/0QqWIUsiyj3AaPwJD81S.bytes")
//                .readOutput(true).execute()
//                .outputUTF8();
//        System.out.println(output);
        return "home";
    }

}
