package Service;

import DAO.*;
import Entity.*;

import java.util.Optional;

public class CodeService {
    private CodeDAO codeDAO = new CodeDAO();

    public Optional<CodeLocation> addCode(String code, String codeDetail) {
        Optional<CodeLocation> tempCode = codeDAO.addCode(code, codeDetail);
        if (tempCode.isPresent()) {
            return tempCode;
        } else {
            return Optional.empty();
        }
    }
}
