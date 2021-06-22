package com.mality.emir.db;

import com.mality.emir.code.Code;
import com.mality.emir.code.CodeDTO;
import com.mality.emir.exceptions.CodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeService {

    private CodeRepository codeRepository;

    public List<Code> list() {
        return codeRepository.findAll();
    }

    public void save(Code code) {
        codeRepository.save(code);
    }

    public Code findByUuid(String uuid) {
        deleteAllExpired();
        Code code = codeRepository.findByUuid(uuid);
        if (code == null) {
            throw new CodeNotFoundException("Code with uuid: \"" + uuid + "\" not found");
        }
        return code;
    }

    public Code findByUuidNotRestricted(String uuid) {
        deleteAllExpired();
        Code code = codeRepository.findByUuid(uuid);
        if (code == null || code.isRestricted()) {
            throw new CodeNotFoundException("Not restricted code with uuid: \"" + uuid + "\" not found");
        }
        return code;
    }

    public void deleteByUuid(String uuid) {
        codeRepository.deleteByUuid(uuid);
    }

    public void deleteAllExpired() {
        List<Code> codes = codeRepository.findAll();
        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i).isExpired()) {
                deleteByUuid(codes.get(i).getUuid());
            }
        }
    }

    public List<CodeDTO> codeToCodeDTO(List<Code> codeList) {
        List<CodeDTO> codeDTOList = new ArrayList<>();
        for (Code code : codeList) {
            codeDTOList.add(new CodeDTO(code.getCode(), code.getDateTime(), code.getTimeLeft(), code.getViewsLeft()));
        }
        return codeDTOList;
    }

    public List<Code> getLastTenNotRestrictedCodes() {
        deleteAllExpired();
        List<Code> codes = list();
        List<Code> list = new ArrayList<>();
        for (int i = codes.size() - 1; i >= 0; i--) {
            if (codes.get(i).isRestricted()) {
                continue;
            }
            list.add(codes.get(i));
            if (list.size() == 10) {
                break;
            }
        }
        return list;
    }

    @Autowired
    public void setCodeRepository(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }
}
