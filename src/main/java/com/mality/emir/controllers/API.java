package com.mality.emir.controllers;

import com.mality.emir.code.Code;
import com.mality.emir.code.CodeDTO;
import com.mality.emir.db.CodeService;
import com.mality.emir.exceptions.CodeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/code")
public class API {

    private CodeService codeService;

    private final Logger log = LoggerFactory.getLogger(API.class);

    @GetMapping("/test")
    public void test() {
        codeService.findByUuid("sadas");
    }

    /**
     * Get code by uuid
     * @param uuid
     * @return
     */
    @GetMapping("/{uuid}")
    public CodeDTO getCodeByUuid(@PathVariable String uuid) {
        try {
            Code code = codeService.findByUuid(uuid);
            code.setViewsLeft(code.getViewsLeft() - 1);
            codeService.save(code);
            CodeDTO codeDTO = new CodeDTO(code.getCode(), code.getDateTime());
            codeDTO.setTime(code.getTimeLeft());
            if (code.getViewsLeft() >= 0) {
                codeDTO.setViews(code.getViewsLeft());
            }
            return codeDTO;
        } catch (CodeNotFoundException e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get last 10 codes without any restriction
     */
    @GetMapping("/latest")
    public List<CodeDTO> get10LatestNotRestrictedCodes() {
        List<Code> codes = codeService.getLastTenNotRestrictedCodes();
        codes = codes.stream().peek(code -> code.setViewsLeft(code.getViewsLeft() - 1)).collect(Collectors.toList());
        List<Code> list = new ArrayList<>(codes);
        return codeService.codeToCodeDTO(list);
    }

    /**
     * Create new code
     * @param code
     * @return
     */
    @PostMapping("/new")
    public String postNewCode(@RequestBody CodeDTO code) {
        log.info("Post new code");
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(code.getTime() + " " + code.getViews());
        Code newCode = new Code(code.getCode(), dateTime, code.getTime(), code.getViews());
        codeService.save(newCode);
        return "{ \"id\": \"" + newCode.getUuid() + "\" }";
    }

    @Autowired
    public void setCodeService(CodeService codeService) {
        this.codeService = codeService;
    }
}
