package com.mality.emir.controllers;

import com.mality.emir.code.Code;
import com.mality.emir.db.CodeService;
import com.mality.emir.exceptions.CodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/code")
public class WebController {

    private CodeService codeService;

    private final Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/{uuid}")
    public String getCodeByUuid(@PathVariable String uuid, Model model) {
        log.info("Get code by uuid: " + uuid);
        try {
            Code code = codeService.findByUuid(uuid);
            code.setViewsLeft(code.getViewsLeft() - 1);
            codeService.save(code);
            model.addAttribute("codes", List.of(code));
            model.addAttribute("latest", false);
        } catch (CodeNotFoundException e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "code";
    }

    @GetMapping("/latest")
    public String get10LatestNotRestrictedCodes(Model model) {
        model.addAttribute("latest", true);
        List<Code> codes = codeService.getLastTenNotRestrictedCodes();
        codes = codes.stream().peek(code -> code.setViewsLeft(code.getViewsLeft() - 1)).collect(Collectors.toList());
        model.addAttribute("codes", codes);
        return "code";
    }

    @GetMapping("/new")
    public String getNewCodePage() {
        return "code_new";
    }

    @Autowired
    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }
}
