package com.capstone.sts.controller;

import com.capstone.sts.domain.History;
import com.capstone.sts.domain.UploadFile;
import com.capstone.sts.dto.HistoryDto;
import com.capstone.sts.service.AwsS3Service;
import com.capstone.sts.service.HistoryService;
import com.capstone.sts.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.exec.ProcessExecutor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static com.capstone.sts.constraint.PathConst.UPLOAD_FILE_DIRECTORY;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UploadFileService uploadFileService;

    private final HistoryService historyService;

    private final AwsS3Service awsS3Service;

    @PostMapping("/result")
    public String uploadFile(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model,
            @RequestPart(name = "file") MultipartFile file) throws IOException, InterruptedException, TimeoutException {
        String path = UPLOAD_FILE_DIRECTORY + file.getOriginalFilename();
        File saveFile = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        UploadFile uploadFile = UploadFile.builder()
                .name(file.getOriginalFilename())
                .path(path)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        uploadFileService.saveUploadFile(
                uploadFile);
        String output = new ProcessExecutor().command("python3", "./model/service.py", path)
                .readOutput(true).execute()
                .outputUTF8();
        request.setAttribute("result", output);
        model.addAttribute("result", output);

        History history = new History(uploadFile, output);
        historyService.saveHistory(history);

        List<Cookie> cookieList = request.getCookies() == null ? new ArrayList<>() : Arrays.asList(request.getCookies());

        List<HistoryDto> historyList = new ArrayList<>();
        for (int i = cookieList.size() - 1; i >= 0; i--) {
            Cookie cookie = cookieList.get(i);

            if (cookie.getName().contains("history")) {
                historyList.add(new HistoryDto(historyService.getHistory(Long.parseLong(cookie.getValue()))));
            }
        }

        model.addAttribute("historyList", historyList);
        System.out.println("history" + cookieList.size());
        Cookie cookie = new Cookie("history" + cookieList.size(), history.getId().toString());
        cookie.setMaxAge(10 * 60);
        response.addCookie(cookie);
        return "result";
    }

}
