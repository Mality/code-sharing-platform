package com.mality.emir.controllers;

import com.mality.emir.code.Code;
import com.mality.emir.db.CodeService;
import com.mality.emir.exceptions.CodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/code")
public class WebController {

    private CodeService codeService;

    private final Logger log = LoggerFactory.getLogger(WebController.class);

    @GetMapping("/{uuid}")
    public ModelAndView getCodeByUuid(@PathVariable String uuid) {
        log.info("Get code by uuid: " + uuid);
        ModelAndView model = new ModelAndView("code");
        try {
            Code code = codeService.findByUuid(uuid);
            code.setViewsLeft(code.getViewsLeft() - 1);
            codeService.save(code);
            model.addObject("codes", List.of(code));
            model.addObject("latest", false);
        } catch (CodeNotFoundException e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return model;
    }

    @GetMapping("/latest")
    public ModelAndView get10LatestNotRestrictedCodes() {
        ModelAndView model = new ModelAndView("code");
        model.addObject("latest", true);
        List<Code> codes = codeService.getLastTenNotRestrictedCodes();
        codes = codes.stream().peek(code -> code.setViewsLeft(code.getViewsLeft() - 1)).collect(Collectors.toList());
        model.addObject("codes", codes);
        return model;
    }

    @GetMapping("/new")
    public ModelAndView getNewCodePage() {
        return new ModelAndView("code_new");
    }

    @Autowired
    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }
}
