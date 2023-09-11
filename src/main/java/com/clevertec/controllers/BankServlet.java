package com.clevertec.controllers;

import com.clevertec.domain.dto.BankDto;
import com.clevertec.domain.validator.BankValidator;
import com.clevertec.service.BankService;
import com.clevertec.service.impl.BankServiceSingleton;
import com.clevertec.utils.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import org.modelmapper.ValidationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "BankServlet", urlPatterns = "/bank")
@NoArgsConstructor
public class BankServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BankService bankService = BankServiceSingleton.getInstance();
    private final BankValidator bankValidator = new BankValidator();
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
        try {

            if (id != null) {
                if (Long.valueOf(id) > 0) {
                    resp.getWriter().write(mapper.writeValueAsString(bankService.read(Long.valueOf(id))));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                resp.getWriter()
                        .write(mapper.registerModule(new JavaTimeModule()).writeValueAsString(bankService.getAll()));
                resp.setStatus(HttpServletResponse.SC_OK);
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
            BankDto dto = mapper.readValue(jsonString, BankDto.class);
            try {
                bankValidator.validate(dto);
            } catch (ValidationException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            BankDto bank = bankService.create(dto);
            resp.getWriter().write(mapper.writeValueAsString(bank));
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println(e);
        }
    }



}
