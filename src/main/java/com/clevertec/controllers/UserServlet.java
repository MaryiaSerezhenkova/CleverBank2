package com.clevertec.controllers;

import com.clevertec.domain.dto.UserDto;
import com.clevertec.domain.validator.UserValidator;
import com.clevertec.service.UserService;
import com.clevertec.service.impl.UserServiceSingleton;
import com.clevertec.utils.JsonConverter;
import com.clevertec.utils.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final UserService userService = UserServiceSingleton.getInstance();
	private final UserValidator userValidator = new UserValidator();
	private static final String CONTENT_TYPE = "application/json";
	private static final String ENCODING = "UTF-8";
	private static final String PARAMETER_ID = "id";
	private static final String PARAMETER_VERSION = "dt_update";
	private final ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
	

	public UserServlet() {
		super();
	}
	

	// 1) Read list
	// 2) Read item (card) need id param
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType(CONTENT_TYPE);
		resp.setCharacterEncoding(ENCODING);
		String id = req.getParameter(PARAMETER_ID);
		try {

			if (id != null) {
				if (Long.valueOf(id) > 0) {
					resp.getWriter().write(mapper.writeValueAsString(userService.read(Long.valueOf(id))));
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
			UserDto dto = mapper.readValue(jsonString, UserDto.class);
			try {
				userValidator.validate(dto);
			} catch (ValidationException e) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			UserDto user = userService.create(dto);
			resp.getWriter().write(mapper.writeValueAsString(user));
			resp.setStatus(HttpServletResponse.SC_CREATED);

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			System.err.println(e);
		}
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setCharacterEncoding(ENCODING);
			resp.setContentType(CONTENT_TYPE);
			String id = req.getParameter(PARAMETER_ID);
			String version = req.getParameter(PARAMETER_VERSION);
			String jsonString = req.getReader().lines().collect(Collectors.joining());
			 
			if (id != null && version != null) {
				UserDto dto = mapper.readValue(jsonString, UserDto.class);
				try {
					userValidator.validate(dto);
				} catch (ValidationException e) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				UserDto p = userService.update(Long.parseLong(id),
						JsonConverter.convert(Long.parseLong(version)), dto);
				resp.getWriter().write(mapper.registerModule(new JavaTimeModule()).writeValueAsString(p));
				resp.setStatus(HttpServletResponse.SC_CREATED);
			} else {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setCharacterEncoding(ENCODING);
			resp.setContentType(CONTENT_TYPE);
			String id = req.getParameter(PARAMETER_ID);
			String version = req.getParameter(PARAMETER_VERSION);
			if (id != null && version != null) {
				userService.delete(Long.parseLong(id), JsonConverter.convert(Long.parseLong(version)));
				resp.setStatus(HttpServletResponse.SC_OK);
			} else {
				resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}