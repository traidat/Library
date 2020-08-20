package Service;

import DAO.*;
import Entity.*;

import java.util.Optional;

public class CodeService {
    private CodeDAO codeDAO = new CodeDAO();

    public Optional<CodeLocation> addCode(String code, String codeDetail) {
        if (codeDAO.addCode(code, codeDetail).isPresent()) {
            return codeDAO.addCode(code, codeDetail);
        } else {
            return Optional.empty();
        }
    }
}
