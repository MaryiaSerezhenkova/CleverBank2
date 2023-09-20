package com.clevertec.controllers;

import com.clevertec.domain.dto.AccountDto;
import com.clevertec.domain.validator.AccountValidator;
import com.clevertec.service.AccountService;
import com.clevertec.service.impl.AccountServiceSingleton;
import com.clevertec.utils.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.modelmapper.ValidationException;


import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
@NoArgsConstructor
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AccountService service = AccountServiceSingleton.getInstance();
    private final AccountValidator validator = new AccountValidator();
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_VERSION = "dt_update";
    private final ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);
        String id = req.getParameter(PARAMETER_ID);
        String userId = req.getParameter("user");
        String bankId = req.getParameter("bank");
        try {

            if (id != null) {
                if (Long.valueOf(id) > 0) {
                    resp.getWriter().write(mapper.writeValueAsString(service.read(Long.valueOf(id))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
            else if(userId !=null) {
                if (Long.valueOf(userId) > 0) {
                    resp.getWriter().write(mapper.writeValueAsString(service.userAccounts(Long.valueOf(userId))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
            else if(bankId !=null) {
                if (Long.valueOf(bankId) > 0) {
                    resp.getWriter().write(mapper.writeValueAsString(service.bankAccounts(Long.valueOf(bankId))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setCharacterEncoding(ENCODING);
            resp.setContentType(CONTENT_TYPE);
            String jsonString = req.getReader().lines().collect(Collectors.joining());
            AccountDto dto = mapper.readValue(jsonString, AccountDto.class);
            try {
                validator.validate(dto);
            } catch (ValidationException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            AccountDto acc = service.create(dto);
            resp.getWriter().write(mapper.writeValueAsString(acc));
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println(e);
        }
    }

}
