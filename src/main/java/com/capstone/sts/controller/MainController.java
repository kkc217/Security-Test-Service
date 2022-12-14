package com.capstone.sts.controller;

import com.capstone.sts.domain.UploadFile;
import com.capstone.sts.service.AwsS3Service;
import com.capstone.sts.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.exec.ProcessExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.capstone.sts.constraint.PathConst.UPLOAD_FILE_DIRECTORY;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UploadFileService uploadFileService;

    private final AwsS3Service awsS3Service;

    @PostMapping("/result")
    public String uploadFile(
            HttpServletResponse response,
            HttpServletRequest request,
            Model model,
            @RequestPart(name = "file") MultipartFile file) throws IOException, InterruptedException, TimeoutException {
//        String path = awsS3Service.uploadFile("file", file.getOriginalFilename(), file);

//        if (Objects.isNull(path)) {
//            response.setContentType("text/html; charset=UTF-8");
//            PrintWriter out = response.getWriter();
//            out.println("<script>alert('테스트에 실패하였습니다.\n다시 시도해주시기 바랍니다.');</script>");
//            return "redirect:/";
//        }
        String path = UPLOAD_FILE_DIRECTORY + file.getOriginalFilename();
        File uploadFile = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        uploadFileService.saveUploadFile(
                UploadFile.builder()
                        .name(file.getOriginalFilename())
                        .path(path)
                        .contentType(file.getContentType())
                        .size(file.getSize())
                        .build());
        String output = new ProcessExecutor().command("python3", "./model/service.py", path)
                .readOutput(true).execute()
                .outputUTF8();
        System.out.println("output: " + output);
        request.setAttribute("result", output);
        model.addAttribute("result", output);
        return "result";
    }

//    @GetMapping("/result")
//    public String resultTemplate(
//            Model model,
//            HttpServletRequest request) {
//        String result = (String) model.getAttribute("result");
//        System.out.println(result);
//        return "result";
//    }

}
